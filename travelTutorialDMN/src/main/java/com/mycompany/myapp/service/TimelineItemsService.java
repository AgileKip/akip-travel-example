package com.mycompany.myapp.service;

import com.mycompany.myapp.repository.TimelineItemsRepository;
import java.util.List;
import org.akip.domain.TaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TimelineItemsService {

    private final Logger log = LoggerFactory.getLogger(TimelineItemsService.class);
    private TimelineItemsRepository timelineItemsRepository;

    public TimelineItemsService(TimelineItemsRepository timelineItemsRepository) {
        this.timelineItemsRepository = timelineItemsRepository;
    }

    /**
     * Get one travelPlanProcess by id.
     *
     * @param processInstanceId the id of the processInstance associated to the entity.
     * @return the entity list.
     */
    @Transactional(readOnly = true)
    public List<TaskInstance> findByProcessInstanceId(Long processInstanceId) {
        log.debug("Request to get TravelPlanProcess by  processInstanceId: {}", processInstanceId);
        return timelineItemsRepository.findByProcessInstanceId(processInstanceId);
    }
}
