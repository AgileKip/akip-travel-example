package com.mycompany.myapp.service;

import com.mycompany.myapp.repository.GenericTimelineProcessRepository;
import com.mycompany.myapp.service.dto.*;
import com.mycompany.myapp.service.mapper.GenericTimelineProcessMapper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.akip.domain.ProcessInstance;
import org.akip.repository.ProcessInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessInstanceTimelineService {

    @Autowired
    private ProcessInstanceRepository processInstanceRepository;

    @Autowired
    private GenericTimelineProcessRepository genericTimelineProcessRepository;

    @Autowired
    private ProcessInstanceTimelineDefinitionService processInstanceTimelineDefinitionService;

    @Autowired
    private GenericTimelineProcessMapper genericTimelineProcessMapper;

    @Autowired
    private ProcessInstanceTimelineExpressionService expressionService;

    //TODO: usar o processInstanceId passado como parametro para recuperar o processInstance
    public ProcessInstanceTimelineDTO getTimeline(Long processInstanceId) {
        ProcessInstance processInstance = processInstanceRepository.findById(processInstanceId).orElseThrow();
        GenericTimelineProcessDTO genericTimelineProcessDTO = genericTimelineProcessRepository
            .findByProcessInstanceId(processInstanceId)
            .map(genericTimelineProcessMapper::toDto)
            .orElseThrow();

        ProcessInstanceTimelineGroupDefinitionDTO timelineGroupDefinition = processInstanceTimelineDefinitionService.getTimelineGroup(
            processInstance.getProcessDefinition().getId()
        );
        ProcessInstanceTimelineDefinitionDTO timelineDefinition = findTimelineDefinition(
            genericTimelineProcessDTO,
            timelineGroupDefinition
        );

        return calculateTimeline(processInstance, timelineDefinition);
    }

    //        GenericTimelineProcessDTO processEntity,
    private ProcessInstanceTimelineDefinitionDTO findTimelineDefinition(
        GenericTimelineProcessDTO processEntity,
        ProcessInstanceTimelineGroupDefinitionDTO timelineGroupDefinition
    ) {
        if (timelineGroupDefinition.getTimelineDefinitions().isEmpty()) {
            throw new RuntimeException("No timeline definition defined");
        }

        if (timelineGroupDefinition.getTimelineDefinitions().size() == 1) {
            return timelineGroupDefinition.getTimelineDefinitions().get(0);
        }

        return defineTimeline(processEntity, timelineGroupDefinition);
    }

    private ProcessInstanceTimelineDTO calculateTimeline(
        ProcessInstance processInstance,
        ProcessInstanceTimelineDefinitionDTO timelineDefinition
    ) {
        ProcessInstanceTimelineDTO timeline = new ProcessInstanceTimelineDTO();
        timeline.setTitle(timelineDefinition.getName());

        timelineDefinition
            .getItems()
            .forEach(
                timelineItemDefinition -> {
                    timeline
                        .getItems()
                        .add(
                            new ProcessInstanceTimelineItemDTO()
                                .title(timelineItemDefinition.getName())
                                .status("WAITING")
                                .icon("clock")
                                .itemDefinition(timelineItemDefinition)
                        );
                }
            );

        calculateStatus(processInstance, timeline);

        return timeline;
    }

    private ProcessInstanceTimelineDefinitionDTO defineTimeline(
        GenericTimelineProcessDTO processEntity,
        ProcessInstanceTimelineGroupDefinitionDTO timelineGroupDefinitionDTO
    ) {
        for (ProcessInstanceTimelineDefinitionDTO t : timelineGroupDefinitionDTO.getTimelineDefinitions()) {
            if (t.getConditionExpression() != null && expressionService.evaluateTimeline(processEntity, t.getConditionExpression())) {
                return t;
            }
        }

        return timelineGroupDefinitionDTO.getTimelineDefinitions().get(0);
    }

    private void calculateStatus(ProcessInstance processInstance, ProcessInstanceTimelineDTO timeline) {
        for (ProcessInstanceTimelineItemDTO timelineItem : timeline.getItems()) {
            calculateStatus(processInstance, timelineItem);
            if (timelineItem.getStatus().equals("WAITING") || timelineItem.getStatus().equals("RUNNING")) {
                return;
            }
        }
    }

    private void calculateStatus(ProcessInstance processInstance, ProcessInstanceTimelineItemDTO timelineItem) {
        if (
            expressionService.evaluateCompleteStatusExpression(processInstance, timelineItem.getItemDefinition().getCheckStatusExpression())
        ) {
            timelineItem.setStatus("COMPLETED");
            timelineItem.setIcon("check");
        } else if (
            expressionService.evaluateRunningStatusExpression(processInstance, timelineItem.getItemDefinition().getCheckStatusExpression())
        ) {
            timelineItem.setStatus("RUNNING");
            timelineItem.setIcon("hourglass");
        }
    }

    private void calculateStatusWithoutParameter(ProcessInstance processInstance, ProcessInstanceTimelineItemDTO timelineItem)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method checkStatusMethod =
            ProcessInstanceTimelineService.class.getMethod(
                    timelineItem.getItemDefinition().getCheckStatusExpression(),
                    ProcessInstance.class,
                    ProcessInstanceTimelineItemDTO.class
                );
        checkStatusMethod.invoke(this, processInstance, timelineItem);
    }

    private void calculateStatusWithParameters(ProcessInstance processInstance, ProcessInstanceTimelineItemDTO timelineItem)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String expression = timelineItem.getItemDefinition().getCheckStatusExpression().split("\\s+")[0];
        String params = timelineItem.getItemDefinition().getCheckStatusExpression().split("\\s+")[1];
        Method checkStatusMethod =
            ProcessInstanceTimelineService.class.getMethod(
                    timelineItem.getItemDefinition().getCheckStatusExpression(),
                    ProcessInstance.class,
                    ProcessInstanceTimelineItemDTO.class,
                    String.class
                );
        checkStatusMethod.invoke(this, processInstance, timelineItem, params);
    }
}
