package org.agilekip.tutorials.travelcomplete.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.agilekip.tutorials.travelcomplete.repository.AirportRepository;
import org.agilekip.tutorials.travelcomplete.service.AirportService;
import org.agilekip.tutorials.travelcomplete.service.dto.AirportDTO;
import org.agilekip.tutorials.travelcomplete.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.agilekip.tutorials.travelcomplete.domain.Airport}.
 */
@RestController
@RequestMapping("/api")
public class AirportResource {

    private final Logger log = LoggerFactory.getLogger(AirportResource.class);

    private static final String ENTITY_NAME = "airport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AirportService airportService;

    private final AirportRepository airportRepository;

    public AirportResource(AirportService airportService, AirportRepository airportRepository) {
        this.airportService = airportService;
        this.airportRepository = airportRepository;
    }

    /**
     * {@code POST  /airports} : Create a new airport.
     *
     * @param airportDTO the airportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new airportDTO, or with status {@code 400 (Bad Request)} if the airport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/airports")
    public ResponseEntity<AirportDTO> createAirport(@Valid @RequestBody AirportDTO airportDTO) throws URISyntaxException {
        log.debug("REST request to save Airport : {}", airportDTO);
        if (airportDTO.getId() != null) {
            throw new BadRequestAlertException("A new airport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AirportDTO result = airportService.save(airportDTO);
        return ResponseEntity
            .created(new URI("/api/airports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /airports/:id} : Updates an existing airport.
     *
     * @param id the id of the airportDTO to save.
     * @param airportDTO the airportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated airportDTO,
     * or with status {@code 400 (Bad Request)} if the airportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the airportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/airports/{id}")
    public ResponseEntity<AirportDTO> updateAirport(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AirportDTO airportDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Airport : {}, {}", id, airportDTO);
        if (airportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, airportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!airportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AirportDTO result = airportService.save(airportDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, airportDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /airports/:id} : Partial updates given fields of an existing airport, field will ignore if it is null
     *
     * @param id the id of the airportDTO to save.
     * @param airportDTO the airportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated airportDTO,
     * or with status {@code 400 (Bad Request)} if the airportDTO is not valid,
     * or with status {@code 404 (Not Found)} if the airportDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the airportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/airports/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AirportDTO> partialUpdateAirport(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AirportDTO airportDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Airport partially : {}, {}", id, airportDTO);
        if (airportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, airportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!airportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AirportDTO> result = airportService.partialUpdate(airportDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, airportDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /airports} : get all the airports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of airports in body.
     */
    @GetMapping("/airports")
    public List<AirportDTO> getAllAirports() {
        log.debug("REST request to get all Airports");
        return airportService.findAll();
    }

    /**
     * {@code GET  /airports/:id} : get the "id" airport.
     *
     * @param id the id of the airportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the airportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/airports/{id}")
    public ResponseEntity<AirportDTO> getAirport(@PathVariable Long id) {
        log.debug("REST request to get Airport : {}", id);
        Optional<AirportDTO> airportDTO = airportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(airportDTO);
    }

    /**
     * {@code DELETE  /airports/:id} : delete the "id" airport.
     *
     * @param id the id of the airportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/airports/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        log.debug("REST request to delete Airport : {}", id);
        airportService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
