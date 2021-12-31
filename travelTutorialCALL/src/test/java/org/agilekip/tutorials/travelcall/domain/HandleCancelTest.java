package org.agilekip.tutorials.travelcall.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelcall.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HandleCancelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HandleCancel.class);
        HandleCancel handleCancel1 = new HandleCancel();
        handleCancel1.setId(1L);
        HandleCancel handleCancel2 = new HandleCancel();
        handleCancel2.setId(handleCancel1.getId());
        assertThat(handleCancel1).isEqualTo(handleCancel2);
        handleCancel2.setId(2L);
        assertThat(handleCancel1).isNotEqualTo(handleCancel2);
        handleCancel1.setId(null);
        assertThat(handleCancel1).isNotEqualTo(handleCancel2);
    }
}
