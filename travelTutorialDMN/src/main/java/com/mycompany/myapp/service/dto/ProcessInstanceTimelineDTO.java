package com.mycompany.myapp.service.dto;

import java.util.ArrayList;
import java.util.List;

public class ProcessInstanceTimelineDTO {

    private String title;

    private List<ProcessInstanceTimelineItemDTO> items = new ArrayList<>();


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ProcessInstanceTimelineItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ProcessInstanceTimelineItemDTO> items) {
        this.items = items;
    }
}
