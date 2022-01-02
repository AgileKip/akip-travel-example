package org.agilekip.tutorials.travelcomplete.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelcomplete.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaskSelectFlightDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskSelectFlightDTO.class);
        TaskSelectFlightDTO taskSelectFlightDTO1 = new TaskSelectFlightDTO();
        taskSelectFlightDTO1.setId(1L);
        TaskSelectFlightDTO taskSelectFlightDTO2 = new TaskSelectFlightDTO();
        assertThat(taskSelectFlightDTO1).isNotEqualTo(taskSelectFlightDTO2);
        taskSelectFlightDTO2.setId(taskSelectFlightDTO1.getId());
        assertThat(taskSelectFlightDTO1).isEqualTo(taskSelectFlightDTO2);
        taskSelectFlightDTO2.setId(2L);
        assertThat(taskSelectFlightDTO1).isNotEqualTo(taskSelectFlightDTO2);
        taskSelectFlightDTO1.setId(null);
        assertThat(taskSelectFlightDTO1).isNotEqualTo(taskSelectFlightDTO2);
    }
}
