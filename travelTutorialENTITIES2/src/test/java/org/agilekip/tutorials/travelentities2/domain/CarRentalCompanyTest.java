package org.agilekip.tutorials.travelentities2.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.agilekip.tutorials.travelentities2.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CarRentalCompanyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarRentalCompany.class);
        CarRentalCompany carRentalCompany1 = new CarRentalCompany();
        carRentalCompany1.setId(1L);
        CarRentalCompany carRentalCompany2 = new CarRentalCompany();
        carRentalCompany2.setId(carRentalCompany1.getId());
        assertThat(carRentalCompany1).isEqualTo(carRentalCompany2);
        carRentalCompany2.setId(2L);
        assertThat(carRentalCompany1).isNotEqualTo(carRentalCompany2);
        carRentalCompany1.setId(null);
        assertThat(carRentalCompany1).isNotEqualTo(carRentalCompany2);
    }
}
