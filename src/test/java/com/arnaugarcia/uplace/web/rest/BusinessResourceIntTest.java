package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;

import com.arnaugarcia.uplace.domain.Business;
import com.arnaugarcia.uplace.repository.BusinessRepository;
import com.arnaugarcia.uplace.service.BusinessService;
import com.arnaugarcia.uplace.web.rest.errors.ExceptionTranslator;
import com.arnaugarcia.uplace.service.dto.BusinessCriteria;
import com.arnaugarcia.uplace.service.BusinessQueryService;

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

import com.arnaugarcia.uplace.domain.enumeration.BusinessType;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
/**
 * Test class for the BusinessResource REST controller.
 *
 * @see BusinessResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UplaceApp.class)
public class BusinessResourceIntTest {

    private static final BusinessType DEFAULT_TYPE = BusinessType.PUB;
    private static final BusinessType UPDATED_TYPE = BusinessType.HOTEL;

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
    private BusinessRepository businessRepository;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private BusinessQueryService businessQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBusinessMockMvc;

    private Business business;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BusinessResource businessResource = new BusinessResource(businessService, businessQueryService);
        this.restBusinessMockMvc = MockMvcBuilders.standaloneSetup(businessResource)
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
    public static Business createEntity(EntityManager em) {
        Business business = new Business()
            .type(DEFAULT_TYPE)
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
        return business;
    }

    @Before
    public void initTest() {
        business = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusiness() throws Exception {
        int databaseSizeBeforeCreate = businessRepository.findAll().size();

        // Create the Business
        restBusinessMockMvc.perform(post("/api/businesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(business)))
            .andExpect(status().isCreated());

        // Validate the Business in the database
        List<Business> businessList = businessRepository.findAll();
        assertThat(businessList).hasSize(databaseSizeBeforeCreate + 1);
        Business testBusiness = businessList.get(businessList.size() - 1);
        assertThat(testBusiness.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBusiness.getNumberBathrooms()).isEqualTo(DEFAULT_NUMBER_BATHROOMS);
        assertThat(testBusiness.getElevator()).isEqualTo(DEFAULT_ELEVATOR);
        assertThat(testBusiness.getAc()).isEqualTo(DEFAULT_AC);
        assertThat(testBusiness.getHeat()).isEqualTo(DEFAULT_HEAT);
        assertThat(testBusiness.getSurfaceTerrace()).isEqualTo(DEFAULT_SURFACE_TERRACE);
        assertThat(testBusiness.getSurfaceGarden()).isEqualTo(DEFAULT_SURFACE_GARDEN);
        assertThat(testBusiness.getNumberOffice()).isEqualTo(DEFAULT_NUMBER_OFFICE);
        assertThat(testBusiness.getSurfaceSaloon()).isEqualTo(DEFAULT_SURFACE_SALOON);
        assertThat(testBusiness.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testBusiness.getPool()).isEqualTo(DEFAULT_POOL);
    }

    @Test
    @Transactional
    public void createBusinessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessRepository.findAll().size();

        // Create the Business with an existing ID
        business.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessMockMvc.perform(post("/api/businesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(business)))
            .andExpect(status().isBadRequest());

        // Validate the Business in the database
        List<Business> businessList = businessRepository.findAll();
        assertThat(businessList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBusinesses() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList
        restBusinessMockMvc.perform(get("/api/businesses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(business.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
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
    public void getBusiness() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get the business
        restBusinessMockMvc.perform(get("/api/businesses/{id}", business.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(business.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
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
    public void getAllBusinessesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where type equals to DEFAULT_TYPE
        defaultBusinessShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the businessList where type equals to UPDATED_TYPE
        defaultBusinessShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllBusinessesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultBusinessShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the businessList where type equals to UPDATED_TYPE
        defaultBusinessShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllBusinessesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where type is not null
        defaultBusinessShouldBeFound("type.specified=true");

        // Get all the businessList where type is null
        defaultBusinessShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesByNumberBathroomsIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where numberBathrooms equals to DEFAULT_NUMBER_BATHROOMS
        defaultBusinessShouldBeFound("numberBathrooms.equals=" + DEFAULT_NUMBER_BATHROOMS);

        // Get all the businessList where numberBathrooms equals to UPDATED_NUMBER_BATHROOMS
        defaultBusinessShouldNotBeFound("numberBathrooms.equals=" + UPDATED_NUMBER_BATHROOMS);
    }

    @Test
    @Transactional
    public void getAllBusinessesByNumberBathroomsIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where numberBathrooms in DEFAULT_NUMBER_BATHROOMS or UPDATED_NUMBER_BATHROOMS
        defaultBusinessShouldBeFound("numberBathrooms.in=" + DEFAULT_NUMBER_BATHROOMS + "," + UPDATED_NUMBER_BATHROOMS);

        // Get all the businessList where numberBathrooms equals to UPDATED_NUMBER_BATHROOMS
        defaultBusinessShouldNotBeFound("numberBathrooms.in=" + UPDATED_NUMBER_BATHROOMS);
    }

    @Test
    @Transactional
    public void getAllBusinessesByNumberBathroomsIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where numberBathrooms is not null
        defaultBusinessShouldBeFound("numberBathrooms.specified=true");

        // Get all the businessList where numberBathrooms is null
        defaultBusinessShouldNotBeFound("numberBathrooms.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesByNumberBathroomsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where numberBathrooms greater than or equals to DEFAULT_NUMBER_BATHROOMS
        defaultBusinessShouldBeFound("numberBathrooms.greaterOrEqualThan=" + DEFAULT_NUMBER_BATHROOMS);

        // Get all the businessList where numberBathrooms greater than or equals to UPDATED_NUMBER_BATHROOMS
        defaultBusinessShouldNotBeFound("numberBathrooms.greaterOrEqualThan=" + UPDATED_NUMBER_BATHROOMS);
    }

    @Test
    @Transactional
    public void getAllBusinessesByNumberBathroomsIsLessThanSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where numberBathrooms less than or equals to DEFAULT_NUMBER_BATHROOMS
        defaultBusinessShouldNotBeFound("numberBathrooms.lessThan=" + DEFAULT_NUMBER_BATHROOMS);

        // Get all the businessList where numberBathrooms less than or equals to UPDATED_NUMBER_BATHROOMS
        defaultBusinessShouldBeFound("numberBathrooms.lessThan=" + UPDATED_NUMBER_BATHROOMS);
    }


    @Test
    @Transactional
    public void getAllBusinessesByElevatorIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where elevator equals to DEFAULT_ELEVATOR
        defaultBusinessShouldBeFound("elevator.equals=" + DEFAULT_ELEVATOR);

        // Get all the businessList where elevator equals to UPDATED_ELEVATOR
        defaultBusinessShouldNotBeFound("elevator.equals=" + UPDATED_ELEVATOR);
    }

    @Test
    @Transactional
    public void getAllBusinessesByElevatorIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where elevator in DEFAULT_ELEVATOR or UPDATED_ELEVATOR
        defaultBusinessShouldBeFound("elevator.in=" + DEFAULT_ELEVATOR + "," + UPDATED_ELEVATOR);

        // Get all the businessList where elevator equals to UPDATED_ELEVATOR
        defaultBusinessShouldNotBeFound("elevator.in=" + UPDATED_ELEVATOR);
    }

    @Test
    @Transactional
    public void getAllBusinessesByElevatorIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where elevator is not null
        defaultBusinessShouldBeFound("elevator.specified=true");

        // Get all the businessList where elevator is null
        defaultBusinessShouldNotBeFound("elevator.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesByAcIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where ac equals to DEFAULT_AC
        defaultBusinessShouldBeFound("ac.equals=" + DEFAULT_AC);

        // Get all the businessList where ac equals to UPDATED_AC
        defaultBusinessShouldNotBeFound("ac.equals=" + UPDATED_AC);
    }

    @Test
    @Transactional
    public void getAllBusinessesByAcIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where ac in DEFAULT_AC or UPDATED_AC
        defaultBusinessShouldBeFound("ac.in=" + DEFAULT_AC + "," + UPDATED_AC);

        // Get all the businessList where ac equals to UPDATED_AC
        defaultBusinessShouldNotBeFound("ac.in=" + UPDATED_AC);
    }

    @Test
    @Transactional
    public void getAllBusinessesByAcIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where ac is not null
        defaultBusinessShouldBeFound("ac.specified=true");

        // Get all the businessList where ac is null
        defaultBusinessShouldNotBeFound("ac.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesByHeatIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where heat equals to DEFAULT_HEAT
        defaultBusinessShouldBeFound("heat.equals=" + DEFAULT_HEAT);

        // Get all the businessList where heat equals to UPDATED_HEAT
        defaultBusinessShouldNotBeFound("heat.equals=" + UPDATED_HEAT);
    }

    @Test
    @Transactional
    public void getAllBusinessesByHeatIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where heat in DEFAULT_HEAT or UPDATED_HEAT
        defaultBusinessShouldBeFound("heat.in=" + DEFAULT_HEAT + "," + UPDATED_HEAT);

        // Get all the businessList where heat equals to UPDATED_HEAT
        defaultBusinessShouldNotBeFound("heat.in=" + UPDATED_HEAT);
    }

    @Test
    @Transactional
    public void getAllBusinessesByHeatIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where heat is not null
        defaultBusinessShouldBeFound("heat.specified=true");

        // Get all the businessList where heat is null
        defaultBusinessShouldNotBeFound("heat.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesBySurfaceTerraceIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where surfaceTerrace equals to DEFAULT_SURFACE_TERRACE
        defaultBusinessShouldBeFound("surfaceTerrace.equals=" + DEFAULT_SURFACE_TERRACE);

        // Get all the businessList where surfaceTerrace equals to UPDATED_SURFACE_TERRACE
        defaultBusinessShouldNotBeFound("surfaceTerrace.equals=" + UPDATED_SURFACE_TERRACE);
    }

    @Test
    @Transactional
    public void getAllBusinessesBySurfaceTerraceIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where surfaceTerrace in DEFAULT_SURFACE_TERRACE or UPDATED_SURFACE_TERRACE
        defaultBusinessShouldBeFound("surfaceTerrace.in=" + DEFAULT_SURFACE_TERRACE + "," + UPDATED_SURFACE_TERRACE);

        // Get all the businessList where surfaceTerrace equals to UPDATED_SURFACE_TERRACE
        defaultBusinessShouldNotBeFound("surfaceTerrace.in=" + UPDATED_SURFACE_TERRACE);
    }

    @Test
    @Transactional
    public void getAllBusinessesBySurfaceTerraceIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where surfaceTerrace is not null
        defaultBusinessShouldBeFound("surfaceTerrace.specified=true");

        // Get all the businessList where surfaceTerrace is null
        defaultBusinessShouldNotBeFound("surfaceTerrace.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesBySurfaceTerraceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where surfaceTerrace greater than or equals to DEFAULT_SURFACE_TERRACE
        defaultBusinessShouldBeFound("surfaceTerrace.greaterOrEqualThan=" + DEFAULT_SURFACE_TERRACE);

        // Get all the businessList where surfaceTerrace greater than or equals to UPDATED_SURFACE_TERRACE
        defaultBusinessShouldNotBeFound("surfaceTerrace.greaterOrEqualThan=" + UPDATED_SURFACE_TERRACE);
    }

    @Test
    @Transactional
    public void getAllBusinessesBySurfaceTerraceIsLessThanSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where surfaceTerrace less than or equals to DEFAULT_SURFACE_TERRACE
        defaultBusinessShouldNotBeFound("surfaceTerrace.lessThan=" + DEFAULT_SURFACE_TERRACE);

        // Get all the businessList where surfaceTerrace less than or equals to UPDATED_SURFACE_TERRACE
        defaultBusinessShouldBeFound("surfaceTerrace.lessThan=" + UPDATED_SURFACE_TERRACE);
    }


    @Test
    @Transactional
    public void getAllBusinessesBySurfaceGardenIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where surfaceGarden equals to DEFAULT_SURFACE_GARDEN
        defaultBusinessShouldBeFound("surfaceGarden.equals=" + DEFAULT_SURFACE_GARDEN);

        // Get all the businessList where surfaceGarden equals to UPDATED_SURFACE_GARDEN
        defaultBusinessShouldNotBeFound("surfaceGarden.equals=" + UPDATED_SURFACE_GARDEN);
    }

    @Test
    @Transactional
    public void getAllBusinessesBySurfaceGardenIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where surfaceGarden in DEFAULT_SURFACE_GARDEN or UPDATED_SURFACE_GARDEN
        defaultBusinessShouldBeFound("surfaceGarden.in=" + DEFAULT_SURFACE_GARDEN + "," + UPDATED_SURFACE_GARDEN);

        // Get all the businessList where surfaceGarden equals to UPDATED_SURFACE_GARDEN
        defaultBusinessShouldNotBeFound("surfaceGarden.in=" + UPDATED_SURFACE_GARDEN);
    }

    @Test
    @Transactional
    public void getAllBusinessesBySurfaceGardenIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where surfaceGarden is not null
        defaultBusinessShouldBeFound("surfaceGarden.specified=true");

        // Get all the businessList where surfaceGarden is null
        defaultBusinessShouldNotBeFound("surfaceGarden.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesBySurfaceGardenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where surfaceGarden greater than or equals to DEFAULT_SURFACE_GARDEN
        defaultBusinessShouldBeFound("surfaceGarden.greaterOrEqualThan=" + DEFAULT_SURFACE_GARDEN);

        // Get all the businessList where surfaceGarden greater than or equals to UPDATED_SURFACE_GARDEN
        defaultBusinessShouldNotBeFound("surfaceGarden.greaterOrEqualThan=" + UPDATED_SURFACE_GARDEN);
    }

    @Test
    @Transactional
    public void getAllBusinessesBySurfaceGardenIsLessThanSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where surfaceGarden less than or equals to DEFAULT_SURFACE_GARDEN
        defaultBusinessShouldNotBeFound("surfaceGarden.lessThan=" + DEFAULT_SURFACE_GARDEN);

        // Get all the businessList where surfaceGarden less than or equals to UPDATED_SURFACE_GARDEN
        defaultBusinessShouldBeFound("surfaceGarden.lessThan=" + UPDATED_SURFACE_GARDEN);
    }


    @Test
    @Transactional
    public void getAllBusinessesByNumberOfficeIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where numberOffice equals to DEFAULT_NUMBER_OFFICE
        defaultBusinessShouldBeFound("numberOffice.equals=" + DEFAULT_NUMBER_OFFICE);

        // Get all the businessList where numberOffice equals to UPDATED_NUMBER_OFFICE
        defaultBusinessShouldNotBeFound("numberOffice.equals=" + UPDATED_NUMBER_OFFICE);
    }

    @Test
    @Transactional
    public void getAllBusinessesByNumberOfficeIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where numberOffice in DEFAULT_NUMBER_OFFICE or UPDATED_NUMBER_OFFICE
        defaultBusinessShouldBeFound("numberOffice.in=" + DEFAULT_NUMBER_OFFICE + "," + UPDATED_NUMBER_OFFICE);

        // Get all the businessList where numberOffice equals to UPDATED_NUMBER_OFFICE
        defaultBusinessShouldNotBeFound("numberOffice.in=" + UPDATED_NUMBER_OFFICE);
    }

    @Test
    @Transactional
    public void getAllBusinessesByNumberOfficeIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where numberOffice is not null
        defaultBusinessShouldBeFound("numberOffice.specified=true");

        // Get all the businessList where numberOffice is null
        defaultBusinessShouldNotBeFound("numberOffice.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesByNumberOfficeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where numberOffice greater than or equals to DEFAULT_NUMBER_OFFICE
        defaultBusinessShouldBeFound("numberOffice.greaterOrEqualThan=" + DEFAULT_NUMBER_OFFICE);

        // Get all the businessList where numberOffice greater than or equals to UPDATED_NUMBER_OFFICE
        defaultBusinessShouldNotBeFound("numberOffice.greaterOrEqualThan=" + UPDATED_NUMBER_OFFICE);
    }

    @Test
    @Transactional
    public void getAllBusinessesByNumberOfficeIsLessThanSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where numberOffice less than or equals to DEFAULT_NUMBER_OFFICE
        defaultBusinessShouldNotBeFound("numberOffice.lessThan=" + DEFAULT_NUMBER_OFFICE);

        // Get all the businessList where numberOffice less than or equals to UPDATED_NUMBER_OFFICE
        defaultBusinessShouldBeFound("numberOffice.lessThan=" + UPDATED_NUMBER_OFFICE);
    }


    @Test
    @Transactional
    public void getAllBusinessesBySurfaceSaloonIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where surfaceSaloon equals to DEFAULT_SURFACE_SALOON
        defaultBusinessShouldBeFound("surfaceSaloon.equals=" + DEFAULT_SURFACE_SALOON);

        // Get all the businessList where surfaceSaloon equals to UPDATED_SURFACE_SALOON
        defaultBusinessShouldNotBeFound("surfaceSaloon.equals=" + UPDATED_SURFACE_SALOON);
    }

    @Test
    @Transactional
    public void getAllBusinessesBySurfaceSaloonIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where surfaceSaloon in DEFAULT_SURFACE_SALOON or UPDATED_SURFACE_SALOON
        defaultBusinessShouldBeFound("surfaceSaloon.in=" + DEFAULT_SURFACE_SALOON + "," + UPDATED_SURFACE_SALOON);

        // Get all the businessList where surfaceSaloon equals to UPDATED_SURFACE_SALOON
        defaultBusinessShouldNotBeFound("surfaceSaloon.in=" + UPDATED_SURFACE_SALOON);
    }

    @Test
    @Transactional
    public void getAllBusinessesBySurfaceSaloonIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where surfaceSaloon is not null
        defaultBusinessShouldBeFound("surfaceSaloon.specified=true");

        // Get all the businessList where surfaceSaloon is null
        defaultBusinessShouldNotBeFound("surfaceSaloon.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesBySurfaceSaloonIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where surfaceSaloon greater than or equals to DEFAULT_SURFACE_SALOON
        defaultBusinessShouldBeFound("surfaceSaloon.greaterOrEqualThan=" + DEFAULT_SURFACE_SALOON);

        // Get all the businessList where surfaceSaloon greater than or equals to UPDATED_SURFACE_SALOON
        defaultBusinessShouldNotBeFound("surfaceSaloon.greaterOrEqualThan=" + UPDATED_SURFACE_SALOON);
    }

    @Test
    @Transactional
    public void getAllBusinessesBySurfaceSaloonIsLessThanSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where surfaceSaloon less than or equals to DEFAULT_SURFACE_SALOON
        defaultBusinessShouldNotBeFound("surfaceSaloon.lessThan=" + DEFAULT_SURFACE_SALOON);

        // Get all the businessList where surfaceSaloon less than or equals to UPDATED_SURFACE_SALOON
        defaultBusinessShouldBeFound("surfaceSaloon.lessThan=" + UPDATED_SURFACE_SALOON);
    }


    @Test
    @Transactional
    public void getAllBusinessesByHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where height equals to DEFAULT_HEIGHT
        defaultBusinessShouldBeFound("height.equals=" + DEFAULT_HEIGHT);

        // Get all the businessList where height equals to UPDATED_HEIGHT
        defaultBusinessShouldNotBeFound("height.equals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllBusinessesByHeightIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where height in DEFAULT_HEIGHT or UPDATED_HEIGHT
        defaultBusinessShouldBeFound("height.in=" + DEFAULT_HEIGHT + "," + UPDATED_HEIGHT);

        // Get all the businessList where height equals to UPDATED_HEIGHT
        defaultBusinessShouldNotBeFound("height.in=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllBusinessesByHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where height is not null
        defaultBusinessShouldBeFound("height.specified=true");

        // Get all the businessList where height is null
        defaultBusinessShouldNotBeFound("height.specified=false");
    }

    @Test
    @Transactional
    public void getAllBusinessesByHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where height greater than or equals to DEFAULT_HEIGHT
        defaultBusinessShouldBeFound("height.greaterOrEqualThan=" + DEFAULT_HEIGHT);

        // Get all the businessList where height greater than or equals to UPDATED_HEIGHT
        defaultBusinessShouldNotBeFound("height.greaterOrEqualThan=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllBusinessesByHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where height less than or equals to DEFAULT_HEIGHT
        defaultBusinessShouldNotBeFound("height.lessThan=" + DEFAULT_HEIGHT);

        // Get all the businessList where height less than or equals to UPDATED_HEIGHT
        defaultBusinessShouldBeFound("height.lessThan=" + UPDATED_HEIGHT);
    }


    @Test
    @Transactional
    public void getAllBusinessesByPoolIsEqualToSomething() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where pool equals to DEFAULT_POOL
        defaultBusinessShouldBeFound("pool.equals=" + DEFAULT_POOL);

        // Get all the businessList where pool equals to UPDATED_POOL
        defaultBusinessShouldNotBeFound("pool.equals=" + UPDATED_POOL);
    }

    @Test
    @Transactional
    public void getAllBusinessesByPoolIsInShouldWork() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where pool in DEFAULT_POOL or UPDATED_POOL
        defaultBusinessShouldBeFound("pool.in=" + DEFAULT_POOL + "," + UPDATED_POOL);

        // Get all the businessList where pool equals to UPDATED_POOL
        defaultBusinessShouldNotBeFound("pool.in=" + UPDATED_POOL);
    }

    @Test
    @Transactional
    public void getAllBusinessesByPoolIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);

        // Get all the businessList where pool is not null
        defaultBusinessShouldBeFound("pool.specified=true");

        // Get all the businessList where pool is null
        defaultBusinessShouldNotBeFound("pool.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBusinessShouldBeFound(String filter) throws Exception {
        restBusinessMockMvc.perform(get("/api/businesses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(business.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
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

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBusinessShouldNotBeFound(String filter) throws Exception {
        restBusinessMockMvc.perform(get("/api/businesses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingBusiness() throws Exception {
        // Get the business
        restBusinessMockMvc.perform(get("/api/businesses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusiness() throws Exception {
        // Initialize the database
        businessService.save(business);

        int databaseSizeBeforeUpdate = businessRepository.findAll().size();

        // Update the business
        Business updatedBusiness = businessRepository.findOne(business.getId());
        // Disconnect from session so that the updates on updatedBusiness are not directly saved in db
        em.detach(updatedBusiness);
        updatedBusiness
            .type(UPDATED_TYPE)
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

        restBusinessMockMvc.perform(put("/api/businesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBusiness)))
            .andExpect(status().isOk());

        // Validate the Business in the database
        List<Business> businessList = businessRepository.findAll();
        assertThat(businessList).hasSize(databaseSizeBeforeUpdate);
        Business testBusiness = businessList.get(businessList.size() - 1);
        assertThat(testBusiness.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBusiness.getNumberBathrooms()).isEqualTo(UPDATED_NUMBER_BATHROOMS);
        assertThat(testBusiness.getElevator()).isEqualTo(UPDATED_ELEVATOR);
        assertThat(testBusiness.getAc()).isEqualTo(UPDATED_AC);
        assertThat(testBusiness.getHeat()).isEqualTo(UPDATED_HEAT);
        assertThat(testBusiness.getSurfaceTerrace()).isEqualTo(UPDATED_SURFACE_TERRACE);
        assertThat(testBusiness.getSurfaceGarden()).isEqualTo(UPDATED_SURFACE_GARDEN);
        assertThat(testBusiness.getNumberOffice()).isEqualTo(UPDATED_NUMBER_OFFICE);
        assertThat(testBusiness.getSurfaceSaloon()).isEqualTo(UPDATED_SURFACE_SALOON);
        assertThat(testBusiness.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testBusiness.getPool()).isEqualTo(UPDATED_POOL);
    }

    @Test
    @Transactional
    public void updateNonExistingBusiness() throws Exception {
        int databaseSizeBeforeUpdate = businessRepository.findAll().size();

        // Create the Business

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBusinessMockMvc.perform(put("/api/businesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(business)))
            .andExpect(status().isCreated());

        // Validate the Business in the database
        List<Business> businessList = businessRepository.findAll();
        assertThat(businessList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBusiness() throws Exception {
        // Initialize the database
        businessService.save(business);

        int databaseSizeBeforeDelete = businessRepository.findAll().size();

        // Get the business
        restBusinessMockMvc.perform(delete("/api/businesses/{id}", business.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Business> businessList = businessRepository.findAll();
        assertThat(businessList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Business.class);
        Business business1 = new Business();
        business1.setId(1L);
        Business business2 = new Business();
        business2.setId(business1.getId());
        assertThat(business1).isEqualTo(business2);
        business2.setId(2L);
        assertThat(business1).isNotEqualTo(business2);
        business1.setId(null);
        assertThat(business1).isNotEqualTo(business2);
    }
}
