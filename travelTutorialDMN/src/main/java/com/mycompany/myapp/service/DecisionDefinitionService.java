package com.mycompany.myapp.service;

import java.util.*;
import java.util.stream.Collectors;

import com.mycompany.myapp.domain.DecisionDefinition;
import com.mycompany.myapp.repository.DecisionDefinitionRepository;
import com.mycompany.myapp.service.dto.DecisionDefinitionDTO;
import com.mycompany.myapp.service.mapper.DecisionDefinitionMapper;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DecisionDefinition}.
 */
@Service
@Transactional
public class DecisionDefinitionService {

    private final Logger log = LoggerFactory.getLogger(DecisionDefinitionService.class);

    private final DecisionDefinitionRepository decisionDefinitionRepository;

    private final DecisionDefinitionMapper decisionDefinitionMapper;

    public DecisionDefinitionService(
        DecisionDefinitionRepository decisionDefinitionRepository, DecisionDefinitionMapper decisionDefinitionMapper) {
        this.decisionDefinitionRepository = decisionDefinitionRepository;
        this.decisionDefinitionMapper = decisionDefinitionMapper;
    }

    public DecisionDefinition createOrUpdateDecisionDefinition(DmnModelInstance dmnModelInstance) {
        Process process = extracAndValidProcessFromModel(dmnModelInstance);
        Optional<DecisionDefinition> optionalProcessDefinition = decisionDefinitionRepository.findByDmnDecisionDefinitionId(process.getId());

        if (optionalProcessDefinition.isPresent()) {
            return updateDecisionDefinition(process);
        }

        return createDecisionDefinition(process);
    }

    private Process extracAndValidProcessFromModel(DmnModelInstance modelInstance) {
        ModelElementType processType = modelInstance.getModel().getType(Process.class);
        Process process = (Process) modelInstance.getModelElementsByType(processType).iterator().next();

        if (!process.isExecutable()) {
            throw new BadRequestErrorException("loginProcessosApp.processDefinition.error.bpmnProcessIsNotExecutable");
        }

        if (StringUtils.isBlank(process.getName())) {
            throw new BadRequestErrorException("loginProcessosApp.processDefinition.error.bpmnNameNotProvided");
        }

        return process;
    }

    private DecisionDefinition createDecisionDefinition(Process process) {
        DecisionDefinition decisionDefinition = new DecisionDefinition();
        decisionDefinition.setDmnDecisionDefinitionId(process.getId());
        decisionDefinition.setName(process.getName());
        if (!process.getDocumentations().isEmpty()) {
            decisionDefinition.setDescription(process.getDocumentations().iterator().next().getRawTextContent());
        }

        return decisionDefinitionRepository.save(decisionDefinition);
    }

    private DecisionDefinition updateDecisionDefinition(Process process) {
        DecisionDefinition decisionDefinition = decisionDefinitionRepository.findByDmnDecisionDefinitionId(process.getId()).orElseThrow();
        decisionDefinition.setName(process.getName());
        if (!process.getDocumentations().isEmpty()) {
            decisionDefinition.setDescription(process.getDocumentations().iterator().next().getRawTextContent());
        }

        return decisionDefinitionRepository.save(decisionDefinition);
    }

    /**
     * Get all the decisionDefinitions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DecisionDefinitionDTO> findAll() {
        log.debug("Request to get all DecisionDefinitions");
        return decisionDefinitionRepository
            .findAll()
            .stream()
            .map(decisionDefinitionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one decisionDefinition by id.
     *
     * @param idOrDmnDecisionDefinitionId the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DecisionDefinitionDTO> findByIdOrDmnDecisionDefinitionId(String idOrDmnDecisionDefinitionId) {
        log.debug("Request to get ProcessDefinition : {}", idOrDmnDecisionDefinitionId);
        if (StringUtils.isNumeric(idOrDmnDecisionDefinitionId)) {
            return decisionDefinitionRepository.findById(Long.parseLong(idOrDmnDecisionDefinitionId)).map(decisionDefinitionMapper::toDto);
        }
        return decisionDefinitionRepository.findByDmnDecisionDefinitionId(idOrDmnDecisionDefinitionId).map(decisionDefinitionMapper::toDto);
    }

    /**
     * Delete the decisionDefinition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DecisionDefinition : {}", id);
        decisionDefinitionRepository.deleteById(id);
    }
}
