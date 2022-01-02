package org.agilekip.tutorials.travelcomplete.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelcomplete.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaskSelectCarTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskSelectCar.class);
        TaskSelectCar taskSelectCar1 = new TaskSelectCar();
        taskSelectCar1.setId(1L);
        TaskSelectCar taskSelectCar2 = new TaskSelectCar();
        taskSelectCar2.setId(taskSelectCar1.getId());
        assertThat(taskSelectCar1).isEqualTo(taskSelectCar2);
        taskSelectCar2.setId(2L);
        assertThat(taskSelectCar1).isNotEqualTo(taskSelectCar2);
        taskSelectCar1.setId(null);
        assertThat(taskSelectCar1).isNotEqualTo(taskSelectCar2);
    }
}
