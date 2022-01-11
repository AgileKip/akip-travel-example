package org.agilekip.tutorials.travelcomplete.web.rest;

import static org.agilekip.tutorials.travelcomplete.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.agilekip.tutorials.travelcomplete.IntegrationTest;
import org.agilekip.tutorials.travelcomplete.domain.AirlineCompany;
import org.agilekip.tutorials.travelcomplete.domain.Airport;
import org.agilekip.tutorials.travelcomplete.domain.Flight;
import org.agilekip.tutorials.travelcomplete.repository.FlightRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.FlightDTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.FlightMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FlightResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FlightResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final ZonedDateTime DEFAULT_DEPARTURE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DEPARTURE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Float DEFAULT_DURATION = 1F;
    private static final Float UPDATED_DURATION = 2F;

    private static final Integer DEFAULT_EMPTY_SEATS = 1;
    private static final Integer UPDATED_EMPTY_SEATS = 2;

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final String ENTITY_API_URL = "/api/flights";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFlightMockMvc;

    private Flight flight;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Flight createEntity(EntityManager em) {
        Flight flight = new Flight()
            .code(DEFAULT_CODE)
            .departure(DEFAULT_DEPARTURE)
            .duration(DEFAULT_DURATION)
            .emptySeats(DEFAULT_EMPTY_SEATS)
            .price(DEFAULT_PRICE);
        // Add required entity
        Airport airport;
        if (TestUtil.findAll(em, Airport.class).isEmpty()) {
            airport = AirportResourceIT.createEntity(em);
            em.persist(airport);
            em.flush();
        } else {
            airport = TestUtil.findAll(em, Airport.class).get(0);
        }
        flight.setFrom(airport);
        // Add required entity
        flight.setTo(airport);
        // Add required entity
        AirlineCompany airlineCompany;
        if (TestUtil.findAll(em, AirlineCompany.class).isEmpty()) {
            airlineCompany = AirlineCompanyResourceIT.createEntity(em);
            em.persist(airlineCompany);
            em.flush();
        } else {
            airlineCompany = TestUtil.findAll(em, AirlineCompany.class).get(0);
        }
        flight.setAirline(airlineCompany);
        return flight;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Flight createUpdatedEntity(EntityManager em) {
        Flight flight = new Flight()
            .code(UPDATED_CODE)
            .departure(UPDATED_DEPARTURE)
            .duration(UPDATED_DURATION)
            .emptySeats(UPDATED_EMPTY_SEATS)
            .price(UPDATED_PRICE);
        // Add required entity
        Airport airport;
        if (TestUtil.findAll(em, Airport.class).isEmpty()) {
            airport = AirportResourceIT.createUpdatedEntity(em);
            em.persist(airport);
            em.flush();
        } else {
            airport = TestUtil.findAll(em, Airport.class).get(0);
        }
        flight.setFrom(airport);
        // Add required entity
        flight.setTo(airport);
        // Add required entity
        AirlineCompany airlineCompany;
        if (TestUtil.findAll(em, AirlineCompany.class).isEmpty()) {
            airlineCompany = AirlineCompanyResourceIT.createUpdatedEntity(em);
            em.persist(airlineCompany);
            em.flush();
        } else {
            airlineCompany = TestUtil.findAll(em, AirlineCompany.class).get(0);
        }
        flight.setAirline(airlineCompany);
        return flight;
    }

    @BeforeEach
    public void initTest() {
        flight = createEntity(em);
    }

    @Test
    @Transactional
    void createFlight() throws Exception {
        int databaseSizeBeforeCreate = flightRepository.findAll().size();
        // Create the Flight
        FlightDTO flightDTO = flightMapper.toDto(flight);
        restFlightMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flightDTO)))
            .andExpect(status().isCreated());

        // Validate the Flight in the database
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeCreate + 1);
        Flight testFlight = flightList.get(flightList.size() - 1);
        assertThat(testFlight.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFlight.getDeparture()).isEqualTo(DEFAULT_DEPARTURE);
        assertThat(testFlight.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testFlight.getEmptySeats()).isEqualTo(DEFAULT_EMPTY_SEATS);
        assertThat(testFlight.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void createFlightWithExistingId() throws Exception {
        // Create the Flight with an existing ID
        flight.setId(1L);
        FlightDTO flightDTO = flightMapper.toDto(flight);

        int databaseSizeBeforeCreate = flightRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFlightMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flightDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Flight in the database
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = flightRepository.findAll().size();
        // set the field null
        flight.setCode(null);

        // Create the Flight, which fails.
        FlightDTO flightDTO = flightMapper.toDto(flight);

        restFlightMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flightDTO)))
            .andExpect(status().isBadRequest());

        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDepartureIsRequired() throws Exception {
        int databaseSizeBeforeTest = flightRepository.findAll().size();
        // set the field null
        flight.setDeparture(null);

        // Create the Flight, which fails.
        FlightDTO flightDTO = flightMapper.toDto(flight);

        restFlightMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flightDTO)))
            .andExpect(status().isBadRequest());

        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = flightRepository.findAll().size();
        // set the field null
        flight.setDuration(null);

        // Create the Flight, which fails.
        FlightDTO flightDTO = flightMapper.toDto(flight);

        restFlightMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flightDTO)))
            .andExpect(status().isBadRequest());

        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmptySeatsIsRequired() throws Exception {
        int databaseSizeBeforeTest = flightRepository.findAll().size();
        // set the field null
        flight.setEmptySeats(null);

        // Create the Flight, which fails.
        FlightDTO flightDTO = flightMapper.toDto(flight);

        restFlightMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flightDTO)))
            .andExpect(status().isBadRequest());

        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = flightRepository.findAll().size();
        // set the field null
        flight.setPrice(null);

        // Create the Flight, which fails.
        FlightDTO flightDTO = flightMapper.toDto(flight);

        restFlightMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flightDTO)))
            .andExpect(status().isBadRequest());

        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFlights() throws Exception {
        // Initialize the database
        flightRepository.saveAndFlush(flight);

        // Get all the flightList
        restFlightMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flight.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].departure").value(hasItem(sameInstant(DEFAULT_DEPARTURE))))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].emptySeats").value(hasItem(DEFAULT_EMPTY_SEATS)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)));
    }

    @Test
    @Transactional
    void getFlight() throws Exception {
        // Initialize the database
        flightRepository.saveAndFlush(flight);

        // Get the flight
        restFlightMockMvc
            .perform(get(ENTITY_API_URL_ID, flight.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(flight.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.departure").value(sameInstant(DEFAULT_DEPARTURE)))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.doubleValue()))
            .andExpect(jsonPath("$.emptySeats").value(DEFAULT_EMPTY_SEATS))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE));
    }

    @Test
    @Transactional
    void getNonExistingFlight() throws Exception {
        // Get the flight
        restFlightMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFlight() throws Exception {
        // Initialize the database
        flightRepository.saveAndFlush(flight);

        int databaseSizeBeforeUpdate = flightRepository.findAll().size();

        // Update the flight
        Flight updatedFlight = flightRepository.findById(flight.getId()).get();
        // Disconnect from session so that the updates on updatedFlight are not directly saved in db
        em.detach(updatedFlight);
        updatedFlight
            .code(UPDATED_CODE)
            .departure(UPDATED_DEPARTURE)
            .duration(UPDATED_DURATION)
            .emptySeats(UPDATED_EMPTY_SEATS)
            .price(UPDATED_PRICE);
        FlightDTO flightDTO = flightMapper.toDto(updatedFlight);

        restFlightMockMvc
            .perform(
                put(ENTITY_API_URL_ID, flightDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(flightDTO))
            )
            .andExpect(status().isOk());

        // Validate the Flight in the database
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeUpdate);
        Flight testFlight = flightList.get(flightList.size() - 1);
        assertThat(testFlight.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFlight.getDeparture()).isEqualTo(UPDATED_DEPARTURE);
        assertThat(testFlight.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testFlight.getEmptySeats()).isEqualTo(UPDATED_EMPTY_SEATS);
        assertThat(testFlight.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingFlight() throws Exception {
        int databaseSizeBeforeUpdate = flightRepository.findAll().size();
        flight.setId(count.incrementAndGet());

        // Create the Flight
        FlightDTO flightDTO = flightMapper.toDto(flight);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFlightMockMvc
            .perform(
                put(ENTITY_API_URL_ID, flightDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(flightDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Flight in the database
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFlight() throws Exception {
        int databaseSizeBeforeUpdate = flightRepository.findAll().size();
        flight.setId(count.incrementAndGet());

        // Create the Flight
        FlightDTO flightDTO = flightMapper.toDto(flight);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlightMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(flightDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Flight in the database
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFlight() throws Exception {
        int databaseSizeBeforeUpdate = flightRepository.findAll().size();
        flight.setId(count.incrementAndGet());

        // Create the Flight
        FlightDTO flightDTO = flightMapper.toDto(flight);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlightMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(flightDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Flight in the database
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFlightWithPatch() throws Exception {
        // Initialize the database
        flightRepository.saveAndFlush(flight);

        int databaseSizeBeforeUpdate = flightRepository.findAll().size();

        // Update the flight using partial update
        Flight partialUpdatedFlight = new Flight();
        partialUpdatedFlight.setId(flight.getId());

        partialUpdatedFlight.emptySeats(UPDATED_EMPTY_SEATS).price(UPDATED_PRICE);

        restFlightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFlight.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFlight))
            )
            .andExpect(status().isOk());

        // Validate the Flight in the database
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeUpdate);
        Flight testFlight = flightList.get(flightList.size() - 1);
        assertThat(testFlight.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFlight.getDeparture()).isEqualTo(DEFAULT_DEPARTURE);
        assertThat(testFlight.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testFlight.getEmptySeats()).isEqualTo(UPDATED_EMPTY_SEATS);
        assertThat(testFlight.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateFlightWithPatch() throws Exception {
        // Initialize the database
        flightRepository.saveAndFlush(flight);

        int databaseSizeBeforeUpdate = flightRepository.findAll().size();

        // Update the flight using partial update
        Flight partialUpdatedFlight = new Flight();
        partialUpdatedFlight.setId(flight.getId());

        partialUpdatedFlight
            .code(UPDATED_CODE)
            .departure(UPDATED_DEPARTURE)
            .duration(UPDATED_DURATION)
            .emptySeats(UPDATED_EMPTY_SEATS)
            .price(UPDATED_PRICE);

        restFlightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFlight.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFlight))
            )
            .andExpect(status().isOk());

        // Validate the Flight in the database
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeUpdate);
        Flight testFlight = flightList.get(flightList.size() - 1);
        assertThat(testFlight.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFlight.getDeparture()).isEqualTo(UPDATED_DEPARTURE);
        assertThat(testFlight.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testFlight.getEmptySeats()).isEqualTo(UPDATED_EMPTY_SEATS);
        assertThat(testFlight.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingFlight() throws Exception {
        int databaseSizeBeforeUpdate = flightRepository.findAll().size();
        flight.setId(count.incrementAndGet());

        // Create the Flight
        FlightDTO flightDTO = flightMapper.toDto(flight);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFlightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, flightDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(flightDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Flight in the database
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFlight() throws Exception {
        int databaseSizeBeforeUpdate = flightRepository.findAll().size();
        flight.setId(count.incrementAndGet());

        // Create the Flight
        FlightDTO flightDTO = flightMapper.toDto(flight);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlightMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(flightDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Flight in the database
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFlight() throws Exception {
        int databaseSizeBeforeUpdate = flightRepository.findAll().size();
        flight.setId(count.incrementAndGet());

        // Create the Flight
        FlightDTO flightDTO = flightMapper.toDto(flight);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFlightMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(flightDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Flight in the database
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFlight() throws Exception {
        // Initialize the database
        flightRepository.saveAndFlush(flight);

        int databaseSizeBeforeDelete = flightRepository.findAll().size();

        // Delete the flight
        restFlightMockMvc
            .perform(delete(ENTITY_API_URL_ID, flight.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
