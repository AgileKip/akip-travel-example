package org.agilekip.tutorials.travelentities2.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelentities2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HotelCompanyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HotelCompany.class);
        HotelCompany hotelCompany1 = new HotelCompany();
        hotelCompany1.setId(1L);
        HotelCompany hotelCompany2 = new HotelCompany();
        hotelCompany2.setId(hotelCompany1.getId());
        assertThat(hotelCompany1).isEqualTo(hotelCompany2);
        hotelCompany2.setId(2L);
        assertThat(hotelCompany1).isNotEqualTo(hotelCompany2);
        hotelCompany1.setId(null);
        assertThat(hotelCompany1).isNotEqualTo(hotelCompany2);
    }
}
