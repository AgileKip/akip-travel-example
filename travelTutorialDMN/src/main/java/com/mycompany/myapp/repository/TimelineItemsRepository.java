package com.mycompany.myapp.repository;

import java.util.List;
import org.akip.domain.TaskInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimelineItemsRepository extends JpaRepository<TaskInstance, Long> {
    List<TaskInstance> findByProcessInstanceId(Long processInstanceId);
}
