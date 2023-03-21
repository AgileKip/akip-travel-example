package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.GenericTimelineRepository;
import com.mycompany.myapp.service.GenericTimelineService;
import com.mycompany.myapp.service.dto.GenericTimelineDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.GenericTimeline}.
 */
@RestController
@RequestMapping("/api")
public class GenericTimelineResource {

    private final Logger log = LoggerFactory.getLogger(GenericTimelineResource.class);

    private final GenericTimelineService genericTimelineService;

    private final GenericTimelineRepository genericTimelineRepository;

    public GenericTimelineResource(GenericTimelineService genericTimelineService, GenericTimelineRepository genericTimelineRepository) {
        this.genericTimelineService = genericTimelineService;
        this.genericTimelineRepository = genericTimelineRepository;
    }

    /**
     * {@code GET  /generic-timelines} : get all the genericTimelines.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of genericTimelines in body.
     */
    @GetMapping("/generic-timelines")
    public List<GenericTimelineDTO> getAllGenericTimelines() {
        log.debug("REST request to get all GenericTimelines");
        return genericTimelineService.findAll();
    }

    /**
     * {@code GET  /generic-timelines/:id} : get the "id" genericTimeline.
     *
     * @param id the id of the genericTimelineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the genericTimelineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/generic-timelines/{id}")
    public ResponseEntity<GenericTimelineDTO> getGenericTimeline(@PathVariable Long id) {
        log.debug("REST request to get GenericTimeline : {}", id);
        Optional<GenericTimelineDTO> genericTimelineDTO = genericTimelineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(genericTimelineDTO);
    }
}
