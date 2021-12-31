package org.agilekip.tutorials.travelentities3.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelentities3.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HotelCompanyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HotelCompanyDTO.class);
        HotelCompanyDTO hotelCompanyDTO1 = new HotelCompanyDTO();
        hotelCompanyDTO1.setId(1L);
        HotelCompanyDTO hotelCompanyDTO2 = new HotelCompanyDTO();
        assertThat(hotelCompanyDTO1).isNotEqualTo(hotelCompanyDTO2);
        hotelCompanyDTO2.setId(hotelCompanyDTO1.getId());
        assertThat(hotelCompanyDTO1).isEqualTo(hotelCompanyDTO2);
        hotelCompanyDTO2.setId(2L);
        assertThat(hotelCompanyDTO1).isNotEqualTo(hotelCompanyDTO2);
        hotelCompanyDTO1.setId(null);
        assertThat(hotelCompanyDTO1).isNotEqualTo(hotelCompanyDTO2);
    }
}
