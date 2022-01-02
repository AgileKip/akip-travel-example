package org.agilekip.tutorials.travelcomplete.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AirportMapperTest {

    private AirportMapper airportMapper;

    @BeforeEach
    public void setUp() {
        airportMapper = new AirportMapperImpl();
    }
}
