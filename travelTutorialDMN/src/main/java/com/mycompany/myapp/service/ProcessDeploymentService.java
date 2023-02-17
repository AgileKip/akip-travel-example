package com.mycompany.myapp.service;

import br.com.loginlogistica.domain.ProcessDefinition;
import br.com.loginlogistica.domain.ProcessDeployment;
import br.com.loginlogistica.domain.enumeration.StatusProcessDeployment;
import br.com.loginlogistica.repository.ProcessDeploymentRepository;
import br.com.loginlogistica.service.dto.ProcessDefinitionDTO;
import br.com.loginlogistica.service.dto.ProcessDeploymentBpmnModelDTO;
import br.com.loginlogistica.service.dto.ProcessDeploymentDTO;
import br.com.loginlogistica.service.mapper.ProcessDeploymentMapper;
import br.com.loginlogistica.service.util.DateUtils;
import br.com.loginlogistica.web.rest.errors.BadRequestErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.stream.Collectors;

import com.mycompany.myapp.domain.ProcessDefinition;
import com.mycompany.myapp.domain.ProcessDeployment;
import com.mycompany.myapp.repository.ProcessDeploymentRepository;
import com.mycompany.myapp.service.dto.ProcessDeploymentDTO;
import org.akip.domain.enumeration.StatusProcessDeployment;
import org.akip.service.dto.ProcessDefinitionDTO;
import org.akip.service.mapper.ProcessDeploymentMapper;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaExecutionListener;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaTaskListener;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProcessDefinition}.
 */
@Service
@Transactional
public class ProcessDeploymentService {

    private final Logger log = LoggerFactory.getLogger(ProcessDeploymentService.class);

    private final ProcessDefinitionService processDefinitionService;

    private final ProcessDeploymentRepository processDeploymentRepository;

    private final RepositoryService repositoryService;

    private final ProcessDeploymentMapper processDeploymentMapper;

    public ProcessDeploymentService(
        ProcessDefinitionService processDefinitionService,
        ProcessDeploymentRepository processDeploymentRepository,
        RepositoryService repositoryService,
        ProcessDeploymentMapper processDeploymentMapper
    ) {
        this.processDefinitionService = processDefinitionService;
        this.processDeploymentRepository = processDeploymentRepository;
        this.repositoryService = repositoryService;
        this.processDeploymentMapper = processDeploymentMapper;
    }

    public ProcessDeploymentDTO deploy(ProcessDeploymentDTO processDeploymentDTO) {
        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(
            new ByteArrayInputStream(processDeploymentDTO.getSpecificationFile())
        );
        ProcessDefinition processDefinition = processDefinitionService.createOrUpdateProcessDefinition(bpmnModelInstance);

        org.camunda.bpm.engine.repository.Deployment camundaDeployment = deployInCamunda(
            processDeploymentDTO,
            processDefinition,
            bpmnModelInstance
        );

        org.camunda.bpm.engine.repository.ProcessDefinition camundaProcessDefinition = repositoryService
            .createProcessDefinitionQuery()
            .deploymentId(camundaDeployment.getId())
            .singleResult();

        processDeploymentDTO.setProps(extractProperties(bpmnModelInstance));

        ProcessDeployment processDeployment = processDeploymentMapper.toEntity(processDeploymentDTO);
        processDeployment.setProcessDefinition(processDefinition);
        processDeployment.setCamundaDeploymentId(camundaDeployment.getId());
        processDeployment.setCamundaProcessDefinitionId(camundaProcessDefinition.getId());
        processDeployment.setStatus(StatusProcessDeployment.ACTIVE);
        processDeployment.setDeployDate(DateUtils.getLocalDateTimeBrt());
        processDeployment.setActivationDate(processDeployment.getDeployDate());

        inactivePreviousProcessDeployments(processDeployment);

        return processDeploymentMapper.toDto(processDeploymentRepository.save(processDeployment));
    }

    public Optional<ProcessDeploymentDTO> findOne(Long id) {
        return processDeploymentRepository.findById(id).map(processDeploymentMapper::toDto);
    }

    public Optional<ProcessDeploymentBpmnModelDTO> findBpmnModel(Long id) {
        return processDeploymentRepository.findById(id).map(processDeploymentMapper::toBpmnModelDto);
    }

    public void active(Long processDeploymentId) {
        ProcessDeployment processDeployment = processDeploymentRepository.findById(processDeploymentId).orElseThrow();
        inactivePreviousProcessDeployments(processDeployment);
        processDeploymentRepository.updateStatusById(StatusProcessDeployment.ACTIVE, processDeploymentId);
        processDeploymentRepository.updateActivationDateById(DateUtils.getLocalDateTimeBrt(), processDeploymentId);
    }

    public void inactive(Long processDeploymentId) {
        processDeploymentRepository.updateStatusById(StatusProcessDeployment.INACTIVE, processDeploymentId);
        processDeploymentRepository.updateInactivationDateById(DateUtils.getLocalDateTimeBrt(), processDeploymentId);
    }

    public List<ProcessDeploymentDTO> findByProcessDefinition(String idOrBpmnProcessDefinitionId) {
        ProcessDefinitionDTO processDefinitionDTO = processDefinitionService
            .findByIdOrBpmnProcessDefinitionId(idOrBpmnProcessDefinitionId)
            .orElseThrow();
        return processDeploymentRepository
            .findByProcessDefinitionId(processDefinitionDTO.getId())
            .stream()
            .map(processDeploymentMapper::toDto)
            .collect(Collectors.toList());
    }

    public List<ProcessDeploymentDTO> findActiveByProcessDefinition(String idOrBpmnProcessDefinitionId) {
        ProcessDefinitionDTO processDefinitionDTO = processDefinitionService
            .findByIdOrBpmnProcessDefinitionId(idOrBpmnProcessDefinitionId)
            .orElseThrow();
        return processDeploymentRepository
            .findByProcessDefinitionIdAndStatusIsActive(processDefinitionDTO.getId())
            .stream()
            .map(processDeploymentMapper::toDto)
            .collect(Collectors.toList());
    }

    private org.camunda.bpm.engine.repository.Deployment deployInCamunda(
        ProcessDeploymentDTO processDeploymentDTO,
        ProcessDefinition processDefinition,
        BpmnModelInstance bpmnModelInstance
    ) {
        configureListeners(bpmnModelInstance);
        if (processDeploymentDTO.getTenant() == null) {
            return repositoryService
                .createDeployment()
                .addModelInstance(processDefinition.getBpmnProcessDefinitionId() + ".bpmn", bpmnModelInstance)
                .name(processDefinition.getBpmnProcessDefinitionId())
                .deploy();
        }

        return repositoryService
            .createDeployment()
            .addModelInstance(processDefinition.getBpmnProcessDefinitionId() + ".bpmn", bpmnModelInstance)
            .name(processDefinition.getBpmnProcessDefinitionId())
            .tenantId(processDeploymentDTO.getTenant().getIdentifier())
            .deploy();
    }

    private void inactivePreviousProcessDeployments(ProcessDeployment processDeployment) {
        List<ProcessDeployment> previousProcessDeployments = getActiveProcessDeployment(processDeployment);
        previousProcessDeployments.forEach(
            previousProcessDeployment -> {
                inactive(previousProcessDeployment.getId());
            }
        );
    }

    private List<ProcessDeployment> getActiveProcessDeployment(ProcessDeployment processDeployment) {
        if (processDeployment.getTenant() == null) {
            return processDeploymentRepository.findByProcessDefinitionIdAndStatusAndTenantIsNull(
                processDeployment.getProcessDefinition().getId(),
                StatusProcessDeployment.ACTIVE
            );
        }

        return processDeploymentRepository.findByProcessDefinitionIdAndStatusAndTenantId(
            processDeployment.getProcessDefinition().getId(),
            StatusProcessDeployment.ACTIVE,
            processDeployment.getTenant().getId()
        );
    }

    private Map<String, String> extractProperties(BpmnModelInstance modelInstance) {
        ModelElementType processType = modelInstance.getModel().getType(Process.class);
        Process process = (Process) modelInstance.getModelElementsByType(processType).iterator().next();

        if (
            process.getExtensionElements() == null ||
            process.getExtensionElements().getElementsQuery().filterByType(CamundaProperties.class).count() == 0
        ) {
            return Collections.emptyMap();
        }

        Map<String, String> properties = new HashMap<>();
        CamundaProperties camundaProperties = process
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

    private void configureListeners(BpmnModelInstance modelInstance) {
        ModelElementType processType = modelInstance.getModel().getType(Process.class);
        Process process = (Process) modelInstance.getModelElementsByType(processType).iterator().next();

        if (process.getExtensionElements() == null) {
            process.setExtensionElements(modelInstance.newInstance(ExtensionElements.class));
        }

        {
            CamundaExecutionListener processInstanceEndListener = process
                .getExtensionElements()
                .addExtensionElement(CamundaExecutionListener.class);
            processInstanceEndListener.setAttributeValue("event", "end");
            processInstanceEndListener.setAttributeValue("delegateExpression", "${camundaProcessInstanceEndListener}");
        }

        ModelElementType userTaskType = modelInstance.getModel().getType(UserTask.class);
        Collection<ModelElementInstance> userTaskInstances = modelInstance.getModelElementsByType(userTaskType);

        if (userTaskInstances == null) {
            return;
        }

        userTaskInstances
            .stream()
            .forEach(
                modelElementInstance -> {
                    UserTask userTask = (UserTask) modelElementInstance;
                    List<ModelElementInstance> listenersToMove = new ArrayList<>();

                    if (userTask.getExtensionElements() == null) {
                        userTask.setExtensionElements(modelInstance.newInstance(ExtensionElements.class));
                    }

                    // Remove custom task listeners from the bpmn in order to execute the default listeners first
                    for (ModelElementInstance element : userTask.getExtensionElements().getElements()) {
                        if (element instanceof CamundaTaskListener) {
                            listenersToMove.add(element);
                            userTask.getExtensionElements().removeChildElement(element);
                        }
                    }

                    {
                        CamundaTaskListener createListener = userTask.getExtensionElements().addExtensionElement(CamundaTaskListener.class);
                        createListener.setAttributeValue("event", "create");
                        createListener.setAttributeValue("delegateExpression", "${camundaTaskCreateListener}");
                    }

                    {
                        CamundaTaskListener assigmentListener = userTask
                            .getExtensionElements()
                            .addExtensionElement(CamundaTaskListener.class);
                        assigmentListener.setAttributeValue("event", "assignment");
                        assigmentListener.setAttributeValue("delegateExpression", "${camundaTaskAssignmentListener}");
                        userTask.getExtensionElements().getElements().add(assigmentListener);
                    }

                    {
                        CamundaTaskListener completeListener = userTask
                            .getExtensionElements()
                            .addExtensionElement(CamundaTaskListener.class);
                        completeListener.setAttributeValue("event", "complete");
                        completeListener.setAttributeValue("delegateExpression", "${camundaTaskCompleteListener}");
                        userTask.getExtensionElements().getElements().add(completeListener);
                    }

                    {
                        CamundaTaskListener deleteListener = userTask.getExtensionElements().addExtensionElement(CamundaTaskListener.class);
                        deleteListener.setAttributeValue("event", "delete");
                        deleteListener.setAttributeValue("delegateExpression", "${camundaTaskDeleteListener}");
                        userTask.getExtensionElements().getElements().add(deleteListener);
                    }

                    {
                        CamundaTaskListener updateListener = userTask.getExtensionElements().addExtensionElement(CamundaTaskListener.class);
                        updateListener.setAttributeValue("event", "update");
                        updateListener.setAttributeValue("delegateExpression", "${camundaTaskUpdateListener}");
                        userTask.getExtensionElements().getElements().add(updateListener);
                    }

                    // Put back the removed task listeners
                    for (ModelElementInstance listener : listenersToMove) {
                        userTask.getExtensionElements().addChildElement(listener);
                    }
                }
            );
    }

    public void saveProperties(Long id, Map<String, String> properties) {
        try {
            String propertiesAsString = processDeploymentMapper.mapToString(properties);
            processDeploymentRepository.updatePropertiesById(propertiesAsString, id);
        } catch (JsonProcessingException e) {
            throw new BadRequestErrorException(e.getMessage());
        }
    }
}
