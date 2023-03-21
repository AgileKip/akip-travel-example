package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.GenericTimelineProcessService;
import com.mycompany.myapp.service.dto.GenericTimelineProcessDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.GenericTimelineProcess}.
 */
@RestController
@RequestMapping("/api")
public class GenericTimelineProcessResource {

    private final Logger log = LoggerFactory.getLogger(GenericTimelineProcessResource.class);

    private static final String ENTITY_NAME = "genericTimelineProcess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GenericTimelineProcessService genericTimelineProcessService;

    public GenericTimelineProcessResource(GenericTimelineProcessService genericTimelineProcessService) {
        this.genericTimelineProcessService = genericTimelineProcessService;
    }

    /**
     * {@code POST  /generic-timeline-processes} : Create a new genericTimelineProcess.
     *
     * @param genericTimelineProcessDTO the genericTimelineProcessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new genericTimelineProcessDTO, or with status {@code 400 (Bad Request)} if the genericTimelineProcess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/generic-timeline-processes")
    public ResponseEntity<GenericTimelineProcessDTO> create(@RequestBody GenericTimelineProcessDTO genericTimelineProcessDTO)
        throws URISyntaxException {
        log.debug("REST request to save GenericTimelineProcess : {}", genericTimelineProcessDTO);
        if (genericTimelineProcessDTO.getId() != null) {
            throw new BadRequestAlertException("A new genericTimelineProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GenericTimelineProcessDTO result = genericTimelineProcessService.create(genericTimelineProcessDTO);
        return ResponseEntity
            .created(new URI("/api/generic-timeline-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /generic-timeline-processes} : get all the genericTimelineProcesss.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of genericTimelineProcesss in body.
     */
    @GetMapping("/generic-timeline-processes")
    public List<GenericTimelineProcessDTO> getAllGenericTimelineProcesss() {
        log.debug("REST request to get all GenericTimelineProcesss");
        return genericTimelineProcessService.findAll();
    }

    /**
     * {@code GET  /generic-timeline-processes/:id} : get the "id" genericTimelineProcess.
     *
     * @param processInstanceId the id of the genericTimelineProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the genericTimelineProcessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/generic-timeline-processes/{processInstanceId}")
    public ResponseEntity<GenericTimelineProcessDTO> getByProcessInstanceId(@PathVariable Long processInstanceId) {
        log.debug("REST request to get GenericTimelineProcess by processInstanceId : {}", processInstanceId);
        Optional<GenericTimelineProcessDTO> genericTimelineProcessDTO = genericTimelineProcessService.findByProcessInstanceId(
            processInstanceId
        );
        return ResponseUtil.wrapOrNotFound(genericTimelineProcessDTO);
    }
}
