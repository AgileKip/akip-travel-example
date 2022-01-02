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
import org.agilekip.tutorials.travelcomplete.domain.TravelPlanStartForm;
import org.agilekip.tutorials.travelcomplete.repository.TravelPlanStartFormRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelPlanStartFormDTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.TravelPlanStartFormMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TravelPlanStartFormResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TravelPlanStartFormResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM_PAX = 1;
    private static final Integer UPDATED_NUM_PAX = 2;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/travel-plan-start-forms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TravelPlanStartFormRepository travelPlanStartFormRepository;

    @Autowired
    private TravelPlanStartFormMapper travelPlanStartFormMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTravelPlanStartFormMockMvc;

    private TravelPlanStartForm travelPlanStartForm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TravelPlanStartForm createEntity(EntityManager em) {
        TravelPlanStartForm travelPlanStartForm = new TravelPlanStartForm()
            .name(DEFAULT_NAME)
            .numPax(DEFAULT_NUM_PAX)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return travelPlanStartForm;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TravelPlanStartForm createUpdatedEntity(EntityManager em) {
        TravelPlanStartForm travelPlanStartForm = new TravelPlanStartForm()
            .name(UPDATED_NAME)
            .numPax(UPDATED_NUM_PAX)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return travelPlanStartForm;
    }

    @BeforeEach
    public void initTest() {
        travelPlanStartForm = createEntity(em);
    }

    @Test
    @Transactional
    void createTravelPlanStartForm() throws Exception {
        int databaseSizeBeforeCreate = travelPlanStartFormRepository.findAll().size();
        // Create the TravelPlanStartForm
        TravelPlanStartFormDTO travelPlanStartFormDTO = travelPlanStartFormMapper.toDto(travelPlanStartForm);
        restTravelPlanStartFormMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(travelPlanStartFormDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TravelPlanStartForm in the database
        List<TravelPlanStartForm> travelPlanStartFormList = travelPlanStartFormRepository.findAll();
        assertThat(travelPlanStartFormList).hasSize(databaseSizeBeforeCreate + 1);
        TravelPlanStartForm testTravelPlanStartForm = travelPlanStartFormList.get(travelPlanStartFormList.size() - 1);
        assertThat(testTravelPlanStartForm.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTravelPlanStartForm.getNumPax()).isEqualTo(DEFAULT_NUM_PAX);
        assertThat(testTravelPlanStartForm.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTravelPlanStartForm.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    void createTravelPlanStartFormWithExistingId() throws Exception {
        // Create the TravelPlanStartForm with an existing ID
        travelPlanStartForm.setId(1L);
        TravelPlanStartFormDTO travelPlanStartFormDTO = travelPlanStartFormMapper.toDto(travelPlanStartForm);

        int databaseSizeBeforeCreate = travelPlanStartFormRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTravelPlanStartFormMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(travelPlanStartFormDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TravelPlanStartForm in the database
        List<TravelPlanStartForm> travelPlanStartFormList = travelPlanStartFormRepository.findAll();
        assertThat(travelPlanStartFormList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTravelPlanStartForms() throws Exception {
        // Initialize the database
        travelPlanStartFormRepository.saveAndFlush(travelPlanStartForm);

        // Get all the travelPlanStartFormList
        restTravelPlanStartFormMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(travelPlanStartForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].numPax").value(hasItem(DEFAULT_NUM_PAX)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    void getTravelPlanStartForm() throws Exception {
        // Initialize the database
        travelPlanStartFormRepository.saveAndFlush(travelPlanStartForm);

        // Get the travelPlanStartForm
        restTravelPlanStartFormMockMvc
            .perform(get(ENTITY_API_URL_ID, travelPlanStartForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(travelPlanStartForm.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.numPax").value(DEFAULT_NUM_PAX))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTravelPlanStartForm() throws Exception {
        // Get the travelPlanStartForm
        restTravelPlanStartFormMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTravelPlanStartForm() throws Exception {
        // Initialize the database
        travelPlanStartFormRepository.saveAndFlush(travelPlanStartForm);

        int databaseSizeBeforeUpdate = travelPlanStartFormRepository.findAll().size();

        // Update the travelPlanStartForm
        TravelPlanStartForm updatedTravelPlanStartForm = travelPlanStartFormRepository.findById(travelPlanStartForm.getId()).get();
        // Disconnect from session so that the updates on updatedTravelPlanStartForm are not directly saved in db
        em.detach(updatedTravelPlanStartForm);
        updatedTravelPlanStartForm.name(UPDATED_NAME).numPax(UPDATED_NUM_PAX).startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE);
        TravelPlanStartFormDTO travelPlanStartFormDTO = travelPlanStartFormMapper.toDto(updatedTravelPlanStartForm);

        restTravelPlanStartFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, travelPlanStartFormDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(travelPlanStartFormDTO))
            )
            .andExpect(status().isOk());

        // Validate the TravelPlanStartForm in the database
        List<TravelPlanStartForm> travelPlanStartFormList = travelPlanStartFormRepository.findAll();
        assertThat(travelPlanStartFormList).hasSize(databaseSizeBeforeUpdate);
        TravelPlanStartForm testTravelPlanStartForm = travelPlanStartFormList.get(travelPlanStartFormList.size() - 1);
        assertThat(testTravelPlanStartForm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTravelPlanStartForm.getNumPax()).isEqualTo(UPDATED_NUM_PAX);
        assertThat(testTravelPlanStartForm.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTravelPlanStartForm.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTravelPlanStartForm() throws Exception {
        int databaseSizeBeforeUpdate = travelPlanStartFormRepository.findAll().size();
        travelPlanStartForm.setId(count.incrementAndGet());

        // Create the TravelPlanStartForm
        TravelPlanStartFormDTO travelPlanStartFormDTO = travelPlanStartFormMapper.toDto(travelPlanStartForm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTravelPlanStartFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, travelPlanStartFormDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(travelPlanStartFormDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TravelPlanStartForm in the database
        List<TravelPlanStartForm> travelPlanStartFormList = travelPlanStartFormRepository.findAll();
        assertThat(travelPlanStartFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTravelPlanStartForm() throws Exception {
        int databaseSizeBeforeUpdate = travelPlanStartFormRepository.findAll().size();
        travelPlanStartForm.setId(count.incrementAndGet());

        // Create the TravelPlanStartForm
        TravelPlanStartFormDTO travelPlanStartFormDTO = travelPlanStartFormMapper.toDto(travelPlanStartForm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTravelPlanStartFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(travelPlanStartFormDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TravelPlanStartForm in the database
        List<TravelPlanStartForm> travelPlanStartFormList = travelPlanStartFormRepository.findAll();
        assertThat(travelPlanStartFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTravelPlanStartForm() throws Exception {
        int databaseSizeBeforeUpdate = travelPlanStartFormRepository.findAll().size();
        travelPlanStartForm.setId(count.incrementAndGet());

        // Create the TravelPlanStartForm
        TravelPlanStartFormDTO travelPlanStartFormDTO = travelPlanStartFormMapper.toDto(travelPlanStartForm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTravelPlanStartFormMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(travelPlanStartFormDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TravelPlanStartForm in the database
        List<TravelPlanStartForm> travelPlanStartFormList = travelPlanStartFormRepository.findAll();
        assertThat(travelPlanStartFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTravelPlanStartFormWithPatch() throws Exception {
        // Initialize the database
        travelPlanStartFormRepository.saveAndFlush(travelPlanStartForm);

        int databaseSizeBeforeUpdate = travelPlanStartFormRepository.findAll().size();

        // Update the travelPlanStartForm using partial update
        TravelPlanStartForm partialUpdatedTravelPlanStartForm = new TravelPlanStartForm();
        partialUpdatedTravelPlanStartForm.setId(travelPlanStartForm.getId());

        partialUpdatedTravelPlanStartForm.numPax(UPDATED_NUM_PAX).endDate(UPDATED_END_DATE);

        restTravelPlanStartFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTravelPlanStartForm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTravelPlanStartForm))
            )
            .andExpect(status().isOk());

        // Validate the TravelPlanStartForm in the database
        List<TravelPlanStartForm> travelPlanStartFormList = travelPlanStartFormRepository.findAll();
        assertThat(travelPlanStartFormList).hasSize(databaseSizeBeforeUpdate);
        TravelPlanStartForm testTravelPlanStartForm = travelPlanStartFormList.get(travelPlanStartFormList.size() - 1);
        assertThat(testTravelPlanStartForm.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTravelPlanStartForm.getNumPax()).isEqualTo(UPDATED_NUM_PAX);
        assertThat(testTravelPlanStartForm.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTravelPlanStartForm.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTravelPlanStartFormWithPatch() throws Exception {
        // Initialize the database
        travelPlanStartFormRepository.saveAndFlush(travelPlanStartForm);

        int databaseSizeBeforeUpdate = travelPlanStartFormRepository.findAll().size();

        // Update the travelPlanStartForm using partial update
        TravelPlanStartForm partialUpdatedTravelPlanStartForm = new TravelPlanStartForm();
        partialUpdatedTravelPlanStartForm.setId(travelPlanStartForm.getId());

        partialUpdatedTravelPlanStartForm
            .name(UPDATED_NAME)
            .numPax(UPDATED_NUM_PAX)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);

        restTravelPlanStartFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTravelPlanStartForm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTravelPlanStartForm))
            )
            .andExpect(status().isOk());

        // Validate the TravelPlanStartForm in the database
        List<TravelPlanStartForm> travelPlanStartFormList = travelPlanStartFormRepository.findAll();
        assertThat(travelPlanStartFormList).hasSize(databaseSizeBeforeUpdate);
        TravelPlanStartForm testTravelPlanStartForm = travelPlanStartFormList.get(travelPlanStartFormList.size() - 1);
        assertThat(testTravelPlanStartForm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTravelPlanStartForm.getNumPax()).isEqualTo(UPDATED_NUM_PAX);
        assertThat(testTravelPlanStartForm.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTravelPlanStartForm.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTravelPlanStartForm() throws Exception {
        int databaseSizeBeforeUpdate = travelPlanStartFormRepository.findAll().size();
        travelPlanStartForm.setId(count.incrementAndGet());

        // Create the TravelPlanStartForm
        TravelPlanStartFormDTO travelPlanStartFormDTO = travelPlanStartFormMapper.toDto(travelPlanStartForm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTravelPlanStartFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, travelPlanStartFormDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(travelPlanStartFormDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TravelPlanStartForm in the database
        List<TravelPlanStartForm> travelPlanStartFormList = travelPlanStartFormRepository.findAll();
        assertThat(travelPlanStartFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTravelPlanStartForm() throws Exception {
        int databaseSizeBeforeUpdate = travelPlanStartFormRepository.findAll().size();
        travelPlanStartForm.setId(count.incrementAndGet());

        // Create the TravelPlanStartForm
        TravelPlanStartFormDTO travelPlanStartFormDTO = travelPlanStartFormMapper.toDto(travelPlanStartForm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTravelPlanStartFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(travelPlanStartFormDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TravelPlanStartForm in the database
        List<TravelPlanStartForm> travelPlanStartFormList = travelPlanStartFormRepository.findAll();
        assertThat(travelPlanStartFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTravelPlanStartForm() throws Exception {
        int databaseSizeBeforeUpdate = travelPlanStartFormRepository.findAll().size();
        travelPlanStartForm.setId(count.incrementAndGet());

        // Create the TravelPlanStartForm
        TravelPlanStartFormDTO travelPlanStartFormDTO = travelPlanStartFormMapper.toDto(travelPlanStartForm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTravelPlanStartFormMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(travelPlanStartFormDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TravelPlanStartForm in the database
        List<TravelPlanStartForm> travelPlanStartFormList = travelPlanStartFormRepository.findAll();
        assertThat(travelPlanStartFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTravelPlanStartForm() throws Exception {
        // Initialize the database
        travelPlanStartFormRepository.saveAndFlush(travelPlanStartForm);

        int databaseSizeBeforeDelete = travelPlanStartFormRepository.findAll().size();

        // Delete the travelPlanStartForm
        restTravelPlanStartFormMockMvc
            .perform(delete(ENTITY_API_URL_ID, travelPlanStartForm.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TravelPlanStartForm> travelPlanStartFormList = travelPlanStartFormRepository.findAll();
        assertThat(travelPlanStartFormList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
