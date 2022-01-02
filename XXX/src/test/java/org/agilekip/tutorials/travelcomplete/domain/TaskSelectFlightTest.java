package org.agilekip.tutorials.travelcomplete.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelcomplete.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaskSelectFlightTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskSelectFlight.class);
        TaskSelectFlight taskSelectFlight1 = new TaskSelectFlight();
        taskSelectFlight1.setId(1L);
        TaskSelectFlight taskSelectFlight2 = new TaskSelectFlight();
        taskSelectFlight2.setId(taskSelectFlight1.getId());
        assertThat(taskSelectFlight1).isEqualTo(taskSelectFlight2);
        taskSelectFlight2.setId(2L);
        assertThat(taskSelectFlight1).isNotEqualTo(taskSelectFlight2);
        taskSelectFlight1.setId(null);
        assertThat(taskSelectFlight1).isNotEqualTo(taskSelectFlight2);
    }
}
