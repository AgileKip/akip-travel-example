package org.agilekip.tutorials.travelcomplete.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskSelectFlightMapperTest {

    private TaskSelectFlightMapper taskSelectFlightMapper;

    @BeforeEach
    public void setUp() {
        taskSelectFlightMapper = new TaskSelectFlightMapperImpl();
    }
}
