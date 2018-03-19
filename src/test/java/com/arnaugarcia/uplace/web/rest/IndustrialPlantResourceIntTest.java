package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;

import com.arnaugarcia.uplace.domain.IndustrialPlant;
import com.arnaugarcia.uplace.repository.IndustrialPlantRepository;
import com.arnaugarcia.uplace.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.arnaugarcia.uplace.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;
/**
 * Test class for the IndustrialPlantResource REST controller.
 *
 * @see IndustrialPlantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UplaceApp.class)
public class IndustrialPlantResourceIntTest {

    private static final Integer DEFAULT_SOLAR_SURFACE = 1;
    private static final Integer UPDATED_SOLAR_SURFACE = 2;

    private static final Integer DEFAULT_NUMBER_ROOMS = 1;
    private static final Integer UPDATED_NUMBER_ROOMS = 2;

    private static final Integer DEFAULT_M_2_OFFICES = 1;
    private static final Integer UPDATED_M_2_OFFICES = 2;

    private static final Integer DEFAULT_M_2_STORAGE = 1;
    private static final Integer UPDATED_M_2_STORAGE = 2;

    private static final Integer DEFAULT_HEIGHT_FREE = 1;
    private static final Integer UPDATED_HEIGHT_FREE = 2;

    private static final Integer DEFAULT_NUMBER_DOCKS = 1;
    private static final Integer UPDATED_NUMBER_DOCKS = 2;

    private static final EnergyCertificate DEFAULT_ENERGY_CERTIFICATE = EnergyCertificate.A;
    private static final EnergyCertificate UPDATED_ENERGY_CERTIFICATE = EnergyCertificate.B;

    @Autowired
    private IndustrialPlantRepository industrialPlantRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIndustrialPlantMockMvc;

    private IndustrialPlant industrialPlant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IndustrialPlantResource industrialPlantResource = new IndustrialPlantResource(industrialPlantRepository);
        this.restIndustrialPlantMockMvc = MockMvcBuilders.standaloneSetup(industrialPlantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndustrialPlant createEntity(EntityManager em) {
        IndustrialPlant industrialPlant = new IndustrialPlant()
            .solarSurface(DEFAULT_SOLAR_SURFACE)
            .numberRooms(DEFAULT_NUMBER_ROOMS)
            .m2Offices(DEFAULT_M_2_OFFICES)
            .m2Storage(DEFAULT_M_2_STORAGE)
            .heightFree(DEFAULT_HEIGHT_FREE)
            .numberDocks(DEFAULT_NUMBER_DOCKS)
            .energyCertificate(DEFAULT_ENERGY_CERTIFICATE);
        return industrialPlant;
    }

    @Before
    public void initTest() {
        industrialPlant = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndustrialPlant() throws Exception {
        int databaseSizeBeforeCreate = industrialPlantRepository.findAll().size();

        // Create the IndustrialPlant
        restIndustrialPlantMockMvc.perform(post("/api/industrial-plants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industrialPlant)))
            .andExpect(status().isCreated());

        // Validate the IndustrialPlant in the database
        List<IndustrialPlant> industrialPlantList = industrialPlantRepository.findAll();
        assertThat(industrialPlantList).hasSize(databaseSizeBeforeCreate + 1);
        IndustrialPlant testIndustrialPlant = industrialPlantList.get(industrialPlantList.size() - 1);
        assertThat(testIndustrialPlant.getSolarSurface()).isEqualTo(DEFAULT_SOLAR_SURFACE);
        assertThat(testIndustrialPlant.getNumberRooms()).isEqualTo(DEFAULT_NUMBER_ROOMS);
        assertThat(testIndustrialPlant.getm2Offices()).isEqualTo(DEFAULT_M_2_OFFICES);
        assertThat(testIndustrialPlant.getm2Storage()).isEqualTo(DEFAULT_M_2_STORAGE);
        assertThat(testIndustrialPlant.getHeightFree()).isEqualTo(DEFAULT_HEIGHT_FREE);
        assertThat(testIndustrialPlant.getNumberDocks()).isEqualTo(DEFAULT_NUMBER_DOCKS);
        assertThat(testIndustrialPlant.getEnergyCertificate()).isEqualTo(DEFAULT_ENERGY_CERTIFICATE);
    }

    @Test
    @Transactional
    public void createIndustrialPlantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = industrialPlantRepository.findAll().size();

        // Create the IndustrialPlant with an existing ID
        industrialPlant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndustrialPlantMockMvc.perform(post("/api/industrial-plants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industrialPlant)))
            .andExpect(status().isBadRequest());

        // Validate the IndustrialPlant in the database
        List<IndustrialPlant> industrialPlantList = industrialPlantRepository.findAll();
        assertThat(industrialPlantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlants() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList
        restIndustrialPlantMockMvc.perform(get("/api/industrial-plants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industrialPlant.getId().intValue())))
            .andExpect(jsonPath("$.[*].solarSurface").value(hasItem(DEFAULT_SOLAR_SURFACE)))
            .andExpect(jsonPath("$.[*].numberRooms").value(hasItem(DEFAULT_NUMBER_ROOMS)))
            .andExpect(jsonPath("$.[*].m2Offices").value(hasItem(DEFAULT_M_2_OFFICES)))
            .andExpect(jsonPath("$.[*].m2Storage").value(hasItem(DEFAULT_M_2_STORAGE)))
            .andExpect(jsonPath("$.[*].heightFree").value(hasItem(DEFAULT_HEIGHT_FREE)))
            .andExpect(jsonPath("$.[*].numberDocks").value(hasItem(DEFAULT_NUMBER_DOCKS)))
            .andExpect(jsonPath("$.[*].energyCertificate").value(hasItem(DEFAULT_ENERGY_CERTIFICATE.toString())));
    }

    @Test
    @Transactional
    public void getIndustrialPlant() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get the industrialPlant
        restIndustrialPlantMockMvc.perform(get("/api/industrial-plants/{id}", industrialPlant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(industrialPlant.getId().intValue()))
            .andExpect(jsonPath("$.solarSurface").value(DEFAULT_SOLAR_SURFACE))
            .andExpect(jsonPath("$.numberRooms").value(DEFAULT_NUMBER_ROOMS))
            .andExpect(jsonPath("$.m2Offices").value(DEFAULT_M_2_OFFICES))
            .andExpect(jsonPath("$.m2Storage").value(DEFAULT_M_2_STORAGE))
            .andExpect(jsonPath("$.heightFree").value(DEFAULT_HEIGHT_FREE))
            .andExpect(jsonPath("$.numberDocks").value(DEFAULT_NUMBER_DOCKS))
            .andExpect(jsonPath("$.energyCertificate").value(DEFAULT_ENERGY_CERTIFICATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIndustrialPlant() throws Exception {
        // Get the industrialPlant
        restIndustrialPlantMockMvc.perform(get("/api/industrial-plants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndustrialPlant() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);
        int databaseSizeBeforeUpdate = industrialPlantRepository.findAll().size();

        // Update the industrialPlant
        IndustrialPlant updatedIndustrialPlant = industrialPlantRepository.findOne(industrialPlant.getId());
        // Disconnect from session so that the updates on updatedIndustrialPlant are not directly saved in db
        em.detach(updatedIndustrialPlant);
        updatedIndustrialPlant
            .solarSurface(UPDATED_SOLAR_SURFACE)
            .numberRooms(UPDATED_NUMBER_ROOMS)
            .m2Offices(UPDATED_M_2_OFFICES)
            .m2Storage(UPDATED_M_2_STORAGE)
            .heightFree(UPDATED_HEIGHT_FREE)
            .numberDocks(UPDATED_NUMBER_DOCKS)
            .energyCertificate(UPDATED_ENERGY_CERTIFICATE);

        restIndustrialPlantMockMvc.perform(put("/api/industrial-plants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndustrialPlant)))
            .andExpect(status().isOk());

        // Validate the IndustrialPlant in the database
        List<IndustrialPlant> industrialPlantList = industrialPlantRepository.findAll();
        assertThat(industrialPlantList).hasSize(databaseSizeBeforeUpdate);
        IndustrialPlant testIndustrialPlant = industrialPlantList.get(industrialPlantList.size() - 1);
        assertThat(testIndustrialPlant.getSolarSurface()).isEqualTo(UPDATED_SOLAR_SURFACE);
        assertThat(testIndustrialPlant.getNumberRooms()).isEqualTo(UPDATED_NUMBER_ROOMS);
        assertThat(testIndustrialPlant.getm2Offices()).isEqualTo(UPDATED_M_2_OFFICES);
        assertThat(testIndustrialPlant.getm2Storage()).isEqualTo(UPDATED_M_2_STORAGE);
        assertThat(testIndustrialPlant.getHeightFree()).isEqualTo(UPDATED_HEIGHT_FREE);
        assertThat(testIndustrialPlant.getNumberDocks()).isEqualTo(UPDATED_NUMBER_DOCKS);
        assertThat(testIndustrialPlant.getEnergyCertificate()).isEqualTo(UPDATED_ENERGY_CERTIFICATE);
    }

    @Test
    @Transactional
    public void updateNonExistingIndustrialPlant() throws Exception {
        int databaseSizeBeforeUpdate = industrialPlantRepository.findAll().size();

        // Create the IndustrialPlant

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIndustrialPlantMockMvc.perform(put("/api/industrial-plants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industrialPlant)))
            .andExpect(status().isCreated());

        // Validate the IndustrialPlant in the database
        List<IndustrialPlant> industrialPlantList = industrialPlantRepository.findAll();
        assertThat(industrialPlantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIndustrialPlant() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);
        int databaseSizeBeforeDelete = industrialPlantRepository.findAll().size();

        // Get the industrialPlant
        restIndustrialPlantMockMvc.perform(delete("/api/industrial-plants/{id}", industrialPlant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IndustrialPlant> industrialPlantList = industrialPlantRepository.findAll();
        assertThat(industrialPlantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndustrialPlant.class);
        IndustrialPlant industrialPlant1 = new IndustrialPlant();
        industrialPlant1.setId(1L);
        IndustrialPlant industrialPlant2 = new IndustrialPlant();
        industrialPlant2.setId(industrialPlant1.getId());
        assertThat(industrialPlant1).isEqualTo(industrialPlant2);
        industrialPlant2.setId(2L);
        assertThat(industrialPlant1).isNotEqualTo(industrialPlant2);
        industrialPlant1.setId(null);
        assertThat(industrialPlant1).isNotEqualTo(industrialPlant2);
    }
}
