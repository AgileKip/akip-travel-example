package org.agilekip.tutorials.travelcall.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelcall.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HandleCancelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HandleCancelDTO.class);
        HandleCancelDTO handleCancelDTO1 = new HandleCancelDTO();
        handleCancelDTO1.setId(1L);
        HandleCancelDTO handleCancelDTO2 = new HandleCancelDTO();
        assertThat(handleCancelDTO1).isNotEqualTo(handleCancelDTO2);
        handleCancelDTO2.setId(handleCancelDTO1.getId());
        assertThat(handleCancelDTO1).isEqualTo(handleCancelDTO2);
        handleCancelDTO2.setId(2L);
        assertThat(handleCancelDTO1).isNotEqualTo(handleCancelDTO2);
        handleCancelDTO1.setId(null);
        assertThat(handleCancelDTO1).isNotEqualTo(handleCancelDTO2);
    }
}
