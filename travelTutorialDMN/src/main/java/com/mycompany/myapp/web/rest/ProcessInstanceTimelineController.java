package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.TimelineItemsService;
import com.mycompany.myapp.service.dto.TimelineItemDTO;
import java.util.ArrayList;
import java.util.List;
import org.akip.domain.TaskInstance;
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

        timelineItemDTOs.add(createUpdateTimeline(instances, "Choose flight"));
        timelineItemDTOs.add(createUpdateTimeline(instances, "Book a hotel"));
        timelineItemDTOs.add(createUpdateTimeline(instances, "Rent a car"));

        return timelineItemDTOs;
    }

    public TimelineItemDTO createUpdateTimeline(List<TaskInstance> instances, String taskName) {
        TimelineItemDTO timelineItemDTO = new TimelineItemDTO();
        for (TaskInstance taskInstance : instances) {
            if (taskName.equals(taskInstance.getName())) {
                timelineItemDTO.setTitle(taskName);
                timelineItemDTO.setStatus(String.valueOf(taskInstance.getStatus()));
                timelineItemDTO.setIcon(chooseIcon(String.valueOf(taskInstance.getStatus())));
                timelineItemDTO.setCreatedDate(taskInstance.getCreateDate());
                return timelineItemDTO;
            }

            timelineItemDTO.setTitle(taskName);
            timelineItemDTO.setStatus("NEW");
            timelineItemDTO.setIcon(chooseIcon(String.valueOf(timelineItemDTO.getStatus())));
            timelineItemDTO.setCreatedDate(taskInstance.getCreateDate());
        }

        return timelineItemDTO;
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
