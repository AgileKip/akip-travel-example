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
public class TaskChooseHotelRoomService {

    private final TaskInstanceService taskInstanceService;

    private final TravelPlanService travelPlanService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final TravelPlanProcessRepository travelPlanProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskChooseHotelRoomMapper taskChooseHotelRoomMapper;

    private final TravelPlanProcessMapper travelPlanProcessMapper;

    public TaskChooseHotelRoomService(
        TaskInstanceService taskInstanceService,
        TravelPlanService travelPlanService,
        TaskInstanceRepository taskInstanceRepository,
        TravelPlanProcessRepository travelPlanProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskChooseHotelRoomMapper taskChooseHotelRoomMapper,
        TravelPlanProcessMapper travelPlanProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.travelPlanService = travelPlanService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.travelPlanProcessRepository = travelPlanProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskChooseHotelRoomMapper = taskChooseHotelRoomMapper;
        this.travelPlanProcessMapper = travelPlanProcessMapper;
    }

    public TaskChooseHotelRoomContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        TravelPlanProcessDTO travelPlanProcess = travelPlanProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskChooseHotelRoomMapper::toTravelPlanProcessDTO)
            .orElseThrow();

        TaskChooseHotelRoomContextDTO taskChooseHotelRoomContext = new TaskChooseHotelRoomContextDTO();
        taskChooseHotelRoomContext.setTaskInstance(taskInstanceDTO);
        taskChooseHotelRoomContext.setTravelPlanProcess(travelPlanProcess);

        return taskChooseHotelRoomContext;
    }

    public TaskChooseHotelRoomContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskChooseHotelRoomContextDTO taskChooseHotelRoomContext) {
        TravelPlanDTO travelPlanDTO = travelPlanService
            .findOne(taskChooseHotelRoomContext.getTravelPlanProcess().getTravelPlan().getId())
            .orElseThrow();
        travelPlanDTO.setName(taskChooseHotelRoomContext.getTravelPlanProcess().getTravelPlan().getName());
        travelPlanDTO.setStartDate(taskChooseHotelRoomContext.getTravelPlanProcess().getTravelPlan().getStartDate());
        travelPlanDTO.setEndDate(taskChooseHotelRoomContext.getTravelPlanProcess().getTravelPlan().getEndDate());
        travelPlanDTO.setHotelRoom(taskChooseHotelRoomContext.getTravelPlanProcess().getTravelPlan().getHotelRoom());
        travelPlanService.save(travelPlanDTO);
    }

    public void complete(TaskChooseHotelRoomContextDTO taskChooseHotelRoomContext) {
        save(taskChooseHotelRoomContext);
        TravelPlanProcessDTO travelPlanProcess = travelPlanProcessRepository
            .findByProcessInstanceId(taskChooseHotelRoomContext.getTravelPlanProcess().getProcessInstance().getId())
            .map(travelPlanProcessMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskChooseHotelRoomContext.getTaskInstance(), travelPlanProcess);
    }
}
