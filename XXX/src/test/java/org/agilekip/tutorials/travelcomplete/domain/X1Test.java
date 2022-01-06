package org.agilekip.tutorials.travelcomplete.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelcomplete.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class X1Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(X1.class);
        X1 x11 = new X1();
        x11.setId(1L);
        X1 x12 = new X1();
        x12.setId(x11.getId());
        assertThat(x11).isEqualTo(x12);
        x12.setId(2L);
        assertThat(x11).isNotEqualTo(x12);
        x11.setId(null);
        assertThat(x11).isNotEqualTo(x12);
    }
}
