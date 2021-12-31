package org.agilekip.tutorials.travelcall.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.agilekip.tutorials.travelcall.repository.HandleCancelRepository;
import org.agilekip.tutorials.travelcall.service.HandleCancelService;
import org.agilekip.tutorials.travelcall.service.dto.HandleCancelDTO;
import org.agilekip.tutorials.travelcall.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.agilekip.tutorials.travelcall.domain.HandleCancel}.
 */
@RestController
@RequestMapping("/api")
public class HandleCancelResource {

    private final Logger log = LoggerFactory.getLogger(HandleCancelResource.class);

    private final HandleCancelService handleCancelService;

    private final HandleCancelRepository handleCancelRepository;

    public HandleCancelResource(HandleCancelService handleCancelService, HandleCancelRepository handleCancelRepository) {
        this.handleCancelService = handleCancelService;
        this.handleCancelRepository = handleCancelRepository;
    }

    /**
     * {@code GET  /handle-cancels} : get all the handleCancels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of handleCancels in body.
     */
    @GetMapping("/handle-cancels")
    public List<HandleCancelDTO> getAllHandleCancels() {
        log.debug("REST request to get all HandleCancels");
        return handleCancelService.findAll();
    }

    /**
     * {@code GET  /handle-cancels/:id} : get the "id" handleCancel.
     *
     * @param id the id of the handleCancelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the handleCancelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/handle-cancels/{id}")
    public ResponseEntity<HandleCancelDTO> getHandleCancel(@PathVariable Long id) {
        log.debug("REST request to get HandleCancel : {}", id);
        Optional<HandleCancelDTO> handleCancelDTO = handleCancelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(handleCancelDTO);
    }
}
