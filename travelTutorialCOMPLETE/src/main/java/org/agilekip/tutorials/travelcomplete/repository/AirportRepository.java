package org.agilekip.tutorials.travelcomplete.repository;

import org.agilekip.tutorials.travelcomplete.domain.Airport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Airport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {}
