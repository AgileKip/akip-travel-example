package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenericTimelineMapperTest {

    private GenericTimelineMapper genericTimelineMapper;

    @BeforeEach
    public void setUp() {
        genericTimelineMapper = new GenericTimelineMapperImpl();
    }
}
