package org.agilekip.tutorials.travelcomplete.repository;

import org.agilekip.tutorials.travelcomplete.domain.TravelPlanStartForm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TravelPlanStartForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TravelPlanStartFormRepository extends JpaRepository<TravelPlanStartForm, Long> {}
