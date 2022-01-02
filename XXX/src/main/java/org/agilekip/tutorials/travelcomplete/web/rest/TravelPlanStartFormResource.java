package org.agilekip.tutorials.travelcomplete.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.agilekip.tutorials.travelcomplete.repository.TravelPlanStartFormRepository;
import org.agilekip.tutorials.travelcomplete.service.TravelPlanStartFormService;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelPlanStartFormDTO;
import org.agilekip.tutorials.travelcomplete.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.agilekip.tutorials.travelcomplete.domain.TravelPlanStartForm}.
 */
@RestController
@RequestMapping("/api")
public class TravelPlanStartFormResource {

    private final Logger log = LoggerFactory.getLogger(TravelPlanStartFormResource.class);

    private static final String ENTITY_NAME = "travelPlanStartForm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TravelPlanStartFormService travelPlanStartFormService;

    private final TravelPlanStartFormRepository travelPlanStartFormRepository;

    public TravelPlanStartFormResource(
        TravelPlanStartFormService travelPlanStartFormService,
        TravelPlanStartFormRepository travelPlanStartFormRepository
    ) {
        this.travelPlanStartFormService = travelPlanStartFormService;
        this.travelPlanStartFormRepository = travelPlanStartFormRepository;
    }

    /**
     * {@code POST  /travel-plan-start-forms} : Create a new travelPlanStartForm.
     *
     * @param travelPlanStartFormDTO the travelPlanStartFormDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new travelPlanStartFormDTO, or with status {@code 400 (Bad Request)} if the travelPlanStartForm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/travel-plan-start-forms")
    public ResponseEntity<TravelPlanStartFormDTO> createTravelPlanStartForm(@RequestBody TravelPlanStartFormDTO travelPlanStartFormDTO)
        throws URISyntaxException {
        log.debug("REST request to save TravelPlanStartForm : {}", travelPlanStartFormDTO);
        if (travelPlanStartFormDTO.getId() != null) {
            throw new BadRequestAlertException("A new travelPlanStartForm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TravelPlanStartFormDTO result = travelPlanStartFormService.save(travelPlanStartFormDTO);
        return ResponseEntity
            .created(new URI("/api/travel-plan-start-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /travel-plan-start-forms/:id} : Updates an existing travelPlanStartForm.
     *
     * @param id the id of the travelPlanStartFormDTO to save.
     * @param travelPlanStartFormDTO the travelPlanStartFormDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated travelPlanStartFormDTO,
     * or with status {@code 400 (Bad Request)} if the travelPlanStartFormDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the travelPlanStartFormDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/travel-plan-start-forms/{id}")
    public ResponseEntity<TravelPlanStartFormDTO> updateTravelPlanStartForm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TravelPlanStartFormDTO travelPlanStartFormDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TravelPlanStartForm : {}, {}", id, travelPlanStartFormDTO);
        if (travelPlanStartFormDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, travelPlanStartFormDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!travelPlanStartFormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TravelPlanStartFormDTO result = travelPlanStartFormService.save(travelPlanStartFormDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, travelPlanStartFormDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /travel-plan-start-forms/:id} : Partial updates given fields of an existing travelPlanStartForm, field will ignore if it is null
     *
     * @param id the id of the travelPlanStartFormDTO to save.
     * @param travelPlanStartFormDTO the travelPlanStartFormDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated travelPlanStartFormDTO,
     * or with status {@code 400 (Bad Request)} if the travelPlanStartFormDTO is not valid,
     * or with status {@code 404 (Not Found)} if the travelPlanStartFormDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the travelPlanStartFormDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/travel-plan-start-forms/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TravelPlanStartFormDTO> partialUpdateTravelPlanStartForm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TravelPlanStartFormDTO travelPlanStartFormDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TravelPlanStartForm partially : {}, {}", id, travelPlanStartFormDTO);
        if (travelPlanStartFormDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, travelPlanStartFormDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!travelPlanStartFormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TravelPlanStartFormDTO> result = travelPlanStartFormService.partialUpdate(travelPlanStartFormDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, travelPlanStartFormDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /travel-plan-start-forms} : get all the travelPlanStartForms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of travelPlanStartForms in body.
     */
    @GetMapping("/travel-plan-start-forms")
    public List<TravelPlanStartFormDTO> getAllTravelPlanStartForms() {
        log.debug("REST request to get all TravelPlanStartForms");
        return travelPlanStartFormService.findAll();
    }

    /**
     * {@code GET  /travel-plan-start-forms/:id} : get the "id" travelPlanStartForm.
     *
     * @param id the id of the travelPlanStartFormDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the travelPlanStartFormDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/travel-plan-start-forms/{id}")
    public ResponseEntity<TravelPlanStartFormDTO> getTravelPlanStartForm(@PathVariable Long id) {
        log.debug("REST request to get TravelPlanStartForm : {}", id);
        Optional<TravelPlanStartFormDTO> travelPlanStartFormDTO = travelPlanStartFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(travelPlanStartFormDTO);
    }

    /**
     * {@code DELETE  /travel-plan-start-forms/:id} : delete the "id" travelPlanStartForm.
     *
     * @param id the id of the travelPlanStartFormDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/travel-plan-start-forms/{id}")
    public ResponseEntity<Void> deleteTravelPlanStartForm(@PathVariable Long id) {
        log.debug("REST request to delete TravelPlanStartForm : {}", id);
        travelPlanStartFormService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
