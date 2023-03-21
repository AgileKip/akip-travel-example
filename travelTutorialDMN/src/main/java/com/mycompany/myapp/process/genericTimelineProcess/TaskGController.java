package com.mycompany.myapp.process.genericTimelineProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/generic-timeline-process/task-g")
public class TaskGController {

    private final Logger log = LoggerFactory.getLogger(TaskGController.class);

    private final TaskGService taskGService;

    public TaskGController(TaskGService taskGService) {
        this.taskGService = taskGService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskGContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskGContextDTO taskGContext = taskGService.loadContext(id);
        return ResponseEntity.ok(taskGContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskGContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskGContextDTO taskGContext = taskGService.claim(id);
        return ResponseEntity.ok(taskGContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskGContextDTO taskGContext) {
        log.debug("REST request to complete GenericTimelineProcess.TaskG {}", taskGContext.getTaskInstance().getId());
        taskGService.complete(taskGContext);
        return ResponseEntity.noContent().build();
    }
}
