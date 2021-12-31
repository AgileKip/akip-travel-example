package org.agilekip.tutorials.travelxor.process.travelPlanProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/travel-plan-process/task-rent-a-car")
public class TaskRentACarController {

    private final Logger log = LoggerFactory.getLogger(TaskRentACarController.class);

    private final TaskRentACarService taskRentACarService;

    public TaskRentACarController(TaskRentACarService taskRentACarService) {
        this.taskRentACarService = taskRentACarService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskRentACarContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskRentACarContextDTO taskRentACarContext = taskRentACarService.loadContext(id);
        return ResponseEntity.ok(taskRentACarContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskRentACarContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskRentACarContextDTO taskRentACarContext = taskRentACarService.claim(id);
        return ResponseEntity.ok(taskRentACarContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskRentACarContextDTO taskRentACarContext) {
        log.debug("REST request to complete TravelPlanProcess.TaskRentACar {}", taskRentACarContext.getTaskInstance().getId());
        taskRentACarService.complete(taskRentACarContext);
        return ResponseEntity.noContent().build();
    }
}
