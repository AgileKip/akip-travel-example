package org.agilekip.tutorials.travelcomplete.repository;

import org.agilekip.tutorials.travelcomplete.domain.TaskSelectCar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaskSelectCar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskSelectCarRepository extends JpaRepository<TaskSelectCar, Long> {}
