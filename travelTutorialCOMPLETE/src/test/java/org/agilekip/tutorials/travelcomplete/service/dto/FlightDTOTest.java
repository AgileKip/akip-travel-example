package org.agilekip.tutorials.travelcomplete.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelcomplete.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FlightDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FlightDTO.class);
        FlightDTO flightDTO1 = new FlightDTO();
        flightDTO1.setId(1L);
        FlightDTO flightDTO2 = new FlightDTO();
        assertThat(flightDTO1).isNotEqualTo(flightDTO2);
        flightDTO2.setId(flightDTO1.getId());
        assertThat(flightDTO1).isEqualTo(flightDTO2);
        flightDTO2.setId(2L);
        assertThat(flightDTO1).isNotEqualTo(flightDTO2);
        flightDTO1.setId(null);
        assertThat(flightDTO1).isNotEqualTo(flightDTO2);
    }
}
