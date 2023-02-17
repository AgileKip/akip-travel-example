package com.mycompany.myapp.repository;

import br.com.loginlogistica.domain.ProcessDeploymentEntity;
import java.util.List;
import java.util.Optional;

import com.mycompany.myapp.domain.ProcessDeploymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProcessDeploymentEntity entity.
 */
@Repository
public interface ProcessDeploymentEntityRepository extends JpaRepository<ProcessDeploymentEntity, Long> {
    Optional<ProcessDeploymentEntity> findByEntityNameAndEntityIdAndProcessDeploymentId(
        String entityName,
        Long entityId,
        Long processDeploymentId
    );

    List<ProcessDeploymentEntity> findByProcessDeploymentId(Long processDeploymentId);
}
