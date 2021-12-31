package org.agilekip.tutorials.travelcall.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.agilekip.tutorials.travelcall.IntegrationTest;
import org.agilekip.tutorials.travelcall.domain.HandleCancel;
import org.agilekip.tutorials.travelcall.repository.HandleCancelRepository;
import org.agilekip.tutorials.travelcall.service.dto.HandleCancelDTO;
import org.agilekip.tutorials.travelcall.service.mapper.HandleCancelMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HandleCancelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HandleCancelResourceIT {

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/handle-cancels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HandleCancelRepository handleCancelRepository;

    @Autowired
    private HandleCancelMapper handleCancelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHandleCancelMockMvc;

    private HandleCancel handleCancel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HandleCancel createEntity(EntityManager em) {
        HandleCancel handleCancel = new HandleCancel().reason(DEFAULT_REASON);
        return handleCancel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HandleCancel createUpdatedEntity(EntityManager em) {
        HandleCancel handleCancel = new HandleCancel().reason(UPDATED_REASON);
        return handleCancel;
    }

    @BeforeEach
    public void initTest() {
        handleCancel = createEntity(em);
    }

    @Test
    @Transactional
    void getAllHandleCancels() throws Exception {
        // Initialize the database
        handleCancelRepository.saveAndFlush(handleCancel);

        // Get all the handleCancelList
        restHandleCancelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(handleCancel.getId().intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)));
    }

    @Test
    @Transactional
    void getHandleCancel() throws Exception {
        // Initialize the database
        handleCancelRepository.saveAndFlush(handleCancel);

        // Get the handleCancel
        restHandleCancelMockMvc
            .perform(get(ENTITY_API_URL_ID, handleCancel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(handleCancel.getId().intValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON));
    }

    @Test
    @Transactional
    void getNonExistingHandleCancel() throws Exception {
        // Get the handleCancel
        restHandleCancelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
