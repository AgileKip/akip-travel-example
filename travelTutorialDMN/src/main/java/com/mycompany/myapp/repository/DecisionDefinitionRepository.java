package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DecisionDefinition;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DecisionDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DecisionDefinitionRepository extends JpaRepository<DecisionDefinition, Long> {
    Optional<DecisionDefinition> findByDmnDecisionDefinitionId(String dmnDecisionDefinitionId);
}
