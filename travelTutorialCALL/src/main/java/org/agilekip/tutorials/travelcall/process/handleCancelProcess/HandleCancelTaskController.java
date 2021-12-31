package org.agilekip.tutorials.travelcall.process.handleCancelProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/handle-cancel-process/handle-cancel-task")
public class HandleCancelTaskController {

    private final Logger log = LoggerFactory.getLogger(HandleCancelTaskController.class);

    private final HandleCancelTaskService handleCancelTaskService;

    public HandleCancelTaskController(HandleCancelTaskService handleCancelTaskService) {
        this.handleCancelTaskService = handleCancelTaskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<HandleCancelTaskContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        HandleCancelTaskContextDTO handleCancelTaskContext = handleCancelTaskService.loadContext(id);
        return ResponseEntity.ok(handleCancelTaskContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<HandleCancelTaskContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        HandleCancelTaskContextDTO handleCancelTaskContext = handleCancelTaskService.claim(id);
        return ResponseEntity.ok(handleCancelTaskContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody HandleCancelTaskContextDTO handleCancelTaskContext) {
        log.debug("REST request to complete HandleCancelProcess.HandleCancelTask {}", handleCancelTaskContext.getTaskInstance().getId());
        handleCancelTaskService.complete(handleCancelTaskContext);
        return ResponseEntity.noContent().build();
    }
}
