package org.agilekip.tutorials.travelpool.repository;

import org.agilekip.tutorials.travelpool.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
