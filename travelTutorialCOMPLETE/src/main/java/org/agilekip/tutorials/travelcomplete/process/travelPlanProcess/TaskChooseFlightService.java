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
public class TaskChooseFlightService {

    private final TaskInstanceService taskInstanceService;

    private final TravelPlanService travelPlanService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final TravelPlanProcessRepository travelPlanProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskChooseFlightMapper taskChooseFlightMapper;

    private final TravelPlanProcessMapper travelPlanProcessMapper;

    public TaskChooseFlightService(
        TaskInstanceService taskInstanceService,
        TravelPlanService travelPlanService,
        TaskInstanceRepository taskInstanceRepository,
        TravelPlanProcessRepository travelPlanProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskChooseFlightMapper taskChooseFlightMapper,
        TravelPlanProcessMapper travelPlanProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.travelPlanService = travelPlanService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.travelPlanProcessRepository = travelPlanProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskChooseFlightMapper = taskChooseFlightMapper;
        this.travelPlanProcessMapper = travelPlanProcessMapper;
    }

    public TaskChooseFlightContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        TravelPlanProcessDTO travelPlanProcess = travelPlanProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskChooseFlightMapper::toTravelPlanProcessDTO)
            .orElseThrow();

        TaskChooseFlightContextDTO taskChooseFlightContext = new TaskChooseFlightContextDTO();
        taskChooseFlightContext.setTaskInstance(taskInstanceDTO);
        taskChooseFlightContext.setTravelPlanProcess(travelPlanProcess);

        return taskChooseFlightContext;
    }

    public TaskChooseFlightContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskChooseFlightContextDTO taskChooseFlightContext) {
        TravelPlanDTO travelPlanDTO = travelPlanService
            .findOne(taskChooseFlightContext.getTravelPlanProcess().getTravelPlan().getId())
            .orElseThrow();
        travelPlanDTO.setName(taskChooseFlightContext.getTravelPlanProcess().getTravelPlan().getName());
        travelPlanDTO.setStartDate(taskChooseFlightContext.getTravelPlanProcess().getTravelPlan().getStartDate());
        travelPlanDTO.setEndDate(taskChooseFlightContext.getTravelPlanProcess().getTravelPlan().getEndDate());
        travelPlanDTO.setFlight(taskChooseFlightContext.getTravelPlanProcess().getTravelPlan().getFlight());
        travelPlanService.save(travelPlanDTO);
    }

    public void complete(TaskChooseFlightContextDTO taskChooseFlightContext) {
        save(taskChooseFlightContext);
        TravelPlanProcessDTO travelPlanProcess = travelPlanProcessRepository
            .findByProcessInstanceId(taskChooseFlightContext.getTravelPlanProcess().getProcessInstance().getId())
            .map(travelPlanProcessMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskChooseFlightContext.getTaskInstance(), travelPlanProcess);
    }
}
