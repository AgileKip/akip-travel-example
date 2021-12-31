package org.agilekip.tutorials.travelcall.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HandleCancelMapperTest {

    private HandleCancelMapper handleCancelMapper;

    @BeforeEach
    public void setUp() {
        handleCancelMapper = new HandleCancelMapperImpl();
    }
}
