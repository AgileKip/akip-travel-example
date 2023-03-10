package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.akip.domain.enumeration.StatusProcessInstance;

public class TimelineItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String icon;

    private String status;

    private String title;

    private LocalDateTime createdDate;

    public TimelineItemDTO(Long id, String icon, String status, String title, LocalDateTime createdDate) {
        this.id = id;
        this.icon = icon;
        this.status = status;
        this.title = title;
        this.createdDate = createdDate;
    }

    public TimelineItemDTO() {}

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
}
