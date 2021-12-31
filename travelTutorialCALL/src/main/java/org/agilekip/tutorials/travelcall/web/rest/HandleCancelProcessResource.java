package org.agilekip.tutorials.travelcall.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.agilekip.tutorials.travelcall.service.HandleCancelProcessService;
import org.agilekip.tutorials.travelcall.service.dto.HandleCancelProcessDTO;
import org.agilekip.tutorials.travelcall.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.agilekip.tutorials.travelcall.domain.HandleCancelProcess}.
 */
@RestController
@RequestMapping("/api")
public class HandleCancelProcessResource {

    private final Logger log = LoggerFactory.getLogger(HandleCancelProcessResource.class);

    private static final String ENTITY_NAME = "handleCancelProcess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HandleCancelProcessService handleCancelProcessService;

    public HandleCancelProcessResource(HandleCancelProcessService handleCancelProcessService) {
        this.handleCancelProcessService = handleCancelProcessService;
    }

    /**
     * {@code POST  /handle-cancel-processes} : Create a new handleCancelProcess.
     *
     * @param handleCancelProcessDTO the handleCancelProcessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new handleCancelProcessDTO, or with status {@code 400 (Bad Request)} if the handleCancelProcess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/handle-cancel-processes")
    public ResponseEntity<HandleCancelProcessDTO> create(@RequestBody HandleCancelProcessDTO handleCancelProcessDTO)
        throws URISyntaxException {
        log.debug("REST request to save HandleCancelProcess : {}", handleCancelProcessDTO);
        if (handleCancelProcessDTO.getId() != null) {
            throw new BadRequestAlertException("A new handleCancelProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HandleCancelProcessDTO result = handleCancelProcessService.create(handleCancelProcessDTO);
        return ResponseEntity
            .created(new URI("/api/handle-cancel-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /handle-cancel-processes} : get all the handleCancelProcesss.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of handleCancelProcesss in body.
     */
    @GetMapping("/handle-cancel-processes")
    public List<HandleCancelProcessDTO> getAllHandleCancelProcesss() {
        log.debug("REST request to get all HandleCancelProcesss");
        return handleCancelProcessService.findAll();
    }

    /**
     * {@code GET  /handle-cancel-processes/:id} : get the "id" handleCancelProcess.
     *
     * @param processInstanceId the id of the handleCancelProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the handleCancelProcessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/handle-cancel-processes/{processInstanceId}")
    public ResponseEntity<HandleCancelProcessDTO> getByProcessInstanceId(@PathVariable Long processInstanceId) {
        log.debug("REST request to get HandleCancelProcess by processInstanceId : {}", processInstanceId);
        Optional<HandleCancelProcessDTO> handleCancelProcessDTO = handleCancelProcessService.findByProcessInstanceId(processInstanceId);
        return ResponseUtil.wrapOrNotFound(handleCancelProcessDTO);
    }
}
