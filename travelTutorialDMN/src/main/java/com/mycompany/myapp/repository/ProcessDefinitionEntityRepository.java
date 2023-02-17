package com.mycompany.myapp.repository;

import br.com.loginlogistica.domain.ProcessDefinitionEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProcessDefinitionEntity entity.
 */
@Repository
public interface ProcessDefinitionEntityRepository extends JpaRepository<ProcessDefinitionEntity, Long> {
    Optional<ProcessDefinitionEntity> findByEntityNameAndEntityIdAndProcessDefinitionBpmnProcessDefinitionId(
        String entityName,
        Long entityId,
        String bpmnProcessDefinitionId
    );

    List<ProcessDefinitionEntity> findByProcessDefinitionBpmnProcessDefinitionIdAndEntityName(
        String bpmnProcessDefinitionId,
        String entityName
    );

    List<ProcessDefinitionEntity> findByProcessDefinitionBpmnProcessDefinitionId(String bpmnProcessDefinitionId);
}
