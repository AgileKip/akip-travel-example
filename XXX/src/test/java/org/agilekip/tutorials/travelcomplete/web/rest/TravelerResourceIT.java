package org.agilekip.tutorials.travelcomplete.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.agilekip.tutorials.travelcomplete.IntegrationTest;
import org.agilekip.tutorials.travelcomplete.domain.Traveler;
import org.agilekip.tutorials.travelcomplete.repository.TravelerRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelerDTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.TravelerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TravelerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TravelerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 0;
    private static final Integer UPDATED_AGE = 1;

    private static final String ENTITY_API_URL = "/api/travelers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TravelerRepository travelerRepository;

    @Autowired
    private TravelerMapper travelerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTravelerMockMvc;

    private Traveler traveler;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Traveler createEntity(EntityManager em) {
        Traveler traveler = new Traveler().name(DEFAULT_NAME).age(DEFAULT_AGE);
        return traveler;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Traveler createUpdatedEntity(EntityManager em) {
        Traveler traveler = new Traveler().name(UPDATED_NAME).age(UPDATED_AGE);
        return traveler;
    }

    @BeforeEach
    public void initTest() {
        traveler = createEntity(em);
    }

    @Test
    @Transactional
    void createTraveler() throws Exception {
        int databaseSizeBeforeCreate = travelerRepository.findAll().size();
        // Create the Traveler
        TravelerDTO travelerDTO = travelerMapper.toDto(traveler);
        restTravelerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(travelerDTO)))
            .andExpect(status().isCreated());

        // Validate the Traveler in the database
        List<Traveler> travelerList = travelerRepository.findAll();
        assertThat(travelerList).hasSize(databaseSizeBeforeCreate + 1);
        Traveler testTraveler = travelerList.get(travelerList.size() - 1);
        assertThat(testTraveler.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTraveler.getAge()).isEqualTo(DEFAULT_AGE);
    }

    @Test
    @Transactional
    void createTravelerWithExistingId() throws Exception {
        // Create the Traveler with an existing ID
        traveler.setId(1L);
        TravelerDTO travelerDTO = travelerMapper.toDto(traveler);

        int databaseSizeBeforeCreate = travelerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTravelerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(travelerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Traveler in the database
        List<Traveler> travelerList = travelerRepository.findAll();
        assertThat(travelerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = travelerRepository.findAll().size();
        // set the field null
        traveler.setAge(null);

        // Create the Traveler, which fails.
        TravelerDTO travelerDTO = travelerMapper.toDto(traveler);

        restTravelerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(travelerDTO)))
            .andExpect(status().isBadRequest());

        List<Traveler> travelerList = travelerRepository.findAll();
        assertThat(travelerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTravelers() throws Exception {
        // Initialize the database
        travelerRepository.saveAndFlush(traveler);

        // Get all the travelerList
        restTravelerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traveler.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)));
    }

    @Test
    @Transactional
    void getTraveler() throws Exception {
        // Initialize the database
        travelerRepository.saveAndFlush(traveler);

        // Get the traveler
        restTravelerMockMvc
            .perform(get(ENTITY_API_URL_ID, traveler.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(traveler.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE));
    }

    @Test
    @Transactional
    void getNonExistingTraveler() throws Exception {
        // Get the traveler
        restTravelerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTraveler() throws Exception {
        // Initialize the database
        travelerRepository.saveAndFlush(traveler);

        int databaseSizeBeforeUpdate = travelerRepository.findAll().size();

        // Update the traveler
        Traveler updatedTraveler = travelerRepository.findById(traveler.getId()).get();
        // Disconnect from session so that the updates on updatedTraveler are not directly saved in db
        em.detach(updatedTraveler);
        updatedTraveler.name(UPDATED_NAME).age(UPDATED_AGE);
        TravelerDTO travelerDTO = travelerMapper.toDto(updatedTraveler);

        restTravelerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, travelerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(travelerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Traveler in the database
        List<Traveler> travelerList = travelerRepository.findAll();
        assertThat(travelerList).hasSize(databaseSizeBeforeUpdate);
        Traveler testTraveler = travelerList.get(travelerList.size() - 1);
        assertThat(testTraveler.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTraveler.getAge()).isEqualTo(UPDATED_AGE);
    }

    @Test
    @Transactional
    void putNonExistingTraveler() throws Exception {
        int databaseSizeBeforeUpdate = travelerRepository.findAll().size();
        traveler.setId(count.incrementAndGet());

        // Create the Traveler
        TravelerDTO travelerDTO = travelerMapper.toDto(traveler);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTravelerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, travelerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(travelerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Traveler in the database
        List<Traveler> travelerList = travelerRepository.findAll();
        assertThat(travelerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTraveler() throws Exception {
        int databaseSizeBeforeUpdate = travelerRepository.findAll().size();
        traveler.setId(count.incrementAndGet());

        // Create the Traveler
        TravelerDTO travelerDTO = travelerMapper.toDto(traveler);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTravelerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(travelerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Traveler in the database
        List<Traveler> travelerList = travelerRepository.findAll();
        assertThat(travelerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTraveler() throws Exception {
        int databaseSizeBeforeUpdate = travelerRepository.findAll().size();
        traveler.setId(count.incrementAndGet());

        // Create the Traveler
        TravelerDTO travelerDTO = travelerMapper.toDto(traveler);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTravelerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(travelerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Traveler in the database
        List<Traveler> travelerList = travelerRepository.findAll();
        assertThat(travelerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTravelerWithPatch() throws Exception {
        // Initialize the database
        travelerRepository.saveAndFlush(traveler);

        int databaseSizeBeforeUpdate = travelerRepository.findAll().size();

        // Update the traveler using partial update
        Traveler partialUpdatedTraveler = new Traveler();
        partialUpdatedTraveler.setId(traveler.getId());

        partialUpdatedTraveler.name(UPDATED_NAME).age(UPDATED_AGE);

        restTravelerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraveler.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTraveler))
            )
            .andExpect(status().isOk());

        // Validate the Traveler in the database
        List<Traveler> travelerList = travelerRepository.findAll();
        assertThat(travelerList).hasSize(databaseSizeBeforeUpdate);
        Traveler testTraveler = travelerList.get(travelerList.size() - 1);
        assertThat(testTraveler.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTraveler.getAge()).isEqualTo(UPDATED_AGE);
    }

    @Test
    @Transactional
    void fullUpdateTravelerWithPatch() throws Exception {
        // Initialize the database
        travelerRepository.saveAndFlush(traveler);

        int databaseSizeBeforeUpdate = travelerRepository.findAll().size();

        // Update the traveler using partial update
        Traveler partialUpdatedTraveler = new Traveler();
        partialUpdatedTraveler.setId(traveler.getId());

        partialUpdatedTraveler.name(UPDATED_NAME).age(UPDATED_AGE);

        restTravelerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTraveler.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTraveler))
            )
            .andExpect(status().isOk());

        // Validate the Traveler in the database
        List<Traveler> travelerList = travelerRepository.findAll();
        assertThat(travelerList).hasSize(databaseSizeBeforeUpdate);
        Traveler testTraveler = travelerList.get(travelerList.size() - 1);
        assertThat(testTraveler.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTraveler.getAge()).isEqualTo(UPDATED_AGE);
    }

    @Test
    @Transactional
    void patchNonExistingTraveler() throws Exception {
        int databaseSizeBeforeUpdate = travelerRepository.findAll().size();
        traveler.setId(count.incrementAndGet());

        // Create the Traveler
        TravelerDTO travelerDTO = travelerMapper.toDto(traveler);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTravelerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, travelerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(travelerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Traveler in the database
        List<Traveler> travelerList = travelerRepository.findAll();
        assertThat(travelerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTraveler() throws Exception {
        int databaseSizeBeforeUpdate = travelerRepository.findAll().size();
        traveler.setId(count.incrementAndGet());

        // Create the Traveler
        TravelerDTO travelerDTO = travelerMapper.toDto(traveler);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTravelerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(travelerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Traveler in the database
        List<Traveler> travelerList = travelerRepository.findAll();
        assertThat(travelerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTraveler() throws Exception {
        int databaseSizeBeforeUpdate = travelerRepository.findAll().size();
        traveler.setId(count.incrementAndGet());

        // Create the Traveler
        TravelerDTO travelerDTO = travelerMapper.toDto(traveler);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTravelerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(travelerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Traveler in the database
        List<Traveler> travelerList = travelerRepository.findAll();
        assertThat(travelerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTraveler() throws Exception {
        // Initialize the database
        travelerRepository.saveAndFlush(traveler);

        int databaseSizeBeforeDelete = travelerRepository.findAll().size();

        // Delete the traveler
        restTravelerMockMvc
            .perform(delete(ENTITY_API_URL_ID, traveler.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Traveler> travelerList = travelerRepository.findAll();
        assertThat(travelerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
