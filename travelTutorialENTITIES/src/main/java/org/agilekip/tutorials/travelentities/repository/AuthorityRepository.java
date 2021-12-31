package org.agilekip.tutorials.travelentities.repository;

import org.agilekip.tutorials.travelentities.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
