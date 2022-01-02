package org.agilekip.tutorials.travelcomplete.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.agilekip.tutorials.travelcomplete.repository.CarRentalCompanyRepository;
import org.agilekip.tutorials.travelcomplete.service.CarRentalCompanyService;
import org.agilekip.tutorials.travelcomplete.service.dto.CarRentalCompanyDTO;
import org.agilekip.tutorials.travelcomplete.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.agilekip.tutorials.travelcomplete.domain.CarRentalCompany}.
 */
@RestController
@RequestMapping("/api")
public class CarRentalCompanyResource {

    private final Logger log = LoggerFactory.getLogger(CarRentalCompanyResource.class);

    private static final String ENTITY_NAME = "carRentalCompany";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarRentalCompanyService carRentalCompanyService;

    private final CarRentalCompanyRepository carRentalCompanyRepository;

    public CarRentalCompanyResource(
        CarRentalCompanyService carRentalCompanyService,
        CarRentalCompanyRepository carRentalCompanyRepository
    ) {
        this.carRentalCompanyService = carRentalCompanyService;
        this.carRentalCompanyRepository = carRentalCompanyRepository;
    }

    /**
     * {@code POST  /car-rental-companies} : Create a new carRentalCompany.
     *
     * @param carRentalCompanyDTO the carRentalCompanyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carRentalCompanyDTO, or with status {@code 400 (Bad Request)} if the carRentalCompany has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/car-rental-companies")
    public ResponseEntity<CarRentalCompanyDTO> createCarRentalCompany(@RequestBody CarRentalCompanyDTO carRentalCompanyDTO)
        throws URISyntaxException {
        log.debug("REST request to save CarRentalCompany : {}", carRentalCompanyDTO);
        if (carRentalCompanyDTO.getId() != null) {
            throw new BadRequestAlertException("A new carRentalCompany cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarRentalCompanyDTO result = carRentalCompanyService.save(carRentalCompanyDTO);
        return ResponseEntity
            .created(new URI("/api/car-rental-companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /car-rental-companies/:id} : Updates an existing carRentalCompany.
     *
     * @param id the id of the carRentalCompanyDTO to save.
     * @param carRentalCompanyDTO the carRentalCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carRentalCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the carRentalCompanyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carRentalCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/car-rental-companies/{id}")
    public ResponseEntity<CarRentalCompanyDTO> updateCarRentalCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CarRentalCompanyDTO carRentalCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CarRentalCompany : {}, {}", id, carRentalCompanyDTO);
        if (carRentalCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carRentalCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carRentalCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CarRentalCompanyDTO result = carRentalCompanyService.save(carRentalCompanyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carRentalCompanyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /car-rental-companies/:id} : Partial updates given fields of an existing carRentalCompany, field will ignore if it is null
     *
     * @param id the id of the carRentalCompanyDTO to save.
     * @param carRentalCompanyDTO the carRentalCompanyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carRentalCompanyDTO,
     * or with status {@code 400 (Bad Request)} if the carRentalCompanyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the carRentalCompanyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the carRentalCompanyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/car-rental-companies/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CarRentalCompanyDTO> partialUpdateCarRentalCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CarRentalCompanyDTO carRentalCompanyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CarRentalCompany partially : {}, {}", id, carRentalCompanyDTO);
        if (carRentalCompanyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carRentalCompanyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carRentalCompanyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarRentalCompanyDTO> result = carRentalCompanyService.partialUpdate(carRentalCompanyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carRentalCompanyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /car-rental-companies} : get all the carRentalCompanies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carRentalCompanies in body.
     */
    @GetMapping("/car-rental-companies")
    public List<CarRentalCompanyDTO> getAllCarRentalCompanies() {
        log.debug("REST request to get all CarRentalCompanies");
        return carRentalCompanyService.findAll();
    }

    /**
     * {@code GET  /car-rental-companies/:id} : get the "id" carRentalCompany.
     *
     * @param id the id of the carRentalCompanyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carRentalCompanyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/car-rental-companies/{id}")
    public ResponseEntity<CarRentalCompanyDTO> getCarRentalCompany(@PathVariable Long id) {
        log.debug("REST request to get CarRentalCompany : {}", id);
        Optional<CarRentalCompanyDTO> carRentalCompanyDTO = carRentalCompanyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carRentalCompanyDTO);
    }

    /**
     * {@code DELETE  /car-rental-companies/:id} : delete the "id" carRentalCompany.
     *
     * @param id the id of the carRentalCompanyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/car-rental-companies/{id}")
    public ResponseEntity<Void> deleteCarRentalCompany(@PathVariable Long id) {
        log.debug("REST request to delete CarRentalCompany : {}", id);
        carRentalCompanyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
