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
import org.agilekip.tutorials.travelcomplete.domain.TaskSelectFlight;
import org.agilekip.tutorials.travelcomplete.repository.TaskSelectFlightRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.TaskSelectFlightDTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.TaskSelectFlightMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TaskSelectFlightResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaskSelectFlightResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_AIRLINE_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AIRLINE_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AIRLINE_TICKET_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_AIRLINE_TICKET_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/task-select-flights";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaskSelectFlightRepository taskSelectFlightRepository;

    @Autowired
    private TaskSelectFlightMapper taskSelectFlightMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskSelectFlightMockMvc;

    private TaskSelectFlight taskSelectFlight;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskSelectFlight createEntity(EntityManager em) {
        TaskSelectFlight taskSelectFlight = new TaskSelectFlight()
            .name(DEFAULT_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .airlineCompanyName(DEFAULT_AIRLINE_COMPANY_NAME)
            .airlineTicketNumber(DEFAULT_AIRLINE_TICKET_NUMBER);
        return taskSelectFlight;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskSelectFlight createUpdatedEntity(EntityManager em) {
        TaskSelectFlight taskSelectFlight = new TaskSelectFlight()
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .airlineCompanyName(UPDATED_AIRLINE_COMPANY_NAME)
            .airlineTicketNumber(UPDATED_AIRLINE_TICKET_NUMBER);
        return taskSelectFlight;
    }

    @BeforeEach
    public void initTest() {
        taskSelectFlight = createEntity(em);
    }

    @Test
    @Transactional
    void createTaskSelectFlight() throws Exception {
        int databaseSizeBeforeCreate = taskSelectFlightRepository.findAll().size();
        // Create the TaskSelectFlight
        TaskSelectFlightDTO taskSelectFlightDTO = taskSelectFlightMapper.toDto(taskSelectFlight);
        restTaskSelectFlightMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskSelectFlightDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TaskSelectFlight in the database
        List<TaskSelectFlight> taskSelectFlightList = taskSelectFlightRepository.findAll();
        assertThat(taskSelectFlightList).hasSize(databaseSizeBeforeCreate + 1);
        TaskSelectFlight testTaskSelectFlight = taskSelectFlightList.get(taskSelectFlightList.size() - 1);
        assertThat(testTaskSelectFlight.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaskSelectFlight.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTaskSelectFlight.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTaskSelectFlight.getAirlineCompanyName()).isEqualTo(DEFAULT_AIRLINE_COMPANY_NAME);
        assertThat(testTaskSelectFlight.getAirlineTicketNumber()).isEqualTo(DEFAULT_AIRLINE_TICKET_NUMBER);
    }

    @Test
    @Transactional
    void createTaskSelectFlightWithExistingId() throws Exception {
        // Create the TaskSelectFlight with an existing ID
        taskSelectFlight.setId(1L);
        TaskSelectFlightDTO taskSelectFlightDTO = taskSelectFlightMapper.toDto(taskSelectFlight);

        int databaseSizeBeforeCreate = taskSelectFlightRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskSelectFlightMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskSelectFlightDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskSelectFlight in the database
        List<TaskSelectFlight> taskSelectFlightList = taskSelectFlightRepository.findAll();
        assertThat(taskSelectFlightList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTaskSelectFlights() throws Exception {
        // Initialize the database
        taskSelectFlightRepository.saveAndFlush(taskSelectFlight);

        // Get all the taskSelectFlightList
        restTaskSelectFlightMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskSelectFlight.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].airlineCompanyName").value(hasItem(DEFAULT_AIRLINE_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].airlineTicketNumber").value(hasItem(DEFAULT_AIRLINE_TICKET_NUMBER)));
    }

    @Test
    @Transactional
    void getTaskSelectFlight() throws Exception {
        // Initialize the database
        taskSelectFlightRepository.saveAndFlush(taskSelectFlight);

        // Get the taskSelectFlight
        restTaskSelectFlightMockMvc
            .perform(get(ENTITY_API_URL_ID, taskSelectFlight.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskSelectFlight.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.airlineCompanyName").value(DEFAULT_AIRLINE_COMPANY_NAME))
            .andExpect(jsonPath("$.airlineTicketNumber").value(DEFAULT_AIRLINE_TICKET_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingTaskSelectFlight() throws Exception {
        // Get the taskSelectFlight
        restTaskSelectFlightMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaskSelectFlight() throws Exception {
        // Initialize the database
        taskSelectFlightRepository.saveAndFlush(taskSelectFlight);

        int databaseSizeBeforeUpdate = taskSelectFlightRepository.findAll().size();

        // Update the taskSelectFlight
        TaskSelectFlight updatedTaskSelectFlight = taskSelectFlightRepository.findById(taskSelectFlight.getId()).get();
        // Disconnect from session so that the updates on updatedTaskSelectFlight are not directly saved in db
        em.detach(updatedTaskSelectFlight);
        updatedTaskSelectFlight
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .airlineCompanyName(UPDATED_AIRLINE_COMPANY_NAME)
            .airlineTicketNumber(UPDATED_AIRLINE_TICKET_NUMBER);
        TaskSelectFlightDTO taskSelectFlightDTO = taskSelectFlightMapper.toDto(updatedTaskSelectFlight);

        restTaskSelectFlightMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taskSelectFlightDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskSelectFlightDTO))
            )
            .andExpect(status().isOk());

        // Validate the TaskSelectFlight in the database
        List<TaskSelectFlight> taskSelectFlightList = taskSelectFlightRepository.findAll();
        assertThat(taskSelectFlightList).hasSize(databaseSizeBeforeUpdate);
        TaskSelectFlight testTaskSelectFlight = taskSelectFlightList.get(taskSelectFlightList.size() - 1);
        assertThat(testTaskSelectFlight.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaskSelectFlight.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTaskSelectFlight.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTaskSelectFlight.getAirlineCompanyName()).isEqualTo(UPDATED_AIRLINE_COMPANY_NAME);
        assertThat(testTaskSelectFlight.getAirlineTicketNumber()).isEqualTo(UPDATED_AIRLINE_TICKET_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingTaskSelectFlight() throws Exception {
        int databaseSizeBeforeUpdate = taskSelectFlightRepository.findAll().size();
        taskSelectFlight.setId(count.incrementAndGet());

        // Create the TaskSelectFlight
        TaskSelectFlightDTO taskSelectFlightDTO = taskSelectFlightMapper.toDto(taskSelectFlight);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskSelectFlightMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taskSelectFlightDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskSelectFlightDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskSelectFlight in the database
        List<TaskSelectFlight> taskSelectFlightList = taskSelectFlightRepository.findAll();
        assertThat(taskSelectFlightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaskSelectFlight() throws Exception {
        int databaseSizeBeforeUpdate = taskSelectFlightRepository.findAll().size();
        taskSelectFlight.setId(count.incrementAndGet());

        // Create the TaskSelectFlight
        TaskSelectFlightDTO taskSelectFlightDTO = taskSelectFlightMapper.toDto(taskSelectFlight);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskSelectFlightMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskSelectFlightDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskSelectFlight in the database
        List<TaskSelectFlight> taskSelectFlightList = taskSelectFlightRepository.findAll();
        assertThat(taskSelectFlightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaskSelectFlight() throws Exception {
        int databaseSizeBeforeUpdate = taskSelectFlightRepository.findAll().size();
        taskSelectFlight.setId(count.incrementAndGet());

        // Create the TaskSelectFlight
        TaskSelectFlightDTO taskSelectFlightDTO = taskSelectFlightMapper.toDto(taskSelectFlight);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskSelectFlightMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskSelectFlightDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaskSelectFlight in the database
        List<TaskSelectFlight> taskSelectFlightList = taskSelectFlightRepository.findAll();
        assertThat(taskSelectFlightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaskSelectFlightWithPatch() throws Exception {
        // Initialize the database
        taskSelectFlightRepository.saveAndFlush(taskSelectFlight);

        int databaseSizeBeforeUpdate = taskSelectFlightRepository.findAll().size();

        // Update the taskSelectFlight using partial update
        TaskSelectFlight partialUpdatedTaskSelectFlight = new TaskSelectFlight();
        partialUpdatedTaskSelectFlight.setId(taskSelectFlight.getId());

        partialUpdatedTaskSelectFlight
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .airlineCompanyName(UPDATED_AIRLINE_COMPANY_NAME)
            .airlineTicketNumber(UPDATED_AIRLINE_TICKET_NUMBER);

        restTaskSelectFlightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaskSelectFlight.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskSelectFlight))
            )
            .andExpect(status().isOk());

        // Validate the TaskSelectFlight in the database
        List<TaskSelectFlight> taskSelectFlightList = taskSelectFlightRepository.findAll();
        assertThat(taskSelectFlightList).hasSize(databaseSizeBeforeUpdate);
        TaskSelectFlight testTaskSelectFlight = taskSelectFlightList.get(taskSelectFlightList.size() - 1);
        assertThat(testTaskSelectFlight.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaskSelectFlight.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTaskSelectFlight.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTaskSelectFlight.getAirlineCompanyName()).isEqualTo(UPDATED_AIRLINE_COMPANY_NAME);
        assertThat(testTaskSelectFlight.getAirlineTicketNumber()).isEqualTo(UPDATED_AIRLINE_TICKET_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateTaskSelectFlightWithPatch() throws Exception {
        // Initialize the database
        taskSelectFlightRepository.saveAndFlush(taskSelectFlight);

        int databaseSizeBeforeUpdate = taskSelectFlightRepository.findAll().size();

        // Update the taskSelectFlight using partial update
        TaskSelectFlight partialUpdatedTaskSelectFlight = new TaskSelectFlight();
        partialUpdatedTaskSelectFlight.setId(taskSelectFlight.getId());

        partialUpdatedTaskSelectFlight
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .airlineCompanyName(UPDATED_AIRLINE_COMPANY_NAME)
            .airlineTicketNumber(UPDATED_AIRLINE_TICKET_NUMBER);

        restTaskSelectFlightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaskSelectFlight.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskSelectFlight))
            )
            .andExpect(status().isOk());

        // Validate the TaskSelectFlight in the database
        List<TaskSelectFlight> taskSelectFlightList = taskSelectFlightRepository.findAll();
        assertThat(taskSelectFlightList).hasSize(databaseSizeBeforeUpdate);
        TaskSelectFlight testTaskSelectFlight = taskSelectFlightList.get(taskSelectFlightList.size() - 1);
        assertThat(testTaskSelectFlight.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaskSelectFlight.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTaskSelectFlight.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTaskSelectFlight.getAirlineCompanyName()).isEqualTo(UPDATED_AIRLINE_COMPANY_NAME);
        assertThat(testTaskSelectFlight.getAirlineTicketNumber()).isEqualTo(UPDATED_AIRLINE_TICKET_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingTaskSelectFlight() throws Exception {
        int databaseSizeBeforeUpdate = taskSelectFlightRepository.findAll().size();
        taskSelectFlight.setId(count.incrementAndGet());

        // Create the TaskSelectFlight
        TaskSelectFlightDTO taskSelectFlightDTO = taskSelectFlightMapper.toDto(taskSelectFlight);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskSelectFlightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taskSelectFlightDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taskSelectFlightDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskSelectFlight in the database
        List<TaskSelectFlight> taskSelectFlightList = taskSelectFlightRepository.findAll();
        assertThat(taskSelectFlightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaskSelectFlight() throws Exception {
        int databaseSizeBeforeUpdate = taskSelectFlightRepository.findAll().size();
        taskSelectFlight.setId(count.incrementAndGet());

        // Create the TaskSelectFlight
        TaskSelectFlightDTO taskSelectFlightDTO = taskSelectFlightMapper.toDto(taskSelectFlight);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskSelectFlightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taskSelectFlightDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskSelectFlight in the database
        List<TaskSelectFlight> taskSelectFlightList = taskSelectFlightRepository.findAll();
        assertThat(taskSelectFlightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaskSelectFlight() throws Exception {
        int databaseSizeBeforeUpdate = taskSelectFlightRepository.findAll().size();
        taskSelectFlight.setId(count.incrementAndGet());

        // Create the TaskSelectFlight
        TaskSelectFlightDTO taskSelectFlightDTO = taskSelectFlightMapper.toDto(taskSelectFlight);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskSelectFlightMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taskSelectFlightDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaskSelectFlight in the database
        List<TaskSelectFlight> taskSelectFlightList = taskSelectFlightRepository.findAll();
        assertThat(taskSelectFlightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaskSelectFlight() throws Exception {
        // Initialize the database
        taskSelectFlightRepository.saveAndFlush(taskSelectFlight);

        int databaseSizeBeforeDelete = taskSelectFlightRepository.findAll().size();

        // Delete the taskSelectFlight
        restTaskSelectFlightMockMvc
            .perform(delete(ENTITY_API_URL_ID, taskSelectFlight.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskSelectFlight> taskSelectFlightList = taskSelectFlightRepository.findAll();
        assertThat(taskSelectFlightList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
