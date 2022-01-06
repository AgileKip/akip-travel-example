package org.agilekip.tutorials.travelcomplete.process.travelPlanProcess;

import org.agilekip.tutorials.travelcomplete.repository.TravelPlanProcessRepository;
import org.agilekip.tutorials.travelcomplete.service.TravelPlanService;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelPlanDTO;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelPlanProcessDTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.TravelPlanProcessMapper;
import org.akip.repository.TaskInstanceRepository;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.TaskInstanceMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskChooseCarService {

    private final TaskInstanceService taskInstanceService;

    private final TravelPlanService travelPlanService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final TravelPlanProcessRepository travelPlanProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskChooseCarMapper taskChooseCarMapper;

    private final TravelPlanProcessMapper travelPlanProcessMapper;

    public TaskChooseCarService(
        TaskInstanceService taskInstanceService,
        TravelPlanService travelPlanService,
        TaskInstanceRepository taskInstanceRepository,
        TravelPlanProcessRepository travelPlanProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskChooseCarMapper taskChooseCarMapper,
        TravelPlanProcessMapper travelPlanProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.travelPlanService = travelPlanService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.travelPlanProcessRepository = travelPlanProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskChooseCarMapper = taskChooseCarMapper;
        this.travelPlanProcessMapper = travelPlanProcessMapper;
    }

    public TaskChooseCarContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        TravelPlanProcessDTO travelPlanProcess = travelPlanProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskChooseCarMapper::toTravelPlanProcessDTO)
            .orElseThrow();

        TaskChooseCarContextDTO taskChooseCarContext = new TaskChooseCarContextDTO();
        taskChooseCarContext.setTaskInstance(taskInstanceDTO);
        taskChooseCarContext.setTravelPlanProcess(travelPlanProcess);

        return taskChooseCarContext;
    }

    public TaskChooseCarContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskChooseCarContextDTO taskChooseCarContext) {
        TravelPlanDTO travelPlanDTO = travelPlanService
            .findOne(taskChooseCarContext.getTravelPlanProcess().getTravelPlan().getId())
            .orElseThrow();
        travelPlanDTO.setName(taskChooseCarContext.getTravelPlanProcess().getTravelPlan().getName());
        travelPlanDTO.setStartDate(taskChooseCarContext.getTravelPlanProcess().getTravelPlan().getStartDate());
        travelPlanDTO.setEndDate(taskChooseCarContext.getTravelPlanProcess().getTravelPlan().getEndDate());
        travelPlanDTO.setCarStartDate(taskChooseCarContext.getTravelPlanProcess().getTravelPlan().getCarStartDate());
        travelPlanDTO.setCarDuration(taskChooseCarContext.getTravelPlanProcess().getTravelPlan().getCarDuration());
        travelPlanDTO.setCar(taskChooseCarContext.getTravelPlanProcess().getTravelPlan().getCar());
        travelPlanService.save(travelPlanDTO);
    }

    public void complete(TaskChooseCarContextDTO taskChooseCarContext) {
        save(taskChooseCarContext);
        TravelPlanProcessDTO travelPlanProcess = travelPlanProcessRepository
            .findByProcessInstanceId(taskChooseCarContext.getTravelPlanProcess().getProcessInstance().getId())
            .map(travelPlanProcessMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskChooseCarContext.getTaskInstance(), travelPlanProcess);
    }
}
