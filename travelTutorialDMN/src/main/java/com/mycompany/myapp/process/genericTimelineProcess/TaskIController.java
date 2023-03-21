package com.mycompany.myapp.process.genericTimelineProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/generic-timeline-process/task-i")
public class TaskIController {

    private final Logger log = LoggerFactory.getLogger(TaskIController.class);

    private final TaskIService taskIService;

    public TaskIController(TaskIService taskIService) {
        this.taskIService = taskIService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskIContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskIContextDTO taskIContext = taskIService.loadContext(id);
        return ResponseEntity.ok(taskIContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskIContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskIContextDTO taskIContext = taskIService.claim(id);
        return ResponseEntity.ok(taskIContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskIContextDTO taskIContext) {
        log.debug("REST request to complete GenericTimelineProcess.TaskI {}", taskIContext.getTaskInstance().getId());
        taskIService.complete(taskIContext);
        return ResponseEntity.noContent().build();
    }
}
