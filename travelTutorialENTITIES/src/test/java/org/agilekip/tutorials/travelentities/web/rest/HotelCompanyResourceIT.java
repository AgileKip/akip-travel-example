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
import org.agilekip.tutorials.travelentities.domain.HotelCompany;
import org.agilekip.tutorials.travelentities.repository.HotelCompanyRepository;
import org.agilekip.tutorials.travelentities.service.dto.HotelCompanyDTO;
import org.agilekip.tutorials.travelentities.service.mapper.HotelCompanyMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HotelCompanyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HotelCompanyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hotel-companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HotelCompanyRepository hotelCompanyRepository;

    @Autowired
    private HotelCompanyMapper hotelCompanyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHotelCompanyMockMvc;

    private HotelCompany hotelCompany;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HotelCompany createEntity(EntityManager em) {
        HotelCompany hotelCompany = new HotelCompany().name(DEFAULT_NAME).code(DEFAULT_CODE);
        return hotelCompany;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HotelCompany createUpdatedEntity(EntityManager em) {
        HotelCompany hotelCompany = new HotelCompany().name(UPDATED_NAME).code(UPDATED_CODE);
        return hotelCompany;
    }

    @BeforeEach
    public void initTest() {
        hotelCompany = createEntity(em);
    }

    @Test
    @Transactional
    void createHotelCompany() throws Exception {
        int databaseSizeBeforeCreate = hotelCompanyRepository.findAll().size();
        // Create the HotelCompany
        HotelCompanyDTO hotelCompanyDTO = hotelCompanyMapper.toDto(hotelCompany);
        restHotelCompanyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hotelCompanyDTO))
            )
            .andExpect(status().isCreated());

        // Validate the HotelCompany in the database
        List<HotelCompany> hotelCompanyList = hotelCompanyRepository.findAll();
        assertThat(hotelCompanyList).hasSize(databaseSizeBeforeCreate + 1);
        HotelCompany testHotelCompany = hotelCompanyList.get(hotelCompanyList.size() - 1);
        assertThat(testHotelCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHotelCompany.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createHotelCompanyWithExistingId() throws Exception {
        // Create the HotelCompany with an existing ID
        hotelCompany.setId(1L);
        HotelCompanyDTO hotelCompanyDTO = hotelCompanyMapper.toDto(hotelCompany);

        int databaseSizeBeforeCreate = hotelCompanyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHotelCompanyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hotelCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelCompany in the database
        List<HotelCompany> hotelCompanyList = hotelCompanyRepository.findAll();
        assertThat(hotelCompanyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHotelCompanies() throws Exception {
        // Initialize the database
        hotelCompanyRepository.saveAndFlush(hotelCompany);

        // Get all the hotelCompanyList
        restHotelCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotelCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getHotelCompany() throws Exception {
        // Initialize the database
        hotelCompanyRepository.saveAndFlush(hotelCompany);

        // Get the hotelCompany
        restHotelCompanyMockMvc
            .perform(get(ENTITY_API_URL_ID, hotelCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hotelCompany.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingHotelCompany() throws Exception {
        // Get the hotelCompany
        restHotelCompanyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHotelCompany() throws Exception {
        // Initialize the database
        hotelCompanyRepository.saveAndFlush(hotelCompany);

        int databaseSizeBeforeUpdate = hotelCompanyRepository.findAll().size();

        // Update the hotelCompany
        HotelCompany updatedHotelCompany = hotelCompanyRepository.findById(hotelCompany.getId()).get();
        // Disconnect from session so that the updates on updatedHotelCompany are not directly saved in db
        em.detach(updatedHotelCompany);
        updatedHotelCompany.name(UPDATED_NAME).code(UPDATED_CODE);
        HotelCompanyDTO hotelCompanyDTO = hotelCompanyMapper.toDto(updatedHotelCompany);

        restHotelCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hotelCompanyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelCompanyDTO))
            )
            .andExpect(status().isOk());

        // Validate the HotelCompany in the database
        List<HotelCompany> hotelCompanyList = hotelCompanyRepository.findAll();
        assertThat(hotelCompanyList).hasSize(databaseSizeBeforeUpdate);
        HotelCompany testHotelCompany = hotelCompanyList.get(hotelCompanyList.size() - 1);
        assertThat(testHotelCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHotelCompany.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingHotelCompany() throws Exception {
        int databaseSizeBeforeUpdate = hotelCompanyRepository.findAll().size();
        hotelCompany.setId(count.incrementAndGet());

        // Create the HotelCompany
        HotelCompanyDTO hotelCompanyDTO = hotelCompanyMapper.toDto(hotelCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hotelCompanyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelCompany in the database
        List<HotelCompany> hotelCompanyList = hotelCompanyRepository.findAll();
        assertThat(hotelCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHotelCompany() throws Exception {
        int databaseSizeBeforeUpdate = hotelCompanyRepository.findAll().size();
        hotelCompany.setId(count.incrementAndGet());

        // Create the HotelCompany
        HotelCompanyDTO hotelCompanyDTO = hotelCompanyMapper.toDto(hotelCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelCompany in the database
        List<HotelCompany> hotelCompanyList = hotelCompanyRepository.findAll();
        assertThat(hotelCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHotelCompany() throws Exception {
        int databaseSizeBeforeUpdate = hotelCompanyRepository.findAll().size();
        hotelCompany.setId(count.incrementAndGet());

        // Create the HotelCompany
        HotelCompanyDTO hotelCompanyDTO = hotelCompanyMapper.toDto(hotelCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelCompanyMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hotelCompanyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HotelCompany in the database
        List<HotelCompany> hotelCompanyList = hotelCompanyRepository.findAll();
        assertThat(hotelCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHotelCompanyWithPatch() throws Exception {
        // Initialize the database
        hotelCompanyRepository.saveAndFlush(hotelCompany);

        int databaseSizeBeforeUpdate = hotelCompanyRepository.findAll().size();

        // Update the hotelCompany using partial update
        HotelCompany partialUpdatedHotelCompany = new HotelCompany();
        partialUpdatedHotelCompany.setId(hotelCompany.getId());

        restHotelCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotelCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHotelCompany))
            )
            .andExpect(status().isOk());

        // Validate the HotelCompany in the database
        List<HotelCompany> hotelCompanyList = hotelCompanyRepository.findAll();
        assertThat(hotelCompanyList).hasSize(databaseSizeBeforeUpdate);
        HotelCompany testHotelCompany = hotelCompanyList.get(hotelCompanyList.size() - 1);
        assertThat(testHotelCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHotelCompany.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void fullUpdateHotelCompanyWithPatch() throws Exception {
        // Initialize the database
        hotelCompanyRepository.saveAndFlush(hotelCompany);

        int databaseSizeBeforeUpdate = hotelCompanyRepository.findAll().size();

        // Update the hotelCompany using partial update
        HotelCompany partialUpdatedHotelCompany = new HotelCompany();
        partialUpdatedHotelCompany.setId(hotelCompany.getId());

        partialUpdatedHotelCompany.name(UPDATED_NAME).code(UPDATED_CODE);

        restHotelCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotelCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHotelCompany))
            )
            .andExpect(status().isOk());

        // Validate the HotelCompany in the database
        List<HotelCompany> hotelCompanyList = hotelCompanyRepository.findAll();
        assertThat(hotelCompanyList).hasSize(databaseSizeBeforeUpdate);
        HotelCompany testHotelCompany = hotelCompanyList.get(hotelCompanyList.size() - 1);
        assertThat(testHotelCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHotelCompany.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingHotelCompany() throws Exception {
        int databaseSizeBeforeUpdate = hotelCompanyRepository.findAll().size();
        hotelCompany.setId(count.incrementAndGet());

        // Create the HotelCompany
        HotelCompanyDTO hotelCompanyDTO = hotelCompanyMapper.toDto(hotelCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hotelCompanyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hotelCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelCompany in the database
        List<HotelCompany> hotelCompanyList = hotelCompanyRepository.findAll();
        assertThat(hotelCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHotelCompany() throws Exception {
        int databaseSizeBeforeUpdate = hotelCompanyRepository.findAll().size();
        hotelCompany.setId(count.incrementAndGet());

        // Create the HotelCompany
        HotelCompanyDTO hotelCompanyDTO = hotelCompanyMapper.toDto(hotelCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hotelCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelCompany in the database
        List<HotelCompany> hotelCompanyList = hotelCompanyRepository.findAll();
        assertThat(hotelCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHotelCompany() throws Exception {
        int databaseSizeBeforeUpdate = hotelCompanyRepository.findAll().size();
        hotelCompany.setId(count.incrementAndGet());

        // Create the HotelCompany
        HotelCompanyDTO hotelCompanyDTO = hotelCompanyMapper.toDto(hotelCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hotelCompanyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HotelCompany in the database
        List<HotelCompany> hotelCompanyList = hotelCompanyRepository.findAll();
        assertThat(hotelCompanyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHotelCompany() throws Exception {
        // Initialize the database
        hotelCompanyRepository.saveAndFlush(hotelCompany);

        int databaseSizeBeforeDelete = hotelCompanyRepository.findAll().size();

        // Delete the hotelCompany
        restHotelCompanyMockMvc
            .perform(delete(ENTITY_API_URL_ID, hotelCompany.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HotelCompany> hotelCompanyList = hotelCompanyRepository.findAll();
        assertThat(hotelCompanyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
