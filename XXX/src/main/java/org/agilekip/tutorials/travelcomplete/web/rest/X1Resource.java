package org.agilekip.tutorials.travelcomplete.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.agilekip.tutorials.travelcomplete.repository.X1Repository;
import org.agilekip.tutorials.travelcomplete.service.X1Service;
import org.agilekip.tutorials.travelcomplete.service.dto.X1DTO;
import org.agilekip.tutorials.travelcomplete.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.agilekip.tutorials.travelcomplete.domain.X1}.
 */
@RestController
@RequestMapping("/api")
public class X1Resource {

    private final Logger log = LoggerFactory.getLogger(X1Resource.class);

    private static final String ENTITY_NAME = "x1";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final X1Service x1Service;

    private final X1Repository x1Repository;

    public X1Resource(X1Service x1Service, X1Repository x1Repository) {
        this.x1Service = x1Service;
        this.x1Repository = x1Repository;
    }

    /**
     * {@code POST  /x-1-s} : Create a new x1.
     *
     * @param x1DTO the x1DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new x1DTO, or with status {@code 400 (Bad Request)} if the x1 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/x-1-s")
    public ResponseEntity<X1DTO> createX1(@Valid @RequestBody X1DTO x1DTO) throws URISyntaxException {
        log.debug("REST request to save X1 : {}", x1DTO);
        if (x1DTO.getId() != null) {
            throw new BadRequestAlertException("A new x1 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        X1DTO result = x1Service.save(x1DTO);
        return ResponseEntity
            .created(new URI("/api/x-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /x-1-s/:id} : Updates an existing x1.
     *
     * @param id the id of the x1DTO to save.
     * @param x1DTO the x1DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated x1DTO,
     * or with status {@code 400 (Bad Request)} if the x1DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the x1DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/x-1-s/{id}")
    public ResponseEntity<X1DTO> updateX1(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody X1DTO x1DTO)
        throws URISyntaxException {
        log.debug("REST request to update X1 : {}, {}", id, x1DTO);
        if (x1DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, x1DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!x1Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        X1DTO result = x1Service.save(x1DTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, x1DTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /x-1-s/:id} : Partial updates given fields of an existing x1, field will ignore if it is null
     *
     * @param id the id of the x1DTO to save.
     * @param x1DTO the x1DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated x1DTO,
     * or with status {@code 400 (Bad Request)} if the x1DTO is not valid,
     * or with status {@code 404 (Not Found)} if the x1DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the x1DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/x-1-s/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<X1DTO> partialUpdateX1(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody X1DTO x1DTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update X1 partially : {}, {}", id, x1DTO);
        if (x1DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, x1DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!x1Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<X1DTO> result = x1Service.partialUpdate(x1DTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, x1DTO.getId().toString())
        );
    }

    /**
     * {@code GET  /x-1-s} : get all the x1s.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of x1s in body.
     */
    @GetMapping("/x-1-s")
    public List<X1DTO> getAllX1s(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all X1s");
        return x1Service.findAll();
    }

    /**
     * {@code GET  /x-1-s/:id} : get the "id" x1.
     *
     * @param id the id of the x1DTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the x1DTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/x-1-s/{id}")
    public ResponseEntity<X1DTO> getX1(@PathVariable Long id) {
        log.debug("REST request to get X1 : {}", id);
        Optional<X1DTO> x1DTO = x1Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(x1DTO);
    }

    /**
     * {@code DELETE  /x-1-s/:id} : delete the "id" x1.
     *
     * @param id the id of the x1DTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/x-1-s/{id}")
    public ResponseEntity<Void> deleteX1(@PathVariable Long id) {
        log.debug("REST request to delete X1 : {}", id);
        x1Service.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
