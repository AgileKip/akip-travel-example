package org.agilekip.tutorials.travelcomplete.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.agilekip.tutorials.travelcomplete.repository.HotelCompanyRepository;
import org.agilekip.tutorials.travelcomplete.service.HotelCompanyService;
import org.agilekip.tutorials.travelcomplete.service.dto.HotelCompanyDTO;
import org.agilekip.tutorials.travelcomplete.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.agilekip.tutorials.travelcomplete.domain.HotelCompany}.
 */
@RestController
@RequestMapping("/api")
public class HotelCompanyResource {

    private final Logger log = LoggerFactory.getLogger(HotelCompanyResource.class);

    private static final String ENTITY_NAME = "hotelCompany";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HotelCompanyService hotelCompanyService;

    private final HotelCompanyRepository hotelCompanyRepository;

    public HotelCompanyResource(HotelCompanyService hotelCompanyService, HotelCompanyRepository hotelCompanyRepository) {
        this.hotelCompanyService = hotelCompanyService;
        this.hotelCompanyRepository = hotelCompanyRepository;
    }

    /**
     * {@code POST  /hotel-companies} : Create a new hotelCompany.
     *
     * @param hotelCompanyDTO the hotelCompanyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hotelCompanyDTO, or with status {@code 400 (Bad Request)} if the hotelCompany has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hotel-companies")
    public ResponseEntity<HotelCompanyDTO> createHotelCompany(@Valid @RequestBody HotelCompanyDTO hotelCompanyDTO)
        throws URISyntaxException {
        log.debug("REST request to save HotelCompany : {}", hotelCompanyDTO);
        if (hotelCompanyDTO.getId() != null) {
            throw new BadRequestAlertException("A new hotelCompany cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HotelCompanyDTO result = hotelCompanyService.save(hotelCompanyDTO);
        return ResponseEntity
            .created(new URI("/api/hotel-companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hotel-companies/:id} : Updates an existing hotelCompany.
     *
     * @param id the id of the hotelCompanyDTO to save.
     * @param hotelCompanyDTO the hotelCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotelCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the hotelCompanyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hotelCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hotel-companies/{id}")
    public ResponseEntity<HotelCompanyDTO> updateHotelCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HotelCompanyDTO hotelCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HotelCompany : {}, {}", id, hotelCompanyDTO);
        if (hotelCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotelCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HotelCompanyDTO result = hotelCompanyService.save(hotelCompanyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hotelCompanyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hotel-companies/:id} : Partial updates given fields of an existing hotelCompany, field will ignore if it is null
     *
     * @param id the id of the hotelCompanyDTO to save.
     * @param hotelCompanyDTO the hotelCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotelCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the hotelCompanyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hotelCompanyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hotelCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hotel-companies/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<HotelCompanyDTO> partialUpdateHotelCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HotelCompanyDTO hotelCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HotelCompany partially : {}, {}", id, hotelCompanyDTO);
        if (hotelCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotelCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HotelCompanyDTO> result = hotelCompanyService.partialUpdate(hotelCompanyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hotelCompanyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hotel-companies} : get all the hotelCompanies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hotelCompanies in body.
     */
    @GetMapping("/hotel-companies")
    public List<HotelCompanyDTO> getAllHotelCompanies() {
        log.debug("REST request to get all HotelCompanies");
        return hotelCompanyService.findAll();
    }

    /**
     * {@code GET  /hotel-companies/:id} : get the "id" hotelCompany.
     *
     * @param id the id of the hotelCompanyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hotelCompanyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hotel-companies/{id}")
    public ResponseEntity<HotelCompanyDTO> getHotelCompany(@PathVariable Long id) {
        log.debug("REST request to get HotelCompany : {}", id);
        Optional<HotelCompanyDTO> hotelCompanyDTO = hotelCompanyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hotelCompanyDTO);
    }

    /**
     * {@code DELETE  /hotel-companies/:id} : delete the "id" hotelCompany.
     *
     * @param id the id of the hotelCompanyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hotel-companies/{id}")
    public ResponseEntity<Void> deleteHotelCompany(@PathVariable Long id) {
        log.debug("REST request to delete HotelCompany : {}", id);
        hotelCompanyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
