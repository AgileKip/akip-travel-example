package org.agilekip.tutorials.travelsrv.repository;

import org.agilekip.tutorials.travelsrv.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
