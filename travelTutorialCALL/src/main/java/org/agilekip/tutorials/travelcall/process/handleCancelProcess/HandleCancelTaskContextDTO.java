package org.agilekip.tutorials.travelcall.process.handleCancelProcess;

import org.agilekip.tutorials.travelcall.service.dto.HandleCancelProcessDTO;
import org.akip.service.dto.TaskInstanceDTO;

public class HandleCancelTaskContextDTO {

    private HandleCancelProcessDTO handleCancelProcess;
    private TaskInstanceDTO taskInstance;

    public HandleCancelProcessDTO getHandleCancelProcess() {
        return handleCancelProcess;
    }

    public void setHandleCancelProcess(HandleCancelProcessDTO handleCancelProcess) {
        this.handleCancelProcess = handleCancelProcess;
    }

    public TaskInstanceDTO getTaskInstance() {
        return taskInstance;
    }

    public void setTaskInstance(TaskInstanceDTO taskInstance) {
        this.taskInstance = taskInstance;
    }
}
