package org.agilekip.tutorials.travelcomplete.repository;

import org.agilekip.tutorials.travelcomplete.domain.TaskSelectHotel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaskSelectHotel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskSelectHotelRepository extends JpaRepository<TaskSelectHotel, Long> {}
