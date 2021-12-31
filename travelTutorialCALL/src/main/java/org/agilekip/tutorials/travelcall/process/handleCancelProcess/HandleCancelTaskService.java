package org.agilekip.tutorials.travelcall.process.handleCancelProcess;

import org.agilekip.tutorials.travelcall.repository.HandleCancelProcessRepository;
import org.agilekip.tutorials.travelcall.service.HandleCancelService;
import org.agilekip.tutorials.travelcall.service.dto.HandleCancelDTO;
import org.agilekip.tutorials.travelcall.service.dto.HandleCancelProcessDTO;
import org.agilekip.tutorials.travelcall.service.mapper.HandleCancelProcessMapper;
import org.akip.repository.TaskInstanceRepository;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.TaskInstanceMapper;
import org.springframework.stereotype.Component;

@Component
public class HandleCancelTaskService {

    private final TaskInstanceService taskInstanceService;

    private final HandleCancelService handleCancelService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final HandleCancelProcessRepository handleCancelProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final HandleCancelTaskMapper handleCancelTaskMapper;

    private final HandleCancelProcessMapper handleCancelProcessMapper;

    public HandleCancelTaskService(
        TaskInstanceService taskInstanceService,
        HandleCancelService handleCancelService,
        TaskInstanceRepository taskInstanceRepository,
        HandleCancelProcessRepository handleCancelProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        HandleCancelTaskMapper handleCancelTaskMapper,
        HandleCancelProcessMapper handleCancelProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.handleCancelService = handleCancelService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.handleCancelProcessRepository = handleCancelProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.handleCancelTaskMapper = handleCancelTaskMapper;
        this.handleCancelProcessMapper = handleCancelProcessMapper;
    }

    public HandleCancelTaskContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        HandleCancelProcessDTO handleCancelProcess = handleCancelProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(handleCancelTaskMapper::toHandleCancelProcessDTO)
            .orElseThrow();

        HandleCancelTaskContextDTO handleCancelTaskContext = new HandleCancelTaskContextDTO();
        handleCancelTaskContext.setTaskInstance(taskInstanceDTO);
        handleCancelTaskContext.setHandleCancelProcess(handleCancelProcess);

        return handleCancelTaskContext;
    }

    public HandleCancelTaskContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(HandleCancelTaskContextDTO handleCancelTaskContext) {
        HandleCancelDTO handleCancelDTO = handleCancelService
            .findOne(handleCancelTaskContext.getHandleCancelProcess().getHandleCancel().getId())
            .orElseThrow();
        handleCancelDTO.setReason(handleCancelTaskContext.getHandleCancelProcess().getHandleCancel().getReason());
        handleCancelService.save(handleCancelDTO);
    }

    public void complete(HandleCancelTaskContextDTO handleCancelTaskContext) {
        save(handleCancelTaskContext);
        HandleCancelProcessDTO handleCancelProcess = handleCancelProcessRepository
            .findByProcessInstanceId(handleCancelTaskContext.getHandleCancelProcess().getProcessInstance().getId())
            .map(handleCancelProcessMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(handleCancelTaskContext.getTaskInstance(), handleCancelProcess);
    }
}
