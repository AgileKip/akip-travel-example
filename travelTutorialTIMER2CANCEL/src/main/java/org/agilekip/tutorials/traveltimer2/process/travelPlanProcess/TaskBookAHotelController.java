package org.agilekip.tutorials.traveltimer2.process.travelPlanProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/travel-plan-process/task-book-a-hotel")
public class TaskBookAHotelController {

    private final Logger log = LoggerFactory.getLogger(TaskBookAHotelController.class);

    private final TaskBookAHotelService taskBookAHotelService;

    public TaskBookAHotelController(TaskBookAHotelService taskBookAHotelService) {
        this.taskBookAHotelService = taskBookAHotelService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskBookAHotelContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskBookAHotelContextDTO taskBookAHotelContext = taskBookAHotelService.loadContext(id);
        return ResponseEntity.ok(taskBookAHotelContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskBookAHotelContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskBookAHotelContextDTO taskBookAHotelContext = taskBookAHotelService.claim(id);
        return ResponseEntity.ok(taskBookAHotelContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskBookAHotelContextDTO taskBookAHotelContext) {
        log.debug("REST request to complete TravelPlanProcess.TaskBookAHotel {}", taskBookAHotelContext.getTaskInstance().getId());
        taskBookAHotelService.complete(taskBookAHotelContext);
        return ResponseEntity.noContent().build();
    }
}
