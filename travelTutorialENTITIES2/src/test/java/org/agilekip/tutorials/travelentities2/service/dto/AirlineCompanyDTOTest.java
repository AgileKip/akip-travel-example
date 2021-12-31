package org.agilekip.tutorials.travelentities2.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelentities2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AirlineCompanyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AirlineCompanyDTO.class);
        AirlineCompanyDTO airlineCompanyDTO1 = new AirlineCompanyDTO();
        airlineCompanyDTO1.setId(1L);
        AirlineCompanyDTO airlineCompanyDTO2 = new AirlineCompanyDTO();
        assertThat(airlineCompanyDTO1).isNotEqualTo(airlineCompanyDTO2);
        airlineCompanyDTO2.setId(airlineCompanyDTO1.getId());
        assertThat(airlineCompanyDTO1).isEqualTo(airlineCompanyDTO2);
        airlineCompanyDTO2.setId(2L);
        assertThat(airlineCompanyDTO1).isNotEqualTo(airlineCompanyDTO2);
        airlineCompanyDTO1.setId(null);
        assertThat(airlineCompanyDTO1).isNotEqualTo(airlineCompanyDTO2);
    }
}
