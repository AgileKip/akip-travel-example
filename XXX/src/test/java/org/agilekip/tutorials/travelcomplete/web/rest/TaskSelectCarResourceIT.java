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
import org.agilekip.tutorials.travelcomplete.domain.TaskSelectCar;
import org.agilekip.tutorials.travelcomplete.repository.TaskSelectCarRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.TaskSelectCarDTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.TaskSelectCarMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TaskSelectCarResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaskSelectCarResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CAR_BOOKING_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CAR_BOOKING_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/task-select-cars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaskSelectCarRepository taskSelectCarRepository;

    @Autowired
    private TaskSelectCarMapper taskSelectCarMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskSelectCarMockMvc;

    private TaskSelectCar taskSelectCar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskSelectCar createEntity(EntityManager em) {
        TaskSelectCar taskSelectCar = new TaskSelectCar()
            .name(DEFAULT_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .carBookingNumber(DEFAULT_CAR_BOOKING_NUMBER);
        return taskSelectCar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskSelectCar createUpdatedEntity(EntityManager em) {
        TaskSelectCar taskSelectCar = new TaskSelectCar()
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .carBookingNumber(UPDATED_CAR_BOOKING_NUMBER);
        return taskSelectCar;
    }

    @BeforeEach
    public void initTest() {
        taskSelectCar = createEntity(em);
    }

    @Test
    @Transactional
    void createTaskSelectCar() throws Exception {
        int databaseSizeBeforeCreate = taskSelectCarRepository.findAll().size();
        // Create the TaskSelectCar
        TaskSelectCarDTO taskSelectCarDTO = taskSelectCarMapper.toDto(taskSelectCar);
        restTaskSelectCarMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskSelectCarDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TaskSelectCar in the database
        List<TaskSelectCar> taskSelectCarList = taskSelectCarRepository.findAll();
        assertThat(taskSelectCarList).hasSize(databaseSizeBeforeCreate + 1);
        TaskSelectCar testTaskSelectCar = taskSelectCarList.get(taskSelectCarList.size() - 1);
        assertThat(testTaskSelectCar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaskSelectCar.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTaskSelectCar.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTaskSelectCar.getCarBookingNumber()).isEqualTo(DEFAULT_CAR_BOOKING_NUMBER);
    }

    @Test
    @Transactional
    void createTaskSelectCarWithExistingId() throws Exception {
        // Create the TaskSelectCar with an existing ID
        taskSelectCar.setId(1L);
        TaskSelectCarDTO taskSelectCarDTO = taskSelectCarMapper.toDto(taskSelectCar);

        int databaseSizeBeforeCreate = taskSelectCarRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskSelectCarMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskSelectCarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskSelectCar in the database
        List<TaskSelectCar> taskSelectCarList = taskSelectCarRepository.findAll();
        assertThat(taskSelectCarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTaskSelectCars() throws Exception {
        // Initialize the database
        taskSelectCarRepository.saveAndFlush(taskSelectCar);

        // Get all the taskSelectCarList
        restTaskSelectCarMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskSelectCar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].carBookingNumber").value(hasItem(DEFAULT_CAR_BOOKING_NUMBER)));
    }

    @Test
    @Transactional
    void getTaskSelectCar() throws Exception {
        // Initialize the database
        taskSelectCarRepository.saveAndFlush(taskSelectCar);

        // Get the taskSelectCar
        restTaskSelectCarMockMvc
            .perform(get(ENTITY_API_URL_ID, taskSelectCar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskSelectCar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.carBookingNumber").value(DEFAULT_CAR_BOOKING_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingTaskSelectCar() throws Exception {
        // Get the taskSelectCar
        restTaskSelectCarMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaskSelectCar() throws Exception {
        // Initialize the database
        taskSelectCarRepository.saveAndFlush(taskSelectCar);

        int databaseSizeBeforeUpdate = taskSelectCarRepository.findAll().size();

        // Update the taskSelectCar
        TaskSelectCar updatedTaskSelectCar = taskSelectCarRepository.findById(taskSelectCar.getId()).get();
        // Disconnect from session so that the updates on updatedTaskSelectCar are not directly saved in db
        em.detach(updatedTaskSelectCar);
        updatedTaskSelectCar
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .carBookingNumber(UPDATED_CAR_BOOKING_NUMBER);
        TaskSelectCarDTO taskSelectCarDTO = taskSelectCarMapper.toDto(updatedTaskSelectCar);

        restTaskSelectCarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taskSelectCarDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskSelectCarDTO))
            )
            .andExpect(status().isOk());

        // Validate the TaskSelectCar in the database
        List<TaskSelectCar> taskSelectCarList = taskSelectCarRepository.findAll();
        assertThat(taskSelectCarList).hasSize(databaseSizeBeforeUpdate);
        TaskSelectCar testTaskSelectCar = taskSelectCarList.get(taskSelectCarList.size() - 1);
        assertThat(testTaskSelectCar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaskSelectCar.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTaskSelectCar.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTaskSelectCar.getCarBookingNumber()).isEqualTo(UPDATED_CAR_BOOKING_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingTaskSelectCar() throws Exception {
        int databaseSizeBeforeUpdate = taskSelectCarRepository.findAll().size();
        taskSelectCar.setId(count.incrementAndGet());

        // Create the TaskSelectCar
        TaskSelectCarDTO taskSelectCarDTO = taskSelectCarMapper.toDto(taskSelectCar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskSelectCarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taskSelectCarDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskSelectCarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskSelectCar in the database
        List<TaskSelectCar> taskSelectCarList = taskSelectCarRepository.findAll();
        assertThat(taskSelectCarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaskSelectCar() throws Exception {
        int databaseSizeBeforeUpdate = taskSelectCarRepository.findAll().size();
        taskSelectCar.setId(count.incrementAndGet());

        // Create the TaskSelectCar
        TaskSelectCarDTO taskSelectCarDTO = taskSelectCarMapper.toDto(taskSelectCar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskSelectCarMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskSelectCarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskSelectCar in the database
        List<TaskSelectCar> taskSelectCarList = taskSelectCarRepository.findAll();
        assertThat(taskSelectCarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaskSelectCar() throws Exception {
        int databaseSizeBeforeUpdate = taskSelectCarRepository.findAll().size();
        taskSelectCar.setId(count.incrementAndGet());

        // Create the TaskSelectCar
        TaskSelectCarDTO taskSelectCarDTO = taskSelectCarMapper.toDto(taskSelectCar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskSelectCarMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskSelectCarDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaskSelectCar in the database
        List<TaskSelectCar> taskSelectCarList = taskSelectCarRepository.findAll();
        assertThat(taskSelectCarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaskSelectCarWithPatch() throws Exception {
        // Initialize the database
        taskSelectCarRepository.saveAndFlush(taskSelectCar);

        int databaseSizeBeforeUpdate = taskSelectCarRepository.findAll().size();

        // Update the taskSelectCar using partial update
        TaskSelectCar partialUpdatedTaskSelectCar = new TaskSelectCar();
        partialUpdatedTaskSelectCar.setId(taskSelectCar.getId());

        partialUpdatedTaskSelectCar.name(UPDATED_NAME).carBookingNumber(UPDATED_CAR_BOOKING_NUMBER);

        restTaskSelectCarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaskSelectCar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskSelectCar))
            )
            .andExpect(status().isOk());

        // Validate the TaskSelectCar in the database
        List<TaskSelectCar> taskSelectCarList = taskSelectCarRepository.findAll();
        assertThat(taskSelectCarList).hasSize(databaseSizeBeforeUpdate);
        TaskSelectCar testTaskSelectCar = taskSelectCarList.get(taskSelectCarList.size() - 1);
        assertThat(testTaskSelectCar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaskSelectCar.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTaskSelectCar.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTaskSelectCar.getCarBookingNumber()).isEqualTo(UPDATED_CAR_BOOKING_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateTaskSelectCarWithPatch() throws Exception {
        // Initialize the database
        taskSelectCarRepository.saveAndFlush(taskSelectCar);

        int databaseSizeBeforeUpdate = taskSelectCarRepository.findAll().size();

        // Update the taskSelectCar using partial update
        TaskSelectCar partialUpdatedTaskSelectCar = new TaskSelectCar();
        partialUpdatedTaskSelectCar.setId(taskSelectCar.getId());

        partialUpdatedTaskSelectCar
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .carBookingNumber(UPDATED_CAR_BOOKING_NUMBER);

        restTaskSelectCarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaskSelectCar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskSelectCar))
            )
            .andExpect(status().isOk());

        // Validate the TaskSelectCar in the database
        List<TaskSelectCar> taskSelectCarList = taskSelectCarRepository.findAll();
        assertThat(taskSelectCarList).hasSize(databaseSizeBeforeUpdate);
        TaskSelectCar testTaskSelectCar = taskSelectCarList.get(taskSelectCarList.size() - 1);
        assertThat(testTaskSelectCar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaskSelectCar.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTaskSelectCar.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTaskSelectCar.getCarBookingNumber()).isEqualTo(UPDATED_CAR_BOOKING_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingTaskSelectCar() throws Exception {
        int databaseSizeBeforeUpdate = taskSelectCarRepository.findAll().size();
        taskSelectCar.setId(count.incrementAndGet());

        // Create the TaskSelectCar
        TaskSelectCarDTO taskSelectCarDTO = taskSelectCarMapper.toDto(taskSelectCar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskSelectCarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taskSelectCarDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taskSelectCarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskSelectCar in the database
        List<TaskSelectCar> taskSelectCarList = taskSelectCarRepository.findAll();
        assertThat(taskSelectCarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaskSelectCar() throws Exception {
        int databaseSizeBeforeUpdate = taskSelectCarRepository.findAll().size();
        taskSelectCar.setId(count.incrementAndGet());

        // Create the TaskSelectCar
        TaskSelectCarDTO taskSelectCarDTO = taskSelectCarMapper.toDto(taskSelectCar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskSelectCarMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taskSelectCarDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskSelectCar in the database
        List<TaskSelectCar> taskSelectCarList = taskSelectCarRepository.findAll();
        assertThat(taskSelectCarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaskSelectCar() throws Exception {
        int databaseSizeBeforeUpdate = taskSelectCarRepository.findAll().size();
        taskSelectCar.setId(count.incrementAndGet());

        // Create the TaskSelectCar
        TaskSelectCarDTO taskSelectCarDTO = taskSelectCarMapper.toDto(taskSelectCar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskSelectCarMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taskSelectCarDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaskSelectCar in the database
        List<TaskSelectCar> taskSelectCarList = taskSelectCarRepository.findAll();
        assertThat(taskSelectCarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaskSelectCar() throws Exception {
        // Initialize the database
        taskSelectCarRepository.saveAndFlush(taskSelectCar);

        int databaseSizeBeforeDelete = taskSelectCarRepository.findAll().size();

        // Delete the taskSelectCar
        restTaskSelectCarMockMvc
            .perform(delete(ENTITY_API_URL_ID, taskSelectCar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskSelectCar> taskSelectCarList = taskSelectCarRepository.findAll();
        assertThat(taskSelectCarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
