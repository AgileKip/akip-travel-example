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
import org.agilekip.tutorials.travelcomplete.domain.HotelRoom;
import org.agilekip.tutorials.travelcomplete.repository.HotelRoomRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.HotelRoomDTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.HotelRoomMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HotelRoomResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HotelRoomResourceIT {

    private static final String DEFAULT_ROOM_ID = "AAAAAAAAAA";
    private static final String UPDATED_ROOM_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_SLEEPS = 1;
    private static final Integer UPDATED_SLEEPS = 2;

    private static final LocalDate DEFAULT_BOODKED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BOODKED = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final String ENTITY_API_URL = "/api/hotel-rooms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    @Autowired
    private HotelRoomMapper hotelRoomMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHotelRoomMockMvc;

    private HotelRoom hotelRoom;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HotelRoom createEntity(EntityManager em) {
        HotelRoom hotelRoom = new HotelRoom()
            .roomID(DEFAULT_ROOM_ID)
            .sleeps(DEFAULT_SLEEPS)
            .boodked(DEFAULT_BOODKED)
            .duration(DEFAULT_DURATION)
            .price(DEFAULT_PRICE);
        return hotelRoom;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HotelRoom createUpdatedEntity(EntityManager em) {
        HotelRoom hotelRoom = new HotelRoom()
            .roomID(UPDATED_ROOM_ID)
            .sleeps(UPDATED_SLEEPS)
            .boodked(UPDATED_BOODKED)
            .duration(UPDATED_DURATION)
            .price(UPDATED_PRICE);
        return hotelRoom;
    }

    @BeforeEach
    public void initTest() {
        hotelRoom = createEntity(em);
    }

    @Test
    @Transactional
    void createHotelRoom() throws Exception {
        int databaseSizeBeforeCreate = hotelRoomRepository.findAll().size();
        // Create the HotelRoom
        HotelRoomDTO hotelRoomDTO = hotelRoomMapper.toDto(hotelRoom);
        restHotelRoomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hotelRoomDTO)))
            .andExpect(status().isCreated());

        // Validate the HotelRoom in the database
        List<HotelRoom> hotelRoomList = hotelRoomRepository.findAll();
        assertThat(hotelRoomList).hasSize(databaseSizeBeforeCreate + 1);
        HotelRoom testHotelRoom = hotelRoomList.get(hotelRoomList.size() - 1);
        assertThat(testHotelRoom.getRoomID()).isEqualTo(DEFAULT_ROOM_ID);
        assertThat(testHotelRoom.getSleeps()).isEqualTo(DEFAULT_SLEEPS);
        assertThat(testHotelRoom.getBoodked()).isEqualTo(DEFAULT_BOODKED);
        assertThat(testHotelRoom.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testHotelRoom.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void createHotelRoomWithExistingId() throws Exception {
        // Create the HotelRoom with an existing ID
        hotelRoom.setId(1L);
        HotelRoomDTO hotelRoomDTO = hotelRoomMapper.toDto(hotelRoom);

        int databaseSizeBeforeCreate = hotelRoomRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHotelRoomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hotelRoomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HotelRoom in the database
        List<HotelRoom> hotelRoomList = hotelRoomRepository.findAll();
        assertThat(hotelRoomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHotelRooms() throws Exception {
        // Initialize the database
        hotelRoomRepository.saveAndFlush(hotelRoom);

        // Get all the hotelRoomList
        restHotelRoomMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotelRoom.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomID").value(hasItem(DEFAULT_ROOM_ID)))
            .andExpect(jsonPath("$.[*].sleeps").value(hasItem(DEFAULT_SLEEPS)))
            .andExpect(jsonPath("$.[*].boodked").value(hasItem(DEFAULT_BOODKED.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)));
    }

    @Test
    @Transactional
    void getHotelRoom() throws Exception {
        // Initialize the database
        hotelRoomRepository.saveAndFlush(hotelRoom);

        // Get the hotelRoom
        restHotelRoomMockMvc
            .perform(get(ENTITY_API_URL_ID, hotelRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hotelRoom.getId().intValue()))
            .andExpect(jsonPath("$.roomID").value(DEFAULT_ROOM_ID))
            .andExpect(jsonPath("$.sleeps").value(DEFAULT_SLEEPS))
            .andExpect(jsonPath("$.boodked").value(DEFAULT_BOODKED.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE));
    }

    @Test
    @Transactional
    void getNonExistingHotelRoom() throws Exception {
        // Get the hotelRoom
        restHotelRoomMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHotelRoom() throws Exception {
        // Initialize the database
        hotelRoomRepository.saveAndFlush(hotelRoom);

        int databaseSizeBeforeUpdate = hotelRoomRepository.findAll().size();

        // Update the hotelRoom
        HotelRoom updatedHotelRoom = hotelRoomRepository.findById(hotelRoom.getId()).get();
        // Disconnect from session so that the updates on updatedHotelRoom are not directly saved in db
        em.detach(updatedHotelRoom);
        updatedHotelRoom
            .roomID(UPDATED_ROOM_ID)
            .sleeps(UPDATED_SLEEPS)
            .boodked(UPDATED_BOODKED)
            .duration(UPDATED_DURATION)
            .price(UPDATED_PRICE);
        HotelRoomDTO hotelRoomDTO = hotelRoomMapper.toDto(updatedHotelRoom);

        restHotelRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hotelRoomDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelRoomDTO))
            )
            .andExpect(status().isOk());

        // Validate the HotelRoom in the database
        List<HotelRoom> hotelRoomList = hotelRoomRepository.findAll();
        assertThat(hotelRoomList).hasSize(databaseSizeBeforeUpdate);
        HotelRoom testHotelRoom = hotelRoomList.get(hotelRoomList.size() - 1);
        assertThat(testHotelRoom.getRoomID()).isEqualTo(UPDATED_ROOM_ID);
        assertThat(testHotelRoom.getSleeps()).isEqualTo(UPDATED_SLEEPS);
        assertThat(testHotelRoom.getBoodked()).isEqualTo(UPDATED_BOODKED);
        assertThat(testHotelRoom.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testHotelRoom.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingHotelRoom() throws Exception {
        int databaseSizeBeforeUpdate = hotelRoomRepository.findAll().size();
        hotelRoom.setId(count.incrementAndGet());

        // Create the HotelRoom
        HotelRoomDTO hotelRoomDTO = hotelRoomMapper.toDto(hotelRoom);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hotelRoomDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelRoomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelRoom in the database
        List<HotelRoom> hotelRoomList = hotelRoomRepository.findAll();
        assertThat(hotelRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHotelRoom() throws Exception {
        int databaseSizeBeforeUpdate = hotelRoomRepository.findAll().size();
        hotelRoom.setId(count.incrementAndGet());

        // Create the HotelRoom
        HotelRoomDTO hotelRoomDTO = hotelRoomMapper.toDto(hotelRoom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hotelRoomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelRoom in the database
        List<HotelRoom> hotelRoomList = hotelRoomRepository.findAll();
        assertThat(hotelRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHotelRoom() throws Exception {
        int databaseSizeBeforeUpdate = hotelRoomRepository.findAll().size();
        hotelRoom.setId(count.incrementAndGet());

        // Create the HotelRoom
        HotelRoomDTO hotelRoomDTO = hotelRoomMapper.toDto(hotelRoom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelRoomMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hotelRoomDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HotelRoom in the database
        List<HotelRoom> hotelRoomList = hotelRoomRepository.findAll();
        assertThat(hotelRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHotelRoomWithPatch() throws Exception {
        // Initialize the database
        hotelRoomRepository.saveAndFlush(hotelRoom);

        int databaseSizeBeforeUpdate = hotelRoomRepository.findAll().size();

        // Update the hotelRoom using partial update
        HotelRoom partialUpdatedHotelRoom = new HotelRoom();
        partialUpdatedHotelRoom.setId(hotelRoom.getId());

        partialUpdatedHotelRoom.sleeps(UPDATED_SLEEPS).boodked(UPDATED_BOODKED).duration(UPDATED_DURATION);

        restHotelRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotelRoom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHotelRoom))
            )
            .andExpect(status().isOk());

        // Validate the HotelRoom in the database
        List<HotelRoom> hotelRoomList = hotelRoomRepository.findAll();
        assertThat(hotelRoomList).hasSize(databaseSizeBeforeUpdate);
        HotelRoom testHotelRoom = hotelRoomList.get(hotelRoomList.size() - 1);
        assertThat(testHotelRoom.getRoomID()).isEqualTo(DEFAULT_ROOM_ID);
        assertThat(testHotelRoom.getSleeps()).isEqualTo(UPDATED_SLEEPS);
        assertThat(testHotelRoom.getBoodked()).isEqualTo(UPDATED_BOODKED);
        assertThat(testHotelRoom.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testHotelRoom.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateHotelRoomWithPatch() throws Exception {
        // Initialize the database
        hotelRoomRepository.saveAndFlush(hotelRoom);

        int databaseSizeBeforeUpdate = hotelRoomRepository.findAll().size();

        // Update the hotelRoom using partial update
        HotelRoom partialUpdatedHotelRoom = new HotelRoom();
        partialUpdatedHotelRoom.setId(hotelRoom.getId());

        partialUpdatedHotelRoom
            .roomID(UPDATED_ROOM_ID)
            .sleeps(UPDATED_SLEEPS)
            .boodked(UPDATED_BOODKED)
            .duration(UPDATED_DURATION)
            .price(UPDATED_PRICE);

        restHotelRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotelRoom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHotelRoom))
            )
            .andExpect(status().isOk());

        // Validate the HotelRoom in the database
        List<HotelRoom> hotelRoomList = hotelRoomRepository.findAll();
        assertThat(hotelRoomList).hasSize(databaseSizeBeforeUpdate);
        HotelRoom testHotelRoom = hotelRoomList.get(hotelRoomList.size() - 1);
        assertThat(testHotelRoom.getRoomID()).isEqualTo(UPDATED_ROOM_ID);
        assertThat(testHotelRoom.getSleeps()).isEqualTo(UPDATED_SLEEPS);
        assertThat(testHotelRoom.getBoodked()).isEqualTo(UPDATED_BOODKED);
        assertThat(testHotelRoom.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testHotelRoom.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingHotelRoom() throws Exception {
        int databaseSizeBeforeUpdate = hotelRoomRepository.findAll().size();
        hotelRoom.setId(count.incrementAndGet());

        // Create the HotelRoom
        HotelRoomDTO hotelRoomDTO = hotelRoomMapper.toDto(hotelRoom);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hotelRoomDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hotelRoomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelRoom in the database
        List<HotelRoom> hotelRoomList = hotelRoomRepository.findAll();
        assertThat(hotelRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHotelRoom() throws Exception {
        int databaseSizeBeforeUpdate = hotelRoomRepository.findAll().size();
        hotelRoom.setId(count.incrementAndGet());

        // Create the HotelRoom
        HotelRoomDTO hotelRoomDTO = hotelRoomMapper.toDto(hotelRoom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hotelRoomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelRoom in the database
        List<HotelRoom> hotelRoomList = hotelRoomRepository.findAll();
        assertThat(hotelRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHotelRoom() throws Exception {
        int databaseSizeBeforeUpdate = hotelRoomRepository.findAll().size();
        hotelRoom.setId(count.incrementAndGet());

        // Create the HotelRoom
        HotelRoomDTO hotelRoomDTO = hotelRoomMapper.toDto(hotelRoom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelRoomMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hotelRoomDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HotelRoom in the database
        List<HotelRoom> hotelRoomList = hotelRoomRepository.findAll();
        assertThat(hotelRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHotelRoom() throws Exception {
        // Initialize the database
        hotelRoomRepository.saveAndFlush(hotelRoom);

        int databaseSizeBeforeDelete = hotelRoomRepository.findAll().size();

        // Delete the hotelRoom
        restHotelRoomMockMvc
            .perform(delete(ENTITY_API_URL_ID, hotelRoom.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HotelRoom> hotelRoomList = hotelRoomRepository.findAll();
        assertThat(hotelRoomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
