package org.agilekip.tutorials.travelcomplete.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskSelectHotelMapperTest {

    private TaskSelectHotelMapper taskSelectHotelMapper;

    @BeforeEach
    public void setUp() {
        taskSelectHotelMapper = new TaskSelectHotelMapperImpl();
    }
}
