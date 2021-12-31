package org.agilekip.tutorials.traveltimer2.repository;

import org.agilekip.tutorials.traveltimer2.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
