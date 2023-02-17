package com.mycompany.myapp.repository;

import br.com.loginlogistica.domain.ProcessDefinition;
import java.util.Optional;

import com.mycompany.myapp.domain.ProcessDefinition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProcessDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessDefinitionRepository extends JpaRepository<ProcessDefinition, Long> {
    Optional<ProcessDefinition> findByBpmnProcessDefinitionId(String bpmnProcessDefinitionId);
}
