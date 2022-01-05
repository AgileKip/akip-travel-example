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
public class TaskPaymentDetailsService {

    private final TaskInstanceService taskInstanceService;

    private final TravelPlanService travelPlanService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final TravelPlanProcessRepository travelPlanProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskPaymentDetailsMapper taskPaymentDetailsMapper;

    private final TravelPlanProcessMapper travelPlanProcessMapper;

    public TaskPaymentDetailsService(
        TaskInstanceService taskInstanceService,
        TravelPlanService travelPlanService,
        TaskInstanceRepository taskInstanceRepository,
        TravelPlanProcessRepository travelPlanProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskPaymentDetailsMapper taskPaymentDetailsMapper,
        TravelPlanProcessMapper travelPlanProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.travelPlanService = travelPlanService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.travelPlanProcessRepository = travelPlanProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskPaymentDetailsMapper = taskPaymentDetailsMapper;
        this.travelPlanProcessMapper = travelPlanProcessMapper;
    }

    public TaskPaymentDetailsContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        TravelPlanProcessDTO travelPlanProcess = travelPlanProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskPaymentDetailsMapper::toTravelPlanProcessDTO)
            .orElseThrow();

        TaskPaymentDetailsContextDTO taskPaymentDetailsContext = new TaskPaymentDetailsContextDTO();
        taskPaymentDetailsContext.setTaskInstance(taskInstanceDTO);
        taskPaymentDetailsContext.setTravelPlanProcess(travelPlanProcess);

        return taskPaymentDetailsContext;
    }

    public TaskPaymentDetailsContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskPaymentDetailsContextDTO taskPaymentDetailsContext) {
        TravelPlanDTO travelPlanDTO = travelPlanService
            .findOne(taskPaymentDetailsContext.getTravelPlanProcess().getTravelPlan().getId())
            .orElseThrow();
        travelPlanDTO.setName(taskPaymentDetailsContext.getTravelPlanProcess().getTravelPlan().getName());
        travelPlanDTO.setStartDate(taskPaymentDetailsContext.getTravelPlanProcess().getTravelPlan().getStartDate());
        travelPlanDTO.setEndDate(taskPaymentDetailsContext.getTravelPlanProcess().getTravelPlan().getEndDate());
        travelPlanDTO.setPayment(taskPaymentDetailsContext.getTravelPlanProcess().getTravelPlan().getPayment());
        travelPlanService.save(travelPlanDTO);
    }

    public void complete(TaskPaymentDetailsContextDTO taskPaymentDetailsContext) {
        save(taskPaymentDetailsContext);
        TravelPlanProcessDTO travelPlanProcess = travelPlanProcessRepository
            .findByProcessInstanceId(taskPaymentDetailsContext.getTravelPlanProcess().getProcessInstance().getId())
            .map(travelPlanProcessMapper::toDto)
            .orElseThrow();
        taskInstanceService.complete(taskPaymentDetailsContext.getTaskInstance(), travelPlanProcess);
    }
}
