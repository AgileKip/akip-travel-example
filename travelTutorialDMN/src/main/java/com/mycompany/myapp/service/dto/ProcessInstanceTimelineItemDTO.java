package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ProcessInstanceTimelineItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String icon;

    private String status;

    private String title;

    private LocalDateTime createdDate;

    private ProcessInstanceTimelineItemDefinitionDTO itemDefinition;

    public ProcessInstanceTimelineItemDTO(Long id, String icon, String status, String title, LocalDateTime createdDate) {
        this.id = id;
        this.icon = icon;
        this.status = status;
        this.title = title;
        this.createdDate = createdDate;
    }

    public ProcessInstanceTimelineItemDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ProcessInstanceTimelineItemDefinitionDTO getItemDefinition() {
        return itemDefinition;
    }

    public void setItemDefinition(ProcessInstanceTimelineItemDefinitionDTO itemDefinition) {
        this.itemDefinition = itemDefinition;
    }

    public ProcessInstanceTimelineItemDTO id(Long id) {
        this.id = id;
        return this;
    }

    public ProcessInstanceTimelineItemDTO icon(String icon) {
        this.icon = icon;
        return this;
    }

    public ProcessInstanceTimelineItemDTO status(String status) {
        this.status = status;
        return this;
    }

    public ProcessInstanceTimelineItemDTO title(String title) {
        this.title = title;
        return this;
    }

    public ProcessInstanceTimelineItemDTO createdDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }


    public ProcessInstanceTimelineItemDTO itemDefinition(ProcessInstanceTimelineItemDefinitionDTO itemDefinition) {
        this.itemDefinition = itemDefinition;
        return this;
    }
}
