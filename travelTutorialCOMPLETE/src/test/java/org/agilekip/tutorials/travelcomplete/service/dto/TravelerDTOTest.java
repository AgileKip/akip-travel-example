package org.agilekip.tutorials.travelcomplete.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelcomplete.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TravelerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TravelerDTO.class);
        TravelerDTO travelerDTO1 = new TravelerDTO();
        travelerDTO1.setId(1L);
        TravelerDTO travelerDTO2 = new TravelerDTO();
        assertThat(travelerDTO1).isNotEqualTo(travelerDTO2);
        travelerDTO2.setId(travelerDTO1.getId());
        assertThat(travelerDTO1).isEqualTo(travelerDTO2);
        travelerDTO2.setId(2L);
        assertThat(travelerDTO1).isNotEqualTo(travelerDTO2);
        travelerDTO1.setId(null);
        assertThat(travelerDTO1).isNotEqualTo(travelerDTO2);
    }
}
