package org.agilekip.tutorials.travelentities3.repository;

import org.agilekip.tutorials.travelentities3.domain.HotelCompany;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HotelCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HotelCompanyRepository extends JpaRepository<HotelCompany, Long> {}
