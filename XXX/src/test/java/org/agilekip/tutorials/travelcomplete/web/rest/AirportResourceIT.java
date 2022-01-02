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
import org.agilekip.tutorials.travelcomplete.domain.Airport;
import org.agilekip.tutorials.travelcomplete.repository.AirportRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.AirportDTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.AirportMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AirportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AirportResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAA";
    private static final String UPDATED_CODE = "BBB";

    private static final String ENTITY_API_URL = "/api/airports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private AirportMapper airportMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAirportMockMvc;

    private Airport airport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Airport createEntity(EntityManager em) {
        Airport airport = new Airport().name(DEFAULT_NAME).city(DEFAULT_CITY).code(DEFAULT_CODE);
        return airport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Airport createUpdatedEntity(EntityManager em) {
        Airport airport = new Airport().name(UPDATED_NAME).city(UPDATED_CITY).code(UPDATED_CODE);
        return airport;
    }

    @BeforeEach
    public void initTest() {
        airport = createEntity(em);
    }

    @Test
    @Transactional
    void createAirport() throws Exception {
        int databaseSizeBeforeCreate = airportRepository.findAll().size();
        // Create the Airport
        AirportDTO airportDTO = airportMapper.toDto(airport);
        restAirportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(airportDTO)))
            .andExpect(status().isCreated());

        // Validate the Airport in the database
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeCreate + 1);
        Airport testAirport = airportList.get(airportList.size() - 1);
        assertThat(testAirport.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAirport.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAirport.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createAirportWithExistingId() throws Exception {
        // Create the Airport with an existing ID
        airport.setId(1L);
        AirportDTO airportDTO = airportMapper.toDto(airport);

        int databaseSizeBeforeCreate = airportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAirportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(airportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Airport in the database
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = airportRepository.findAll().size();
        // set the field null
        airport.setName(null);

        // Create the Airport, which fails.
        AirportDTO airportDTO = airportMapper.toDto(airport);

        restAirportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(airportDTO)))
            .andExpect(status().isBadRequest());

        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = airportRepository.findAll().size();
        // set the field null
        airport.setCity(null);

        // Create the Airport, which fails.
        AirportDTO airportDTO = airportMapper.toDto(airport);

        restAirportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(airportDTO)))
            .andExpect(status().isBadRequest());

        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = airportRepository.findAll().size();
        // set the field null
        airport.setCode(null);

        // Create the Airport, which fails.
        AirportDTO airportDTO = airportMapper.toDto(airport);

        restAirportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(airportDTO)))
            .andExpect(status().isBadRequest());

        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAirports() throws Exception {
        // Initialize the database
        airportRepository.saveAndFlush(airport);

        // Get all the airportList
        restAirportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(airport.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getAirport() throws Exception {
        // Initialize the database
        airportRepository.saveAndFlush(airport);

        // Get the airport
        restAirportMockMvc
            .perform(get(ENTITY_API_URL_ID, airport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(airport.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingAirport() throws Exception {
        // Get the airport
        restAirportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAirport() throws Exception {
        // Initialize the database
        airportRepository.saveAndFlush(airport);

        int databaseSizeBeforeUpdate = airportRepository.findAll().size();

        // Update the airport
        Airport updatedAirport = airportRepository.findById(airport.getId()).get();
        // Disconnect from session so that the updates on updatedAirport are not directly saved in db
        em.detach(updatedAirport);
        updatedAirport.name(UPDATED_NAME).city(UPDATED_CITY).code(UPDATED_CODE);
        AirportDTO airportDTO = airportMapper.toDto(updatedAirport);

        restAirportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, airportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(airportDTO))
            )
            .andExpect(status().isOk());

        // Validate the Airport in the database
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeUpdate);
        Airport testAirport = airportList.get(airportList.size() - 1);
        assertThat(testAirport.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAirport.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAirport.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingAirport() throws Exception {
        int databaseSizeBeforeUpdate = airportRepository.findAll().size();
        airport.setId(count.incrementAndGet());

        // Create the Airport
        AirportDTO airportDTO = airportMapper.toDto(airport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAirportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, airportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(airportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Airport in the database
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAirport() throws Exception {
        int databaseSizeBeforeUpdate = airportRepository.findAll().size();
        airport.setId(count.incrementAndGet());

        // Create the Airport
        AirportDTO airportDTO = airportMapper.toDto(airport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAirportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(airportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Airport in the database
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAirport() throws Exception {
        int databaseSizeBeforeUpdate = airportRepository.findAll().size();
        airport.setId(count.incrementAndGet());

        // Create the Airport
        AirportDTO airportDTO = airportMapper.toDto(airport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAirportMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(airportDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Airport in the database
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAirportWithPatch() throws Exception {
        // Initialize the database
        airportRepository.saveAndFlush(airport);

        int databaseSizeBeforeUpdate = airportRepository.findAll().size();

        // Update the airport using partial update
        Airport partialUpdatedAirport = new Airport();
        partialUpdatedAirport.setId(airport.getId());

        partialUpdatedAirport.name(UPDATED_NAME).code(UPDATED_CODE);

        restAirportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAirport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAirport))
            )
            .andExpect(status().isOk());

        // Validate the Airport in the database
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeUpdate);
        Airport testAirport = airportList.get(airportList.size() - 1);
        assertThat(testAirport.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAirport.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAirport.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void fullUpdateAirportWithPatch() throws Exception {
        // Initialize the database
        airportRepository.saveAndFlush(airport);

        int databaseSizeBeforeUpdate = airportRepository.findAll().size();

        // Update the airport using partial update
        Airport partialUpdatedAirport = new Airport();
        partialUpdatedAirport.setId(airport.getId());

        partialUpdatedAirport.name(UPDATED_NAME).city(UPDATED_CITY).code(UPDATED_CODE);

        restAirportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAirport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAirport))
            )
            .andExpect(status().isOk());

        // Validate the Airport in the database
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeUpdate);
        Airport testAirport = airportList.get(airportList.size() - 1);
        assertThat(testAirport.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAirport.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAirport.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingAirport() throws Exception {
        int databaseSizeBeforeUpdate = airportRepository.findAll().size();
        airport.setId(count.incrementAndGet());

        // Create the Airport
        AirportDTO airportDTO = airportMapper.toDto(airport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAirportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, airportDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(airportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Airport in the database
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAirport() throws Exception {
        int databaseSizeBeforeUpdate = airportRepository.findAll().size();
        airport.setId(count.incrementAndGet());

        // Create the Airport
        AirportDTO airportDTO = airportMapper.toDto(airport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAirportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(airportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Airport in the database
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAirport() throws Exception {
        int databaseSizeBeforeUpdate = airportRepository.findAll().size();
        airport.setId(count.incrementAndGet());

        // Create the Airport
        AirportDTO airportDTO = airportMapper.toDto(airport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAirportMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(airportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Airport in the database
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAirport() throws Exception {
        // Initialize the database
        airportRepository.saveAndFlush(airport);

        int databaseSizeBeforeDelete = airportRepository.findAll().size();

        // Delete the airport
        restAirportMockMvc
            .perform(delete(ENTITY_API_URL_ID, airport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Airport> airportList = airportRepository.findAll();
        assertThat(airportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
