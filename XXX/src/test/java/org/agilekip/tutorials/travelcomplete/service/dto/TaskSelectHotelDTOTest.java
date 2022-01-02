package org.agilekip.tutorials.travelcomplete.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelcomplete.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaskSelectHotelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskSelectHotelDTO.class);
        TaskSelectHotelDTO taskSelectHotelDTO1 = new TaskSelectHotelDTO();
        taskSelectHotelDTO1.setId(1L);
        TaskSelectHotelDTO taskSelectHotelDTO2 = new TaskSelectHotelDTO();
        assertThat(taskSelectHotelDTO1).isNotEqualTo(taskSelectHotelDTO2);
        taskSelectHotelDTO2.setId(taskSelectHotelDTO1.getId());
        assertThat(taskSelectHotelDTO1).isEqualTo(taskSelectHotelDTO2);
        taskSelectHotelDTO2.setId(2L);
        assertThat(taskSelectHotelDTO1).isNotEqualTo(taskSelectHotelDTO2);
        taskSelectHotelDTO1.setId(null);
        assertThat(taskSelectHotelDTO1).isNotEqualTo(taskSelectHotelDTO2);
    }
}
