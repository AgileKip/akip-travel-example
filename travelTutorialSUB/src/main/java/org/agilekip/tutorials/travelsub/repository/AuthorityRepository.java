package org.agilekip.tutorials.travelsub.repository;

import org.agilekip.tutorials.travelsub.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
