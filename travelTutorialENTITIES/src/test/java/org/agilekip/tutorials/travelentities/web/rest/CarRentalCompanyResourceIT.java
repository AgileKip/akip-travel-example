package org.agilekip.tutorials.travelentities.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.agilekip.tutorials.travelentities.IntegrationTest;
import org.agilekip.tutorials.travelentities.domain.CarRentalCompany;
import org.agilekip.tutorials.travelentities.repository.CarRentalCompanyRepository;
import org.agilekip.tutorials.travelentities.service.dto.CarRentalCompanyDTO;
import org.agilekip.tutorials.travelentities.service.mapper.CarRentalCompanyMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CarRentalCompanyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CarRentalCompanyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/car-rental-companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CarRentalCompanyRepository carRentalCompanyRepository;

    @Autowired
    private CarRentalCompanyMapper carRentalCompanyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarRentalCompanyMockMvc;

    private CarRentalCompany carRentalCompany;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarRentalCompany createEntity(EntityManager em) {
        CarRentalCompany carRentalCompany = new CarRentalCompany().name(DEFAULT_NAME).code(DEFAULT_CODE);
        return carRentalCompany;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarRentalCompany createUpdatedEntity(EntityManager em) {
        CarRentalCompany carRentalCompany = new CarRentalCompany().name(UPDATED_NAME).code(UPDATED_CODE);
        return carRentalCompany;
    }

    @BeforeEach
    public void initTest() {
        carRentalCompany = createEntity(em);
    }

    @Test
    @Transactional
    void createCarRentalCompany() throws Exception {
        int databaseSizeBeforeCreate = carRentalCompanyRepository.findAll().size();
        // Create the CarRentalCompany
        CarRentalCompanyDTO carRentalCompanyDTO = carRentalCompanyMapper.toDto(carRentalCompany);
        restCarRentalCompanyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carRentalCompanyDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CarRentalCompany in the database
        List<CarRentalCompany> carRentalCompanyList = carRentalCompanyRepository.findAll();
        assertThat(carRentalCompanyList).hasSize(databaseSizeBeforeCreate + 1);
        CarRentalCompany testCarRentalCompany = carRentalCompanyList.get(carRentalCompanyList.size() - 1);
        assertThat(testCarRentalCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCarRentalCompany.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createCarRentalCompanyWithExistingId() throws Exception {
        // Create the CarRentalCompany with an existing ID
        carRentalCompany.setId(1L);
        CarRentalCompanyDTO carRentalCompanyDTO = carRentalCompanyMapper.toDto(carRentalCompany);

        int databaseSizeBeforeCreate = carRentalCompanyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarRentalCompanyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carRentalCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarRentalCompany in the database
        List<CarRentalCompany> carRentalCompanyList = carRentalCompanyRepository.findAll();
        assertThat(carRentalCompanyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCarRentalCompanies() throws Exception {
        // Initialize the database
        carRentalCompanyRepository.saveAndFlush(carRentalCompany);

        // Get all the carRentalCompanyList
        restCarRentalCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carRentalCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getCarRentalCompany() throws Exception {
        // Initialize the database
        carRentalCompanyRepository.saveAndFlush(carRentalCompany);

        // Get the carRentalCompany
        restCarRentalCompanyMockMvc
            .perform(get(ENTITY_API_URL_ID, carRentalCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carRentalCompany.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingCarRentalCompany() throws Exception {
        // Get the carRentalCompany
        restCarRentalCompanyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCarRentalCompany() throws Exception {
        // Initialize the database
        carRentalCompanyRepository.saveAndFlush(carRentalCompany);

        int databaseSizeBeforeUpdate = carRentalCompanyRepository.findAll().size();

        // Update the carRentalCompany
        CarRentalCompany updatedCarRentalCompany = carRentalCompanyRepository.findById(carRentalCompany.getId()).get();
        // Disconnect from session so that the updates on updatedCarRentalCompany are not directly saved in db
        em.detach(updatedCarRentalCompany);
        updatedCarRentalCompany.name(UPDATED_NAME).code(UPDATED_CODE);
        CarRentalCompanyDTO carRentalCompanyDTO = carRentalCompanyMapper.toDto(updatedCarRentalCompany);

        restCarRentalCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carRentalCompanyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carRentalCompanyDTO))
            )
            .andExpect(status().isOk());

        // Validate the CarRentalCompany in the database
        List<CarRentalCompany> carRentalCompanyList = carRentalCompanyRepository.findAll();
        assertThat(carRentalCompanyList).hasSize(databaseSizeBeforeUpdate);
        CarRentalCompany testCarRentalCompany = carRentalCompanyList.get(carRentalCompanyList.size() - 1);
        assertThat(testCarRentalCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCarRentalCompany.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingCarRentalCompany() throws Exception {
        int databaseSizeBeforeUpdate = carRentalCompanyRepository.findAll().size();
        carRentalCompany.setId(count.incrementAndGet());

        // Create the CarRentalCompany
        CarRentalCompanyDTO carRentalCompanyDTO = carRentalCompanyMapper.toDto(carRentalCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarRentalCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carRentalCompanyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carRentalCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarRentalCompany in the database
        List<CarRentalCompany> carRentalCompanyList = carRentalCompanyRepository.findAll();
        assertThat(carRentalCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCarRentalCompany() throws Exception {
        int databaseSizeBeforeUpdate = carRentalCompanyRepository.findAll().size();
        carRentalCompany.setId(count.incrementAndGet());

        // Create the CarRentalCompany
        CarRentalCompanyDTO carRentalCompanyDTO = carRentalCompanyMapper.toDto(carRentalCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarRentalCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carRentalCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarRentalCompany in the database
        List<CarRentalCompany> carRentalCompanyList = carRentalCompanyRepository.findAll();
        assertThat(carRentalCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCarRentalCompany() throws Exception {
        int databaseSizeBeforeUpdate = carRentalCompanyRepository.findAll().size();
        carRentalCompany.setId(count.incrementAndGet());

        // Create the CarRentalCompany
        CarRentalCompanyDTO carRentalCompanyDTO = carRentalCompanyMapper.toDto(carRentalCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarRentalCompanyMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carRentalCompanyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarRentalCompany in the database
        List<CarRentalCompany> carRentalCompanyList = carRentalCompanyRepository.findAll();
        assertThat(carRentalCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCarRentalCompanyWithPatch() throws Exception {
        // Initialize the database
        carRentalCompanyRepository.saveAndFlush(carRentalCompany);

        int databaseSizeBeforeUpdate = carRentalCompanyRepository.findAll().size();

        // Update the carRentalCompany using partial update
        CarRentalCompany partialUpdatedCarRentalCompany = new CarRentalCompany();
        partialUpdatedCarRentalCompany.setId(carRentalCompany.getId());

        restCarRentalCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarRentalCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarRentalCompany))
            )
            .andExpect(status().isOk());

        // Validate the CarRentalCompany in the database
        List<CarRentalCompany> carRentalCompanyList = carRentalCompanyRepository.findAll();
        assertThat(carRentalCompanyList).hasSize(databaseSizeBeforeUpdate);
        CarRentalCompany testCarRentalCompany = carRentalCompanyList.get(carRentalCompanyList.size() - 1);
        assertThat(testCarRentalCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCarRentalCompany.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void fullUpdateCarRentalCompanyWithPatch() throws Exception {
        // Initialize the database
        carRentalCompanyRepository.saveAndFlush(carRentalCompany);

        int databaseSizeBeforeUpdate = carRentalCompanyRepository.findAll().size();

        // Update the carRentalCompany using partial update
        CarRentalCompany partialUpdatedCarRentalCompany = new CarRentalCompany();
        partialUpdatedCarRentalCompany.setId(carRentalCompany.getId());

        partialUpdatedCarRentalCompany.name(UPDATED_NAME).code(UPDATED_CODE);

        restCarRentalCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarRentalCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarRentalCompany))
            )
            .andExpect(status().isOk());

        // Validate the CarRentalCompany in the database
        List<CarRentalCompany> carRentalCompanyList = carRentalCompanyRepository.findAll();
        assertThat(carRentalCompanyList).hasSize(databaseSizeBeforeUpdate);
        CarRentalCompany testCarRentalCompany = carRentalCompanyList.get(carRentalCompanyList.size() - 1);
        assertThat(testCarRentalCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCarRentalCompany.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingCarRentalCompany() throws Exception {
        int databaseSizeBeforeUpdate = carRentalCompanyRepository.findAll().size();
        carRentalCompany.setId(count.incrementAndGet());

        // Create the CarRentalCompany
        CarRentalCompanyDTO carRentalCompanyDTO = carRentalCompanyMapper.toDto(carRentalCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarRentalCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carRentalCompanyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carRentalCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarRentalCompany in the database
        List<CarRentalCompany> carRentalCompanyList = carRentalCompanyRepository.findAll();
        assertThat(carRentalCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCarRentalCompany() throws Exception {
        int databaseSizeBeforeUpdate = carRentalCompanyRepository.findAll().size();
        carRentalCompany.setId(count.incrementAndGet());

        // Create the CarRentalCompany
        CarRentalCompanyDTO carRentalCompanyDTO = carRentalCompanyMapper.toDto(carRentalCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarRentalCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carRentalCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarRentalCompany in the database
        List<CarRentalCompany> carRentalCompanyList = carRentalCompanyRepository.findAll();
        assertThat(carRentalCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCarRentalCompany() throws Exception {
        int databaseSizeBeforeUpdate = carRentalCompanyRepository.findAll().size();
        carRentalCompany.setId(count.incrementAndGet());

        // Create the CarRentalCompany
        CarRentalCompanyDTO carRentalCompanyDTO = carRentalCompanyMapper.toDto(carRentalCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarRentalCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carRentalCompanyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarRentalCompany in the database
        List<CarRentalCompany> carRentalCompanyList = carRentalCompanyRepository.findAll();
        assertThat(carRentalCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCarRentalCompany() throws Exception {
        // Initialize the database
        carRentalCompanyRepository.saveAndFlush(carRentalCompany);

        int databaseSizeBeforeDelete = carRentalCompanyRepository.findAll().size();

        // Delete the carRentalCompany
        restCarRentalCompanyMockMvc
            .perform(delete(ENTITY_API_URL_ID, carRentalCompany.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CarRentalCompany> carRentalCompanyList = carRentalCompanyRepository.findAll();
        assertThat(carRentalCompanyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
