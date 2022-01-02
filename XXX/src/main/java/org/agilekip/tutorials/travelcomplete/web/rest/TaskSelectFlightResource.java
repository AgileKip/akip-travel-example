package org.agilekip.tutorials.travelcomplete.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.agilekip.tutorials.travelcomplete.repository.TaskSelectFlightRepository;
import org.agilekip.tutorials.travelcomplete.service.TaskSelectFlightService;
import org.agilekip.tutorials.travelcomplete.service.dto.TaskSelectFlightDTO;
import org.agilekip.tutorials.travelcomplete.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.agilekip.tutorials.travelcomplete.domain.TaskSelectFlight}.
 */
@RestController
@RequestMapping("/api")
public class TaskSelectFlightResource {

    private final Logger log = LoggerFactory.getLogger(TaskSelectFlightResource.class);

    private static final String ENTITY_NAME = "taskSelectFlight";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskSelectFlightService taskSelectFlightService;

    private final TaskSelectFlightRepository taskSelectFlightRepository;

    public TaskSelectFlightResource(
        TaskSelectFlightService taskSelectFlightService,
        TaskSelectFlightRepository taskSelectFlightRepository
    ) {
        this.taskSelectFlightService = taskSelectFlightService;
        this.taskSelectFlightRepository = taskSelectFlightRepository;
    }

    /**
     * {@code POST  /task-select-flights} : Create a new taskSelectFlight.
     *
     * @param taskSelectFlightDTO the taskSelectFlightDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskSelectFlightDTO, or with status {@code 400 (Bad Request)} if the taskSelectFlight has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-select-flights")
    public ResponseEntity<TaskSelectFlightDTO> createTaskSelectFlight(@RequestBody TaskSelectFlightDTO taskSelectFlightDTO)
        throws URISyntaxException {
        log.debug("REST request to save TaskSelectFlight : {}", taskSelectFlightDTO);
        if (taskSelectFlightDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskSelectFlight cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskSelectFlightDTO result = taskSelectFlightService.save(taskSelectFlightDTO);
        return ResponseEntity
            .created(new URI("/api/task-select-flights/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-select-flights/:id} : Updates an existing taskSelectFlight.
     *
     * @param id the id of the taskSelectFlightDTO to save.
     * @param taskSelectFlightDTO the taskSelectFlightDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskSelectFlightDTO,
     * or with status {@code 400 (Bad Request)} if the taskSelectFlightDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskSelectFlightDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-select-flights/{id}")
    public ResponseEntity<TaskSelectFlightDTO> updateTaskSelectFlight(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskSelectFlightDTO taskSelectFlightDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TaskSelectFlight : {}, {}", id, taskSelectFlightDTO);
        if (taskSelectFlightDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskSelectFlightDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskSelectFlightRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaskSelectFlightDTO result = taskSelectFlightService.save(taskSelectFlightDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskSelectFlightDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /task-select-flights/:id} : Partial updates given fields of an existing taskSelectFlight, field will ignore if it is null
     *
     * @param id the id of the taskSelectFlightDTO to save.
     * @param taskSelectFlightDTO the taskSelectFlightDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskSelectFlightDTO,
     * or with status {@code 400 (Bad Request)} if the taskSelectFlightDTO is not valid,
     * or with status {@code 404 (Not Found)} if the taskSelectFlightDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the taskSelectFlightDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/task-select-flights/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TaskSelectFlightDTO> partialUpdateTaskSelectFlight(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskSelectFlightDTO taskSelectFlightDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaskSelectFlight partially : {}, {}", id, taskSelectFlightDTO);
        if (taskSelectFlightDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskSelectFlightDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskSelectFlightRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaskSelectFlightDTO> result = taskSelectFlightService.partialUpdate(taskSelectFlightDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskSelectFlightDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /task-select-flights} : get all the taskSelectFlights.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskSelectFlights in body.
     */
    @GetMapping("/task-select-flights")
    public List<TaskSelectFlightDTO> getAllTaskSelectFlights() {
        log.debug("REST request to get all TaskSelectFlights");
        return taskSelectFlightService.findAll();
    }

    /**
     * {@code GET  /task-select-flights/:id} : get the "id" taskSelectFlight.
     *
     * @param id the id of the taskSelectFlightDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskSelectFlightDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-select-flights/{id}")
    public ResponseEntity<TaskSelectFlightDTO> getTaskSelectFlight(@PathVariable Long id) {
        log.debug("REST request to get TaskSelectFlight : {}", id);
        Optional<TaskSelectFlightDTO> taskSelectFlightDTO = taskSelectFlightService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskSelectFlightDTO);
    }

    /**
     * {@code DELETE  /task-select-flights/:id} : delete the "id" taskSelectFlight.
     *
     * @param id the id of the taskSelectFlightDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-select-flights/{id}")
    public ResponseEntity<Void> deleteTaskSelectFlight(@PathVariable Long id) {
        log.debug("REST request to delete TaskSelectFlight : {}", id);
        taskSelectFlightService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
