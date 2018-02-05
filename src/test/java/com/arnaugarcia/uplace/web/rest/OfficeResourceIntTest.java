package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;

import com.arnaugarcia.uplace.domain.Office;
import com.arnaugarcia.uplace.domain.enumeration.TransactionType;
import com.arnaugarcia.uplace.repository.OfficeRepository;
import com.arnaugarcia.uplace.service.OfficeService;
import com.arnaugarcia.uplace.service.PropertyService;
import com.arnaugarcia.uplace.web.rest.errors.ExceptionTranslator;
import com.arnaugarcia.uplace.service.OfficeQueryService;

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

import com.arnaugarcia.uplace.domain.enumeration.Select;

/**
 * Test class for the OfficeResource REST controller.
 *
 * @see OfficeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UplaceApp.class)
public class OfficeResourceIntTest {

    private static final String DEFAULT_TITLE = "TEST Apartment";

    private static final Double DEFAULT_PRICE = 0.0;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.now();

    private static final TransactionType DEFAULT_TRANSACCTION = TransactionType.RENT_BUY;

    private static final String DEFAULT_REFERENCE = "AAAAAAA";

    private static final String DEFAULT_BATHROOMS = "AAAAAAAAAA";
    private static final String UPDATED_BATHROOMS = "BBBBBBBBBB";

    private static final Integer DEFAULT_FLOORS = 1;
    private static final Integer UPDATED_FLOORS = 2;

    private static final Select DEFAULT_TERRACE = Select.YES;
    private static final Select UPDATED_TERRACE = Select.NO;

    private static final Select DEFAULT_OFFICE = Select.YES;
    private static final Select UPDATED_OFFICE = Select.NO;

    private static final Select DEFAULT_STORAGE = Select.YES;
    private static final Select UPDATED_STORAGE = Select.NO;

    private static final Integer DEFAULT_STORAGE_SURFACE = 1;
    private static final Integer UPDATED_STORAGE_SURFACE = 2;

    private static final Integer DEFAULT_OFFICES_SURFACE = 1;
    private static final Integer UPDATED_OFFICES_SURFACE = 2;

    private static final Select DEFAULT_AC = Select.YES;
    private static final Select UPDATED_AC = Select.NO;

    private static final Select DEFAULT_HEAT = Select.YES;
    private static final Select UPDATED_HEAT = Select.NO;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private OfficeQueryService officeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOfficeMockMvc;

    private Office office;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OfficeResource officeResource = new OfficeResource(officeService, officeQueryService);
        this.restOfficeMockMvc = MockMvcBuilders.standaloneSetup(officeResource)
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
    public static Office createEntity(EntityManager em) {
        Office office = (Office) new Office()
            .bathrooms(DEFAULT_BATHROOMS)
            .floors(DEFAULT_FLOORS)
            .terrace(DEFAULT_TERRACE)
            .office(DEFAULT_OFFICE)
            .storage(DEFAULT_STORAGE)
            .storageSurface(DEFAULT_STORAGE_SURFACE)
            .officesSurface(DEFAULT_OFFICES_SURFACE)
            .ac(DEFAULT_AC)
            .heat(DEFAULT_HEAT)
            .created(DEFAULT_CREATED)
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE)
            .transaction(DEFAULT_TRANSACCTION)
            .reference(DEFAULT_REFERENCE);
        return office;
    }

    @Before
    public void initTest() {
        office = createEntity(em);
    }

    /*@Test
    @Transactional
    public void createOffice() throws Exception {
        int databaseSizeBeforeCreate = officeRepository.findAll().size();

        // Create the Office
        restOfficeMockMvc.perform(post("/api/offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(office)))
            .andExpect(status().isCreated());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeCreate + 1);
        Office testOffice = officeList.get(officeList.size() - 1);
        assertThat(testOffice.getBathrooms()).isEqualTo(DEFAULT_BATHROOMS);
        assertThat(testOffice.getFloors()).isEqualTo(DEFAULT_FLOORS);
        assertThat(testOffice.getOffice()).isEqualTo(DEFAULT_OFFICE);
        assertThat(testOffice.getStorage()).isEqualTo(DEFAULT_STORAGE);
        assertThat(testOffice.getStorageSurface()).isEqualTo(DEFAULT_STORAGE_SURFACE);
        assertThat(testOffice.getOfficesSurface()).isEqualTo(DEFAULT_OFFICES_SURFACE);
        assertThat(testOffice.getAc()).isEqualTo(DEFAULT_AC);
        assertThat(testOffice.getHeat()).isEqualTo(DEFAULT_HEAT);
    }*/

    @Test
    @Transactional
    public void createOfficeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = officeRepository.findAll().size();

        // Create the Office with an existing ID
        office.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfficeMockMvc.perform(post("/api/offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(office)))
            .andExpect(status().isBadRequest());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOffices() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList
        restOfficeMockMvc.perform(get("/api/offices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(office.getId().intValue())))
            .andExpect(jsonPath("$.[*].bathrooms").value(hasItem(DEFAULT_BATHROOMS.toString())))
            .andExpect(jsonPath("$.[*].floors").value(hasItem(DEFAULT_FLOORS)))
            .andExpect(jsonPath("$.[*].office").value(hasItem(DEFAULT_OFFICE.toString())))
            .andExpect(jsonPath("$.[*].storage").value(hasItem(DEFAULT_STORAGE.toString())))
            .andExpect(jsonPath("$.[*].storageSurface").value(hasItem(DEFAULT_STORAGE_SURFACE)))
            .andExpect(jsonPath("$.[*].officesSurface").value(hasItem(DEFAULT_OFFICES_SURFACE)))
            .andExpect(jsonPath("$.[*].ac").value(hasItem(DEFAULT_AC.toString())))
            .andExpect(jsonPath("$.[*].heat").value(hasItem(DEFAULT_HEAT.toString())));
    }

    @Test
    @Transactional
    public void getOffice() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get the office
        restOfficeMockMvc.perform(get("/api/offices/{id}", office.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(office.getId().intValue()))
            .andExpect(jsonPath("$.bathrooms").value(DEFAULT_BATHROOMS.toString()))
            .andExpect(jsonPath("$.floors").value(DEFAULT_FLOORS))
            .andExpect(jsonPath("$.office").value(DEFAULT_OFFICE.toString()))
            .andExpect(jsonPath("$.storage").value(DEFAULT_STORAGE.toString()))
            .andExpect(jsonPath("$.storageSurface").value(DEFAULT_STORAGE_SURFACE))
            .andExpect(jsonPath("$.officesSurface").value(DEFAULT_OFFICES_SURFACE))
            .andExpect(jsonPath("$.ac").value(DEFAULT_AC.toString()))
            .andExpect(jsonPath("$.heat").value(DEFAULT_HEAT.toString()));
    }

   /* @Test
    @Transactional
    public void getAllOfficesByBathroomsIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where bathrooms equals to DEFAULT_BATHROOMS
        defaultOfficeShouldBeFound("bathrooms.equals=" + DEFAULT_BATHROOMS);

        // Get all the officeList where bathrooms equals to UPDATED_BATHROOMS
        defaultOfficeShouldNotBeFound("bathrooms.equals=" + UPDATED_BATHROOMS);
    }

    @Test
    @Transactional
    public void getAllOfficesByBathroomsIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where bathrooms in DEFAULT_BATHROOMS or UPDATED_BATHROOMS
        defaultOfficeShouldBeFound("bathrooms.in=" + DEFAULT_BATHROOMS + "," + UPDATED_BATHROOMS);

        // Get all the officeList where bathrooms equals to UPDATED_BATHROOMS
        defaultOfficeShouldNotBeFound("bathrooms.in=" + UPDATED_BATHROOMS);
    }

    @Test
    @Transactional
    public void getAllOfficesByBathroomsIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where bathrooms is not null
        defaultOfficeShouldBeFound("bathrooms.specified=true");

        // Get all the officeList where bathrooms is null
        defaultOfficeShouldNotBeFound("bathrooms.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByFloorsIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where floors equals to DEFAULT_FLOORS
        defaultOfficeShouldBeFound("floors.equals=" + DEFAULT_FLOORS);

        // Get all the officeList where floors equals to UPDATED_FLOORS
        defaultOfficeShouldNotBeFound("floors.equals=" + UPDATED_FLOORS);
    }

    @Test
    @Transactional
    public void getAllOfficesByFloorsIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where floors in DEFAULT_FLOORS or UPDATED_FLOORS
        defaultOfficeShouldBeFound("floors.in=" + DEFAULT_FLOORS + "," + UPDATED_FLOORS);

        // Get all the officeList where floors equals to UPDATED_FLOORS
        defaultOfficeShouldNotBeFound("floors.in=" + UPDATED_FLOORS);
    }

    @Test
    @Transactional
    public void getAllOfficesByFloorsIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where floors is not null
        defaultOfficeShouldBeFound("floors.specified=true");

        // Get all the officeList where floors is null
        defaultOfficeShouldNotBeFound("floors.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByFloorsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where floors greater than or equals to DEFAULT_FLOORS
        defaultOfficeShouldBeFound("floors.greaterOrEqualThan=" + DEFAULT_FLOORS);

        // Get all the officeList where floors greater than or equals to UPDATED_FLOORS
        defaultOfficeShouldNotBeFound("floors.greaterOrEqualThan=" + UPDATED_FLOORS);
    }

    @Test
    @Transactional
    public void getAllOfficesByFloorsIsLessThanSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where floors less than or equals to DEFAULT_FLOORS
        defaultOfficeShouldNotBeFound("floors.lessThan=" + DEFAULT_FLOORS);

        // Get all the officeList where floors less than or equals to UPDATED_FLOORS
        defaultOfficeShouldBeFound("floors.lessThan=" + UPDATED_FLOORS);
    }


    @Test
    @Transactional
    public void getAllOfficesByTerraceIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where terrace equals to DEFAULT_TERRACE
        defaultOfficeShouldBeFound("terrace.equals=" + DEFAULT_TERRACE);

        // Get all the officeList where terrace equals to UPDATED_TERRACE
        defaultOfficeShouldNotBeFound("terrace.equals=" + UPDATED_TERRACE);
    }

    @Test
    @Transactional
    public void getAllOfficesByTerraceIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where terrace in DEFAULT_TERRACE or UPDATED_TERRACE
        defaultOfficeShouldBeFound("terrace.in=" + DEFAULT_TERRACE + "," + UPDATED_TERRACE);

        // Get all the officeList where terrace equals to UPDATED_TERRACE
        defaultOfficeShouldNotBeFound("terrace.in=" + UPDATED_TERRACE);
    }

    @Test
    @Transactional
    public void getAllOfficesByTerraceIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where terrace is not null
        defaultOfficeShouldBeFound("terrace.specified=true");

        // Get all the officeList where terrace is null
        defaultOfficeShouldNotBeFound("terrace.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByTerraceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where terrace greater than or equals to DEFAULT_TERRACE
        defaultOfficeShouldBeFound("terrace.greaterOrEqualThan=" + DEFAULT_TERRACE);

        // Get all the officeList where terrace greater than or equals to UPDATED_TERRACE
        defaultOfficeShouldNotBeFound("terrace.greaterOrEqualThan=" + UPDATED_TERRACE);
    }

    @Test
    @Transactional
    public void getAllOfficesByTerraceIsLessThanSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where terrace less than or equals to DEFAULT_TERRACE
        defaultOfficeShouldNotBeFound("terrace.lessThan=" + DEFAULT_TERRACE);

        // Get all the officeList where terrace less than or equals to UPDATED_TERRACE
        defaultOfficeShouldBeFound("terrace.lessThan=" + UPDATED_TERRACE);
    }


    @Test
    @Transactional
    public void getAllOfficesByOfficeIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where office equals to DEFAULT_OFFICE
        defaultOfficeShouldBeFound("office.equals=" + DEFAULT_OFFICE);

        // Get all the officeList where office equals to UPDATED_OFFICE
        defaultOfficeShouldNotBeFound("office.equals=" + UPDATED_OFFICE);
    }

    @Test
    @Transactional
    public void getAllOfficesByOfficeIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where office in DEFAULT_OFFICE or UPDATED_OFFICE
        defaultOfficeShouldBeFound("office.in=" + DEFAULT_OFFICE + "," + UPDATED_OFFICE);

        // Get all the officeList where office equals to UPDATED_OFFICE
        defaultOfficeShouldNotBeFound("office.in=" + UPDATED_OFFICE);
    }

    @Test
    @Transactional
    public void getAllOfficesByOfficeIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where office is not null
        defaultOfficeShouldBeFound("office.specified=true");

        // Get all the officeList where office is null
        defaultOfficeShouldNotBeFound("office.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByStorageIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where storage equals to DEFAULT_STORAGE
        defaultOfficeShouldBeFound("storage.equals=" + DEFAULT_STORAGE);

        // Get all the officeList where storage equals to UPDATED_STORAGE
        defaultOfficeShouldNotBeFound("storage.equals=" + UPDATED_STORAGE);
    }

    @Test
    @Transactional
    public void getAllOfficesByStorageIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where storage in DEFAULT_STORAGE or UPDATED_STORAGE
        defaultOfficeShouldBeFound("storage.in=" + DEFAULT_STORAGE + "," + UPDATED_STORAGE);

        // Get all the officeList where storage equals to UPDATED_STORAGE
        defaultOfficeShouldNotBeFound("storage.in=" + UPDATED_STORAGE);
    }

    @Test
    @Transactional
    public void getAllOfficesByStorageIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where storage is not null
        defaultOfficeShouldBeFound("storage.specified=true");

        // Get all the officeList where storage is null
        defaultOfficeShouldNotBeFound("storage.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByStorageSurfaceIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where storageSurface equals to DEFAULT_STORAGE_SURFACE
        defaultOfficeShouldBeFound("storageSurface.equals=" + DEFAULT_STORAGE_SURFACE);

        // Get all the officeList where storageSurface equals to UPDATED_STORAGE_SURFACE
        defaultOfficeShouldNotBeFound("storageSurface.equals=" + UPDATED_STORAGE_SURFACE);
    }

    @Test
    @Transactional
    public void getAllOfficesByStorageSurfaceIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where storageSurface in DEFAULT_STORAGE_SURFACE or UPDATED_STORAGE_SURFACE
        defaultOfficeShouldBeFound("storageSurface.in=" + DEFAULT_STORAGE_SURFACE + "," + UPDATED_STORAGE_SURFACE);

        // Get all the officeList where storageSurface equals to UPDATED_STORAGE_SURFACE
        defaultOfficeShouldNotBeFound("storageSurface.in=" + UPDATED_STORAGE_SURFACE);
    }

    @Test
    @Transactional
    public void getAllOfficesByStorageSurfaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where storageSurface is not null
        defaultOfficeShouldBeFound("storageSurface.specified=true");

        // Get all the officeList where storageSurface is null
        defaultOfficeShouldNotBeFound("storageSurface.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByStorageSurfaceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where storageSurface greater than or equals to DEFAULT_STORAGE_SURFACE
        defaultOfficeShouldBeFound("storageSurface.greaterOrEqualThan=" + DEFAULT_STORAGE_SURFACE);

        // Get all the officeList where storageSurface greater than or equals to UPDATED_STORAGE_SURFACE
        defaultOfficeShouldNotBeFound("storageSurface.greaterOrEqualThan=" + UPDATED_STORAGE_SURFACE);
    }

    @Test
    @Transactional
    public void getAllOfficesByStorageSurfaceIsLessThanSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where storageSurface less than or equals to DEFAULT_STORAGE_SURFACE
        defaultOfficeShouldNotBeFound("storageSurface.lessThan=" + DEFAULT_STORAGE_SURFACE);

        // Get all the officeList where storageSurface less than or equals to UPDATED_STORAGE_SURFACE
        defaultOfficeShouldBeFound("storageSurface.lessThan=" + UPDATED_STORAGE_SURFACE);
    }


    @Test
    @Transactional
    public void getAllOfficesByOfficesSurfaceIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where officesSurface equals to DEFAULT_OFFICES_SURFACE
        defaultOfficeShouldBeFound("officesSurface.equals=" + DEFAULT_OFFICES_SURFACE);

        // Get all the officeList where officesSurface equals to UPDATED_OFFICES_SURFACE
        defaultOfficeShouldNotBeFound("officesSurface.equals=" + UPDATED_OFFICES_SURFACE);
    }

    @Test
    @Transactional
    public void getAllOfficesByOfficesSurfaceIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where officesSurface in DEFAULT_OFFICES_SURFACE or UPDATED_OFFICES_SURFACE
        defaultOfficeShouldBeFound("officesSurface.in=" + DEFAULT_OFFICES_SURFACE + "," + UPDATED_OFFICES_SURFACE);

        // Get all the officeList where officesSurface equals to UPDATED_OFFICES_SURFACE
        defaultOfficeShouldNotBeFound("officesSurface.in=" + UPDATED_OFFICES_SURFACE);
    }

    @Test
    @Transactional
    public void getAllOfficesByOfficesSurfaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where officesSurface is not null
        defaultOfficeShouldBeFound("officesSurface.specified=true");

        // Get all the officeList where officesSurface is null
        defaultOfficeShouldNotBeFound("officesSurface.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByOfficesSurfaceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where officesSurface greater than or equals to DEFAULT_OFFICES_SURFACE
        defaultOfficeShouldBeFound("officesSurface.greaterOrEqualThan=" + DEFAULT_OFFICES_SURFACE);

        // Get all the officeList where officesSurface greater than or equals to UPDATED_OFFICES_SURFACE
        defaultOfficeShouldNotBeFound("officesSurface.greaterOrEqualThan=" + UPDATED_OFFICES_SURFACE);
    }

    @Test
    @Transactional
    public void getAllOfficesByOfficesSurfaceIsLessThanSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where officesSurface less than or equals to DEFAULT_OFFICES_SURFACE
        defaultOfficeShouldNotBeFound("officesSurface.lessThan=" + DEFAULT_OFFICES_SURFACE);

        // Get all the officeList where officesSurface less than or equals to UPDATED_OFFICES_SURFACE
        defaultOfficeShouldBeFound("officesSurface.lessThan=" + UPDATED_OFFICES_SURFACE);
    }


    @Test
    @Transactional
    public void getAllOfficesByAcIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where ac equals to DEFAULT_AC
        defaultOfficeShouldBeFound("ac.equals=" + DEFAULT_AC);

        // Get all the officeList where ac equals to UPDATED_AC
        defaultOfficeShouldNotBeFound("ac.equals=" + UPDATED_AC);
    }

    @Test
    @Transactional
    public void getAllOfficesByAcIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where ac in DEFAULT_AC or UPDATED_AC
        defaultOfficeShouldBeFound("ac.in=" + DEFAULT_AC + "," + UPDATED_AC);

        // Get all the officeList where ac equals to UPDATED_AC
        defaultOfficeShouldNotBeFound("ac.in=" + UPDATED_AC);
    }

    @Test
    @Transactional
    public void getAllOfficesByAcIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where ac is not null
        defaultOfficeShouldBeFound("ac.specified=true");

        // Get all the officeList where ac is null
        defaultOfficeShouldNotBeFound("ac.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByHeatIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where heat equals to DEFAULT_HEAT
        defaultOfficeShouldBeFound("heat.equals=" + DEFAULT_HEAT);

        // Get all the officeList where heat equals to UPDATED_HEAT
        defaultOfficeShouldNotBeFound("heat.equals=" + UPDATED_HEAT);
    }

    @Test
    @Transactional
    public void getAllOfficesByHeatIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where heat in DEFAULT_HEAT or UPDATED_HEAT
        defaultOfficeShouldBeFound("heat.in=" + DEFAULT_HEAT + "," + UPDATED_HEAT);

        // Get all the officeList where heat equals to UPDATED_HEAT
        defaultOfficeShouldNotBeFound("heat.in=" + UPDATED_HEAT);
    }

    @Test
    @Transactional
    public void getAllOfficesByHeatIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where heat is not null
        defaultOfficeShouldBeFound("heat.specified=true");

        // Get all the officeList where heat is null
        defaultOfficeShouldNotBeFound("heat.specified=false");
    }*/
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultOfficeShouldBeFound(String filter) throws Exception {
        restOfficeMockMvc.perform(get("/api/offices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(office.getId().intValue())))
            .andExpect(jsonPath("$.[*].bathrooms").value(hasItem(DEFAULT_BATHROOMS.toString())))
            .andExpect(jsonPath("$.[*].floors").value(hasItem(DEFAULT_FLOORS)))
            .andExpect(jsonPath("$.[*].terrace").value(hasItem(DEFAULT_TERRACE)))
            .andExpect(jsonPath("$.[*].office").value(hasItem(DEFAULT_OFFICE.toString())))
            .andExpect(jsonPath("$.[*].storage").value(hasItem(DEFAULT_STORAGE.toString())))
            .andExpect(jsonPath("$.[*].storageSurface").value(hasItem(DEFAULT_STORAGE_SURFACE)))
            .andExpect(jsonPath("$.[*].officesSurface").value(hasItem(DEFAULT_OFFICES_SURFACE)))
            .andExpect(jsonPath("$.[*].ac").value(hasItem(DEFAULT_AC.toString())))
            .andExpect(jsonPath("$.[*].heat").value(hasItem(DEFAULT_HEAT.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultOfficeShouldNotBeFound(String filter) throws Exception {
        restOfficeMockMvc.perform(get("/api/offices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingOffice() throws Exception {
        // Get the office
        restOfficeMockMvc.perform(get("/api/offices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffice() throws Exception {
        // Initialize the database
        officeService.save(office);

        int databaseSizeBeforeUpdate = officeRepository.findAll().size();

        // Update the office
        Office updatedOffice = officeRepository.findOne(office.getId());
        // Disconnect from session so that the updates on updatedOffice are not directly saved in db
        em.detach(updatedOffice);
        updatedOffice
            .bathrooms(UPDATED_BATHROOMS)
            .floors(UPDATED_FLOORS)
            .terrace(UPDATED_TERRACE)
            .office(UPDATED_OFFICE)
            .storage(UPDATED_STORAGE)
            .storageSurface(UPDATED_STORAGE_SURFACE)
            .officesSurface(UPDATED_OFFICES_SURFACE)
            .ac(UPDATED_AC)
            .heat(UPDATED_HEAT);

        restOfficeMockMvc.perform(put("/api/offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOffice)))
            .andExpect(status().isOk());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
        Office testOffice = officeList.get(officeList.size() - 1);
        assertThat(testOffice.getBathrooms()).isEqualTo(UPDATED_BATHROOMS);
        assertThat(testOffice.getFloors()).isEqualTo(UPDATED_FLOORS);
        assertThat(testOffice.getTerrace()).isEqualTo(UPDATED_TERRACE);
        assertThat(testOffice.getOffice()).isEqualTo(UPDATED_OFFICE);
        assertThat(testOffice.getStorage()).isEqualTo(UPDATED_STORAGE);
        assertThat(testOffice.getStorageSurface()).isEqualTo(UPDATED_STORAGE_SURFACE);
        assertThat(testOffice.getOfficesSurface()).isEqualTo(UPDATED_OFFICES_SURFACE);
        assertThat(testOffice.getAc()).isEqualTo(UPDATED_AC);
        assertThat(testOffice.getHeat()).isEqualTo(UPDATED_HEAT);
    }

    /*@Test
    @Transactional
    public void updateNonExistingOffice() throws Exception {
        int databaseSizeBeforeUpdate = officeRepository.findAll().size();

        // Create the Office

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOfficeMockMvc.perform(put("/api/offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(office)))
            .andExpect(status().isCreated());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate + 1);
    }*/

    @Test
    @Transactional
    public void deleteOffice() throws Exception {
        // Initialize the database
        officeService.save(office);

        int databaseSizeBeforeDelete = officeRepository.findAll().size();

        // Get the office
        restOfficeMockMvc.perform(delete("/api/offices/{id}", office.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Office.class);
        Office office1 = new Office();
        office1.setId(1L);
        Office office2 = new Office();
        office2.setId(office1.getId());
        assertThat(office1).isEqualTo(office2);
        office2.setId(2L);
        assertThat(office1).isNotEqualTo(office2);
        office1.setId(null);
        assertThat(office1).isNotEqualTo(office2);
    }
}
