package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;

import com.arnaugarcia.uplace.domain.IndustrialPlant;
import com.arnaugarcia.uplace.repository.IndustrialPlantRepository;
import com.arnaugarcia.uplace.service.IndustrialPlantService;
import com.arnaugarcia.uplace.service.PropertyService;
import com.arnaugarcia.uplace.web.rest.errors.ExceptionTranslator;
import com.arnaugarcia.uplace.service.IndustrialPlantQueryService;

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
import java.time.ZonedDateTime;
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

    private static final String DEFAULT_TITLE = "TEST Apartment";

    private static final Double DEFAULT_PRICE = 0.0;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.now();

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
    private IndustrialPlantService industrialPlantService;

    @Autowired
    private IndustrialPlantQueryService industrialPlantQueryService;

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

    private PropertyService propertyService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IndustrialPlantResource industrialPlantResource = new IndustrialPlantResource(industrialPlantService, industrialPlantQueryService, propertyService);
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
        IndustrialPlant industrialPlant = (IndustrialPlant) new IndustrialPlant()
            .solarSurface(DEFAULT_SOLAR_SURFACE)
            .numberRooms(DEFAULT_NUMBER_ROOMS)
            .m2Offices(DEFAULT_M_2_OFFICES)
            .m2Storage(DEFAULT_M_2_STORAGE)
            .heightFree(DEFAULT_HEIGHT_FREE)
            .numberDocks(DEFAULT_NUMBER_DOCKS)
            .energyCertificate(DEFAULT_ENERGY_CERTIFICATE)
            .created(DEFAULT_CREATED)
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE);
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
    public void getAllIndustrialPlantsBySolarSurfaceIsEqualToSomething() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where solarSurface equals to DEFAULT_SOLAR_SURFACE
        defaultIndustrialPlantShouldBeFound("solarSurface.equals=" + DEFAULT_SOLAR_SURFACE);

        // Get all the industrialPlantList where solarSurface equals to UPDATED_SOLAR_SURFACE
        defaultIndustrialPlantShouldNotBeFound("solarSurface.equals=" + UPDATED_SOLAR_SURFACE);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsBySolarSurfaceIsInShouldWork() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where solarSurface in DEFAULT_SOLAR_SURFACE or UPDATED_SOLAR_SURFACE
        defaultIndustrialPlantShouldBeFound("solarSurface.in=" + DEFAULT_SOLAR_SURFACE + "," + UPDATED_SOLAR_SURFACE);

        // Get all the industrialPlantList where solarSurface equals to UPDATED_SOLAR_SURFACE
        defaultIndustrialPlantShouldNotBeFound("solarSurface.in=" + UPDATED_SOLAR_SURFACE);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsBySolarSurfaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where solarSurface is not null
        defaultIndustrialPlantShouldBeFound("solarSurface.specified=true");

        // Get all the industrialPlantList where solarSurface is null
        defaultIndustrialPlantShouldNotBeFound("solarSurface.specified=false");
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsBySolarSurfaceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where solarSurface greater than or equals to DEFAULT_SOLAR_SURFACE
        defaultIndustrialPlantShouldBeFound("solarSurface.greaterOrEqualThan=" + DEFAULT_SOLAR_SURFACE);

        // Get all the industrialPlantList where solarSurface greater than or equals to UPDATED_SOLAR_SURFACE
        defaultIndustrialPlantShouldNotBeFound("solarSurface.greaterOrEqualThan=" + UPDATED_SOLAR_SURFACE);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsBySolarSurfaceIsLessThanSomething() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where solarSurface less than or equals to DEFAULT_SOLAR_SURFACE
        defaultIndustrialPlantShouldNotBeFound("solarSurface.lessThan=" + DEFAULT_SOLAR_SURFACE);

        // Get all the industrialPlantList where solarSurface less than or equals to UPDATED_SOLAR_SURFACE
        defaultIndustrialPlantShouldBeFound("solarSurface.lessThan=" + UPDATED_SOLAR_SURFACE);
    }


    @Test
    @Transactional
    public void getAllIndustrialPlantsByNumberRoomsIsEqualToSomething() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where numberRooms equals to DEFAULT_NUMBER_ROOMS
        defaultIndustrialPlantShouldBeFound("numberRooms.equals=" + DEFAULT_NUMBER_ROOMS);

        // Get all the industrialPlantList where numberRooms equals to UPDATED_NUMBER_ROOMS
        defaultIndustrialPlantShouldNotBeFound("numberRooms.equals=" + UPDATED_NUMBER_ROOMS);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsByNumberRoomsIsInShouldWork() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where numberRooms in DEFAULT_NUMBER_ROOMS or UPDATED_NUMBER_ROOMS
        defaultIndustrialPlantShouldBeFound("numberRooms.in=" + DEFAULT_NUMBER_ROOMS + "," + UPDATED_NUMBER_ROOMS);

        // Get all the industrialPlantList where numberRooms equals to UPDATED_NUMBER_ROOMS
        defaultIndustrialPlantShouldNotBeFound("numberRooms.in=" + UPDATED_NUMBER_ROOMS);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsByNumberRoomsIsNullOrNotNull() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where numberRooms is not null
        defaultIndustrialPlantShouldBeFound("numberRooms.specified=true");

        // Get all the industrialPlantList where numberRooms is null
        defaultIndustrialPlantShouldNotBeFound("numberRooms.specified=false");
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsByNumberRoomsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where numberRooms greater than or equals to DEFAULT_NUMBER_ROOMS
        defaultIndustrialPlantShouldBeFound("numberRooms.greaterOrEqualThan=" + DEFAULT_NUMBER_ROOMS);

        // Get all the industrialPlantList where numberRooms greater than or equals to UPDATED_NUMBER_ROOMS
        defaultIndustrialPlantShouldNotBeFound("numberRooms.greaterOrEqualThan=" + UPDATED_NUMBER_ROOMS);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsByNumberRoomsIsLessThanSomething() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where numberRooms less than or equals to DEFAULT_NUMBER_ROOMS
        defaultIndustrialPlantShouldNotBeFound("numberRooms.lessThan=" + DEFAULT_NUMBER_ROOMS);

        // Get all the industrialPlantList where numberRooms less than or equals to UPDATED_NUMBER_ROOMS
        defaultIndustrialPlantShouldBeFound("numberRooms.lessThan=" + UPDATED_NUMBER_ROOMS);
    }


    @Test
    @Transactional
    public void getAllIndustrialPlantsBym2OfficesIsEqualToSomething() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where m2Offices equals to DEFAULT_M_2_OFFICES
        defaultIndustrialPlantShouldBeFound("m2Offices.equals=" + DEFAULT_M_2_OFFICES);

        // Get all the industrialPlantList where m2Offices equals to UPDATED_M_2_OFFICES
        defaultIndustrialPlantShouldNotBeFound("m2Offices.equals=" + UPDATED_M_2_OFFICES);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsBym2OfficesIsInShouldWork() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where m2Offices in DEFAULT_M_2_OFFICES or UPDATED_M_2_OFFICES
        defaultIndustrialPlantShouldBeFound("m2Offices.in=" + DEFAULT_M_2_OFFICES + "," + UPDATED_M_2_OFFICES);

        // Get all the industrialPlantList where m2Offices equals to UPDATED_M_2_OFFICES
        defaultIndustrialPlantShouldNotBeFound("m2Offices.in=" + UPDATED_M_2_OFFICES);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsBym2OfficesIsNullOrNotNull() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where m2Offices is not null
        defaultIndustrialPlantShouldBeFound("m2Offices.specified=true");

        // Get all the industrialPlantList where m2Offices is null
        defaultIndustrialPlantShouldNotBeFound("m2Offices.specified=false");
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsBym2OfficesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where m2Offices greater than or equals to DEFAULT_M_2_OFFICES
        defaultIndustrialPlantShouldBeFound("m2Offices.greaterOrEqualThan=" + DEFAULT_M_2_OFFICES);

        // Get all the industrialPlantList where m2Offices greater than or equals to UPDATED_M_2_OFFICES
        defaultIndustrialPlantShouldNotBeFound("m2Offices.greaterOrEqualThan=" + UPDATED_M_2_OFFICES);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsBym2OfficesIsLessThanSomething() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where m2Offices less than or equals to DEFAULT_M_2_OFFICES
        defaultIndustrialPlantShouldNotBeFound("m2Offices.lessThan=" + DEFAULT_M_2_OFFICES);

        // Get all the industrialPlantList where m2Offices less than or equals to UPDATED_M_2_OFFICES
        defaultIndustrialPlantShouldBeFound("m2Offices.lessThan=" + UPDATED_M_2_OFFICES);
    }


    @Test
    @Transactional
    public void getAllIndustrialPlantsBym2StorageIsEqualToSomething() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where m2Storage equals to DEFAULT_M_2_STORAGE
        defaultIndustrialPlantShouldBeFound("m2Storage.equals=" + DEFAULT_M_2_STORAGE);

        // Get all the industrialPlantList where m2Storage equals to UPDATED_M_2_STORAGE
        defaultIndustrialPlantShouldNotBeFound("m2Storage.equals=" + UPDATED_M_2_STORAGE);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsBym2StorageIsInShouldWork() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where m2Storage in DEFAULT_M_2_STORAGE or UPDATED_M_2_STORAGE
        defaultIndustrialPlantShouldBeFound("m2Storage.in=" + DEFAULT_M_2_STORAGE + "," + UPDATED_M_2_STORAGE);

        // Get all the industrialPlantList where m2Storage equals to UPDATED_M_2_STORAGE
        defaultIndustrialPlantShouldNotBeFound("m2Storage.in=" + UPDATED_M_2_STORAGE);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsBym2StorageIsNullOrNotNull() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where m2Storage is not null
        defaultIndustrialPlantShouldBeFound("m2Storage.specified=true");

        // Get all the industrialPlantList where m2Storage is null
        defaultIndustrialPlantShouldNotBeFound("m2Storage.specified=false");
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsBym2StorageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where m2Storage greater than or equals to DEFAULT_M_2_STORAGE
        defaultIndustrialPlantShouldBeFound("m2Storage.greaterOrEqualThan=" + DEFAULT_M_2_STORAGE);

        // Get all the industrialPlantList where m2Storage greater than or equals to UPDATED_M_2_STORAGE
        defaultIndustrialPlantShouldNotBeFound("m2Storage.greaterOrEqualThan=" + UPDATED_M_2_STORAGE);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsBym2StorageIsLessThanSomething() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where m2Storage less than or equals to DEFAULT_M_2_STORAGE
        defaultIndustrialPlantShouldNotBeFound("m2Storage.lessThan=" + DEFAULT_M_2_STORAGE);

        // Get all the industrialPlantList where m2Storage less than or equals to UPDATED_M_2_STORAGE
        defaultIndustrialPlantShouldBeFound("m2Storage.lessThan=" + UPDATED_M_2_STORAGE);
    }


    @Test
    @Transactional
    public void getAllIndustrialPlantsByHeightFreeIsEqualToSomething() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where heightFree equals to DEFAULT_HEIGHT_FREE
        defaultIndustrialPlantShouldBeFound("heightFree.equals=" + DEFAULT_HEIGHT_FREE);

        // Get all the industrialPlantList where heightFree equals to UPDATED_HEIGHT_FREE
        defaultIndustrialPlantShouldNotBeFound("heightFree.equals=" + UPDATED_HEIGHT_FREE);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsByHeightFreeIsInShouldWork() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where heightFree in DEFAULT_HEIGHT_FREE or UPDATED_HEIGHT_FREE
        defaultIndustrialPlantShouldBeFound("heightFree.in=" + DEFAULT_HEIGHT_FREE + "," + UPDATED_HEIGHT_FREE);

        // Get all the industrialPlantList where heightFree equals to UPDATED_HEIGHT_FREE
        defaultIndustrialPlantShouldNotBeFound("heightFree.in=" + UPDATED_HEIGHT_FREE);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsByHeightFreeIsNullOrNotNull() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where heightFree is not null
        defaultIndustrialPlantShouldBeFound("heightFree.specified=true");

        // Get all the industrialPlantList where heightFree is null
        defaultIndustrialPlantShouldNotBeFound("heightFree.specified=false");
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsByHeightFreeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where heightFree greater than or equals to DEFAULT_HEIGHT_FREE
        defaultIndustrialPlantShouldBeFound("heightFree.greaterOrEqualThan=" + DEFAULT_HEIGHT_FREE);

        // Get all the industrialPlantList where heightFree greater than or equals to UPDATED_HEIGHT_FREE
        defaultIndustrialPlantShouldNotBeFound("heightFree.greaterOrEqualThan=" + UPDATED_HEIGHT_FREE);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsByHeightFreeIsLessThanSomething() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where heightFree less than or equals to DEFAULT_HEIGHT_FREE
        defaultIndustrialPlantShouldNotBeFound("heightFree.lessThan=" + DEFAULT_HEIGHT_FREE);

        // Get all the industrialPlantList where heightFree less than or equals to UPDATED_HEIGHT_FREE
        defaultIndustrialPlantShouldBeFound("heightFree.lessThan=" + UPDATED_HEIGHT_FREE);
    }


    @Test
    @Transactional
    public void getAllIndustrialPlantsByNumberDocksIsEqualToSomething() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where numberDocks equals to DEFAULT_NUMBER_DOCKS
        defaultIndustrialPlantShouldBeFound("numberDocks.equals=" + DEFAULT_NUMBER_DOCKS);

        // Get all the industrialPlantList where numberDocks equals to UPDATED_NUMBER_DOCKS
        defaultIndustrialPlantShouldNotBeFound("numberDocks.equals=" + UPDATED_NUMBER_DOCKS);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsByNumberDocksIsInShouldWork() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where numberDocks in DEFAULT_NUMBER_DOCKS or UPDATED_NUMBER_DOCKS
        defaultIndustrialPlantShouldBeFound("numberDocks.in=" + DEFAULT_NUMBER_DOCKS + "," + UPDATED_NUMBER_DOCKS);

        // Get all the industrialPlantList where numberDocks equals to UPDATED_NUMBER_DOCKS
        defaultIndustrialPlantShouldNotBeFound("numberDocks.in=" + UPDATED_NUMBER_DOCKS);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsByNumberDocksIsNullOrNotNull() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where numberDocks is not null
        defaultIndustrialPlantShouldBeFound("numberDocks.specified=true");

        // Get all the industrialPlantList where numberDocks is null
        defaultIndustrialPlantShouldNotBeFound("numberDocks.specified=false");
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsByNumberDocksIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where numberDocks greater than or equals to DEFAULT_NUMBER_DOCKS
        defaultIndustrialPlantShouldBeFound("numberDocks.greaterOrEqualThan=" + DEFAULT_NUMBER_DOCKS);

        // Get all the industrialPlantList where numberDocks greater than or equals to UPDATED_NUMBER_DOCKS
        defaultIndustrialPlantShouldNotBeFound("numberDocks.greaterOrEqualThan=" + UPDATED_NUMBER_DOCKS);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsByNumberDocksIsLessThanSomething() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where numberDocks less than or equals to DEFAULT_NUMBER_DOCKS
        defaultIndustrialPlantShouldNotBeFound("numberDocks.lessThan=" + DEFAULT_NUMBER_DOCKS);

        // Get all the industrialPlantList where numberDocks less than or equals to UPDATED_NUMBER_DOCKS
        defaultIndustrialPlantShouldBeFound("numberDocks.lessThan=" + UPDATED_NUMBER_DOCKS);
    }


    @Test
    @Transactional
    public void getAllIndustrialPlantsByEnergyCertificateIsEqualToSomething() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where energyCertificate equals to DEFAULT_ENERGY_CERTIFICATE
        defaultIndustrialPlantShouldBeFound("energyCertificate.equals=" + DEFAULT_ENERGY_CERTIFICATE);

        // Get all the industrialPlantList where energyCertificate equals to UPDATED_ENERGY_CERTIFICATE
        defaultIndustrialPlantShouldNotBeFound("energyCertificate.equals=" + UPDATED_ENERGY_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsByEnergyCertificateIsInShouldWork() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where energyCertificate in DEFAULT_ENERGY_CERTIFICATE or UPDATED_ENERGY_CERTIFICATE
        defaultIndustrialPlantShouldBeFound("energyCertificate.in=" + DEFAULT_ENERGY_CERTIFICATE + "," + UPDATED_ENERGY_CERTIFICATE);

        // Get all the industrialPlantList where energyCertificate equals to UPDATED_ENERGY_CERTIFICATE
        defaultIndustrialPlantShouldNotBeFound("energyCertificate.in=" + UPDATED_ENERGY_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllIndustrialPlantsByEnergyCertificateIsNullOrNotNull() throws Exception {
        // Initialize the database
        industrialPlantRepository.saveAndFlush(industrialPlant);

        // Get all the industrialPlantList where energyCertificate is not null
        defaultIndustrialPlantShouldBeFound("energyCertificate.specified=true");

        // Get all the industrialPlantList where energyCertificate is null
        defaultIndustrialPlantShouldNotBeFound("energyCertificate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultIndustrialPlantShouldBeFound(String filter) throws Exception {
        restIndustrialPlantMockMvc.perform(get("/api/industrial-plants?sort=id,desc&" + filter))
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

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultIndustrialPlantShouldNotBeFound(String filter) throws Exception {
        restIndustrialPlantMockMvc.perform(get("/api/industrial-plants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
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
        industrialPlantService.save(industrialPlant);

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
        industrialPlantService.save(industrialPlant);

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
