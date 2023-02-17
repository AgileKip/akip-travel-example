package com.mycompany.myapp.service;

import br.com.loginlogistica.domain.ProcessDefinition;
import br.com.loginlogistica.domain.enumeration.StatusProcessDefinition;
import br.com.loginlogistica.repository.ProcessDefinitionRepository;
import br.com.loginlogistica.service.dto.ProcessDefinitionDTO;
import br.com.loginlogistica.service.mapper.ProcessDefinitionMapper;
import br.com.loginlogistica.web.rest.errors.BadRequestErrorException;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Process;
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
public class ProcessDefinitionService {

    private final Logger log = LoggerFactory.getLogger(ProcessDefinitionService.class);

    private final ProcessDefinitionRepository processDefinitionRepository;

    private final ProcessDefinitionMapper processDefinitionMapper;

    public ProcessDefinitionService(
        ProcessDefinitionRepository processDefinitionRepository,
        ProcessDefinitionMapper processDefinitionMapper
    ) {
        this.processDefinitionRepository = processDefinitionRepository;
        this.processDefinitionMapper = processDefinitionMapper;
    }

    public ProcessDefinition createOrUpdateProcessDefinition(BpmnModelInstance bpmnModelInstance) {
        Process process = extracAndValidProcessFromModel(bpmnModelInstance);
        Optional<ProcessDefinition> optionalProcessDefinition = processDefinitionRepository.findByBpmnProcessDefinitionId(process.getId());

        if (optionalProcessDefinition.isPresent()) {
            return updateProcessDefinition(process);
        }

        return createProcessDefinition(process);
    }

    private Process extracAndValidProcessFromModel(BpmnModelInstance modelInstance) {
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

    private ProcessDefinition createProcessDefinition(Process process) {
        ProcessDefinition processDefinition = new ProcessDefinition();
        processDefinition.setBpmnProcessDefinitionId(process.getId());
        processDefinition.setName(process.getName());
        processDefinition.setCanBeManuallyStarted(process.isCamundaStartableInTasklist());
        processDefinition.setStatus(StatusProcessDefinition.ACTIVE);
        if (!process.getDocumentations().isEmpty()) {
            processDefinition.setDescription(process.getDocumentations().iterator().next().getRawTextContent());
        }

        return processDefinitionRepository.save(processDefinition);
    }

    private ProcessDefinition updateProcessDefinition(Process process) {
        ProcessDefinition processDefinition = processDefinitionRepository.findByBpmnProcessDefinitionId(process.getId()).orElseThrow();
        processDefinition.setName(process.getName());
        processDefinition.setCanBeManuallyStarted(process.isCamundaStartableInTasklist());
        processDefinition.setStatus(StatusProcessDefinition.ACTIVE);
        if (!process.getDocumentations().isEmpty()) {
            processDefinition.setDescription(process.getDocumentations().iterator().next().getRawTextContent());
        }

        return processDefinitionRepository.save(processDefinition);
    }

    /**
     * Get all the processDefinitions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProcessDefinitionDTO> findAll() {
        log.debug("Request to get all ProcessDefinitions");
        return processDefinitionRepository
            .findAll()
            .stream()
            .map(processDefinitionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one processDefinition by id.
     *
     * @param idOrBpmnProcessDefinitionId the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessDefinitionDTO> findByIdOrBpmnProcessDefinitionId(String idOrBpmnProcessDefinitionId) {
        log.debug("Request to get ProcessDefinition : {}", idOrBpmnProcessDefinitionId);
        if (StringUtils.isNumeric(idOrBpmnProcessDefinitionId)) {
            return processDefinitionRepository.findById(Long.parseLong(idOrBpmnProcessDefinitionId)).map(processDefinitionMapper::toDto);
        }
        return processDefinitionRepository.findByBpmnProcessDefinitionId(idOrBpmnProcessDefinitionId).map(processDefinitionMapper::toDto);
    }

    /**
     * Delete the processDefinition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcessDefinition : {}", id);
        processDefinitionRepository.deleteById(id);
    }
}
