package org.agilekip.tutorials.travelcall.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.agilekip.tutorials.travelcall.domain.HandleCancelProcess;
import org.agilekip.tutorials.travelcall.repository.HandleCancelProcessRepository;
import org.agilekip.tutorials.travelcall.repository.HandleCancelRepository;
import org.agilekip.tutorials.travelcall.service.dto.HandleCancelProcessDTO;
import org.agilekip.tutorials.travelcall.service.mapper.HandleCancelProcessMapper;
import org.akip.domain.ProcessInstance;
import org.akip.service.ProcessInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HandleCancelProcess}.
 */
@Service
@Transactional
public class HandleCancelProcessService {

    public static final String BPMN_PROCESS_DEFINITION_ID = "HandleUserCancel";

    private final Logger log = LoggerFactory.getLogger(HandleCancelProcessService.class);

    private final ProcessInstanceService processInstanceService;

    private final HandleCancelRepository handleCancelRepository;

    private final HandleCancelProcessRepository handleCancelProcessRepository;

    private final HandleCancelProcessMapper handleCancelProcessMapper;

    public HandleCancelProcessService(
        ProcessInstanceService processInstanceService,
        HandleCancelRepository handleCancelRepository,
        HandleCancelProcessRepository handleCancelProcessRepository,
        HandleCancelProcessMapper handleCancelProcessMapper
    ) {
        this.processInstanceService = processInstanceService;
        this.handleCancelRepository = handleCancelRepository;
        this.handleCancelProcessRepository = handleCancelProcessRepository;
        this.handleCancelProcessMapper = handleCancelProcessMapper;
    }

    /**
     * Save a handleCancelProcess.
     *
     * @param handleCancelProcessDTO the entity to save.
     * @return the persisted entity.
     */
    public HandleCancelProcessDTO create(HandleCancelProcessDTO handleCancelProcessDTO) {
        log.debug("Request to save HandleCancelProcess : {}", handleCancelProcessDTO);

        HandleCancelProcess handleCancelProcess = handleCancelProcessMapper.toEntity(handleCancelProcessDTO);

        //Saving the domainEntity
        handleCancelRepository.save(handleCancelProcess.getHandleCancel());

        //Creating the process instance in the Camunda and setting it in the process entity
        ProcessInstance processInstance = processInstanceService.create(
            BPMN_PROCESS_DEFINITION_ID,
            "HandleCancel#" + handleCancelProcess.getHandleCancel().getId(),
            handleCancelProcess
        );
        handleCancelProcess.setProcessInstance(processInstance);

        //Saving the process entity
        handleCancelProcess = handleCancelProcessRepository.save(handleCancelProcess);
        return handleCancelProcessMapper.toDto(handleCancelProcess);
    }

    /**
     * Get all the handleCancelProcesss.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<HandleCancelProcessDTO> findAll() {
        log.debug("Request to get all HandleCancelProcesss");
        return handleCancelProcessRepository
            .findAll()
            .stream()
            .map(handleCancelProcessMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one handleCancelProcess by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HandleCancelProcessDTO> findOne(Long id) {
        log.debug("Request to get HandleCancelProcess : {}", id);
        return handleCancelProcessRepository.findById(id).map(handleCancelProcessMapper::toDto);
    }

    /**
     * Get one handleCancelProcess by id.
     *
     * @param processInstanceId the id of the processInstance associated to the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HandleCancelProcessDTO> findByProcessInstanceId(Long processInstanceId) {
        log.debug("Request to get HandleCancelProcess by  processInstanceId: {}", processInstanceId);
        return handleCancelProcessRepository.findByProcessInstanceId(processInstanceId).map(handleCancelProcessMapper::toDto);
    }
}
