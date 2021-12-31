package org.agilekip.tutorials.travelcall.repository;

import org.agilekip.tutorials.travelcall.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
