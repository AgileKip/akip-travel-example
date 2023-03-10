package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.dto.TimelineItemDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.akip.domain.ProcessInstance;
import org.akip.domain.enumeration.StatusProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProcessInstanceTimelineController {

    private final Logger log = LoggerFactory.getLogger(ProcessInstanceTimelineController.class);

    @GetMapping("/timeline")
    public List<TimelineItemDTO> getBPMNInfo() {
        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setStatus(StatusProcessInstance.COMPLETED);
        processInstance.setId(2123L);
        processInstance.setStartDate(LocalDateTime.parse("2007-12-03T10:15:30"));
        ProcessInstance processInstance2 = new ProcessInstance();
        processInstance2.setStatus(StatusProcessInstance.RUNNING);
        processInstance2.setId(2123444L);
        processInstance2.setStartDate(LocalDateTime.parse("2022-12-03T10:15:30"));
        ProcessInstance processInstance3 = new ProcessInstance();
        processInstance3.setId(2123455L);
        processInstance3.setStartDate(LocalDateTime.parse("2022-12-03T10:15:30"));

        TimelineItemDTO timelineItemDTO = new TimelineItemDTO(
            processInstance.getId(),
            "",
            String.valueOf(processInstance.getStatus()),
            "Buy Airline ticket",
            processInstance.getStartDate()
        );
        timelineItemDTO.setIcon(chooseIcon(timelineItemDTO));
        TimelineItemDTO timelineItemDTO2 = new TimelineItemDTO(
            processInstance2.getId(),
            "",
            String.valueOf(processInstance2.getStatus()),
            "Book a hotel",
            processInstance2.getStartDate()
        );
        timelineItemDTO2.setIcon(chooseIcon(timelineItemDTO2));
        TimelineItemDTO timelineItemDTO3 = new TimelineItemDTO(
            processInstance3.getId(),
            "",
            String.valueOf("WAITING"),
            "Rent a car",
            processInstance3.getStartDate()
        );
        timelineItemDTO3.setIcon(chooseIcon(timelineItemDTO3));

        List<TimelineItemDTO> timelineItemDTOs = new ArrayList<>();
        timelineItemDTOs.add(timelineItemDTO);
        timelineItemDTOs.add(timelineItemDTO2);
        timelineItemDTOs.add(timelineItemDTO3);
        return timelineItemDTOs;
    }

    public String chooseIcon(TimelineItemDTO timelineItemDTO) {
        if (timelineItemDTO.getStatus().equals("RUNNING")) {
            return "hourglass";
        }
        if (timelineItemDTO.getStatus().equals("COMPLETED")) {
            return "check";
        }
        if (timelineItemDTO.getStatus().equals("TERMINATED")) {
            return "flag-checkered";
        }
        if (timelineItemDTO.getStatus().equals("CANCELED")) {
            return "ban";
        }
        if (timelineItemDTO.getStatus().equals("WAITING")) {
            return "clock";
        }
        return "";
    }
}
