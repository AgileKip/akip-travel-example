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
public class TaskIService {

    private final TaskInstanceService taskInstanceService;

    private final GenericTimelineService genericTimelineService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final GenericTimelineProcessRepository genericTimelineProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskIMapper taskIMapper;

    private final GenericTimelineProcessMapper genericTimelineProcessMapper;

    public TaskIService(
        TaskInstanceService taskInstanceService,
        GenericTimelineService genericTimelineService,
        TaskInstanceRepository taskInstanceRepository,
        GenericTimelineProcessRepository genericTimelineProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskIMapper taskIMapper,
        GenericTimelineProcessMapper genericTimelineProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.genericTimelineService = genericTimelineService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.genericTimelineProcessRepository = genericTimelineProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskIMapper = taskIMapper;
        this.genericTimelineProcessMapper = genericTimelineProcessMapper;
    }

    public TaskIContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        GenericTimelineProcessDTO genericTimelineProcess = genericTimelineProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskIMapper::toGenericTimelineProcessDTO)
            .orElseThrow();

        TaskIContextDTO taskIContext = new TaskIContextDTO();
        taskIContext.setTaskInstance(taskInstanceDTO);
        taskIContext.setGenericTimelineProcess(genericTimelineProcess);

        return taskIContext;
    }

    public TaskIContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskIContextDTO taskIContext) {
        GenericTimelineDTO genericTimelineDTO = genericTimelineService
            .findOne(taskIContext.getGenericTimelineProcess().getGenericTimeline().getId())
            .orElseThrow();
        genericTimelineDTO.setLoopEnter(taskIContext.getGenericTimelineProcess().getGenericTimeline().getLoopEnter());
        genericTimelineService.save(genericTimelineDTO);
    }

    public void complete(TaskIContextDTO taskIContext) {
        save(taskIContext);
        GenericTimelineProcessDTO genericTimelineProcess = genericTimelineProcessRepository
            .findByProcessInstanceId(taskIContext.getGenericTimelineProcess().getProcessInstance().getId())
            .map(genericTimelineProcessMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskIContext.getTaskInstance(), genericTimelineProcess);
    }
}
