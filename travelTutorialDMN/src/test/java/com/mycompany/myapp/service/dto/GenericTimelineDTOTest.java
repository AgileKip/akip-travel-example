package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GenericTimelineDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GenericTimelineDTO.class);
        GenericTimelineDTO genericTimelineDTO1 = new GenericTimelineDTO();
        genericTimelineDTO1.setId(1L);
        GenericTimelineDTO genericTimelineDTO2 = new GenericTimelineDTO();
        assertThat(genericTimelineDTO1).isNotEqualTo(genericTimelineDTO2);
        genericTimelineDTO2.setId(genericTimelineDTO1.getId());
        assertThat(genericTimelineDTO1).isEqualTo(genericTimelineDTO2);
        genericTimelineDTO2.setId(2L);
        assertThat(genericTimelineDTO1).isNotEqualTo(genericTimelineDTO2);
        genericTimelineDTO1.setId(null);
        assertThat(genericTimelineDTO1).isNotEqualTo(genericTimelineDTO2);
    }
}
