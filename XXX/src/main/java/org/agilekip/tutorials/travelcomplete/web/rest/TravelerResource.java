package org.agilekip.tutorials.travelcomplete.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.agilekip.tutorials.travelcomplete.repository.TravelerRepository;
import org.agilekip.tutorials.travelcomplete.service.TravelerService;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelerDTO;
import org.agilekip.tutorials.travelcomplete.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.agilekip.tutorials.travelcomplete.domain.Traveler}.
 */
@RestController
@RequestMapping("/api")
public class TravelerResource {

    private final Logger log = LoggerFactory.getLogger(TravelerResource.class);

    private static final String ENTITY_NAME = "traveler";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TravelerService travelerService;

    private final TravelerRepository travelerRepository;

    public TravelerResource(TravelerService travelerService, TravelerRepository travelerRepository) {
        this.travelerService = travelerService;
        this.travelerRepository = travelerRepository;
    }

    /**
     * {@code POST  /travelers} : Create a new traveler.
     *
     * @param travelerDTO the travelerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new travelerDTO, or with status {@code 400 (Bad Request)} if the traveler has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/travelers")
    public ResponseEntity<TravelerDTO> createTraveler(@Valid @RequestBody TravelerDTO travelerDTO) throws URISyntaxException {
        log.debug("REST request to save Traveler : {}", travelerDTO);
        if (travelerDTO.getId() != null) {
            throw new BadRequestAlertException("A new traveler cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TravelerDTO result = travelerService.save(travelerDTO);
        return ResponseEntity
            .created(new URI("/api/travelers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /travelers/:id} : Updates an existing traveler.
     *
     * @param id the id of the travelerDTO to save.
     * @param travelerDTO the travelerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated travelerDTO,
     * or with status {@code 400 (Bad Request)} if the travelerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the travelerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/travelers/{id}")
    public ResponseEntity<TravelerDTO> updateTraveler(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TravelerDTO travelerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Traveler : {}, {}", id, travelerDTO);
        if (travelerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, travelerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!travelerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TravelerDTO result = travelerService.save(travelerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, travelerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /travelers/:id} : Partial updates given fields of an existing traveler, field will ignore if it is null
     *
     * @param id the id of the travelerDTO to save.
     * @param travelerDTO the travelerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated travelerDTO,
     * or with status {@code 400 (Bad Request)} if the travelerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the travelerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the travelerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/travelers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TravelerDTO> partialUpdateTraveler(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TravelerDTO travelerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Traveler partially : {}, {}", id, travelerDTO);
        if (travelerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, travelerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!travelerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TravelerDTO> result = travelerService.partialUpdate(travelerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, travelerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /travelers} : get all the travelers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of travelers in body.
     */
    @GetMapping("/travelers")
    public List<TravelerDTO> getAllTravelers() {
        log.debug("REST request to get all Travelers");
        return travelerService.findAll();
    }

    /**
     * {@code GET  /travelers/:id} : get the "id" traveler.
     *
     * @param id the id of the travelerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the travelerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/travelers/{id}")
    public ResponseEntity<TravelerDTO> getTraveler(@PathVariable Long id) {
        log.debug("REST request to get Traveler : {}", id);
        Optional<TravelerDTO> travelerDTO = travelerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(travelerDTO);
    }

    /**
     * {@code DELETE  /travelers/:id} : delete the "id" traveler.
     *
     * @param id the id of the travelerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/travelers/{id}")
    public ResponseEntity<Void> deleteTraveler(@PathVariable Long id) {
        log.debug("REST request to delete Traveler : {}", id);
        travelerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
