package org.agilekip.tutorials.travelcomplete.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelcomplete.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaskSelectCarDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskSelectCarDTO.class);
        TaskSelectCarDTO taskSelectCarDTO1 = new TaskSelectCarDTO();
        taskSelectCarDTO1.setId(1L);
        TaskSelectCarDTO taskSelectCarDTO2 = new TaskSelectCarDTO();
        assertThat(taskSelectCarDTO1).isNotEqualTo(taskSelectCarDTO2);
        taskSelectCarDTO2.setId(taskSelectCarDTO1.getId());
        assertThat(taskSelectCarDTO1).isEqualTo(taskSelectCarDTO2);
        taskSelectCarDTO2.setId(2L);
        assertThat(taskSelectCarDTO1).isNotEqualTo(taskSelectCarDTO2);
        taskSelectCarDTO1.setId(null);
        assertThat(taskSelectCarDTO1).isNotEqualTo(taskSelectCarDTO2);
    }
}
