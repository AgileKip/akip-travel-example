package org.agilekip.tutorials.travelcomplete.repository;

import org.agilekip.tutorials.travelcomplete.domain.TaskSelectFlight;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaskSelectFlight entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskSelectFlightRepository extends JpaRepository<TaskSelectFlight, Long> {}
