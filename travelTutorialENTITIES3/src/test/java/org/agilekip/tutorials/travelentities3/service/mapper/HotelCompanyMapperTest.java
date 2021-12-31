package org.agilekip.tutorials.travelentities3.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HotelCompanyMapperTest {

    private HotelCompanyMapper hotelCompanyMapper;

    @BeforeEach
    public void setUp() {
        hotelCompanyMapper = new HotelCompanyMapperImpl();
    }
}
