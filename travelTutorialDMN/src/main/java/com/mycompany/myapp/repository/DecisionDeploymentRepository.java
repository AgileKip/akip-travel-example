package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DecisionDeployment;
import com.mycompany.myapp.domain.enumeration.StatusDecisionDeployment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DecisionDeployment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DecisionDeploymentRepository extends JpaRepository<DecisionDeployment, Long> {
    List<DecisionDeployment> findByDecisionDefinitionId(Long processDefinitionId);

    Optional<DecisionDeployment> findByCamundaDecisionDefinitionId(String camundaDecisionDefinitionId);

    List<DecisionDeployment> findByDecisionDefinitionIdAndStatusAndTenantIsNull(Long processDefinitionId, StatusDecisionDeployment status);

    List<DecisionDeployment> findByDecisionDefinitionIdAndStatusAndTenantId(
        Long decisionDefinitionId,
        StatusDecisionDeployment status,
        Long tenantId
    );

    @Modifying
    @Query("update DecisionDeployment set status = ?1 where id = ?2")
    void updateStatusById(StatusDecisionDeployment status, Long id);

    @Modifying
    @Query("update DecisionDeployment set activationDate = ?1 where id = ?2")
    void updateActivationDateById(LocalDateTime localDateTime, Long id);

    @Modifying
    @Query("update DecisionDeployment set inactivationDate = ?1 where id = ?2")
    void updateInactivationDateById(LocalDateTime localDateTime, Long id);

    @Query(
        "from DecisionDeployment where decisionDefinition.id = ?1 and tenant is null and status = com.mycompany.myapp.domain.enumeration.StatusDecisionDeployment.ACTIVE"
    )
    List<DecisionDeployment> findByDecisionDefinitionIdAndStatusIsActiveAndTenantIsNull(Long decisionDefinitionId);

    @Query(
        "from DecisionDeployment where decisionDefinition.id = ?1 and status = com.mycompany.myapp.domain.enumeration.StatusDecisionDeployment.ACTIVE"
    )
    List<DecisionDeployment> findByDecisionDefinitionIdAndStatusIsActive(Long decisionDefinitionId);

    @Query(
        "from DecisionDeployment where decisionDefinition.id = ?1 and tenant is not null and status = com.mycompany.myapp.domain.enumeration.StatusDecisionDeployment.ACTIVE"
    )
    List<DecisionDeployment> findByDecisionDefinitionIdAndStatusIsActiveAndTenantIsNotNull(Long decisionDefinitionId);

    @Query(
        "from DecisionDeployment where decisionDefinition.id = ?1 and tenant.id = ?2 and status = com.mycompany.myapp.domain.enumeration.StatusDecisionDeployment.ACTIVE"
    )
    Optional<DecisionDeployment> findByDecisionDefinitionIdAndStatusIsActiveAndTenantId(Long decisionDefinitionId, Long tenantId);
}
