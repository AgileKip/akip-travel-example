package org.agilekip.tutorials.travelcomplete.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelcomplete.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HotelRoomDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HotelRoomDTO.class);
        HotelRoomDTO hotelRoomDTO1 = new HotelRoomDTO();
        hotelRoomDTO1.setId(1L);
        HotelRoomDTO hotelRoomDTO2 = new HotelRoomDTO();
        assertThat(hotelRoomDTO1).isNotEqualTo(hotelRoomDTO2);
        hotelRoomDTO2.setId(hotelRoomDTO1.getId());
        assertThat(hotelRoomDTO1).isEqualTo(hotelRoomDTO2);
        hotelRoomDTO2.setId(2L);
        assertThat(hotelRoomDTO1).isNotEqualTo(hotelRoomDTO2);
        hotelRoomDTO1.setId(null);
        assertThat(hotelRoomDTO1).isNotEqualTo(hotelRoomDTO2);
    }
}
