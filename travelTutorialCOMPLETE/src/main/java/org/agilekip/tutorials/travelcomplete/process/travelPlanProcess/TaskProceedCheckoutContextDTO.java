package org.agilekip.tutorials.travelcomplete.process.travelPlanProcess;

import org.agilekip.tutorials.travelcomplete.service.dto.TravelPlanProcessDTO;
import org.akip.service.dto.TaskInstanceDTO;

public class TaskProceedCheckoutContextDTO {

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
