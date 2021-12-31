package org.agilekip.tutorials.travelsrv.process.travelPlanProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/travel-plan-process/task-buy-flight-tickets")
public class TaskBuyFlightTicketsController {

    private final Logger log = LoggerFactory.getLogger(TaskBuyFlightTicketsController.class);

    private final TaskBuyFlightTicketsService taskBuyFlightTicketsService;

    public TaskBuyFlightTicketsController(TaskBuyFlightTicketsService taskBuyFlightTicketsService) {
        this.taskBuyFlightTicketsService = taskBuyFlightTicketsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskBuyFlightTicketsContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskBuyFlightTicketsContextDTO taskBuyFlightTicketsContext = taskBuyFlightTicketsService.loadContext(id);
        return ResponseEntity.ok(taskBuyFlightTicketsContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskBuyFlightTicketsContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskBuyFlightTicketsContextDTO taskBuyFlightTicketsContext = taskBuyFlightTicketsService.claim(id);
        return ResponseEntity.ok(taskBuyFlightTicketsContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskBuyFlightTicketsContextDTO taskBuyFlightTicketsContext) {
        log.debug(
            "REST request to complete TravelPlanProcess.TaskBuyFlightTickets {}",
            taskBuyFlightTicketsContext.getTaskInstance().getId()
        );
        taskBuyFlightTicketsService.complete(taskBuyFlightTicketsContext);
        return ResponseEntity.noContent().build();
    }
}
