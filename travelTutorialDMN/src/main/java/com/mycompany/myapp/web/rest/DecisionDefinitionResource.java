package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.DecisionDefinitionService;
import com.mycompany.myapp.service.DecisionDeploymentService;
import com.mycompany.myapp.service.dto.DecisionDefinitionDTO;
import com.mycompany.myapp.service.dto.DecisionDeploymentDTO;
import java.util.List;
import java.util.Optional;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.TaskInstanceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.DecisionDefinition}.
 */

@RestController
@RequestMapping("/api")
public class DecisionDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(DecisionDefinitionResource.class);
    private static final String ENTITY_NAME = "decisionDefinition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DecisionDefinitionService decisionDefinitionService;

    private final DecisionDeploymentService decisionDeploymentService;

    private final TaskInstanceService taskInstanceService;

    public DecisionDefinitionResource(
        DecisionDefinitionService decisionDefinitionService,
        DecisionDeploymentService decisionDeploymentService,
        TaskInstanceService taskInstanceService
    ) {
        this.decisionDefinitionService = decisionDefinitionService;
        this.decisionDeploymentService = decisionDeploymentService;
        this.taskInstanceService = taskInstanceService;
    }

    /**
     * {@code GET  /decision-definitions} : get all the decisionDefinitions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of decisionDefinitions in body.
     */
    @GetMapping("/decision-definitions")
    public List<DecisionDefinitionDTO> getAllDecisionDefinitions() {
        log.debug("REST request to get all DecisionDefinitions");
        return decisionDefinitionService.findAll();
    }

    /**
     * {@code GET  /decision-definitions/:id} : get the "id" decisionDefinition.
     *
     * @param idOrDmnDecisionDefinitionId the id of the decisionDefinitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the decisionDefinitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/decision-definitions/{idOrDmnDecisionDefinitionId}")
    public ResponseEntity<DecisionDefinitionDTO> getDecisionDefinition(@PathVariable String idOrDmnDecisionDefinitionId) {
        log.debug("REST request to get DecisionDefinition : {}", idOrDmnDecisionDefinitionId);
        Optional<DecisionDefinitionDTO> decisionDefinitionDTO = decisionDefinitionService.findByIdOrDmnDecisionDefinitionId(
            idOrDmnDecisionDefinitionId
        );
        return ResponseUtil.wrapOrNotFound(decisionDefinitionDTO);
    }

    /**
     * {@code GET  /decision-definitions/:idOrDmnDecisionDefinitionId/tenants} : get the "idOrDmnDecisionDefinitionId" decisionDefinition.
     *
     * @param idOrDmnDecisionDefinitionId the id of the decisionDefinitionDTO associated with Tenants.
     * @return the list of tenantsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/decision-definitions/{idOrDmnDecisionDefinitionId}/active-deployments")
    public List<DecisionDeploymentDTO> getActiveDecisionDeployments(@PathVariable String idOrDmnDecisionDefinitionId) {
        log.debug("REST request to get Tenants of the DecisionDefinition : {}", idOrDmnDecisionDefinitionId);
        return decisionDeploymentService.findActiveByDecisionDefinition(idOrDmnDecisionDefinitionId);
    }

    /**
     * {@code GET  /decision-definitions/:idOrDmnDecisionDefinitionId/deployments} : get the "idOrDmnDecisionDefinitionId" decisionDefinition.
     *
     * @param idOrDmnDecisionDefinitionId the id of the decisionDefinitionDTO owner of the DecisionDeployments.
     * @return the list of decisionInstanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/decision-definitions/{idOrDmnDecisionDefinitionId}/deployments")
    public List<DecisionDeploymentDTO> getDecisionDeployments(@PathVariable String idOrDmnDecisionDefinitionId) {
        log.debug("REST request to get DecisionDeployments of the DecisionDefinition : {}", idOrDmnDecisionDefinitionId);
        return decisionDeploymentService.findByDecisionDefinition(idOrDmnDecisionDefinitionId);
    }

    /**
     * {@code GET  /decision-definitions/:idOrDmnDecisionDefinitionId/instances} : get the "idOrDmnDecisionDefinitionId" decisionDefinition.
     *
     * @param idOrDmnDecisionDefinitionId the id of the DefinitionDTO owner of the DecisionInstances.
     * @return the list of decisionInstanceDTO, or with status {@code 404 (Not Found)}.
     */
    //    @GetMapping("/decision-definitions/{idOrBpmnDecisionDefinitionId}/instances")
    //    public List<DecisionInstanceDTO> getDecisionInstances(@PathVariable String idOrDmnDecisionDefinitionId) {
    //        log.debug("REST request to get DecisionInstances of the DecisionDefinition : {}", idOrDmnDecisionDefinitionId);
    //        return decisionInstanceService.findByDecisionDefinition(idOrBpmnDecisionDefinitionId);
    //    }

    /**
     * {@code GET  /decision-definition/:idOrDmnDecisionDefinitionId/candidateTenants} : get the "id" decisionInstance.
     *
     * @param idOrDmnDecisionDefinitionId the id or dmnDecisionDefinitionId of the decisionDefinition owner of the TaskInstances.
     * @return the list of candidateTenants, or with status {@code 404 (Not Found)}.**/
    //    @GetMapping("/decision-definitions/{idOrDmnDecisionDefinitionId}/candidateTenantsCurrentUser")
    //    public List<TenantDTO> getCandidateTenantsCurrentUser(@PathVariable String idOrDmnDecisionDefinitionId) {
    //        log.debug("REST request to get TaskInstances of the DecisionDefinition : {}", idOrDmnDecisionDefinitionId);
    //        return decisionInstanceService.getCandidateTenantsCurrentUser(idOrDmnDecisionDefinitionId);
    //    }

    /**
     * {@code GET  /decision-definition/:idOrDmnDecisionDefinitionId/tasks} : get the "id" decisionInstance.
     *
     * @param idOrDmnDecisionDefinitionId the id or dmnDecisionDefinitionId of the decisionDefinition owner of the TaskInstances.
     * @return the list of decisionInstanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/decision-definition/{idOrDmnDecisionDefinitionId}/tasks")
    public List<TaskInstanceDTO> getTaskInstances(@PathVariable String idOrDmnDecisionDefinitionId) {
        log.debug("REST request to get TaskInstances of the DecisionDefinition : {}", idOrDmnDecisionDefinitionId);
        return taskInstanceService.findByProcessDefinition(idOrDmnDecisionDefinitionId);
    }

    /**
     * {@code DELETE  /decision-definitions/:id} : delete the "id" decisionDefinition.
     *
     * @param id the id of the decisionDefinitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin/decision-definitions/{id}")
    public ResponseEntity<Void> deleteDecisionDefinition(@PathVariable Long id) {
        log.debug("REST request to delete DecisionDefinition : {}", id);
        decisionDefinitionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
