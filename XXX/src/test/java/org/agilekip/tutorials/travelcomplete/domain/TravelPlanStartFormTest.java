package org.agilekip.tutorials.travelcomplete.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelcomplete.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TravelPlanStartFormTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TravelPlanStartForm.class);
        TravelPlanStartForm travelPlanStartForm1 = new TravelPlanStartForm();
        travelPlanStartForm1.setId(1L);
        TravelPlanStartForm travelPlanStartForm2 = new TravelPlanStartForm();
        travelPlanStartForm2.setId(travelPlanStartForm1.getId());
        assertThat(travelPlanStartForm1).isEqualTo(travelPlanStartForm2);
        travelPlanStartForm2.setId(2L);
        assertThat(travelPlanStartForm1).isNotEqualTo(travelPlanStartForm2);
        travelPlanStartForm1.setId(null);
        assertThat(travelPlanStartForm1).isNotEqualTo(travelPlanStartForm2);
    }
}
