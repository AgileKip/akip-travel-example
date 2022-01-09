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
public class TaskProceedCheckoutService {

    private final TaskInstanceService taskInstanceService;

    private final TravelPlanService travelPlanService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final TravelPlanProcessRepository travelPlanProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskProceedCheckoutMapper taskProceedCheckoutMapper;

    private final TravelPlanProcessMapper travelPlanProcessMapper;

    public TaskProceedCheckoutService(
        TaskInstanceService taskInstanceService,
        TravelPlanService travelPlanService,
        TaskInstanceRepository taskInstanceRepository,
        TravelPlanProcessRepository travelPlanProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskProceedCheckoutMapper taskProceedCheckoutMapper,
        TravelPlanProcessMapper travelPlanProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.travelPlanService = travelPlanService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.travelPlanProcessRepository = travelPlanProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskProceedCheckoutMapper = taskProceedCheckoutMapper;
        this.travelPlanProcessMapper = travelPlanProcessMapper;
    }

    public TaskProceedCheckoutContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        TravelPlanProcessDTO travelPlanProcess = travelPlanProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskProceedCheckoutMapper::toTravelPlanProcessDTO)
            .orElseThrow();

        TaskProceedCheckoutContextDTO taskProceedCheckoutContext = new TaskProceedCheckoutContextDTO();
        taskProceedCheckoutContext.setTaskInstance(taskInstanceDTO);
        taskProceedCheckoutContext.setTravelPlanProcess(travelPlanProcess);

        return taskProceedCheckoutContext;
    }

    public TaskProceedCheckoutContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskProceedCheckoutContextDTO taskProceedCheckoutContext) {
        TravelPlanDTO travelPlanDTO = travelPlanService
            .findOne(taskProceedCheckoutContext.getTravelPlanProcess().getTravelPlan().getId())
            .orElseThrow();
        travelPlanDTO.setName(taskProceedCheckoutContext.getTravelPlanProcess().getTravelPlan().getName());
        travelPlanDTO.setStartDate(taskProceedCheckoutContext.getTravelPlanProcess().getTravelPlan().getStartDate());
        travelPlanDTO.setEndDate(taskProceedCheckoutContext.getTravelPlanProcess().getTravelPlan().getEndDate());
        travelPlanDTO.setPrice(taskProceedCheckoutContext.getTravelPlanProcess().getTravelPlan().getPrice());
        travelPlanDTO.setProceedToCheckOut(taskProceedCheckoutContext.getTravelPlanProcess().getTravelPlan().getProceedToCheckOut());
        travelPlanService.save(travelPlanDTO);
    }

    public void complete(TaskProceedCheckoutContextDTO taskProceedCheckoutContext) {
        save(taskProceedCheckoutContext);
        TravelPlanProcessDTO travelPlanProcess = travelPlanProcessRepository
            .findByProcessInstanceId(taskProceedCheckoutContext.getTravelPlanProcess().getProcessInstance().getId())
            .map(travelPlanProcessMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskProceedCheckoutContext.getTaskInstance(), travelPlanProcess);
    }
}
