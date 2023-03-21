package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.GenericTimelineProcess;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GenericTimelineProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenericTimelineProcessRepository extends JpaRepository<GenericTimelineProcess, Long> {
    Optional<GenericTimelineProcess> findByProcessInstanceId(Long processInstanceId);
}
