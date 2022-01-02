package org.agilekip.tutorials.travelcomplete.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelcomplete.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HotelRoomTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HotelRoom.class);
        HotelRoom hotelRoom1 = new HotelRoom();
        hotelRoom1.setId(1L);
        HotelRoom hotelRoom2 = new HotelRoom();
        hotelRoom2.setId(hotelRoom1.getId());
        assertThat(hotelRoom1).isEqualTo(hotelRoom2);
        hotelRoom2.setId(2L);
        assertThat(hotelRoom1).isNotEqualTo(hotelRoom2);
        hotelRoom1.setId(null);
        assertThat(hotelRoom1).isNotEqualTo(hotelRoom2);
    }
}
