package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ProcessInstanceTimelineDefinitionDTO;
import com.mycompany.myapp.service.dto.ProcessInstanceTimelineGroupDefinitionDTO;
import com.mycompany.myapp.service.dto.ProcessInstanceTimelineItemDefinitionDTO;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class ProcessInstanceTimelineDefinitionService {

    public ProcessInstanceTimelineGroupDefinitionDTO getTimelineGroup(Long processDefinitionId) {
        ProcessInstanceTimelineGroupDefinitionDTO timelineGroupDefinition = new ProcessInstanceTimelineGroupDefinitionDTO();

        timelineGroupDefinition.getTimelineDefinitions().add(createMainTimeLine());

        timelineGroupDefinition.getTimelineDefinitions().add(createAlternativeTimeLine());

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

        timelineDefinition
            .getItems()
            .add(new ProcessInstanceTimelineItemDefinitionDTO().step(2).name("Task A").checkStatusExpression("TaskA"));

        timelineDefinition
            .getItems()
            .add(new ProcessInstanceTimelineItemDefinitionDTO().step(3).name("Task B/C").checkStatusExpression("TaskB and TaskC"));

        timelineDefinition
            .getItems()
            .add(new ProcessInstanceTimelineItemDefinitionDTO().step(4).name("Task E/F").checkStatusExpression("TaskE or TaskF"));

        timelineDefinition
            .getItems()
            .add(new ProcessInstanceTimelineItemDefinitionDTO().step(5).name("Task G").checkStatusExpression("TaskG"));

        timelineDefinition
            .getItems()
            .add(new ProcessInstanceTimelineItemDefinitionDTO().step(6).name("Task I").checkStatusExpression("TaskI"));

        timelineDefinition
            .getItems()
            .add(new ProcessInstanceTimelineItemDefinitionDTO().step(7).name("Task J").checkStatusExpression("TaskJ"));

        //process started
        timelineDefinition
            .getItems()
            .add(
                new ProcessInstanceTimelineItemDefinitionDTO()
                    .step(5)
                    .name("Generic Timeline finished")
                    .checkStatusExpression("processInstanceCompleted")
            );

        return timelineDefinition;
    }

    private ProcessInstanceTimelineDefinitionDTO createAlternativeTimeLine() {
        ProcessInstanceTimelineDefinitionDTO timelineDefinition = new ProcessInstanceTimelineDefinitionDTO();
        timelineDefinition.setName("Alternative Timeline");
        timelineDefinition.setConditionExpression("processEntity.genericTimeline.needTaskH");

        //process started
        timelineDefinition
            .getItems()
            .add(
                new ProcessInstanceTimelineItemDefinitionDTO()
                    .step(1)
                    .name("Form submitted")
                    .checkStatusExpression("processInstanceStarted")
            );

        //task A
        timelineDefinition
            .getItems()
            .add(new ProcessInstanceTimelineItemDefinitionDTO().step(2).name("Task A").checkStatusExpression("TaskA"));

        //task B and C
        timelineDefinition
            .getItems()
            .add(new ProcessInstanceTimelineItemDefinitionDTO().step(3).name("Task B/C").checkStatusExpression("TaskB and TaskC"));

        //task E and F
        timelineDefinition
            .getItems()
            .add(new ProcessInstanceTimelineItemDefinitionDTO().step(4).name("Task E/F").checkStatusExpression("TaskE or TaskF"));

        //task G
        timelineDefinition
            .getItems()
            .add(new ProcessInstanceTimelineItemDefinitionDTO().step(4).name("TaskG").checkStatusExpression("TaskG"));

        //task H
        timelineDefinition
            .getItems()
            .add(new ProcessInstanceTimelineItemDefinitionDTO().step(5).name("TaskH").checkStatusExpression("TaskH"));

        //task I
        timelineDefinition
            .getItems()
            .add(new ProcessInstanceTimelineItemDefinitionDTO().step(6).name("TaskI").checkStatusExpression("TaskI"));

        //task J
        timelineDefinition
            .getItems()
            .add(new ProcessInstanceTimelineItemDefinitionDTO().step(7).name("TaskJ").checkStatusExpression("TaskJ"));

        //process started
        timelineDefinition
            .getItems()
            .add(
                new ProcessInstanceTimelineItemDefinitionDTO()
                    .step(8)
                    .name("Generic process finished")
                    .checkStatusExpression("processInstanceCompleted")
            );

        return timelineDefinition;
    }
}
