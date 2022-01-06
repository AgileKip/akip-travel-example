package org.agilekip.tutorials.travelcomplete.repository;

import java.util.List;
import java.util.Optional;
import org.agilekip.tutorials.travelcomplete.domain.X1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the X1 entity.
 */
@Repository
public interface X1Repository extends JpaRepository<X1, Long> {
    @Query(value = "select distinct x1 from X1 x1 left join fetch x1.cars", countQuery = "select count(distinct x1) from X1 x1")
    Page<X1> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct x1 from X1 x1 left join fetch x1.cars")
    List<X1> findAllWithEagerRelationships();

    @Query("select x1 from X1 x1 left join fetch x1.cars where x1.id =:id")
    Optional<X1> findOneWithEagerRelationships(@Param("id") Long id);
}
