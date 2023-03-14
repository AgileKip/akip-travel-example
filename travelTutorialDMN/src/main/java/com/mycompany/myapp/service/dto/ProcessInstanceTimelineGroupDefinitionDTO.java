package com.mycompany.myapp.service.dto;

import java.util.ArrayList;
import java.util.List;

public class ProcessInstanceTimelineGroupDefinitionDTO {

    List<ProcessInstanceTimelineDefinitionDTO> timelineDefinitions = new ArrayList<>();

    public List<ProcessInstanceTimelineDefinitionDTO> getTimelineDefinitions() {
        return timelineDefinitions;
    }

    public void setTimelineDefinitions(List<ProcessInstanceTimelineDefinitionDTO> timelineDefinitions) {
        this.timelineDefinitions = timelineDefinitions;
    }
}
