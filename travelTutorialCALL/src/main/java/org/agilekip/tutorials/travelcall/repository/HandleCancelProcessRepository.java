package org.agilekip.tutorials.travelcall.repository;

import java.util.Optional;
import org.agilekip.tutorials.travelcall.domain.HandleCancelProcess;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HandleCancelProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HandleCancelProcessRepository extends JpaRepository<HandleCancelProcess, Long> {
    Optional<HandleCancelProcess> findByProcessInstanceId(Long processInstanceId);
}
