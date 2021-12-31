package org.agilekip.tutorials.travelentities2.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TravelPlanMapperTest {

    private TravelPlanMapper travelPlanMapper;

    @BeforeEach
    public void setUp() {
        travelPlanMapper = new TravelPlanMapperImpl();
    }
}
