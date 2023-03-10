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
    public List<TimelineItemDTO> get() {
        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setStatus(StatusProcessInstance.COMPLETED);
        processInstance.setId(2123L);
        processInstance.setStartDate(LocalDateTime.parse("2007-12-03T10:15:30"));

        TimelineItemDTO timelineItemDTO = new TimelineItemDTO();
        timelineItemDTO.setId(processInstance.getId());
        timelineItemDTO.setIcon("check");
        timelineItemDTO.setStatus(String.valueOf(processInstance.getStatus()));
        timelineItemDTO.setTitle("Teste");
        timelineItemDTO.setCreatedDate(processInstance.getStartDate());

        List<TimelineItemDTO> timelineItemDTOs = new ArrayList<>();
        timelineItemDTOs.add(timelineItemDTO);
        return timelineItemDTOs;
    }
}
