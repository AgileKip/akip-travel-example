package org.agilekip.tutorials.travelcomplete.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.agilekip.tutorials.travelcomplete.repository.FlightRepository;
import org.agilekip.tutorials.travelcomplete.service.FlightService;
import org.agilekip.tutorials.travelcomplete.service.dto.FlightDTO;
import org.agilekip.tutorials.travelcomplete.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.agilekip.tutorials.travelcomplete.domain.Flight}.
 */
@RestController
@RequestMapping("/api")
public class FlightResource {

    private final Logger log = LoggerFactory.getLogger(FlightResource.class);

    private static final String ENTITY_NAME = "flight";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FlightService flightService;

    private final FlightRepository flightRepository;

    public FlightResource(FlightService flightService, FlightRepository flightRepository) {
        this.flightService = flightService;
        this.flightRepository = flightRepository;
    }

    /**
     * {@code POST  /flights} : Create a new flight.
     *
     * @param flightDTO the flightDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new flightDTO, or with status {@code 400 (Bad Request)} if the flight has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/flights")
    public ResponseEntity<FlightDTO> createFlight(@Valid @RequestBody FlightDTO flightDTO) throws URISyntaxException {
        log.debug("REST request to save Flight : {}", flightDTO);
        if (flightDTO.getId() != null) {
            throw new BadRequestAlertException("A new flight cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FlightDTO result = flightService.save(flightDTO);
        return ResponseEntity
            .created(new URI("/api/flights/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /flights/:id} : Updates an existing flight.
     *
     * @param id the id of the flightDTO to save.
     * @param flightDTO the flightDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flightDTO,
     * or with status {@code 400 (Bad Request)} if the flightDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the flightDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/flights/{id}")
    public ResponseEntity<FlightDTO> updateFlight(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FlightDTO flightDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Flight : {}, {}", id, flightDTO);
        if (flightDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, flightDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!flightRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FlightDTO result = flightService.save(flightDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, flightDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /flights/:id} : Partial updates given fields of an existing flight, field will ignore if it is null
     *
     * @param id the id of the flightDTO to save.
     * @param flightDTO the flightDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flightDTO,
     * or with status {@code 400 (Bad Request)} if the flightDTO is not valid,
     * or with status {@code 404 (Not Found)} if the flightDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the flightDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/flights/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FlightDTO> partialUpdateFlight(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FlightDTO flightDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Flight partially : {}, {}", id, flightDTO);
        if (flightDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, flightDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!flightRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FlightDTO> result = flightService.partialUpdate(flightDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, flightDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /flights} : get all the flights.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of flights in body.
     */
    @GetMapping("/flights")
    public List<FlightDTO> getAllFlights() {
        log.debug("REST request to get all Flights");
        return flightService.findAll();
    }

    /**
     * {@code GET  /flights/:id} : get the "id" flight.
     *
     * @param id the id of the flightDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the flightDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/flights/{id}")
    public ResponseEntity<FlightDTO> getFlight(@PathVariable Long id) {
        log.debug("REST request to get Flight : {}", id);
        Optional<FlightDTO> flightDTO = flightService.findOne(id);
        return ResponseUtil.wrapOrNotFound(flightDTO);
    }

    /**
     * {@code DELETE  /flights/:id} : delete the "id" flight.
     *
     * @param id the id of the flightDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/flights/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        log.debug("REST request to delete Flight : {}", id);
        flightService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
