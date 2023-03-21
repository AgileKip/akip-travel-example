package com.mycompany.myapp.process.genericTimelineProcess;

import com.mycompany.myapp.service.dto.GenericTimelineProcessDTO;
import org.akip.service.dto.TaskInstanceDTO;

public class TaskDContextDTO {

    private GenericTimelineProcessDTO genericTimelineProcess;
    private TaskInstanceDTO taskInstance;

    public GenericTimelineProcessDTO getGenericTimelineProcess() {
        return genericTimelineProcess;
    }

    public void setGenericTimelineProcess(GenericTimelineProcessDTO genericTimelineProcess) {
        this.genericTimelineProcess = genericTimelineProcess;
    }

    public TaskInstanceDTO getTaskInstance() {
        return taskInstance;
    }

    public void setTaskInstance(TaskInstanceDTO taskInstance) {
        this.taskInstance = taskInstance;
    }
}
