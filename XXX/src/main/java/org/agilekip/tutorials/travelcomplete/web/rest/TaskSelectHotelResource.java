package org.agilekip.tutorials.travelcomplete.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.agilekip.tutorials.travelcomplete.repository.TaskSelectHotelRepository;
import org.agilekip.tutorials.travelcomplete.service.TaskSelectHotelService;
import org.agilekip.tutorials.travelcomplete.service.dto.TaskSelectHotelDTO;
import org.agilekip.tutorials.travelcomplete.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.agilekip.tutorials.travelcomplete.domain.TaskSelectHotel}.
 */
@RestController
@RequestMapping("/api")
public class TaskSelectHotelResource {

    private final Logger log = LoggerFactory.getLogger(TaskSelectHotelResource.class);

    private static final String ENTITY_NAME = "taskSelectHotel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskSelectHotelService taskSelectHotelService;

    private final TaskSelectHotelRepository taskSelectHotelRepository;

    public TaskSelectHotelResource(TaskSelectHotelService taskSelectHotelService, TaskSelectHotelRepository taskSelectHotelRepository) {
        this.taskSelectHotelService = taskSelectHotelService;
        this.taskSelectHotelRepository = taskSelectHotelRepository;
    }

    /**
     * {@code POST  /task-select-hotels} : Create a new taskSelectHotel.
     *
     * @param taskSelectHotelDTO the taskSelectHotelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskSelectHotelDTO, or with status {@code 400 (Bad Request)} if the taskSelectHotel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-select-hotels")
    public ResponseEntity<TaskSelectHotelDTO> createTaskSelectHotel(@RequestBody TaskSelectHotelDTO taskSelectHotelDTO)
        throws URISyntaxException {
        log.debug("REST request to save TaskSelectHotel : {}", taskSelectHotelDTO);
        if (taskSelectHotelDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskSelectHotel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskSelectHotelDTO result = taskSelectHotelService.save(taskSelectHotelDTO);
        return ResponseEntity
            .created(new URI("/api/task-select-hotels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-select-hotels/:id} : Updates an existing taskSelectHotel.
     *
     * @param id the id of the taskSelectHotelDTO to save.
     * @param taskSelectHotelDTO the taskSelectHotelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskSelectHotelDTO,
     * or with status {@code 400 (Bad Request)} if the taskSelectHotelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskSelectHotelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-select-hotels/{id}")
    public ResponseEntity<TaskSelectHotelDTO> updateTaskSelectHotel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskSelectHotelDTO taskSelectHotelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TaskSelectHotel : {}, {}", id, taskSelectHotelDTO);
        if (taskSelectHotelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskSelectHotelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskSelectHotelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaskSelectHotelDTO result = taskSelectHotelService.save(taskSelectHotelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskSelectHotelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /task-select-hotels/:id} : Partial updates given fields of an existing taskSelectHotel, field will ignore if it is null
     *
     * @param id the id of the taskSelectHotelDTO to save.
     * @param taskSelectHotelDTO the taskSelectHotelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskSelectHotelDTO,
     * or with status {@code 400 (Bad Request)} if the taskSelectHotelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the taskSelectHotelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the taskSelectHotelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/task-select-hotels/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TaskSelectHotelDTO> partialUpdateTaskSelectHotel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskSelectHotelDTO taskSelectHotelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaskSelectHotel partially : {}, {}", id, taskSelectHotelDTO);
        if (taskSelectHotelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskSelectHotelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskSelectHotelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaskSelectHotelDTO> result = taskSelectHotelService.partialUpdate(taskSelectHotelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskSelectHotelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /task-select-hotels} : get all the taskSelectHotels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskSelectHotels in body.
     */
    @GetMapping("/task-select-hotels")
    public List<TaskSelectHotelDTO> getAllTaskSelectHotels() {
        log.debug("REST request to get all TaskSelectHotels");
        return taskSelectHotelService.findAll();
    }

    /**
     * {@code GET  /task-select-hotels/:id} : get the "id" taskSelectHotel.
     *
     * @param id the id of the taskSelectHotelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskSelectHotelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-select-hotels/{id}")
    public ResponseEntity<TaskSelectHotelDTO> getTaskSelectHotel(@PathVariable Long id) {
        log.debug("REST request to get TaskSelectHotel : {}", id);
        Optional<TaskSelectHotelDTO> taskSelectHotelDTO = taskSelectHotelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskSelectHotelDTO);
    }

    /**
     * {@code DELETE  /task-select-hotels/:id} : delete the "id" taskSelectHotel.
     *
     * @param id the id of the taskSelectHotelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-select-hotels/{id}")
    public ResponseEntity<Void> deleteTaskSelectHotel(@PathVariable Long id) {
        log.debug("REST request to delete TaskSelectHotel : {}", id);
        taskSelectHotelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
