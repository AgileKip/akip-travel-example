package org.agilekip.tutorials.travelcomplete.process.travelPlanProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/travel-plan-process/task-choose-flight")
public class TaskChooseFlightController {

    private final Logger log = LoggerFactory.getLogger(TaskChooseFlightController.class);

    private final TaskChooseFlightService taskChooseFlightService;

    public TaskChooseFlightController(TaskChooseFlightService taskChooseFlightService) {
        this.taskChooseFlightService = taskChooseFlightService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskChooseFlightContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskChooseFlightContextDTO taskChooseFlightContext = taskChooseFlightService.loadContext(id);
        return ResponseEntity.ok(taskChooseFlightContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskChooseFlightContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskChooseFlightContextDTO taskChooseFlightContext = taskChooseFlightService.claim(id);
        return ResponseEntity.ok(taskChooseFlightContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskChooseFlightContextDTO taskChooseFlightContext) {
        log.debug("REST request to complete TravelPlanProcess.TaskChooseFlight {}", taskChooseFlightContext.getTaskInstance().getId());
        taskChooseFlightService.complete(taskChooseFlightContext);
        return ResponseEntity.noContent().build();
    }
}
