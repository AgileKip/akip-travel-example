package org.agilekip.tutorials.travelcomplete.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelcomplete.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class X1DTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(X1DTO.class);
        X1DTO x1DTO1 = new X1DTO();
        x1DTO1.setId(1L);
        X1DTO x1DTO2 = new X1DTO();
        assertThat(x1DTO1).isNotEqualTo(x1DTO2);
        x1DTO2.setId(x1DTO1.getId());
        assertThat(x1DTO1).isEqualTo(x1DTO2);
        x1DTO2.setId(2L);
        assertThat(x1DTO1).isNotEqualTo(x1DTO2);
        x1DTO1.setId(null);
        assertThat(x1DTO1).isNotEqualTo(x1DTO2);
    }
}
