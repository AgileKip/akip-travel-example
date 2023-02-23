package com.mycompany.myapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mycompany.myapp.domain.DecisionDefinition;
import com.mycompany.myapp.domain.DecisionDeployment;
import com.mycompany.myapp.domain.enumeration.StatusDecisionDeployment;
import com.mycompany.myapp.repository.DecisionDeploymentRepository;
import com.mycompany.myapp.service.dto.DecisionDefinitionDTO;
import com.mycompany.myapp.service.dto.DecisionDeploymentDTO;
import com.mycompany.myapp.service.dto.DecisionDeploymentDmnModelDTO;
import com.mycompany.myapp.service.mapper.DecisionDeploymentMapper;
import org.akip.domain.enumeration.StatusProcessDeployment;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.model.bpmn.instance.BusinessRuleTask;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaExecutionListener;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaTaskListener;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.camunda.bpm.model.dmn.instance.Decision;
import org.camunda.bpm.model.dmn.instance.ExtensionElements;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mycompany.myapp.service.util.DateUtils;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class DecisionDeploymentService {
    private final Logger log = LoggerFactory.getLogger(DecisionDeploymentService.class);

    private final DecisionDefinitionService decisionDefinitionService;

    private final DecisionDeploymentRepository decisionDeploymentRepository;

    private final RepositoryService repositoryService;

    private final DecisionDeploymentMapper decisionDeploymentMapper;

    public DecisionDeploymentService(
        DecisionDefinitionService decisionDefinitionService, DecisionDeploymentRepository decisionDeploymentRepository, RepositoryService repositoryService, DecisionDeploymentMapper decisionDeploymentMapper) {
        this.decisionDefinitionService = decisionDefinitionService;
        this.decisionDeploymentRepository = decisionDeploymentRepository;
        this.repositoryService = repositoryService;
        this.decisionDeploymentMapper = decisionDeploymentMapper;
    }

    public DecisionDeploymentDTO deploy(DecisionDeploymentDTO decisionDeploymentDTO) {
        DmnModelInstance dmnModelInstance = Dmn.readModelFromStream(
            new ByteArrayInputStream(decisionDeploymentDTO.getSpecificationFile())
        );
        DecisionDefinition decisionDefinition = decisionDefinitionService.createOrUpdateDecisionDefinition(dmnModelInstance);

        org.camunda.bpm.engine.repository.Deployment camundaDeployment = deployInCamunda(
            decisionDeploymentDTO,
            decisionDefinition,
            dmnModelInstance
        );

        org.camunda.bpm.engine.repository.ProcessDefinition camundaDecisionDefinition = repositoryService
            .createProcessDefinitionQuery()
            .deploymentId(camundaDeployment.getId())
            .singleResult();

        decisionDeploymentDTO.setProps(extractProperties(dmnModelInstance));

        DecisionDeployment decisionDeployment = decisionDeploymentMapper.toEntity(decisionDeploymentDTO);
        decisionDeployment.setDecisionDefinition(decisionDefinition);
        decisionDeployment.setCamundaDeploymentId(camundaDeployment.getId());
        decisionDeployment.setCamundaDecisionDefinitionId(camundaDecisionDefinition.getId());
        decisionDeployment.setStatus(StatusDecisionDeployment.ACTIVE);
        decisionDeployment.setDeployDate(DateUtils.getLocalDateTimeBrt());
        decisionDeployment.setActivationDate(decisionDeployment.getDeployDate());

        inactivePreviousDecisionDeployments(decisionDeployment);

        return decisionDeploymentMapper.toDto(decisionDeploymentRepository.save(decisionDeployment));
    }

    public Optional<DecisionDeploymentDTO> findOne(Long id) {
        return decisionDeploymentRepository.findById(id).map(decisionDeploymentMapper::toDto);
    }

    public Optional<DecisionDeploymentDmnModelDTO> findDmnModel(Long id) {
        return decisionDeploymentRepository.findById(id).map(decisionDeploymentMapper::toDmnModelDto);
    }

    public void active(Long decisionDeploymentId) {
        DecisionDeployment decisionDeployment = decisionDeploymentRepository.findById(decisionDeploymentId).orElseThrow();
        inactivePreviousDecisionDeployments(decisionDeployment);
        decisionDeploymentRepository.updateStatusById(StatusDecisionDeployment.ACTIVE, decisionDeploymentId);
       decisionDeploymentRepository.updateActivationDateById(DateUtils.getLocalDateTimeBrt(), decisionDeploymentId);
    }

    public void inactive(Long decisionDeploymentId) {
        decisionDeploymentRepository.updateStatusById(StatusDecisionDeployment.INACTIVE, decisionDeploymentId);
        decisionDeploymentRepository.updateInactivationDateById(DateUtils.getLocalDateTimeBrt(), decisionDeploymentId);
    }

    public List<DecisionDeploymentDTO> findByDecisionDefinition(String idOrDmnDecisionDefinitionId) {
        DecisionDefinitionDTO decisionDefinitionDTO = decisionDefinitionService
            .findByIdOrDmnDecisionDefinitionId(idOrDmnDecisionDefinitionId)
            .orElseThrow();
        return decisionDeploymentRepository
            .findByDecisionDefinitionId(decisionDefinitionDTO.getId())
            .stream()
            .map(decisionDeploymentMapper::toDto)
            .collect(Collectors.toList());
    }

    public List<DecisionDeploymentDTO> findActiveByDecisionDefinition(String idOrDmnDecisionDefinitionId) {
        DecisionDefinitionDTO decisionDefinitionDTO = decisionDefinitionService
            .findByIdOrDmnDecisionDefinitionId(idOrDmnDecisionDefinitionId)
            .orElseThrow();
        return decisionDeploymentRepository
            .findByDecisionDefinitionIdAndStatusIsActive(decisionDefinitionDTO.getId())
            .stream()
            .map(decisionDeploymentMapper::toDto)
            .collect(Collectors.toList());
    }

    private org.camunda.bpm.engine.repository.Deployment deployInCamunda(
        DecisionDeploymentDTO decisionDeploymentDTO,
        DecisionDefinition decisionDefinition,
        DmnModelInstance dmnModelInstance
    ) {
        configureListeners(dmnModelInstance);
        if (decisionDeploymentDTO.getTenant() == null) {
            return repositoryService
                .createDeployment()
                .addModelInstance(decisionDefinition.getDmnDecisionDefinitionId() + ".dmn", dmnModelInstance)
                .name(decisionDefinition.getDmnDecisionDefinitionId())
                .deploy();
        }

        return repositoryService
            .createDeployment()
            .addModelInstance(decisionDefinition.getDmnDecisionDefinitionId() + ".dmn", dmnModelInstance)
            .name(decisionDefinition.getDmnDecisionDefinitionId())
            .tenantId(decisionDeploymentDTO.getTenant().getIdentifier())
            .deploy();
    }

    private void inactivePreviousDecisionDeployments(DecisionDeployment decisionDeployment) {
        List<DecisionDeployment> previousDecisionDeployments = getActiveDecisionDeployment(decisionDeployment);
        previousDecisionDeployments.forEach(
            previousDecisionDeployment -> {
                inactive(previousDecisionDeployment.getId());
            }
        );
    }

    private List<DecisionDeployment> getActiveDecisionDeployment(DecisionDeployment decisionDeployment) {
        if (decisionDeployment.getTenant() == null) {
            return decisionDeploymentRepository.findByDecisionDefinitionIdAndStatusAndTenantIsNull(
                decisionDeployment.getDecisionDefinition().getId(),
                StatusDecisionDeployment.ACTIVE
            );
        }

        return decisionDeploymentRepository.findByDecisionDefinitionIdAndStatusAndTenantId(
            decisionDeployment.getDecisionDefinition().getId(),
            StatusDecisionDeployment.ACTIVE,
            decisionDeployment.getTenant().getId()
        );
    }

    private Map<String, String> extractProperties(DmnModelInstance modelInstance) {
        ModelElementType decisionType = modelInstance.getModel().getType(Decision.class);
        Decision decision = (Decision) modelInstance.getModelElementsByType(decisionType).iterator().next();

        if (
            decision.getExtensionElements() == null ||
                decision.getExtensionElements().getElementsQuery().filterByType(CamundaProperties.class).count() == 0
        ) {
            return Collections.emptyMap();
        }

        Map<String, String> properties = new HashMap<>();
        CamundaProperties camundaProperties = decision
            .getExtensionElements()
            .getElementsQuery()
            .filterByType(CamundaProperties.class)
            .singleResult();
        camundaProperties
            .getCamundaProperties()
            .forEach(
                camundaProperty -> {
                    properties.put(camundaProperty.getCamundaName(), camundaProperty.getCamundaValue());
                }
            );
        return properties;
    }

    private void configureListeners(DmnModelInstance modelInstance) {
        ModelElementType decisionType = modelInstance.getModel().getType(Decision.class);
        Decision decision = (Decision) modelInstance.getModelElementsByType(decisionType).iterator().next();

        if (decision.getExtensionElements() == null) {
            decision.setExtensionElements(modelInstance.newInstance(ExtensionElements.class));
        }

        {
            CamundaExecutionListener DecisionInstanceEndListener = decision
                .getExtensionElements()
                .addExtensionElement(CamundaExecutionListener.class);
            DecisionInstanceEndListener.setAttributeValue("event", "end");
            DecisionInstanceEndListener.setAttributeValue("delegateExpression", "${camundaProcessInstanceEndListener}");
        }

        ModelElementType businessRuleTaskType = modelInstance.getModel().getType(BusinessRuleTask.class);
        Collection<ModelElementInstance> businessRuleTaskInstances = modelInstance.getModelElementsByType(businessRuleTaskType);

        if (businessRuleTaskInstances == null) {
            return;
        }

        businessRuleTaskInstances
            .stream()
            .forEach(
                modelElementInstance -> {
                    BusinessRuleTask businessRuleTask = (BusinessRuleTask) modelElementInstance;
                    List<ModelElementInstance> listenersToMove = new ArrayList<>();

                    if (businessRuleTask.getExtensionElements() == null) {
                        businessRuleTask.setExtensionElements(modelInstance.newInstance(ExtensionElements.class));
                    }

                    // Remove custom task listeners from the bpmn in order to execute the default listeners first
                    for (ModelElementInstance element : businessRuleTask.getExtensionElements().getElements()) {
                        if (element instanceof CamundaTaskListener) {
                            listenersToMove.add(element);
                            businessRuleTask.getExtensionElements().removeChildElement(element);
                        }
                    }

                    {
                        CamundaTaskListener createListener = businessRuleTask.getExtensionElements().addExtensionElement(CamundaTaskListener.class);
                        createListener.setAttributeValue("event", "create");
                        createListener.setAttributeValue("delegateExpression", "${camundaTaskCreateListener}");
                    }

                    {
                        CamundaTaskListener assigmentListener = businessRuleTask
                            .getExtensionElements()
                            .addExtensionElement(CamundaTaskListener.class);
                        assigmentListener.setAttributeValue("event", "assignment");
                        assigmentListener.setAttributeValue("delegateExpression", "${camundaTaskAssignmentListener}");
                        businessRuleTask.getExtensionElements().getElements().add(assigmentListener);
                    }

                    {
                        CamundaTaskListener completeListener = businessRuleTask
                            .getExtensionElements()
                            .addExtensionElement(CamundaTaskListener.class);
                        completeListener.setAttributeValue("event", "complete");
                        completeListener.setAttributeValue("delegateExpression", "${camundaTaskCompleteListener}");
                        businessRuleTask.getExtensionElements().getElements().add(completeListener);
                    }

                    {
                        CamundaTaskListener deleteListener = businessRuleTask.getExtensionElements().addExtensionElement(CamundaTaskListener.class);
                        deleteListener.setAttributeValue("event", "delete");
                        deleteListener.setAttributeValue("delegateExpression", "${camundaTaskDeleteListener}");
                        businessRuleTask.getExtensionElements().getElements().add(deleteListener);
                    }

                    {
                        CamundaTaskListener updateListener = businessRuleTask.getExtensionElements().addExtensionElement(CamundaTaskListener.class);
                        updateListener.setAttributeValue("event", "update");
                        updateListener.setAttributeValue("delegateExpression", "${camundaTaskUpdateListener}");
                        businessRuleTask.getExtensionElements().getElements().add(updateListener);
                    }

                    // Put back the removed task listeners
                    for (ModelElementInstance listener : listenersToMove) {
                        businessRuleTask.getExtensionElements().addChildElement(listener);
                    }
                }
            );
    }

    public void saveProperties(Long id, Map<String, String> properties) {
        try {
            String propertiesAsString = decisionDeploymentMapper.mapToString(properties);
            decisionDeploymentRepository.updatePropertiesById(propertiesAsString, id);
        } catch (JsonProcessingException e) {
            throw new BadRequestErrorException(e.getMessage());
        }
    }
}
