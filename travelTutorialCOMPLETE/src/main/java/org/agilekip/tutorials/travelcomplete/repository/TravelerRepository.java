package org.agilekip.tutorials.travelcomplete.repository;

import org.agilekip.tutorials.travelcomplete.domain.Traveler;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Traveler entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TravelerRepository extends JpaRepository<Traveler, Long> {}
