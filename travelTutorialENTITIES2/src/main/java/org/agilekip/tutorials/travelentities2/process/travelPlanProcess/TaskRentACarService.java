package org.agilekip.tutorials.travelentities2.process.travelPlanProcess;

import org.agilekip.tutorials.travelentities2.repository.TravelPlanProcessRepository;
import org.agilekip.tutorials.travelentities2.service.TravelPlanService;
import org.agilekip.tutorials.travelentities2.service.dto.TravelPlanDTO;
import org.agilekip.tutorials.travelentities2.service.dto.TravelPlanProcessDTO;
import org.agilekip.tutorials.travelentities2.service.mapper.TravelPlanProcessMapper;
import org.akip.repository.TaskInstanceRepository;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.TaskInstanceMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskRentACarService {

    private final TaskInstanceService taskInstanceService;

    private final TravelPlanService travelPlanService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final TravelPlanProcessRepository travelPlanProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskRentACarMapper taskRentACarMapper;

    private final TravelPlanProcessMapper travelPlanProcessMapper;

    public TaskRentACarService(
        TaskInstanceService taskInstanceService,
        TravelPlanService travelPlanService,
        TaskInstanceRepository taskInstanceRepository,
        TravelPlanProcessRepository travelPlanProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskRentACarMapper taskRentACarMapper,
        TravelPlanProcessMapper travelPlanProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.travelPlanService = travelPlanService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.travelPlanProcessRepository = travelPlanProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskRentACarMapper = taskRentACarMapper;
        this.travelPlanProcessMapper = travelPlanProcessMapper;
    }

    public TaskRentACarContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        TravelPlanProcessDTO travelPlanProcess = travelPlanProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskRentACarMapper::toTravelPlanProcessDTO)
            .orElseThrow();

        TaskRentACarContextDTO taskRentACarContext = new TaskRentACarContextDTO();
        taskRentACarContext.setTaskInstance(taskInstanceDTO);
        taskRentACarContext.setTravelPlanProcess(travelPlanProcess);

        return taskRentACarContext;
    }

    public TaskRentACarContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskRentACarContextDTO taskRentACarContext) {
        TravelPlanDTO travelPlanDTO = travelPlanService
            .findOne(taskRentACarContext.getTravelPlanProcess().getTravelPlan().getId())
            .orElseThrow();
        travelPlanDTO.setName(taskRentACarContext.getTravelPlanProcess().getTravelPlan().getName());
        travelPlanDTO.setStartDate(taskRentACarContext.getTravelPlanProcess().getTravelPlan().getStartDate());
        travelPlanDTO.setEndDate(taskRentACarContext.getTravelPlanProcess().getTravelPlan().getEndDate());
        travelPlanDTO.setCarBookingNumber(taskRentACarContext.getTravelPlanProcess().getTravelPlan().getCarBookingNumber());
        travelPlanDTO.setCarRentalCompany(taskRentACarContext.getTravelPlanProcess().getTravelPlan().getCarRentalCompany());
        travelPlanService.save(travelPlanDTO);
    }

    public void complete(TaskRentACarContextDTO taskRentACarContext) {
        save(taskRentACarContext);
        TravelPlanProcessDTO travelPlanProcess = travelPlanProcessRepository
            .findByProcessInstanceId(taskRentACarContext.getTravelPlanProcess().getProcessInstance().getId())
            .map(travelPlanProcessMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskRentACarContext.getTaskInstance(), travelPlanProcess);
    }
}
