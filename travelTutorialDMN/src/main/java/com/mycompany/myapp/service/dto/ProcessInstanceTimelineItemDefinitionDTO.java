package com.mycompany.myapp.service.dto;

public class ProcessInstanceTimelineItemDefinitionDTO {

    private int step;

    private String name;

    private String checkStatusExpression;

    public int getStep() {
        return step;
    }

    public ProcessInstanceTimelineItemDefinitionDTO step(int step) {
        this.step = step;
        return this;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getName() {
        return name;
    }

    public ProcessInstanceTimelineItemDefinitionDTO name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCheckStatusExpression() {
        return checkStatusExpression;
    }

    public ProcessInstanceTimelineItemDefinitionDTO checkStatusExpression(String checkStatusExpression) {
        this.checkStatusExpression = checkStatusExpression;
        return this;
    }

    public void setCheckStatusExpression(String checkStatusExpression) {
        this.checkStatusExpression = checkStatusExpression;
    }
}
