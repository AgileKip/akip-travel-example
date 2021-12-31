package org.agilekip.tutorials.travelemsg.repository;

import org.agilekip.tutorials.travelemsg.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
