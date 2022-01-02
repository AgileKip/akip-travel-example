package org.agilekip.tutorials.travelcomplete.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelcomplete.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TravelPlanStartFormDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TravelPlanStartFormDTO.class);
        TravelPlanStartFormDTO travelPlanStartFormDTO1 = new TravelPlanStartFormDTO();
        travelPlanStartFormDTO1.setId(1L);
        TravelPlanStartFormDTO travelPlanStartFormDTO2 = new TravelPlanStartFormDTO();
        assertThat(travelPlanStartFormDTO1).isNotEqualTo(travelPlanStartFormDTO2);
        travelPlanStartFormDTO2.setId(travelPlanStartFormDTO1.getId());
        assertThat(travelPlanStartFormDTO1).isEqualTo(travelPlanStartFormDTO2);
        travelPlanStartFormDTO2.setId(2L);
        assertThat(travelPlanStartFormDTO1).isNotEqualTo(travelPlanStartFormDTO2);
        travelPlanStartFormDTO1.setId(null);
        assertThat(travelPlanStartFormDTO1).isNotEqualTo(travelPlanStartFormDTO2);
    }
}
