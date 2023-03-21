package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.GenericTimelineProcess;
import com.mycompany.myapp.repository.GenericTimelineProcessRepository;
import com.mycompany.myapp.repository.GenericTimelineRepository;
import com.mycompany.myapp.service.dto.GenericTimelineProcessDTO;
import com.mycompany.myapp.service.mapper.GenericTimelineProcessMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.akip.domain.ProcessInstance;
import org.akip.service.ProcessInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GenericTimelineProcess}.
 */
@Service
@Transactional
public class GenericTimelineProcessService {

    public static final String BPMN_PROCESS_DEFINITION_ID = "GenericTimelineProcess";

    private final Logger log = LoggerFactory.getLogger(GenericTimelineProcessService.class);

    private final ProcessInstanceService processInstanceService;

    private final GenericTimelineRepository genericTimelineRepository;

    private final GenericTimelineProcessRepository genericTimelineProcessRepository;

    private final GenericTimelineProcessMapper genericTimelineProcessMapper;

    public GenericTimelineProcessService(
        ProcessInstanceService processInstanceService,
        GenericTimelineRepository genericTimelineRepository,
        GenericTimelineProcessRepository genericTimelineProcessRepository,
        GenericTimelineProcessMapper genericTimelineProcessMapper
    ) {
        this.processInstanceService = processInstanceService;
        this.genericTimelineRepository = genericTimelineRepository;
        this.genericTimelineProcessRepository = genericTimelineProcessRepository;
        this.genericTimelineProcessMapper = genericTimelineProcessMapper;
    }

    /**
     * Save a genericTimelineProcess.
     *
     * @param genericTimelineProcessDTO the entity to save.
     * @return the persisted entity.
     */
    public GenericTimelineProcessDTO create(GenericTimelineProcessDTO genericTimelineProcessDTO) {
        log.debug("Request to save GenericTimelineProcess : {}", genericTimelineProcessDTO);

        GenericTimelineProcess genericTimelineProcess = genericTimelineProcessMapper.toEntity(genericTimelineProcessDTO);

        //Saving the domainEntity
        genericTimelineRepository.save(genericTimelineProcess.getGenericTimeline());

        //Creating the process instance in the Camunda and setting it in the process entity
        ProcessInstance processInstance = processInstanceService.create(
            BPMN_PROCESS_DEFINITION_ID,
            "GenericTimeline#" + genericTimelineProcess.getGenericTimeline().getId(),
            genericTimelineProcess
        );
        genericTimelineProcess.setProcessInstance(processInstance);

        //Saving the process entity
        genericTimelineProcess = genericTimelineProcessRepository.save(genericTimelineProcess);
        return genericTimelineProcessMapper.toDto(genericTimelineProcess);
    }

    /**
     * Get all the genericTimelineProcesss.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GenericTimelineProcessDTO> findAll() {
        log.debug("Request to get all GenericTimelineProcesss");
        return genericTimelineProcessRepository
            .findAll()
            .stream()
            .map(genericTimelineProcessMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one genericTimelineProcess by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GenericTimelineProcessDTO> findOne(Long id) {
        log.debug("Request to get GenericTimelineProcess : {}", id);
        return genericTimelineProcessRepository.findById(id).map(genericTimelineProcessMapper::toDto);
    }

    /**
     * Get one genericTimelineProcess by id.
     *
     * @param processInstanceId the id of the processInstance associated to the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GenericTimelineProcessDTO> findByProcessInstanceId(Long processInstanceId) {
        log.debug("Request to get GenericTimelineProcess by  processInstanceId: {}", processInstanceId);
        return genericTimelineProcessRepository.findByProcessInstanceId(processInstanceId).map(genericTimelineProcessMapper::toDto);
    }
}
