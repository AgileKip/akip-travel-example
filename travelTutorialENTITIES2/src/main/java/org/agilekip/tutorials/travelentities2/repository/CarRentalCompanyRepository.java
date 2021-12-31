package org.agilekip.tutorials.travelentities2.repository;

import org.agilekip.tutorials.travelentities2.domain.CarRentalCompany;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CarRentalCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarRentalCompanyRepository extends JpaRepository<CarRentalCompany, Long> {}
