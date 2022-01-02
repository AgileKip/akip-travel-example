package org.agilekip.tutorials.travelcomplete.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelcomplete.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaskSelectHotelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskSelectHotel.class);
        TaskSelectHotel taskSelectHotel1 = new TaskSelectHotel();
        taskSelectHotel1.setId(1L);
        TaskSelectHotel taskSelectHotel2 = new TaskSelectHotel();
        taskSelectHotel2.setId(taskSelectHotel1.getId());
        assertThat(taskSelectHotel1).isEqualTo(taskSelectHotel2);
        taskSelectHotel2.setId(2L);
        assertThat(taskSelectHotel1).isNotEqualTo(taskSelectHotel2);
        taskSelectHotel1.setId(null);
        assertThat(taskSelectHotel1).isNotEqualTo(taskSelectHotel2);
    }
}
