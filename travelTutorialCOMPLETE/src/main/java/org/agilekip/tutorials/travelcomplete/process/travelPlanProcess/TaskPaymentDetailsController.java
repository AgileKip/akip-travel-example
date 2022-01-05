package org.agilekip.tutorials.travelcomplete.process.travelPlanProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/travel-plan-process/task-payment-details")
public class TaskPaymentDetailsController {

    private final Logger log = LoggerFactory.getLogger(TaskPaymentDetailsController.class);

    private final TaskPaymentDetailsService taskPaymentDetailsService;

    public TaskPaymentDetailsController(TaskPaymentDetailsService taskPaymentDetailsService) {
        this.taskPaymentDetailsService = taskPaymentDetailsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskPaymentDetailsContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskPaymentDetailsContextDTO taskPaymentDetailsContext = taskPaymentDetailsService.loadContext(id);
        return ResponseEntity.ok(taskPaymentDetailsContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskPaymentDetailsContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskPaymentDetailsContextDTO taskPaymentDetailsContext = taskPaymentDetailsService.claim(id);
        return ResponseEntity.ok(taskPaymentDetailsContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskPaymentDetailsContextDTO taskPaymentDetailsContext) {
        log.debug("REST request to complete TravelPlanProcess.TaskPaymentDetails {}", taskPaymentDetailsContext.getTaskInstance().getId());
        taskPaymentDetailsService.complete(taskPaymentDetailsContext);
        return ResponseEntity.noContent().build();
    }
}
