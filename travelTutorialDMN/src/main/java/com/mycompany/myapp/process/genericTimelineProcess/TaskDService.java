package com.mycompany.myapp.process.genericTimelineProcess;

import com.mycompany.myapp.repository.GenericTimelineProcessRepository;
import com.mycompany.myapp.service.GenericTimelineService;
import com.mycompany.myapp.service.dto.GenericTimelineDTO;
import com.mycompany.myapp.service.dto.GenericTimelineProcessDTO;
import com.mycompany.myapp.service.mapper.GenericTimelineProcessMapper;
import org.akip.repository.TaskInstanceRepository;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.TaskInstanceMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskDService {

    private final TaskInstanceService taskInstanceService;

    private final GenericTimelineService genericTimelineService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final GenericTimelineProcessRepository genericTimelineProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskDMapper taskDMapper;

    private final GenericTimelineProcessMapper genericTimelineProcessMapper;

    public TaskDService(
        TaskInstanceService taskInstanceService,
        GenericTimelineService genericTimelineService,
        TaskInstanceRepository taskInstanceRepository,
        GenericTimelineProcessRepository genericTimelineProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskDMapper taskDMapper,
        GenericTimelineProcessMapper genericTimelineProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.genericTimelineService = genericTimelineService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.genericTimelineProcessRepository = genericTimelineProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskDMapper = taskDMapper;
        this.genericTimelineProcessMapper = genericTimelineProcessMapper;
    }

    public TaskDContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        GenericTimelineProcessDTO genericTimelineProcess = genericTimelineProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskDMapper::toGenericTimelineProcessDTO)
            .orElseThrow();

        TaskDContextDTO taskDContext = new TaskDContextDTO();
        taskDContext.setTaskInstance(taskInstanceDTO);
        taskDContext.setGenericTimelineProcess(genericTimelineProcess);

        return taskDContext;
    }

    public TaskDContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskDContextDTO taskDContext) {
        GenericTimelineDTO genericTimelineDTO = genericTimelineService
            .findOne(taskDContext.getGenericTimelineProcess().getGenericTimeline().getId())
            .orElseThrow();
        genericTimelineDTO.setChooseTask(taskDContext.getGenericTimelineProcess().getGenericTimeline().getChooseTask());
        genericTimelineService.save(genericTimelineDTO);
    }

    public void complete(TaskDContextDTO taskDContext) {
        save(taskDContext);
        GenericTimelineProcessDTO genericTimelineProcess = genericTimelineProcessRepository
            .findByProcessInstanceId(taskDContext.getGenericTimelineProcess().getProcessInstance().getId())
            .map(genericTimelineProcessMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskDContext.getTaskInstance(), genericTimelineProcess);
    }
}
