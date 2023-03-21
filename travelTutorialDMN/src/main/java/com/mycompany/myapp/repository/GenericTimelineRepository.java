package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.GenericTimeline;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GenericTimeline entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenericTimelineRepository extends JpaRepository<GenericTimeline, Long> {}
