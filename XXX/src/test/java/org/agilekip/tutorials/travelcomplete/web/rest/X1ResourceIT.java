package org.agilekip.tutorials.travelcomplete.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.agilekip.tutorials.travelcomplete.IntegrationTest;
import org.agilekip.tutorials.travelcomplete.domain.Car;
import org.agilekip.tutorials.travelcomplete.domain.X1;
import org.agilekip.tutorials.travelcomplete.repository.X1Repository;
import org.agilekip.tutorials.travelcomplete.service.X1Service;
import org.agilekip.tutorials.travelcomplete.service.dto.X1DTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.X1Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link X1Resource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class X1ResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/x-1-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private X1Repository x1Repository;

    @Mock
    private X1Repository x1RepositoryMock;

    @Autowired
    private X1Mapper x1Mapper;

    @Mock
    private X1Service x1ServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restX1MockMvc;

    private X1 x1;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static X1 createEntity(EntityManager em) {
        X1 x1 = new X1().name(DEFAULT_NAME);
        // Add required entity
        Car car;
        if (TestUtil.findAll(em, Car.class).isEmpty()) {
            car = CarResourceIT.createEntity(em);
            em.persist(car);
            em.flush();
        } else {
            car = TestUtil.findAll(em, Car.class).get(0);
        }
        x1.getCars().add(car);
        return x1;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static X1 createUpdatedEntity(EntityManager em) {
        X1 x1 = new X1().name(UPDATED_NAME);
        // Add required entity
        Car car;
        if (TestUtil.findAll(em, Car.class).isEmpty()) {
            car = CarResourceIT.createUpdatedEntity(em);
            em.persist(car);
            em.flush();
        } else {
            car = TestUtil.findAll(em, Car.class).get(0);
        }
        x1.getCars().add(car);
        return x1;
    }

    @BeforeEach
    public void initTest() {
        x1 = createEntity(em);
    }

    @Test
    @Transactional
    void createX1() throws Exception {
        int databaseSizeBeforeCreate = x1Repository.findAll().size();
        // Create the X1
        X1DTO x1DTO = x1Mapper.toDto(x1);
        restX1MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(x1DTO)))
            .andExpect(status().isCreated());

        // Validate the X1 in the database
        List<X1> x1List = x1Repository.findAll();
        assertThat(x1List).hasSize(databaseSizeBeforeCreate + 1);
        X1 testX1 = x1List.get(x1List.size() - 1);
        assertThat(testX1.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createX1WithExistingId() throws Exception {
        // Create the X1 with an existing ID
        x1.setId(1L);
        X1DTO x1DTO = x1Mapper.toDto(x1);

        int databaseSizeBeforeCreate = x1Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restX1MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(x1DTO)))
            .andExpect(status().isBadRequest());

        // Validate the X1 in the database
        List<X1> x1List = x1Repository.findAll();
        assertThat(x1List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = x1Repository.findAll().size();
        // set the field null
        x1.setName(null);

        // Create the X1, which fails.
        X1DTO x1DTO = x1Mapper.toDto(x1);

        restX1MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(x1DTO)))
            .andExpect(status().isBadRequest());

        List<X1> x1List = x1Repository.findAll();
        assertThat(x1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllX1s() throws Exception {
        // Initialize the database
        x1Repository.saveAndFlush(x1);

        // Get all the x1List
        restX1MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(x1.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllX1sWithEagerRelationshipsIsEnabled() throws Exception {
        when(x1ServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restX1MockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(x1ServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllX1sWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(x1ServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restX1MockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(x1ServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getX1() throws Exception {
        // Initialize the database
        x1Repository.saveAndFlush(x1);

        // Get the x1
        restX1MockMvc
            .perform(get(ENTITY_API_URL_ID, x1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(x1.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingX1() throws Exception {
        // Get the x1
        restX1MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewX1() throws Exception {
        // Initialize the database
        x1Repository.saveAndFlush(x1);

        int databaseSizeBeforeUpdate = x1Repository.findAll().size();

        // Update the x1
        X1 updatedX1 = x1Repository.findById(x1.getId()).get();
        // Disconnect from session so that the updates on updatedX1 are not directly saved in db
        em.detach(updatedX1);
        updatedX1.name(UPDATED_NAME);
        X1DTO x1DTO = x1Mapper.toDto(updatedX1);

        restX1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, x1DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(x1DTO))
            )
            .andExpect(status().isOk());

        // Validate the X1 in the database
        List<X1> x1List = x1Repository.findAll();
        assertThat(x1List).hasSize(databaseSizeBeforeUpdate);
        X1 testX1 = x1List.get(x1List.size() - 1);
        assertThat(testX1.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingX1() throws Exception {
        int databaseSizeBeforeUpdate = x1Repository.findAll().size();
        x1.setId(count.incrementAndGet());

        // Create the X1
        X1DTO x1DTO = x1Mapper.toDto(x1);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restX1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, x1DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(x1DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the X1 in the database
        List<X1> x1List = x1Repository.findAll();
        assertThat(x1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchX1() throws Exception {
        int databaseSizeBeforeUpdate = x1Repository.findAll().size();
        x1.setId(count.incrementAndGet());

        // Create the X1
        X1DTO x1DTO = x1Mapper.toDto(x1);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restX1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(x1DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the X1 in the database
        List<X1> x1List = x1Repository.findAll();
        assertThat(x1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamX1() throws Exception {
        int databaseSizeBeforeUpdate = x1Repository.findAll().size();
        x1.setId(count.incrementAndGet());

        // Create the X1
        X1DTO x1DTO = x1Mapper.toDto(x1);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restX1MockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(x1DTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the X1 in the database
        List<X1> x1List = x1Repository.findAll();
        assertThat(x1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateX1WithPatch() throws Exception {
        // Initialize the database
        x1Repository.saveAndFlush(x1);

        int databaseSizeBeforeUpdate = x1Repository.findAll().size();

        // Update the x1 using partial update
        X1 partialUpdatedX1 = new X1();
        partialUpdatedX1.setId(x1.getId());

        partialUpdatedX1.name(UPDATED_NAME);

        restX1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedX1.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedX1))
            )
            .andExpect(status().isOk());

        // Validate the X1 in the database
        List<X1> x1List = x1Repository.findAll();
        assertThat(x1List).hasSize(databaseSizeBeforeUpdate);
        X1 testX1 = x1List.get(x1List.size() - 1);
        assertThat(testX1.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateX1WithPatch() throws Exception {
        // Initialize the database
        x1Repository.saveAndFlush(x1);

        int databaseSizeBeforeUpdate = x1Repository.findAll().size();

        // Update the x1 using partial update
        X1 partialUpdatedX1 = new X1();
        partialUpdatedX1.setId(x1.getId());

        partialUpdatedX1.name(UPDATED_NAME);

        restX1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedX1.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedX1))
            )
            .andExpect(status().isOk());

        // Validate the X1 in the database
        List<X1> x1List = x1Repository.findAll();
        assertThat(x1List).hasSize(databaseSizeBeforeUpdate);
        X1 testX1 = x1List.get(x1List.size() - 1);
        assertThat(testX1.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingX1() throws Exception {
        int databaseSizeBeforeUpdate = x1Repository.findAll().size();
        x1.setId(count.incrementAndGet());

        // Create the X1
        X1DTO x1DTO = x1Mapper.toDto(x1);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restX1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, x1DTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(x1DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the X1 in the database
        List<X1> x1List = x1Repository.findAll();
        assertThat(x1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchX1() throws Exception {
        int databaseSizeBeforeUpdate = x1Repository.findAll().size();
        x1.setId(count.incrementAndGet());

        // Create the X1
        X1DTO x1DTO = x1Mapper.toDto(x1);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restX1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(x1DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the X1 in the database
        List<X1> x1List = x1Repository.findAll();
        assertThat(x1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamX1() throws Exception {
        int databaseSizeBeforeUpdate = x1Repository.findAll().size();
        x1.setId(count.incrementAndGet());

        // Create the X1
        X1DTO x1DTO = x1Mapper.toDto(x1);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restX1MockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(x1DTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the X1 in the database
        List<X1> x1List = x1Repository.findAll();
        assertThat(x1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteX1() throws Exception {
        // Initialize the database
        x1Repository.saveAndFlush(x1);

        int databaseSizeBeforeDelete = x1Repository.findAll().size();

        // Delete the x1
        restX1MockMvc.perform(delete(ENTITY_API_URL_ID, x1.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<X1> x1List = x1Repository.findAll();
        assertThat(x1List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
