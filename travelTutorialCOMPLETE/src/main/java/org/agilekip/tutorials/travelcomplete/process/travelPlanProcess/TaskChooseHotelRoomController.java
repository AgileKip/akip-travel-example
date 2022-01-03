package org.agilekip.tutorials.travelcomplete.process.travelPlanProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/travel-plan-process/task-choose-hotel-room")
public class TaskChooseHotelRoomController {

    private final Logger log = LoggerFactory.getLogger(TaskChooseHotelRoomController.class);

    private final TaskChooseHotelRoomService taskChooseHotelRoomService;

    public TaskChooseHotelRoomController(TaskChooseHotelRoomService taskChooseHotelRoomService) {
        this.taskChooseHotelRoomService = taskChooseHotelRoomService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskChooseHotelRoomContextDTO> loadContext(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskChooseHotelRoomContextDTO taskChooseHotelRoomContext = taskChooseHotelRoomService.loadContext(id);
        return ResponseEntity.ok(taskChooseHotelRoomContext);
    }

    @GetMapping("/{id}/claim")
    public ResponseEntity<TaskChooseHotelRoomContextDTO> claim(@PathVariable Long id) {
        log.debug("REST request to load the context of task hotel {}", id);
        TaskChooseHotelRoomContextDTO taskChooseHotelRoomContext = taskChooseHotelRoomService.claim(id);
        return ResponseEntity.ok(taskChooseHotelRoomContext);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> complete(@RequestBody TaskChooseHotelRoomContextDTO taskChooseHotelRoomContext) {
        log.debug(
            "REST request to complete TravelPlanProcess.TaskChooseHotelRoom {}",
            taskChooseHotelRoomContext.getTaskInstance().getId()
        );
        taskChooseHotelRoomService.complete(taskChooseHotelRoomContext);
        return ResponseEntity.noContent().build();
    }
}
