package org.agilekip.tutorials.travelmkr.repository;

import org.agilekip.tutorials.travelmkr.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
