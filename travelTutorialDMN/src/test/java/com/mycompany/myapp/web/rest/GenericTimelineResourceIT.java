package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.GenericTimeline;
import com.mycompany.myapp.repository.GenericTimelineRepository;
import com.mycompany.myapp.service.dto.GenericTimelineDTO;
import com.mycompany.myapp.service.mapper.GenericTimelineMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GenericTimelineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GenericTimelineResourceIT {

    private static final Boolean DEFAULT_NEED_TASK_H = false;
    private static final Boolean UPDATED_NEED_TASK_H = true;

    private static final Boolean DEFAULT_LOOP_ENTER = false;
    private static final Boolean UPDATED_LOOP_ENTER = true;

    private static final String DEFAULT_CHOOSE_TASK = "AAAAAAAAAA";
    private static final String UPDATED_CHOOSE_TASK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/generic-timelines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GenericTimelineRepository genericTimelineRepository;

    @Autowired
    private GenericTimelineMapper genericTimelineMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGenericTimelineMockMvc;

    private GenericTimeline genericTimeline;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GenericTimeline createEntity(EntityManager em) {
        GenericTimeline genericTimeline = new GenericTimeline()
            .needTaskH(DEFAULT_NEED_TASK_H)
            .loopEnter(DEFAULT_LOOP_ENTER)
            .chooseTask(DEFAULT_CHOOSE_TASK);
        return genericTimeline;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GenericTimeline createUpdatedEntity(EntityManager em) {
        GenericTimeline genericTimeline = new GenericTimeline()
            .needTaskH(UPDATED_NEED_TASK_H)
            .loopEnter(UPDATED_LOOP_ENTER)
            .chooseTask(UPDATED_CHOOSE_TASK);
        return genericTimeline;
    }

    @BeforeEach
    public void initTest() {
        genericTimeline = createEntity(em);
    }

    @Test
    @Transactional
    void getAllGenericTimelines() throws Exception {
        // Initialize the database
        genericTimelineRepository.saveAndFlush(genericTimeline);

        // Get all the genericTimelineList
        restGenericTimelineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genericTimeline.getId().intValue())))
            .andExpect(jsonPath("$.[*].needTaskH").value(hasItem(DEFAULT_NEED_TASK_H.booleanValue())))
            .andExpect(jsonPath("$.[*].loopEnter").value(hasItem(DEFAULT_LOOP_ENTER.booleanValue())))
            .andExpect(jsonPath("$.[*].chooseTask").value(hasItem(DEFAULT_CHOOSE_TASK)));
    }

    @Test
    @Transactional
    void getGenericTimeline() throws Exception {
        // Initialize the database
        genericTimelineRepository.saveAndFlush(genericTimeline);

        // Get the genericTimeline
        restGenericTimelineMockMvc
            .perform(get(ENTITY_API_URL_ID, genericTimeline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(genericTimeline.getId().intValue()))
            .andExpect(jsonPath("$.needTaskH").value(DEFAULT_NEED_TASK_H.booleanValue()))
            .andExpect(jsonPath("$.loopEnter").value(DEFAULT_LOOP_ENTER.booleanValue()))
            .andExpect(jsonPath("$.chooseTask").value(DEFAULT_CHOOSE_TASK));
    }

    @Test
    @Transactional
    void getNonExistingGenericTimeline() throws Exception {
        // Get the genericTimeline
        restGenericTimelineMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
