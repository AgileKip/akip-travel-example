package org.agilekip.tutorials.travelcomplete.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FlightMapperTest {

    private FlightMapper flightMapper;

    @BeforeEach
    public void setUp() {
        flightMapper = new FlightMapperImpl();
    }
}
