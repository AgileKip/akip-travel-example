package org.agilekip.tutorials.travelcall.repository;

import org.agilekip.tutorials.travelcall.domain.TravelPlan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TravelPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long> {}
