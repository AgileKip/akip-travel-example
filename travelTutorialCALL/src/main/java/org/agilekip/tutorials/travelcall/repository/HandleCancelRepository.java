package org.agilekip.tutorials.travelcall.repository;

import org.agilekip.tutorials.travelcall.domain.HandleCancel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HandleCancel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HandleCancelRepository extends JpaRepository<HandleCancel, Long> {}
