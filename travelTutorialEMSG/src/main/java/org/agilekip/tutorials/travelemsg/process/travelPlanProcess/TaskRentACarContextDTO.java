package org.agilekip.tutorials.travelemsg.process.travelPlanProcess;

import org.agilekip.tutorials.travelemsg.service.dto.TravelPlanProcessDTO;
import org.akip.service.dto.TaskInstanceDTO;

public class TaskRentACarContextDTO {

    private TravelPlanProcessDTO travelPlanProcess;
    private TaskInstanceDTO taskInstance;

    public TravelPlanProcessDTO getTravelPlanProcess() {
        return travelPlanProcess;
    }

    public void setTravelPlanProcess(TravelPlanProcessDTO travelPlanProcess) {
        this.travelPlanProcess = travelPlanProcess;
    }

    public TaskInstanceDTO getTaskInstance() {
        return taskInstance;
    }

    public void setTaskInstance(TaskInstanceDTO taskInstance) {
        this.taskInstance = taskInstance;
    }
}
