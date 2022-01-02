package org.agilekip.tutorials.travelcomplete.repository;

import org.agilekip.tutorials.travelcomplete.domain.Flight;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Flight entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {}
