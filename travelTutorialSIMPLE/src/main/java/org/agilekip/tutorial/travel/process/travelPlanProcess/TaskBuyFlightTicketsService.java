package org.agilekip.tutorial.travel.process.travelPlanProcess;

import org.agilekip.tutorial.travel.repository.TravelPlanProcessRepository;
import org.agilekip.tutorial.travel.service.TravelPlanService;
import org.agilekip.tutorial.travel.service.dto.TravelPlanDTO;
import org.agilekip.tutorial.travel.service.dto.TravelPlanProcessDTO;
import org.agilekip.tutorial.travel.service.mapper.TravelPlanProcessMapper;
import org.akip.repository.TaskInstanceRepository;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.TaskInstanceMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskBuyFlightTicketsService {

    private final TaskInstanceService taskInstanceService;

    private final TravelPlanService travelPlanService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final TravelPlanProcessRepository travelPlanProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskBuyFlightTicketsMapper taskBuyFlightTicketsMapper;

    private final TravelPlanProcessMapper travelPlanProcessMapper;

    public TaskBuyFlightTicketsService(
        TaskInstanceService taskInstanceService,
        TravelPlanService travelPlanService,
        TaskInstanceRepository taskInstanceRepository,
        TravelPlanProcessRepository travelPlanProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskBuyFlightTicketsMapper taskBuyFlightTicketsMapper,
        TravelPlanProcessMapper travelPlanProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.travelPlanService = travelPlanService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.travelPlanProcessRepository = travelPlanProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskBuyFlightTicketsMapper = taskBuyFlightTicketsMapper;
        this.travelPlanProcessMapper = travelPlanProcessMapper;
    }

    public TaskBuyFlightTicketsContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        TravelPlanProcessDTO travelPlanProcess = travelPlanProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskBuyFlightTicketsMapper::toTravelPlanProcessDTO)
            .orElseThrow();

        TaskBuyFlightTicketsContextDTO taskBuyFlightTicketsContext = new TaskBuyFlightTicketsContextDTO();
        taskBuyFlightTicketsContext.setTaskInstance(taskInstanceDTO);
        taskBuyFlightTicketsContext.setTravelPlanProcess(travelPlanProcess);

        return taskBuyFlightTicketsContext;
    }

    public TaskBuyFlightTicketsContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskBuyFlightTicketsContextDTO taskBuyFlightTicketsContext) {
        TravelPlanDTO travelPlanDTO = travelPlanService
            .findOne(taskBuyFlightTicketsContext.getTravelPlanProcess().getTravelPlan().getId())
            .orElseThrow();
        travelPlanDTO.setName(taskBuyFlightTicketsContext.getTravelPlanProcess().getTravelPlan().getName());
        travelPlanDTO.setStartDate(taskBuyFlightTicketsContext.getTravelPlanProcess().getTravelPlan().getStartDate());
        travelPlanDTO.setEndDate(taskBuyFlightTicketsContext.getTravelPlanProcess().getTravelPlan().getEndDate());
        travelPlanDTO.setAirlineCompanyName(taskBuyFlightTicketsContext.getTravelPlanProcess().getTravelPlan().getAirlineCompanyName());
        travelPlanDTO.setAirlineTicketNumber(taskBuyFlightTicketsContext.getTravelPlanProcess().getTravelPlan().getAirlineTicketNumber());
        travelPlanService.save(travelPlanDTO);
    }

    public void complete(TaskBuyFlightTicketsContextDTO taskBuyFlightTicketsContext) {
        save(taskBuyFlightTicketsContext);
        TravelPlanProcessDTO travelPlanProcess = travelPlanProcessRepository
            .findByProcessInstanceId(taskBuyFlightTicketsContext.getTravelPlanProcess().getProcessInstance().getId())
            .map(travelPlanProcessMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskBuyFlightTicketsContext.getTaskInstance(), travelPlanProcess);
    }
}
