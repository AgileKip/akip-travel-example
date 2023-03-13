package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.TimelineItemsRepository;
import com.mycompany.myapp.service.TimelineItemsService;
import com.mycompany.myapp.service.dto.TimelineItemDTO;
import java.util.ArrayList;
import java.util.List;
import org.akip.domain.TaskInstance;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProcessInstanceTimelineController {

    private final Logger log = LoggerFactory.getLogger(ProcessInstanceTimelineController.class);
    public TimelineItemsService timelineItemsService;

    public ProcessInstanceTimelineController(TimelineItemsService timelineItemsService) {
        this.timelineItemsService = timelineItemsService;
    }

    @GetMapping("/timeline/{processInstanceId}")
    public List<TimelineItemDTO> getBPMNInfo(@PathVariable Long processInstanceId) {
        List<TaskInstance> instances = timelineItemsService.findByProcessInstanceId(processInstanceId);
        List<TimelineItemDTO> timelineItemDTOs = new ArrayList<>();
        for (TaskInstance taskInstance : instances) {
            TimelineItemDTO timelineItemDTO = new TimelineItemDTO();
            timelineItemDTO.setId(taskInstance.getId());
            timelineItemDTO.setTitle(taskInstance.getName());
            timelineItemDTO.setStatus(String.valueOf(taskInstance.getStatus()));
            timelineItemDTO.setIcon(chooseIcon(String.valueOf(taskInstance.getStatus())));
            timelineItemDTO.setCreatedDate(taskInstance.getCreateDate());
            timelineItemDTOs.add(timelineItemDTO);
        }
        return timelineItemDTOs;
    }

    public String chooseIcon(String timelineItemDTO) {
        if (timelineItemDTO.equals("ASSIGNED")) {
            return "hourglass";
        }
        if (timelineItemDTO.equals("COMPLETED")) {
            return "check";
        }
        if (timelineItemDTO.equals("TERMINATED")) {
            return "flag-checkered";
        }
        if (timelineItemDTO.equals("UNASSIGNED")) {
            return "ban";
        }
        return "clock";
    }
}
