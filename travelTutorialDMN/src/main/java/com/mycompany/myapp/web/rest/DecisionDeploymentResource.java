package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.DecisionDeploymentService;
import com.mycompany.myapp.service.dto.DecisionDeploymentDTO;
import com.mycompany.myapp.service.dto.DecisionDeploymentDmnModelDTO;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.DecisionDeployment}.
 */
@RestController
@RequestMapping("/api")
public class DecisionDeploymentResource {

    private final Logger log = LoggerFactory.getLogger(DecisionDeploymentResource.class);

    private static final String ENTITY_NAME = "processDeployment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DecisionDeploymentService decisionDeploymentService;

    public DecisionDeploymentResource(DecisionDeploymentService decisionDeploymentService) {
        this.decisionDeploymentService = decisionDeploymentService;
    }

    /**
     * {@code POST  /decision-definitions} : Create a new Definition.
     *
     * @param decisionDeploymentDTO the decisionDeploymentDTO to deploy.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new decisionDeploymentDTO, or with status {@code 400 (Bad Request)} if the processDeploymentDTO has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin/process-deployment/deploy")
    public ResponseEntity<Void> deploy(@RequestBody DecisionDeploymentDTO decisionDeploymentDTO) throws URISyntaxException {
        log.debug("REST request to deploy ProcessDeployment : {}", decisionDeploymentDTO);
        DecisionDeploymentDTO result = decisionDeploymentService.deploy(decisionDeploymentDTO);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCamundaDecisionDefinitionId()))
            .build();
    }

    /**
     * {@code GET  /decision-definition-deployment/:id} : get the decisionDeployment.
     *
     * @param id the id of the decisionDeployment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @GetMapping("/decision-deployment/{id}")
    public ResponseEntity<DecisionDeploymentDTO> getProcessDeployment(@PathVariable Long id) {
        log.debug("REST request to get ProcessDeployment : {}", id);
        Optional<DecisionDeploymentDTO> result = decisionDeploymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /decision-definition-deployment/:id} : get the decisionDeployment.
     *
     * @param id the id of the decisionDeployment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @GetMapping("/decision-deployment/{id}/dmnModel")
    public ResponseEntity<DecisionDeploymentDmnModelDTO> getDecisionDeploymentDmnModel(@PathVariable Long id) {
        log.debug("REST request to get ProcessDeployment : {}", id);
        Optional<DecisionDeploymentDmnModelDTO> result = decisionDeploymentService.findDmnModel(id);
        return ResponseUtil.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /process-definition-deployment/:id/active} : active the "id" processDeployment.
     *
     * @param id the id of the processDeployment to inactive.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @GetMapping("/decision-deployment/{id}/active")
    public ResponseEntity<Void> activeDecisionDeployment(@PathVariable Long id) {
        log.debug("REST request to inactive DecisionDeployment : {}", id);
        decisionDeploymentService.active(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /decision-definition-deployment/:id/inactive} : inactive the "id" decisionDeployment.
     *
     * @param id the id of the decisionDeployment to inactive.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @GetMapping("/decision-deployment/{id}/inactive")
    public ResponseEntity<Void> inactiveDecisionDeployment(@PathVariable Long id) {
        log.debug("REST request to inactive DecisionDeployment : {}", id);
        decisionDeploymentService.inactive(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PutMapping("/admin/decision-deployment/{id}/properties")
    public ResponseEntity<Void> saveProperties(@PathVariable Long id, @RequestBody Map<String, String> properties) {
        log.debug("REST request to save ProcessDeployment properties: {}", id);
        decisionDeploymentService.saveProperties(id, properties);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME + ".properties", id.toString()))
            .build();
    }
}
