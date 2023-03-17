package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ProcessInstanceTimelineDefinitionDTO;
import com.mycompany.myapp.service.dto.ProcessInstanceTimelineGroupDefinitionDTO;
import com.mycompany.myapp.service.dto.ProcessInstanceTimelineItemDefinitionDTO;
import org.springframework.stereotype.Service;

@Service
public class ProcessInstanceTimelineDefinitionService {

    public ProcessInstanceTimelineGroupDefinitionDTO getTimelineGroup(Long processDefinitionId) {
        ProcessInstanceTimelineGroupDefinitionDTO timelineGroupDefinition = new ProcessInstanceTimelineGroupDefinitionDTO();

        timelineGroupDefinition.getTimelineDefinitions().add(createMainTimeLine());

        return timelineGroupDefinition;
    }

    private ProcessInstanceTimelineDefinitionDTO createMainTimeLine() {
        ProcessInstanceTimelineDefinitionDTO timelineDefinition = new ProcessInstanceTimelineDefinitionDTO();
        timelineDefinition.setName("Generic Trip");

        //process started
        timelineDefinition
            .getItems()
            .add(
                new ProcessInstanceTimelineItemDefinitionDTO()
                    .step(1)
                    .name("Form submitted")
                    .checkStatusExpression("processInstanceStarted")
            );

        //task flight
        timelineDefinition
            .getItems()
            .add(new ProcessInstanceTimelineItemDefinitionDTO().step(2).name("Buy flight tickets").checkStatusExpression("TaskFlight"));

        //task hotel
        timelineDefinition
            .getItems()
            .add(
                new ProcessInstanceTimelineItemDefinitionDTO()
                    .step(3)
                    .name("Book a hotel")
                    .checkStatusExpression("TaskFlight and TaskHotel")
            );

        //task car
        timelineDefinition
            .getItems()
            .add(new ProcessInstanceTimelineItemDefinitionDTO().step(4).name("Book a car").checkStatusExpression("TaskFlight and TaskCar"));

        //task car
        timelineDefinition
            .getItems()
            .add(new ProcessInstanceTimelineItemDefinitionDTO().step(4).name("Validate").checkStatusExpression("TaskFlight and TaskCar"));

        //process started
        timelineDefinition
            .getItems()
            .add(
                new ProcessInstanceTimelineItemDefinitionDTO()
                    .step(5)
                    .name("Travel plan finished")
                    .checkStatusExpression("processInstanceCompleted")
            );

        return timelineDefinition;
    }
}
