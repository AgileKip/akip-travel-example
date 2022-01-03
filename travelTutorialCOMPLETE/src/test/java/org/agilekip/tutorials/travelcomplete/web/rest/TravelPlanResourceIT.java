package org.agilekip.tutorials.travelcomplete.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.agilekip.tutorials.travelcomplete.IntegrationTest;
import org.agilekip.tutorials.travelcomplete.domain.TravelPlan;
import org.agilekip.tutorials.travelcomplete.domain.enumeration.PlanStatus;
import org.agilekip.tutorials.travelcomplete.repository.TravelPlanRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelPlanDTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.TravelPlanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TravelPlanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TravelPlanResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NUM_PAX = 1;
    private static final Integer UPDATED_NUM_PAX = 2;

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final String DEFAULT_PAYMENT = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_HOTEL_DURATION = 1;
    private static final Integer UPDATED_HOTEL_DURATION = 2;

    private static final Integer DEFAULT_CAR_DURATION = 1;
    private static final Integer UPDATED_CAR_DURATION = 2;

    private static final PlanStatus DEFAULT_STATUS = PlanStatus.UNKNOWN;
    private static final PlanStatus UPDATED_STATUS = PlanStatus.CANCELED;

    private static final Boolean DEFAULT_PROCEED_TO_CHECK_OUT = false;
    private static final Boolean UPDATED_PROCEED_TO_CHECK_OUT = true;

    private static final String ENTITY_API_URL = "/api/travel-plans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TravelPlanRepository travelPlanRepository;

    @Autowired
    private TravelPlanMapper travelPlanMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTravelPlanMockMvc;

    private TravelPlan travelPlan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TravelPlan createEntity(EntityManager em) {
        TravelPlan travelPlan = new TravelPlan()
            .name(DEFAULT_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .numPax(DEFAULT_NUM_PAX)
            .price(DEFAULT_PRICE)
            .payment(DEFAULT_PAYMENT)
            .hotelDuration(DEFAULT_HOTEL_DURATION)
            .carDuration(DEFAULT_CAR_DURATION)
            .status(DEFAULT_STATUS)
            .proceedToCheckOut(DEFAULT_PROCEED_TO_CHECK_OUT);
        return travelPlan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TravelPlan createUpdatedEntity(EntityManager em) {
        TravelPlan travelPlan = new TravelPlan()
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .numPax(UPDATED_NUM_PAX)
            .price(UPDATED_PRICE)
            .payment(UPDATED_PAYMENT)
            .hotelDuration(UPDATED_HOTEL_DURATION)
            .carDuration(UPDATED_CAR_DURATION)
            .status(UPDATED_STATUS)
            .proceedToCheckOut(UPDATED_PROCEED_TO_CHECK_OUT);
        return travelPlan;
    }

    @BeforeEach
    public void initTest() {
        travelPlan = createEntity(em);
    }

    @Test
    @Transactional
    void getAllTravelPlans() throws Exception {
        // Initialize the database
        travelPlanRepository.saveAndFlush(travelPlan);

        // Get all the travelPlanList
        restTravelPlanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(travelPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].numPax").value(hasItem(DEFAULT_NUM_PAX)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].payment").value(hasItem(DEFAULT_PAYMENT)))
            .andExpect(jsonPath("$.[*].hotelDuration").value(hasItem(DEFAULT_HOTEL_DURATION)))
            .andExpect(jsonPath("$.[*].carDuration").value(hasItem(DEFAULT_CAR_DURATION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].proceedToCheckOut").value(hasItem(DEFAULT_PROCEED_TO_CHECK_OUT.booleanValue())));
    }

    @Test
    @Transactional
    void getTravelPlan() throws Exception {
        // Initialize the database
        travelPlanRepository.saveAndFlush(travelPlan);

        // Get the travelPlan
        restTravelPlanMockMvc
            .perform(get(ENTITY_API_URL_ID, travelPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(travelPlan.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.numPax").value(DEFAULT_NUM_PAX))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.payment").value(DEFAULT_PAYMENT))
            .andExpect(jsonPath("$.hotelDuration").value(DEFAULT_HOTEL_DURATION))
            .andExpect(jsonPath("$.carDuration").value(DEFAULT_CAR_DURATION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.proceedToCheckOut").value(DEFAULT_PROCEED_TO_CHECK_OUT.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingTravelPlan() throws Exception {
        // Get the travelPlan
        restTravelPlanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }
}
