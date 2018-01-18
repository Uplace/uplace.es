package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;

import com.arnaugarcia.uplace.domain.Bussiness;
import com.arnaugarcia.uplace.repository.BussinessRepository;
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

import com.arnaugarcia.uplace.domain.enumeration.BussinessType;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
/**
 * Test class for the BussinessResource REST controller.
 *
 * @see BussinessResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UplaceApp.class)
public class BussinessResourceIntTest {

    private static final BussinessType DEFAULT_BUSSINESS_TYPE = BussinessType.PUB;
    private static final BussinessType UPDATED_BUSSINESS_TYPE = BussinessType.HOTEL;

    private static final Integer DEFAULT_NUMBER_BATHROOMS = 1;
    private static final Integer UPDATED_NUMBER_BATHROOMS = 2;

    private static final Select DEFAULT_ELEVATOR = Select.YES;
    private static final Select UPDATED_ELEVATOR = Select.NO;

    private static final Select DEFAULT_AC = Select.YES;
    private static final Select UPDATED_AC = Select.NO;

    private static final Select DEFAULT_HEAT = Select.YES;
    private static final Select UPDATED_HEAT = Select.NO;

    private static final Integer DEFAULT_SURFACE_TERRACE = 1;
    private static final Integer UPDATED_SURFACE_TERRACE = 2;

    private static final Integer DEFAULT_SURFACE_GARDEN = 1;
    private static final Integer UPDATED_SURFACE_GARDEN = 2;

    private static final Integer DEFAULT_NUMBER_OFFICE = 1;
    private static final Integer UPDATED_NUMBER_OFFICE = 2;

    private static final Integer DEFAULT_SURFACE_SALOON = 1;
    private static final Integer UPDATED_SURFACE_SALOON = 2;

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final Select DEFAULT_POOL = Select.YES;
    private static final Select UPDATED_POOL = Select.NO;

    @Autowired
    private BussinessRepository bussinessRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBussinessMockMvc;

    private Bussiness bussiness;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BussinessResource bussinessResource = new BussinessResource(bussinessRepository);
        this.restBussinessMockMvc = MockMvcBuilders.standaloneSetup(bussinessResource)
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
    public static Bussiness createEntity(EntityManager em) {
        Bussiness bussiness = new Bussiness()
            .bussinessType(DEFAULT_BUSSINESS_TYPE)
            .numberBathrooms(DEFAULT_NUMBER_BATHROOMS)
            .elevator(DEFAULT_ELEVATOR)
            .ac(DEFAULT_AC)
            .heat(DEFAULT_HEAT)
            .surfaceTerrace(DEFAULT_SURFACE_TERRACE)
            .surfaceGarden(DEFAULT_SURFACE_GARDEN)
            .numberOffice(DEFAULT_NUMBER_OFFICE)
            .surfaceSaloon(DEFAULT_SURFACE_SALOON)
            .height(DEFAULT_HEIGHT)
            .pool(DEFAULT_POOL);
        return bussiness;
    }

    @Before
    public void initTest() {
        bussiness = createEntity(em);
    }

    @Test
    @Transactional
    public void createBussiness() throws Exception {
        int databaseSizeBeforeCreate = bussinessRepository.findAll().size();

        // Create the Bussiness
        restBussinessMockMvc.perform(post("/api/bussinesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bussiness)))
            .andExpect(status().isCreated());

        // Validate the Bussiness in the database
        List<Bussiness> bussinessList = bussinessRepository.findAll();
        assertThat(bussinessList).hasSize(databaseSizeBeforeCreate + 1);
        Bussiness testBussiness = bussinessList.get(bussinessList.size() - 1);
        assertThat(testBussiness.getBussinessType()).isEqualTo(DEFAULT_BUSSINESS_TYPE);
        assertThat(testBussiness.getNumberBathrooms()).isEqualTo(DEFAULT_NUMBER_BATHROOMS);
        assertThat(testBussiness.getElevator()).isEqualTo(DEFAULT_ELEVATOR);
        assertThat(testBussiness.getAc()).isEqualTo(DEFAULT_AC);
        assertThat(testBussiness.getHeat()).isEqualTo(DEFAULT_HEAT);
        assertThat(testBussiness.getSurfaceTerrace()).isEqualTo(DEFAULT_SURFACE_TERRACE);
        assertThat(testBussiness.getSurfaceGarden()).isEqualTo(DEFAULT_SURFACE_GARDEN);
        assertThat(testBussiness.getNumberOffice()).isEqualTo(DEFAULT_NUMBER_OFFICE);
        assertThat(testBussiness.getSurfaceSaloon()).isEqualTo(DEFAULT_SURFACE_SALOON);
        assertThat(testBussiness.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testBussiness.getPool()).isEqualTo(DEFAULT_POOL);
    }

    @Test
    @Transactional
    public void createBussinessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bussinessRepository.findAll().size();

        // Create the Bussiness with an existing ID
        bussiness.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBussinessMockMvc.perform(post("/api/bussinesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bussiness)))
            .andExpect(status().isBadRequest());

        // Validate the Bussiness in the database
        List<Bussiness> bussinessList = bussinessRepository.findAll();
        assertThat(bussinessList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBussinesses() throws Exception {
        // Initialize the database
        bussinessRepository.saveAndFlush(bussiness);

        // Get all the bussinessList
        restBussinessMockMvc.perform(get("/api/bussinesses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bussiness.getId().intValue())))
            .andExpect(jsonPath("$.[*].bussinessType").value(hasItem(DEFAULT_BUSSINESS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].numberBathrooms").value(hasItem(DEFAULT_NUMBER_BATHROOMS)))
            .andExpect(jsonPath("$.[*].elevator").value(hasItem(DEFAULT_ELEVATOR.toString())))
            .andExpect(jsonPath("$.[*].ac").value(hasItem(DEFAULT_AC.toString())))
            .andExpect(jsonPath("$.[*].heat").value(hasItem(DEFAULT_HEAT.toString())))
            .andExpect(jsonPath("$.[*].surfaceTerrace").value(hasItem(DEFAULT_SURFACE_TERRACE)))
            .andExpect(jsonPath("$.[*].surfaceGarden").value(hasItem(DEFAULT_SURFACE_GARDEN)))
            .andExpect(jsonPath("$.[*].numberOffice").value(hasItem(DEFAULT_NUMBER_OFFICE)))
            .andExpect(jsonPath("$.[*].surfaceSaloon").value(hasItem(DEFAULT_SURFACE_SALOON)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].pool").value(hasItem(DEFAULT_POOL.toString())));
    }

    @Test
    @Transactional
    public void getBussiness() throws Exception {
        // Initialize the database
        bussinessRepository.saveAndFlush(bussiness);

        // Get the bussiness
        restBussinessMockMvc.perform(get("/api/bussinesses/{id}", bussiness.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bussiness.getId().intValue()))
            .andExpect(jsonPath("$.bussinessType").value(DEFAULT_BUSSINESS_TYPE.toString()))
            .andExpect(jsonPath("$.numberBathrooms").value(DEFAULT_NUMBER_BATHROOMS))
            .andExpect(jsonPath("$.elevator").value(DEFAULT_ELEVATOR.toString()))
            .andExpect(jsonPath("$.ac").value(DEFAULT_AC.toString()))
            .andExpect(jsonPath("$.heat").value(DEFAULT_HEAT.toString()))
            .andExpect(jsonPath("$.surfaceTerrace").value(DEFAULT_SURFACE_TERRACE))
            .andExpect(jsonPath("$.surfaceGarden").value(DEFAULT_SURFACE_GARDEN))
            .andExpect(jsonPath("$.numberOffice").value(DEFAULT_NUMBER_OFFICE))
            .andExpect(jsonPath("$.surfaceSaloon").value(DEFAULT_SURFACE_SALOON))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.pool").value(DEFAULT_POOL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBussiness() throws Exception {
        // Get the bussiness
        restBussinessMockMvc.perform(get("/api/bussinesses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBussiness() throws Exception {
        // Initialize the database
        bussinessRepository.saveAndFlush(bussiness);
        int databaseSizeBeforeUpdate = bussinessRepository.findAll().size();

        // Update the bussiness
        Bussiness updatedBussiness = bussinessRepository.findOne(bussiness.getId());
        // Disconnect from session so that the updates on updatedBussiness are not directly saved in db
        em.detach(updatedBussiness);
        updatedBussiness
            .bussinessType(UPDATED_BUSSINESS_TYPE)
            .numberBathrooms(UPDATED_NUMBER_BATHROOMS)
            .elevator(UPDATED_ELEVATOR)
            .ac(UPDATED_AC)
            .heat(UPDATED_HEAT)
            .surfaceTerrace(UPDATED_SURFACE_TERRACE)
            .surfaceGarden(UPDATED_SURFACE_GARDEN)
            .numberOffice(UPDATED_NUMBER_OFFICE)
            .surfaceSaloon(UPDATED_SURFACE_SALOON)
            .height(UPDATED_HEIGHT)
            .pool(UPDATED_POOL);

        restBussinessMockMvc.perform(put("/api/bussinesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBussiness)))
            .andExpect(status().isOk());

        // Validate the Bussiness in the database
        List<Bussiness> bussinessList = bussinessRepository.findAll();
        assertThat(bussinessList).hasSize(databaseSizeBeforeUpdate);
        Bussiness testBussiness = bussinessList.get(bussinessList.size() - 1);
        assertThat(testBussiness.getBussinessType()).isEqualTo(UPDATED_BUSSINESS_TYPE);
        assertThat(testBussiness.getNumberBathrooms()).isEqualTo(UPDATED_NUMBER_BATHROOMS);
        assertThat(testBussiness.getElevator()).isEqualTo(UPDATED_ELEVATOR);
        assertThat(testBussiness.getAc()).isEqualTo(UPDATED_AC);
        assertThat(testBussiness.getHeat()).isEqualTo(UPDATED_HEAT);
        assertThat(testBussiness.getSurfaceTerrace()).isEqualTo(UPDATED_SURFACE_TERRACE);
        assertThat(testBussiness.getSurfaceGarden()).isEqualTo(UPDATED_SURFACE_GARDEN);
        assertThat(testBussiness.getNumberOffice()).isEqualTo(UPDATED_NUMBER_OFFICE);
        assertThat(testBussiness.getSurfaceSaloon()).isEqualTo(UPDATED_SURFACE_SALOON);
        assertThat(testBussiness.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testBussiness.getPool()).isEqualTo(UPDATED_POOL);
    }

    @Test
    @Transactional
    public void updateNonExistingBussiness() throws Exception {
        int databaseSizeBeforeUpdate = bussinessRepository.findAll().size();

        // Create the Bussiness

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBussinessMockMvc.perform(put("/api/bussinesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bussiness)))
            .andExpect(status().isCreated());

        // Validate the Bussiness in the database
        List<Bussiness> bussinessList = bussinessRepository.findAll();
        assertThat(bussinessList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBussiness() throws Exception {
        // Initialize the database
        bussinessRepository.saveAndFlush(bussiness);
        int databaseSizeBeforeDelete = bussinessRepository.findAll().size();

        // Get the bussiness
        restBussinessMockMvc.perform(delete("/api/bussinesses/{id}", bussiness.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bussiness> bussinessList = bussinessRepository.findAll();
        assertThat(bussinessList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bussiness.class);
        Bussiness bussiness1 = new Bussiness();
        bussiness1.setId(1L);
        Bussiness bussiness2 = new Bussiness();
        bussiness2.setId(bussiness1.getId());
        assertThat(bussiness1).isEqualTo(bussiness2);
        bussiness2.setId(2L);
        assertThat(bussiness1).isNotEqualTo(bussiness2);
        bussiness1.setId(null);
        assertThat(bussiness1).isNotEqualTo(bussiness2);
    }
}
