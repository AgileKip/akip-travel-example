package org.agilekip.tutorials.travelcomplete.process.travelPlanProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/travel-plan-process/task-choose-car")
public class TaskChooseCarController {

    private final Logger log = LoggerFactory.getLogger(TaskChooseCarController.class);

    private final TaskChooseCarService taskChooseCarService;

    public TaskChooseCarController(TaskChooseCarService taskChooseCarService) {
        this.taskChooseCarService = taskChooseCarService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskChooseCarContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskChooseCarContextDTO taskChooseCarContext = taskChooseCarService.loadContext(id);
        return ResponseEntity.ok(taskChooseCarContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskChooseCarContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskChooseCarContextDTO taskChooseCarContext = taskChooseCarService.claim(id);
        return ResponseEntity.ok(taskChooseCarContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskChooseCarContextDTO taskChooseCarContext) {
        log.debug("REST request to complete TravelPlanProcess.TaskChooseCar {}", taskChooseCarContext.getTaskInstance().getId());
        taskChooseCarService.complete(taskChooseCarContext);
        return ResponseEntity.noContent().build();
    }
}
