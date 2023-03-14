package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.ProcessInstanceTimelineService;
import com.mycompany.myapp.service.dto.ProcessInstanceTimelineItemDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProcessInstanceTimelineController {

    private final Logger log = LoggerFactory.getLogger(ProcessInstanceTimelineController.class);

    @Autowired
    ProcessInstanceTimelineService processInstanceTimelineService;

    @GetMapping("/timeline")
    public List<ProcessInstanceTimelineItemDTO> getBPMNInfo() {
        //TODO: passar o processInstanceId como parametro para esse controller...
        return processInstanceTimelineService.getTimeline(1L).getItems();
    }

}
