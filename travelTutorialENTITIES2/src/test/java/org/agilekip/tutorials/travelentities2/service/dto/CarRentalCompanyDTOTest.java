package org.agilekip.tutorials.travelentities2.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelentities2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CarRentalCompanyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarRentalCompanyDTO.class);
        CarRentalCompanyDTO carRentalCompanyDTO1 = new CarRentalCompanyDTO();
        carRentalCompanyDTO1.setId(1L);
        CarRentalCompanyDTO carRentalCompanyDTO2 = new CarRentalCompanyDTO();
        assertThat(carRentalCompanyDTO1).isNotEqualTo(carRentalCompanyDTO2);
        carRentalCompanyDTO2.setId(carRentalCompanyDTO1.getId());
        assertThat(carRentalCompanyDTO1).isEqualTo(carRentalCompanyDTO2);
        carRentalCompanyDTO2.setId(2L);
        assertThat(carRentalCompanyDTO1).isNotEqualTo(carRentalCompanyDTO2);
        carRentalCompanyDTO1.setId(null);
        assertThat(carRentalCompanyDTO1).isNotEqualTo(carRentalCompanyDTO2);
    }
}
