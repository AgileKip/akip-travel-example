package com.mycompany.myapp.process.genericTimelineProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/generic-timeline-process/task-d")
public class TaskDController {

    private final Logger log = LoggerFactory.getLogger(TaskDController.class);

    private final TaskDService taskDService;

    public TaskDController(TaskDService taskDService) {
        this.taskDService = taskDService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskDContextDTO taskDContext = taskDService.loadContext(id);
        return ResponseEntity.ok(taskDContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskDContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskDContextDTO taskDContext = taskDService.claim(id);
        return ResponseEntity.ok(taskDContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskDContextDTO taskDContext) {
        log.debug("REST request to complete GenericTimelineProcess.TaskD {}", taskDContext.getTaskInstance().getId());
        taskDService.complete(taskDContext);
        return ResponseEntity.noContent().build();
    }
}
