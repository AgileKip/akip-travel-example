package com.mycompany.myapp.service.dto;

import java.util.ArrayList;
import java.util.List;

public class ProcessInstanceTimelineDefinitionDTO {

    private String name;

    private String description;

    private String conditionExpression;

    private List<ProcessInstanceTimelineItemDefinitionDTO> items = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConditionExpression() {
        return conditionExpression;
    }

    public void setConditionExpression(String conditionExpression) {
        this.conditionExpression = conditionExpression;
    }

    public List<ProcessInstanceTimelineItemDefinitionDTO> getItems() {
        return items;
    }

    public void setItems(List<ProcessInstanceTimelineItemDefinitionDTO> items) {
        this.items = items;
    }
}
