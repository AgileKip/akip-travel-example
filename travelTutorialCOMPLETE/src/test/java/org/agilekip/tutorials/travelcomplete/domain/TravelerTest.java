package org.agilekip.tutorials.travelcomplete.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelcomplete.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TravelerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Traveler.class);
        Traveler traveler1 = new Traveler();
        traveler1.setId(1L);
        Traveler traveler2 = new Traveler();
        traveler2.setId(traveler1.getId());
        assertThat(traveler1).isEqualTo(traveler2);
        traveler2.setId(2L);
        assertThat(traveler1).isNotEqualTo(traveler2);
        traveler1.setId(null);
        assertThat(traveler1).isNotEqualTo(traveler2);
    }
}
