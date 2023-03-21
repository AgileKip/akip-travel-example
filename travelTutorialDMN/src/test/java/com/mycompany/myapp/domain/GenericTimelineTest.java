package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GenericTimelineTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GenericTimeline.class);
        GenericTimeline genericTimeline1 = new GenericTimeline();
        genericTimeline1.setId(1L);
        GenericTimeline genericTimeline2 = new GenericTimeline();
        genericTimeline2.setId(genericTimeline1.getId());
        assertThat(genericTimeline1).isEqualTo(genericTimeline2);
        genericTimeline2.setId(2L);
        assertThat(genericTimeline1).isNotEqualTo(genericTimeline2);
        genericTimeline1.setId(null);
        assertThat(genericTimeline1).isNotEqualTo(genericTimeline2);
    }
}
