package org.agilekip.tutorials.travelcomplete.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.agilekip.tutorials.travelcomplete.repository.TravelPlanRepository;
import org.agilekip.tutorials.travelcomplete.service.TravelPlanService;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelPlanDTO;
import org.agilekip.tutorials.travelcomplete.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.agilekip.tutorials.travelcomplete.domain.TravelPlan}.
 */
@RestController
@RequestMapping("/api")
public class TravelPlanResource {

    private final Logger log = LoggerFactory.getLogger(TravelPlanResource.class);

    private static final String ENTITY_NAME = "travelPlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TravelPlanService travelPlanService;

    private final TravelPlanRepository travelPlanRepository;

    public TravelPlanResource(TravelPlanService travelPlanService, TravelPlanRepository travelPlanRepository) {
        this.travelPlanService = travelPlanService;
        this.travelPlanRepository = travelPlanRepository;
    }

    /**
     * {@code POST  /travel-plans} : Create a new travelPlan.
     *
     * @param travelPlanDTO the travelPlanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new travelPlanDTO, or with status {@code 400 (Bad Request)} if the travelPlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/travel-plans")
    public ResponseEntity<TravelPlanDTO> createTravelPlan(@RequestBody TravelPlanDTO travelPlanDTO) throws URISyntaxException {
        log.debug("REST request to save TravelPlan : {}", travelPlanDTO);
        if (travelPlanDTO.getId() != null) {
            throw new BadRequestAlertException("A new travelPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TravelPlanDTO result = travelPlanService.save(travelPlanDTO);
        return ResponseEntity
            .created(new URI("/api/travel-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /travel-plans/:id} : Updates an existing travelPlan.
     *
     * @param id the id of the travelPlanDTO to save.
     * @param travelPlanDTO the travelPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated travelPlanDTO,
     * or with status {@code 400 (Bad Request)} if the travelPlanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the travelPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/travel-plans/{id}")
    public ResponseEntity<TravelPlanDTO> updateTravelPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TravelPlanDTO travelPlanDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TravelPlan : {}, {}", id, travelPlanDTO);
        if (travelPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, travelPlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!travelPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TravelPlanDTO result = travelPlanService.save(travelPlanDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, travelPlanDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /travel-plans/:id} : Partial updates given fields of an existing travelPlan, field will ignore if it is null
     *
     * @param id the id of the travelPlanDTO to save.
     * @param travelPlanDTO the travelPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated travelPlanDTO,
     * or with status {@code 400 (Bad Request)} if the travelPlanDTO is not valid,
     * or with status {@code 404 (Not Found)} if the travelPlanDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the travelPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/travel-plans/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TravelPlanDTO> partialUpdateTravelPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TravelPlanDTO travelPlanDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TravelPlan partially : {}, {}", id, travelPlanDTO);
        if (travelPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, travelPlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!travelPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TravelPlanDTO> result = travelPlanService.partialUpdate(travelPlanDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, travelPlanDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /travel-plans} : get all the travelPlans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of travelPlans in body.
     */
    @GetMapping("/travel-plans")
    public List<TravelPlanDTO> getAllTravelPlans() {
        log.debug("REST request to get all TravelPlans");
        return travelPlanService.findAll();
    }

    /**
     * {@code GET  /travel-plans/:id} : get the "id" travelPlan.
     *
     * @param id the id of the travelPlanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the travelPlanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/travel-plans/{id}")
    public ResponseEntity<TravelPlanDTO> getTravelPlan(@PathVariable Long id) {
        log.debug("REST request to get TravelPlan : {}", id);
        Optional<TravelPlanDTO> travelPlanDTO = travelPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(travelPlanDTO);
    }

    /**
     * {@code DELETE  /travel-plans/:id} : delete the "id" travelPlan.
     *
     * @param id the id of the travelPlanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/travel-plans/{id}")
    public ResponseEntity<Void> deleteTravelPlan(@PathVariable Long id) {
        log.debug("REST request to delete TravelPlan : {}", id);
        travelPlanService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
