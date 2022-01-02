package org.agilekip.tutorials.travelcomplete.repository;

import org.agilekip.tutorials.travelcomplete.domain.CarRentalCompany;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CarRentalCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarRentalCompanyRepository extends JpaRepository<CarRentalCompany, Long> {}
