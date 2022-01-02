package org.agilekip.tutorials.travelcomplete.repository;

import org.agilekip.tutorials.travelcomplete.domain.HotelRoom;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HotelRoom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom, Long> {}
