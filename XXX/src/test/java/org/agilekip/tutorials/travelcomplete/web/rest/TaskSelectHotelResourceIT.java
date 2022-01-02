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
import org.agilekip.tutorials.travelcomplete.domain.TaskSelectHotel;
import org.agilekip.tutorials.travelcomplete.repository.TaskSelectHotelRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.TaskSelectHotelDTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.TaskSelectHotelMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TaskSelectHotelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaskSelectHotelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HOTEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HOTEL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HOTEL_BOOKING_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_HOTEL_BOOKING_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/task-select-hotels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaskSelectHotelRepository taskSelectHotelRepository;

    @Autowired
    private TaskSelectHotelMapper taskSelectHotelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskSelectHotelMockMvc;

    private TaskSelectHotel taskSelectHotel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskSelectHotel createEntity(EntityManager em) {
        TaskSelectHotel taskSelectHotel = new TaskSelectHotel()
            .name(DEFAULT_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .hotelName(DEFAULT_HOTEL_NAME)
            .hotelBookingNumber(DEFAULT_HOTEL_BOOKING_NUMBER);
        return taskSelectHotel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskSelectHotel createUpdatedEntity(EntityManager em) {
        TaskSelectHotel taskSelectHotel = new TaskSelectHotel()
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .hotelName(UPDATED_HOTEL_NAME)
            .hotelBookingNumber(UPDATED_HOTEL_BOOKING_NUMBER);
        return taskSelectHotel;
    }

    @BeforeEach
    public void initTest() {
        taskSelectHotel = createEntity(em);
    }

    @Test
    @Transactional
    void createTaskSelectHotel() throws Exception {
        int databaseSizeBeforeCreate = taskSelectHotelRepository.findAll().size();
        // Create the TaskSelectHotel
        TaskSelectHotelDTO taskSelectHotelDTO = taskSelectHotelMapper.toDto(taskSelectHotel);
        restTaskSelectHotelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskSelectHotelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TaskSelectHotel in the database
        List<TaskSelectHotel> taskSelectHotelList = taskSelectHotelRepository.findAll();
        assertThat(taskSelectHotelList).hasSize(databaseSizeBeforeCreate + 1);
        TaskSelectHotel testTaskSelectHotel = taskSelectHotelList.get(taskSelectHotelList.size() - 1);
        assertThat(testTaskSelectHotel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaskSelectHotel.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTaskSelectHotel.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTaskSelectHotel.getHotelName()).isEqualTo(DEFAULT_HOTEL_NAME);
        assertThat(testTaskSelectHotel.getHotelBookingNumber()).isEqualTo(DEFAULT_HOTEL_BOOKING_NUMBER);
    }

    @Test
    @Transactional
    void createTaskSelectHotelWithExistingId() throws Exception {
        // Create the TaskSelectHotel with an existing ID
        taskSelectHotel.setId(1L);
        TaskSelectHotelDTO taskSelectHotelDTO = taskSelectHotelMapper.toDto(taskSelectHotel);

        int databaseSizeBeforeCreate = taskSelectHotelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskSelectHotelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskSelectHotelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskSelectHotel in the database
        List<TaskSelectHotel> taskSelectHotelList = taskSelectHotelRepository.findAll();
        assertThat(taskSelectHotelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTaskSelectHotels() throws Exception {
        // Initialize the database
        taskSelectHotelRepository.saveAndFlush(taskSelectHotel);

        // Get all the taskSelectHotelList
        restTaskSelectHotelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskSelectHotel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].hotelName").value(hasItem(DEFAULT_HOTEL_NAME)))
            .andExpect(jsonPath("$.[*].hotelBookingNumber").value(hasItem(DEFAULT_HOTEL_BOOKING_NUMBER)));
    }

    @Test
    @Transactional
    void getTaskSelectHotel() throws Exception {
        // Initialize the database
        taskSelectHotelRepository.saveAndFlush(taskSelectHotel);

        // Get the taskSelectHotel
        restTaskSelectHotelMockMvc
            .perform(get(ENTITY_API_URL_ID, taskSelectHotel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskSelectHotel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.hotelName").value(DEFAULT_HOTEL_NAME))
            .andExpect(jsonPath("$.hotelBookingNumber").value(DEFAULT_HOTEL_BOOKING_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingTaskSelectHotel() throws Exception {
        // Get the taskSelectHotel
        restTaskSelectHotelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaskSelectHotel() throws Exception {
        // Initialize the database
        taskSelectHotelRepository.saveAndFlush(taskSelectHotel);

        int databaseSizeBeforeUpdate = taskSelectHotelRepository.findAll().size();

        // Update the taskSelectHotel
        TaskSelectHotel updatedTaskSelectHotel = taskSelectHotelRepository.findById(taskSelectHotel.getId()).get();
        // Disconnect from session so that the updates on updatedTaskSelectHotel are not directly saved in db
        em.detach(updatedTaskSelectHotel);
        updatedTaskSelectHotel
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .hotelName(UPDATED_HOTEL_NAME)
            .hotelBookingNumber(UPDATED_HOTEL_BOOKING_NUMBER);
        TaskSelectHotelDTO taskSelectHotelDTO = taskSelectHotelMapper.toDto(updatedTaskSelectHotel);

        restTaskSelectHotelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taskSelectHotelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskSelectHotelDTO))
            )
            .andExpect(status().isOk());

        // Validate the TaskSelectHotel in the database
        List<TaskSelectHotel> taskSelectHotelList = taskSelectHotelRepository.findAll();
        assertThat(taskSelectHotelList).hasSize(databaseSizeBeforeUpdate);
        TaskSelectHotel testTaskSelectHotel = taskSelectHotelList.get(taskSelectHotelList.size() - 1);
        assertThat(testTaskSelectHotel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaskSelectHotel.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTaskSelectHotel.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTaskSelectHotel.getHotelName()).isEqualTo(UPDATED_HOTEL_NAME);
        assertThat(testTaskSelectHotel.getHotelBookingNumber()).isEqualTo(UPDATED_HOTEL_BOOKING_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingTaskSelectHotel() throws Exception {
        int databaseSizeBeforeUpdate = taskSelectHotelRepository.findAll().size();
        taskSelectHotel.setId(count.incrementAndGet());

        // Create the TaskSelectHotel
        TaskSelectHotelDTO taskSelectHotelDTO = taskSelectHotelMapper.toDto(taskSelectHotel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskSelectHotelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taskSelectHotelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskSelectHotelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskSelectHotel in the database
        List<TaskSelectHotel> taskSelectHotelList = taskSelectHotelRepository.findAll();
        assertThat(taskSelectHotelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaskSelectHotel() throws Exception {
        int databaseSizeBeforeUpdate = taskSelectHotelRepository.findAll().size();
        taskSelectHotel.setId(count.incrementAndGet());

        // Create the TaskSelectHotel
        TaskSelectHotelDTO taskSelectHotelDTO = taskSelectHotelMapper.toDto(taskSelectHotel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskSelectHotelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskSelectHotelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskSelectHotel in the database
        List<TaskSelectHotel> taskSelectHotelList = taskSelectHotelRepository.findAll();
        assertThat(taskSelectHotelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaskSelectHotel() throws Exception {
        int databaseSizeBeforeUpdate = taskSelectHotelRepository.findAll().size();
        taskSelectHotel.setId(count.incrementAndGet());

        // Create the TaskSelectHotel
        TaskSelectHotelDTO taskSelectHotelDTO = taskSelectHotelMapper.toDto(taskSelectHotel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskSelectHotelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskSelectHotelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaskSelectHotel in the database
        List<TaskSelectHotel> taskSelectHotelList = taskSelectHotelRepository.findAll();
        assertThat(taskSelectHotelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaskSelectHotelWithPatch() throws Exception {
        // Initialize the database
        taskSelectHotelRepository.saveAndFlush(taskSelectHotel);

        int databaseSizeBeforeUpdate = taskSelectHotelRepository.findAll().size();

        // Update the taskSelectHotel using partial update
        TaskSelectHotel partialUpdatedTaskSelectHotel = new TaskSelectHotel();
        partialUpdatedTaskSelectHotel.setId(taskSelectHotel.getId());

        partialUpdatedTaskSelectHotel.startDate(UPDATED_START_DATE).hotelBookingNumber(UPDATED_HOTEL_BOOKING_NUMBER);

        restTaskSelectHotelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaskSelectHotel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskSelectHotel))
            )
            .andExpect(status().isOk());

        // Validate the TaskSelectHotel in the database
        List<TaskSelectHotel> taskSelectHotelList = taskSelectHotelRepository.findAll();
        assertThat(taskSelectHotelList).hasSize(databaseSizeBeforeUpdate);
        TaskSelectHotel testTaskSelectHotel = taskSelectHotelList.get(taskSelectHotelList.size() - 1);
        assertThat(testTaskSelectHotel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaskSelectHotel.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTaskSelectHotel.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTaskSelectHotel.getHotelName()).isEqualTo(DEFAULT_HOTEL_NAME);
        assertThat(testTaskSelectHotel.getHotelBookingNumber()).isEqualTo(UPDATED_HOTEL_BOOKING_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateTaskSelectHotelWithPatch() throws Exception {
        // Initialize the database
        taskSelectHotelRepository.saveAndFlush(taskSelectHotel);

        int databaseSizeBeforeUpdate = taskSelectHotelRepository.findAll().size();

        // Update the taskSelectHotel using partial update
        TaskSelectHotel partialUpdatedTaskSelectHotel = new TaskSelectHotel();
        partialUpdatedTaskSelectHotel.setId(taskSelectHotel.getId());

        partialUpdatedTaskSelectHotel
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .hotelName(UPDATED_HOTEL_NAME)
            .hotelBookingNumber(UPDATED_HOTEL_BOOKING_NUMBER);

        restTaskSelectHotelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaskSelectHotel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskSelectHotel))
            )
            .andExpect(status().isOk());

        // Validate the TaskSelectHotel in the database
        List<TaskSelectHotel> taskSelectHotelList = taskSelectHotelRepository.findAll();
        assertThat(taskSelectHotelList).hasSize(databaseSizeBeforeUpdate);
        TaskSelectHotel testTaskSelectHotel = taskSelectHotelList.get(taskSelectHotelList.size() - 1);
        assertThat(testTaskSelectHotel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaskSelectHotel.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTaskSelectHotel.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTaskSelectHotel.getHotelName()).isEqualTo(UPDATED_HOTEL_NAME);
        assertThat(testTaskSelectHotel.getHotelBookingNumber()).isEqualTo(UPDATED_HOTEL_BOOKING_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingTaskSelectHotel() throws Exception {
        int databaseSizeBeforeUpdate = taskSelectHotelRepository.findAll().size();
        taskSelectHotel.setId(count.incrementAndGet());

        // Create the TaskSelectHotel
        TaskSelectHotelDTO taskSelectHotelDTO = taskSelectHotelMapper.toDto(taskSelectHotel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskSelectHotelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taskSelectHotelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taskSelectHotelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskSelectHotel in the database
        List<TaskSelectHotel> taskSelectHotelList = taskSelectHotelRepository.findAll();
        assertThat(taskSelectHotelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaskSelectHotel() throws Exception {
        int databaseSizeBeforeUpdate = taskSelectHotelRepository.findAll().size();
        taskSelectHotel.setId(count.incrementAndGet());

        // Create the TaskSelectHotel
        TaskSelectHotelDTO taskSelectHotelDTO = taskSelectHotelMapper.toDto(taskSelectHotel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskSelectHotelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taskSelectHotelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskSelectHotel in the database
        List<TaskSelectHotel> taskSelectHotelList = taskSelectHotelRepository.findAll();
        assertThat(taskSelectHotelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaskSelectHotel() throws Exception {
        int databaseSizeBeforeUpdate = taskSelectHotelRepository.findAll().size();
        taskSelectHotel.setId(count.incrementAndGet());

        // Create the TaskSelectHotel
        TaskSelectHotelDTO taskSelectHotelDTO = taskSelectHotelMapper.toDto(taskSelectHotel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskSelectHotelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taskSelectHotelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaskSelectHotel in the database
        List<TaskSelectHotel> taskSelectHotelList = taskSelectHotelRepository.findAll();
        assertThat(taskSelectHotelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaskSelectHotel() throws Exception {
        // Initialize the database
        taskSelectHotelRepository.saveAndFlush(taskSelectHotel);

        int databaseSizeBeforeDelete = taskSelectHotelRepository.findAll().size();

        // Delete the taskSelectHotel
        restTaskSelectHotelMockMvc
            .perform(delete(ENTITY_API_URL_ID, taskSelectHotel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskSelectHotel> taskSelectHotelList = taskSelectHotelRepository.findAll();
        assertThat(taskSelectHotelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
