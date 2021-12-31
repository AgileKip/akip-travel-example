package org.agilekip.tutorials.travelor.repository;

import org.agilekip.tutorials.travelor.domain.TravelPlan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TravelPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long> {}
