package org.agilekip.tutorials.travelcomplete.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.agilekip.tutorials.travelcomplete.repository.TaskSelectCarRepository;
import org.agilekip.tutorials.travelcomplete.service.TaskSelectCarService;
import org.agilekip.tutorials.travelcomplete.service.dto.TaskSelectCarDTO;
import org.agilekip.tutorials.travelcomplete.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.agilekip.tutorials.travelcomplete.domain.TaskSelectCar}.
 */
@RestController
@RequestMapping("/api")
public class TaskSelectCarResource {

    private final Logger log = LoggerFactory.getLogger(TaskSelectCarResource.class);

    private static final String ENTITY_NAME = "taskSelectCar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskSelectCarService taskSelectCarService;

    private final TaskSelectCarRepository taskSelectCarRepository;

    public TaskSelectCarResource(TaskSelectCarService taskSelectCarService, TaskSelectCarRepository taskSelectCarRepository) {
        this.taskSelectCarService = taskSelectCarService;
        this.taskSelectCarRepository = taskSelectCarRepository;
    }

    /**
     * {@code POST  /task-select-cars} : Create a new taskSelectCar.
     *
     * @param taskSelectCarDTO the taskSelectCarDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskSelectCarDTO, or with status {@code 400 (Bad Request)} if the taskSelectCar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-select-cars")
    public ResponseEntity<TaskSelectCarDTO> createTaskSelectCar(@RequestBody TaskSelectCarDTO taskSelectCarDTO) throws URISyntaxException {
        log.debug("REST request to save TaskSelectCar : {}", taskSelectCarDTO);
        if (taskSelectCarDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskSelectCar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskSelectCarDTO result = taskSelectCarService.save(taskSelectCarDTO);
        return ResponseEntity
            .created(new URI("/api/task-select-cars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-select-cars/:id} : Updates an existing taskSelectCar.
     *
     * @param id the id of the taskSelectCarDTO to save.
     * @param taskSelectCarDTO the taskSelectCarDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskSelectCarDTO,
     * or with status {@code 400 (Bad Request)} if the taskSelectCarDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskSelectCarDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-select-cars/{id}")
    public ResponseEntity<TaskSelectCarDTO> updateTaskSelectCar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskSelectCarDTO taskSelectCarDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TaskSelectCar : {}, {}", id, taskSelectCarDTO);
        if (taskSelectCarDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskSelectCarDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskSelectCarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaskSelectCarDTO result = taskSelectCarService.save(taskSelectCarDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskSelectCarDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /task-select-cars/:id} : Partial updates given fields of an existing taskSelectCar, field will ignore if it is null
     *
     * @param id the id of the taskSelectCarDTO to save.
     * @param taskSelectCarDTO the taskSelectCarDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskSelectCarDTO,
     * or with status {@code 400 (Bad Request)} if the taskSelectCarDTO is not valid,
     * or with status {@code 404 (Not Found)} if the taskSelectCarDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the taskSelectCarDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/task-select-cars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TaskSelectCarDTO> partialUpdateTaskSelectCar(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskSelectCarDTO taskSelectCarDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaskSelectCar partially : {}, {}", id, taskSelectCarDTO);
        if (taskSelectCarDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskSelectCarDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskSelectCarRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaskSelectCarDTO> result = taskSelectCarService.partialUpdate(taskSelectCarDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskSelectCarDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /task-select-cars} : get all the taskSelectCars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskSelectCars in body.
     */
    @GetMapping("/task-select-cars")
    public List<TaskSelectCarDTO> getAllTaskSelectCars() {
        log.debug("REST request to get all TaskSelectCars");
        return taskSelectCarService.findAll();
    }

    /**
     * {@code GET  /task-select-cars/:id} : get the "id" taskSelectCar.
     *
     * @param id the id of the taskSelectCarDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskSelectCarDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-select-cars/{id}")
    public ResponseEntity<TaskSelectCarDTO> getTaskSelectCar(@PathVariable Long id) {
        log.debug("REST request to get TaskSelectCar : {}", id);
        Optional<TaskSelectCarDTO> taskSelectCarDTO = taskSelectCarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskSelectCarDTO);
    }

    /**
     * {@code DELETE  /task-select-cars/:id} : delete the "id" taskSelectCar.
     *
     * @param id the id of the taskSelectCarDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-select-cars/{id}")
    public ResponseEntity<Void> deleteTaskSelectCar(@PathVariable Long id) {
        log.debug("REST request to delete TaskSelectCar : {}", id);
        taskSelectCarService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
