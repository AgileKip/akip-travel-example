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
public class TaskGService {

    private final TaskInstanceService taskInstanceService;

    private final GenericTimelineService genericTimelineService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final GenericTimelineProcessRepository genericTimelineProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskGMapper taskGMapper;

    private final GenericTimelineProcessMapper genericTimelineProcessMapper;

    public TaskGService(
        TaskInstanceService taskInstanceService,
        GenericTimelineService genericTimelineService,
        TaskInstanceRepository taskInstanceRepository,
        GenericTimelineProcessRepository genericTimelineProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskGMapper taskGMapper,
        GenericTimelineProcessMapper genericTimelineProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.genericTimelineService = genericTimelineService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.genericTimelineProcessRepository = genericTimelineProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskGMapper = taskGMapper;
        this.genericTimelineProcessMapper = genericTimelineProcessMapper;
    }

    public TaskGContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        GenericTimelineProcessDTO genericTimelineProcess = genericTimelineProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskGMapper::toGenericTimelineProcessDTO)
            .orElseThrow();

        TaskGContextDTO taskGContext = new TaskGContextDTO();
        taskGContext.setTaskInstance(taskInstanceDTO);
        taskGContext.setGenericTimelineProcess(genericTimelineProcess);

        return taskGContext;
    }

    public TaskGContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskGContextDTO taskGContext) {
        GenericTimelineDTO genericTimelineDTO = genericTimelineService
            .findOne(taskGContext.getGenericTimelineProcess().getGenericTimeline().getId())
            .orElseThrow();
        genericTimelineDTO.setNeedTaskH(taskGContext.getGenericTimelineProcess().getGenericTimeline().getNeedTaskH());
        genericTimelineService.save(genericTimelineDTO);
    }

    public void complete(TaskGContextDTO taskGContext) {
        save(taskGContext);
        GenericTimelineProcessDTO genericTimelineProcess = genericTimelineProcessRepository
            .findByProcessInstanceId(taskGContext.getGenericTimelineProcess().getProcessInstance().getId())
            .map(genericTimelineProcessMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskGContext.getTaskInstance(), genericTimelineProcess);
    }
}
