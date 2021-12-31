package org.agilekip.tutorials.travelentities3.repository;

import org.agilekip.tutorials.travelentities3.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
