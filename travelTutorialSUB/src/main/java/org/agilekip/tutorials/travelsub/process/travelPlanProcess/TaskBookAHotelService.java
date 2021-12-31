package org.agilekip.tutorials.travelsub.process.travelPlanProcess;

import org.agilekip.tutorials.travelsub.repository.TravelPlanProcessRepository;
import org.agilekip.tutorials.travelsub.service.TravelPlanService;
import org.agilekip.tutorials.travelsub.service.dto.TravelPlanDTO;
import org.agilekip.tutorials.travelsub.service.dto.TravelPlanProcessDTO;
import org.agilekip.tutorials.travelsub.service.mapper.TravelPlanProcessMapper;
import org.akip.repository.TaskInstanceRepository;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.TaskInstanceMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskBookAHotelService {

    private final TaskInstanceService taskInstanceService;

    private final TravelPlanService travelPlanService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final TravelPlanProcessRepository travelPlanProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskBookAHotelMapper taskBookAHotelMapper;

    private final TravelPlanProcessMapper travelPlanProcessMapper;

    public TaskBookAHotelService(
        TaskInstanceService taskInstanceService,
        TravelPlanService travelPlanService,
        TaskInstanceRepository taskInstanceRepository,
        TravelPlanProcessRepository travelPlanProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskBookAHotelMapper taskBookAHotelMapper,
        TravelPlanProcessMapper travelPlanProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.travelPlanService = travelPlanService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.travelPlanProcessRepository = travelPlanProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskBookAHotelMapper = taskBookAHotelMapper;
        this.travelPlanProcessMapper = travelPlanProcessMapper;
    }

    public TaskBookAHotelContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        TravelPlanProcessDTO travelPlanProcess = travelPlanProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskBookAHotelMapper::toTravelPlanProcessDTO)
            .orElseThrow();

        TaskBookAHotelContextDTO taskBookAHotelContext = new TaskBookAHotelContextDTO();
        taskBookAHotelContext.setTaskInstance(taskInstanceDTO);
        taskBookAHotelContext.setTravelPlanProcess(travelPlanProcess);

        return taskBookAHotelContext;
    }

    public TaskBookAHotelContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskBookAHotelContextDTO taskBookAHotelContext) {
        TravelPlanDTO travelPlanDTO = travelPlanService
            .findOne(taskBookAHotelContext.getTravelPlanProcess().getTravelPlan().getId())
            .orElseThrow();
        travelPlanDTO.setName(taskBookAHotelContext.getTravelPlanProcess().getTravelPlan().getName());
        travelPlanDTO.setStartDate(taskBookAHotelContext.getTravelPlanProcess().getTravelPlan().getStartDate());
        travelPlanDTO.setEndDate(taskBookAHotelContext.getTravelPlanProcess().getTravelPlan().getEndDate());
        travelPlanDTO.setHotelName(taskBookAHotelContext.getTravelPlanProcess().getTravelPlan().getHotelName());
        travelPlanDTO.setHotelBookingNumber(taskBookAHotelContext.getTravelPlanProcess().getTravelPlan().getHotelBookingNumber());
        travelPlanService.save(travelPlanDTO);
    }

    public void complete(TaskBookAHotelContextDTO taskBookAHotelContext) {
        save(taskBookAHotelContext);
        TravelPlanProcessDTO travelPlanProcess = travelPlanProcessRepository
            .findByProcessInstanceId(taskBookAHotelContext.getTravelPlanProcess().getProcessInstance().getId())
            .map(travelPlanProcessMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskBookAHotelContext.getTaskInstance(), travelPlanProcess);
    }
}
