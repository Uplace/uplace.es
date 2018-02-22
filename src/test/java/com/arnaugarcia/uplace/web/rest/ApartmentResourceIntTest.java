package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;

import com.arnaugarcia.uplace.domain.Apartment;
import com.arnaugarcia.uplace.repository.ApartmentRepository;
import com.arnaugarcia.uplace.service.ApartmentService;
import com.arnaugarcia.uplace.web.rest.errors.ExceptionTranslator;
import com.arnaugarcia.uplace.service.dto.ApartmentCriteria;
import com.arnaugarcia.uplace.service.ApartmentQueryService;

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

import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.ApartmentType;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;
import com.arnaugarcia.uplace.domain.enumeration.Select;
/**
 * Test class for the ApartmentResource REST controller.
 *
 * @see ApartmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UplaceApp.class)
public class ApartmentResourceIntTest {

    private static final Integer DEFAULT_NUMBER_BEDROOMS = 1;
    private static final Integer UPDATED_NUMBER_BEDROOMS = 2;

    private static final Integer DEFAULT_NUMBER_BATHROOMS = 1;
    private static final Integer UPDATED_NUMBER_BATHROOMS = 2;

    private static final Integer DEFAULT_M_2_EDIFIED = 1;
    private static final Integer UPDATED_M_2_EDIFIED = 2;

    private static final Integer DEFAULT_M_2_USABLE = 1;
    private static final Integer UPDATED_M_2_USABLE = 2;

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final Select DEFAULT_ELEVATOR = Select.YES;
    private static final Select UPDATED_ELEVATOR = Select.NO;

    private static final Select DEFAULT_AC = Select.YES;
    private static final Select UPDATED_AC = Select.NO;

    private static final Select DEFAULT_HEAT = Select.YES;
    private static final Select UPDATED_HEAT = Select.NO;

    private static final Select DEFAULT_PARKING = Select.YES;
    private static final Select UPDATED_PARKING = Select.NO;

    private static final Select DEFAULT_TERRACE = Select.YES;
    private static final Select UPDATED_TERRACE = Select.NO;

    private static final Select DEFAULT_BALCONY = Select.YES;
    private static final Select UPDATED_BALCONY = Select.NO;

    private static final Integer DEFAULT_SURFACE_TERRACE = 1;
    private static final Integer UPDATED_SURFACE_TERRACE = 2;

    private static final Integer DEFAULT_SURFACE_SALOON = 1;
    private static final Integer UPDATED_SURFACE_SALOON = 2;

    private static final ApartmentType DEFAULT_TYPE = ApartmentType.HOUSES;
    private static final ApartmentType UPDATED_TYPE = ApartmentType.RURALS;

    private static final Select DEFAULT_OFFICE = Select.YES;
    private static final Select UPDATED_OFFICE = Select.NO;

    private static final Select DEFAULT_KITCHEN_OFFICE = Select.YES;
    private static final Select UPDATED_KITCHEN_OFFICE = Select.NO;

    private static final Select DEFAULT_STORAGE = Select.YES;
    private static final Select UPDATED_STORAGE = Select.NO;

    private static final Select DEFAULT_SHARED_POOL = Select.YES;
    private static final Select UPDATED_SHARED_POOL = Select.NO;

    private static final Select DEFAULT_NEAR_TRANSPORT = Select.YES;
    private static final Select UPDATED_NEAR_TRANSPORT = Select.NO;

    private static final Select DEFAULT_REFORMED = Select.YES;
    private static final Select UPDATED_REFORMED = Select.NO;

    private static final EnergyCertificate DEFAULT_ENERGY_CERTIFICATE = EnergyCertificate.A;
    private static final EnergyCertificate UPDATED_ENERGY_CERTIFICATE = EnergyCertificate.B;

    private static final Select DEFAULT_CERTIFICATE_HABITABILITY = Select.YES;
    private static final Select UPDATED_CERTIFICATE_HABITABILITY = Select.NO;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private ApartmentQueryService apartmentQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApartmentMockMvc;

    private Apartment apartment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApartmentResource apartmentResource = new ApartmentResource(apartmentService, apartmentQueryService);
        this.restApartmentMockMvc = MockMvcBuilders.standaloneSetup(apartmentResource)
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
    public static Apartment createEntity(EntityManager em) {
        Apartment apartment = new Apartment()
            .numberBedrooms(DEFAULT_NUMBER_BEDROOMS)
            .numberBathrooms(DEFAULT_NUMBER_BATHROOMS)
            .m2Edified(DEFAULT_M_2_EDIFIED)
            .m2Usable(DEFAULT_M_2_USABLE)
            .height(DEFAULT_HEIGHT)
            .elevator(DEFAULT_ELEVATOR)
            .ac(DEFAULT_AC)
            .heat(DEFAULT_HEAT)
            .parking(DEFAULT_PARKING)
            .terrace(DEFAULT_TERRACE)
            .balcony(DEFAULT_BALCONY)
            .surfaceTerrace(DEFAULT_SURFACE_TERRACE)
            .surfaceSaloon(DEFAULT_SURFACE_SALOON)
            .type(DEFAULT_TYPE)
            .office(DEFAULT_OFFICE)
            .kitchenOffice(DEFAULT_KITCHEN_OFFICE)
            .storage(DEFAULT_STORAGE)
            .sharedPool(DEFAULT_SHARED_POOL)
            .nearTransport(DEFAULT_NEAR_TRANSPORT)
            .reformed(DEFAULT_REFORMED)
            .energyCertificate(DEFAULT_ENERGY_CERTIFICATE)
            .certificateHabitability(DEFAULT_CERTIFICATE_HABITABILITY);
        return apartment;
    }

    @Before
    public void initTest() {
        apartment = createEntity(em);
    }

    @Test
    @Transactional
    public void createApartment() throws Exception {
        int databaseSizeBeforeCreate = apartmentRepository.findAll().size();

        // Create the Apartment
        restApartmentMockMvc.perform(post("/api/apartments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apartment)))
            .andExpect(status().isCreated());

        // Validate the Apartment in the database
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeCreate + 1);
        Apartment testApartment = apartmentList.get(apartmentList.size() - 1);
        assertThat(testApartment.getNumberBedrooms()).isEqualTo(DEFAULT_NUMBER_BEDROOMS);
        assertThat(testApartment.getNumberBathrooms()).isEqualTo(DEFAULT_NUMBER_BATHROOMS);
        assertThat(testApartment.getm2Edified()).isEqualTo(DEFAULT_M_2_EDIFIED);
        assertThat(testApartment.getm2Usable()).isEqualTo(DEFAULT_M_2_USABLE);
        assertThat(testApartment.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testApartment.getElevator()).isEqualTo(DEFAULT_ELEVATOR);
        assertThat(testApartment.getAc()).isEqualTo(DEFAULT_AC);
        assertThat(testApartment.getHeat()).isEqualTo(DEFAULT_HEAT);
        assertThat(testApartment.getParking()).isEqualTo(DEFAULT_PARKING);
        assertThat(testApartment.getTerrace()).isEqualTo(DEFAULT_TERRACE);
        assertThat(testApartment.getBalcony()).isEqualTo(DEFAULT_BALCONY);
        assertThat(testApartment.getSurfaceTerrace()).isEqualTo(DEFAULT_SURFACE_TERRACE);
        assertThat(testApartment.getSurfaceSaloon()).isEqualTo(DEFAULT_SURFACE_SALOON);
        assertThat(testApartment.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testApartment.getOffice()).isEqualTo(DEFAULT_OFFICE);
        assertThat(testApartment.getKitchenOffice()).isEqualTo(DEFAULT_KITCHEN_OFFICE);
        assertThat(testApartment.getStorage()).isEqualTo(DEFAULT_STORAGE);
        assertThat(testApartment.getSharedPool()).isEqualTo(DEFAULT_SHARED_POOL);
        assertThat(testApartment.getNearTransport()).isEqualTo(DEFAULT_NEAR_TRANSPORT);
        assertThat(testApartment.getReformed()).isEqualTo(DEFAULT_REFORMED);
        assertThat(testApartment.getEnergyCertificate()).isEqualTo(DEFAULT_ENERGY_CERTIFICATE);
        assertThat(testApartment.getCertificateHabitability()).isEqualTo(DEFAULT_CERTIFICATE_HABITABILITY);
    }

    @Test
    @Transactional
    public void createApartmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apartmentRepository.findAll().size();

        // Create the Apartment with an existing ID
        apartment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApartmentMockMvc.perform(post("/api/apartments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apartment)))
            .andExpect(status().isBadRequest());

        // Validate the Apartment in the database
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllApartments() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList
        restApartmentMockMvc.perform(get("/api/apartments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apartment.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberBedrooms").value(hasItem(DEFAULT_NUMBER_BEDROOMS)))
            .andExpect(jsonPath("$.[*].numberBathrooms").value(hasItem(DEFAULT_NUMBER_BATHROOMS)))
            .andExpect(jsonPath("$.[*].m2Edified").value(hasItem(DEFAULT_M_2_EDIFIED)))
            .andExpect(jsonPath("$.[*].m2Usable").value(hasItem(DEFAULT_M_2_USABLE)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].elevator").value(hasItem(DEFAULT_ELEVATOR.toString())))
            .andExpect(jsonPath("$.[*].ac").value(hasItem(DEFAULT_AC.toString())))
            .andExpect(jsonPath("$.[*].heat").value(hasItem(DEFAULT_HEAT.toString())))
            .andExpect(jsonPath("$.[*].parking").value(hasItem(DEFAULT_PARKING.toString())))
            .andExpect(jsonPath("$.[*].terrace").value(hasItem(DEFAULT_TERRACE.toString())))
            .andExpect(jsonPath("$.[*].balcony").value(hasItem(DEFAULT_BALCONY.toString())))
            .andExpect(jsonPath("$.[*].surfaceTerrace").value(hasItem(DEFAULT_SURFACE_TERRACE)))
            .andExpect(jsonPath("$.[*].surfaceSaloon").value(hasItem(DEFAULT_SURFACE_SALOON)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].office").value(hasItem(DEFAULT_OFFICE.toString())))
            .andExpect(jsonPath("$.[*].kitchenOffice").value(hasItem(DEFAULT_KITCHEN_OFFICE.toString())))
            .andExpect(jsonPath("$.[*].storage").value(hasItem(DEFAULT_STORAGE.toString())))
            .andExpect(jsonPath("$.[*].sharedPool").value(hasItem(DEFAULT_SHARED_POOL.toString())))
            .andExpect(jsonPath("$.[*].nearTransport").value(hasItem(DEFAULT_NEAR_TRANSPORT.toString())))
            .andExpect(jsonPath("$.[*].reformed").value(hasItem(DEFAULT_REFORMED.toString())))
            .andExpect(jsonPath("$.[*].energyCertificate").value(hasItem(DEFAULT_ENERGY_CERTIFICATE.toString())))
            .andExpect(jsonPath("$.[*].certificateHabitability").value(hasItem(DEFAULT_CERTIFICATE_HABITABILITY.toString())));
    }

    @Test
    @Transactional
    public void getApartment() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get the apartment
        restApartmentMockMvc.perform(get("/api/apartments/{id}", apartment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apartment.getId().intValue()))
            .andExpect(jsonPath("$.numberBedrooms").value(DEFAULT_NUMBER_BEDROOMS))
            .andExpect(jsonPath("$.numberBathrooms").value(DEFAULT_NUMBER_BATHROOMS))
            .andExpect(jsonPath("$.m2Edified").value(DEFAULT_M_2_EDIFIED))
            .andExpect(jsonPath("$.m2Usable").value(DEFAULT_M_2_USABLE))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.elevator").value(DEFAULT_ELEVATOR.toString()))
            .andExpect(jsonPath("$.ac").value(DEFAULT_AC.toString()))
            .andExpect(jsonPath("$.heat").value(DEFAULT_HEAT.toString()))
            .andExpect(jsonPath("$.parking").value(DEFAULT_PARKING.toString()))
            .andExpect(jsonPath("$.terrace").value(DEFAULT_TERRACE.toString()))
            .andExpect(jsonPath("$.balcony").value(DEFAULT_BALCONY.toString()))
            .andExpect(jsonPath("$.surfaceTerrace").value(DEFAULT_SURFACE_TERRACE))
            .andExpect(jsonPath("$.surfaceSaloon").value(DEFAULT_SURFACE_SALOON))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.office").value(DEFAULT_OFFICE.toString()))
            .andExpect(jsonPath("$.kitchenOffice").value(DEFAULT_KITCHEN_OFFICE.toString()))
            .andExpect(jsonPath("$.storage").value(DEFAULT_STORAGE.toString()))
            .andExpect(jsonPath("$.sharedPool").value(DEFAULT_SHARED_POOL.toString()))
            .andExpect(jsonPath("$.nearTransport").value(DEFAULT_NEAR_TRANSPORT.toString()))
            .andExpect(jsonPath("$.reformed").value(DEFAULT_REFORMED.toString()))
            .andExpect(jsonPath("$.energyCertificate").value(DEFAULT_ENERGY_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.certificateHabitability").value(DEFAULT_CERTIFICATE_HABITABILITY.toString()));
    }

    @Test
    @Transactional
    public void getAllApartmentsByNumberBedroomsIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where numberBedrooms equals to DEFAULT_NUMBER_BEDROOMS
        defaultApartmentShouldBeFound("numberBedrooms.equals=" + DEFAULT_NUMBER_BEDROOMS);

        // Get all the apartmentList where numberBedrooms equals to UPDATED_NUMBER_BEDROOMS
        defaultApartmentShouldNotBeFound("numberBedrooms.equals=" + UPDATED_NUMBER_BEDROOMS);
    }

    @Test
    @Transactional
    public void getAllApartmentsByNumberBedroomsIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where numberBedrooms in DEFAULT_NUMBER_BEDROOMS or UPDATED_NUMBER_BEDROOMS
        defaultApartmentShouldBeFound("numberBedrooms.in=" + DEFAULT_NUMBER_BEDROOMS + "," + UPDATED_NUMBER_BEDROOMS);

        // Get all the apartmentList where numberBedrooms equals to UPDATED_NUMBER_BEDROOMS
        defaultApartmentShouldNotBeFound("numberBedrooms.in=" + UPDATED_NUMBER_BEDROOMS);
    }

    @Test
    @Transactional
    public void getAllApartmentsByNumberBedroomsIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where numberBedrooms is not null
        defaultApartmentShouldBeFound("numberBedrooms.specified=true");

        // Get all the apartmentList where numberBedrooms is null
        defaultApartmentShouldNotBeFound("numberBedrooms.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsByNumberBedroomsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where numberBedrooms greater than or equals to DEFAULT_NUMBER_BEDROOMS
        defaultApartmentShouldBeFound("numberBedrooms.greaterOrEqualThan=" + DEFAULT_NUMBER_BEDROOMS);

        // Get all the apartmentList where numberBedrooms greater than or equals to UPDATED_NUMBER_BEDROOMS
        defaultApartmentShouldNotBeFound("numberBedrooms.greaterOrEqualThan=" + UPDATED_NUMBER_BEDROOMS);
    }

    @Test
    @Transactional
    public void getAllApartmentsByNumberBedroomsIsLessThanSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where numberBedrooms less than or equals to DEFAULT_NUMBER_BEDROOMS
        defaultApartmentShouldNotBeFound("numberBedrooms.lessThan=" + DEFAULT_NUMBER_BEDROOMS);

        // Get all the apartmentList where numberBedrooms less than or equals to UPDATED_NUMBER_BEDROOMS
        defaultApartmentShouldBeFound("numberBedrooms.lessThan=" + UPDATED_NUMBER_BEDROOMS);
    }


    @Test
    @Transactional
    public void getAllApartmentsByNumberBathroomsIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where numberBathrooms equals to DEFAULT_NUMBER_BATHROOMS
        defaultApartmentShouldBeFound("numberBathrooms.equals=" + DEFAULT_NUMBER_BATHROOMS);

        // Get all the apartmentList where numberBathrooms equals to UPDATED_NUMBER_BATHROOMS
        defaultApartmentShouldNotBeFound("numberBathrooms.equals=" + UPDATED_NUMBER_BATHROOMS);
    }

    @Test
    @Transactional
    public void getAllApartmentsByNumberBathroomsIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where numberBathrooms in DEFAULT_NUMBER_BATHROOMS or UPDATED_NUMBER_BATHROOMS
        defaultApartmentShouldBeFound("numberBathrooms.in=" + DEFAULT_NUMBER_BATHROOMS + "," + UPDATED_NUMBER_BATHROOMS);

        // Get all the apartmentList where numberBathrooms equals to UPDATED_NUMBER_BATHROOMS
        defaultApartmentShouldNotBeFound("numberBathrooms.in=" + UPDATED_NUMBER_BATHROOMS);
    }

    @Test
    @Transactional
    public void getAllApartmentsByNumberBathroomsIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where numberBathrooms is not null
        defaultApartmentShouldBeFound("numberBathrooms.specified=true");

        // Get all the apartmentList where numberBathrooms is null
        defaultApartmentShouldNotBeFound("numberBathrooms.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsByNumberBathroomsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where numberBathrooms greater than or equals to DEFAULT_NUMBER_BATHROOMS
        defaultApartmentShouldBeFound("numberBathrooms.greaterOrEqualThan=" + DEFAULT_NUMBER_BATHROOMS);

        // Get all the apartmentList where numberBathrooms greater than or equals to UPDATED_NUMBER_BATHROOMS
        defaultApartmentShouldNotBeFound("numberBathrooms.greaterOrEqualThan=" + UPDATED_NUMBER_BATHROOMS);
    }

    @Test
    @Transactional
    public void getAllApartmentsByNumberBathroomsIsLessThanSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where numberBathrooms less than or equals to DEFAULT_NUMBER_BATHROOMS
        defaultApartmentShouldNotBeFound("numberBathrooms.lessThan=" + DEFAULT_NUMBER_BATHROOMS);

        // Get all the apartmentList where numberBathrooms less than or equals to UPDATED_NUMBER_BATHROOMS
        defaultApartmentShouldBeFound("numberBathrooms.lessThan=" + UPDATED_NUMBER_BATHROOMS);
    }


    @Test
    @Transactional
    public void getAllApartmentsBym2EdifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where m2Edified equals to DEFAULT_M_2_EDIFIED
        defaultApartmentShouldBeFound("m2Edified.equals=" + DEFAULT_M_2_EDIFIED);

        // Get all the apartmentList where m2Edified equals to UPDATED_M_2_EDIFIED
        defaultApartmentShouldNotBeFound("m2Edified.equals=" + UPDATED_M_2_EDIFIED);
    }

    @Test
    @Transactional
    public void getAllApartmentsBym2EdifiedIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where m2Edified in DEFAULT_M_2_EDIFIED or UPDATED_M_2_EDIFIED
        defaultApartmentShouldBeFound("m2Edified.in=" + DEFAULT_M_2_EDIFIED + "," + UPDATED_M_2_EDIFIED);

        // Get all the apartmentList where m2Edified equals to UPDATED_M_2_EDIFIED
        defaultApartmentShouldNotBeFound("m2Edified.in=" + UPDATED_M_2_EDIFIED);
    }

    @Test
    @Transactional
    public void getAllApartmentsBym2EdifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where m2Edified is not null
        defaultApartmentShouldBeFound("m2Edified.specified=true");

        // Get all the apartmentList where m2Edified is null
        defaultApartmentShouldNotBeFound("m2Edified.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsBym2EdifiedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where m2Edified greater than or equals to DEFAULT_M_2_EDIFIED
        defaultApartmentShouldBeFound("m2Edified.greaterOrEqualThan=" + DEFAULT_M_2_EDIFIED);

        // Get all the apartmentList where m2Edified greater than or equals to UPDATED_M_2_EDIFIED
        defaultApartmentShouldNotBeFound("m2Edified.greaterOrEqualThan=" + UPDATED_M_2_EDIFIED);
    }

    @Test
    @Transactional
    public void getAllApartmentsBym2EdifiedIsLessThanSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where m2Edified less than or equals to DEFAULT_M_2_EDIFIED
        defaultApartmentShouldNotBeFound("m2Edified.lessThan=" + DEFAULT_M_2_EDIFIED);

        // Get all the apartmentList where m2Edified less than or equals to UPDATED_M_2_EDIFIED
        defaultApartmentShouldBeFound("m2Edified.lessThan=" + UPDATED_M_2_EDIFIED);
    }


    @Test
    @Transactional
    public void getAllApartmentsBym2UsableIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where m2Usable equals to DEFAULT_M_2_USABLE
        defaultApartmentShouldBeFound("m2Usable.equals=" + DEFAULT_M_2_USABLE);

        // Get all the apartmentList where m2Usable equals to UPDATED_M_2_USABLE
        defaultApartmentShouldNotBeFound("m2Usable.equals=" + UPDATED_M_2_USABLE);
    }

    @Test
    @Transactional
    public void getAllApartmentsBym2UsableIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where m2Usable in DEFAULT_M_2_USABLE or UPDATED_M_2_USABLE
        defaultApartmentShouldBeFound("m2Usable.in=" + DEFAULT_M_2_USABLE + "," + UPDATED_M_2_USABLE);

        // Get all the apartmentList where m2Usable equals to UPDATED_M_2_USABLE
        defaultApartmentShouldNotBeFound("m2Usable.in=" + UPDATED_M_2_USABLE);
    }

    @Test
    @Transactional
    public void getAllApartmentsBym2UsableIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where m2Usable is not null
        defaultApartmentShouldBeFound("m2Usable.specified=true");

        // Get all the apartmentList where m2Usable is null
        defaultApartmentShouldNotBeFound("m2Usable.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsBym2UsableIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where m2Usable greater than or equals to DEFAULT_M_2_USABLE
        defaultApartmentShouldBeFound("m2Usable.greaterOrEqualThan=" + DEFAULT_M_2_USABLE);

        // Get all the apartmentList where m2Usable greater than or equals to UPDATED_M_2_USABLE
        defaultApartmentShouldNotBeFound("m2Usable.greaterOrEqualThan=" + UPDATED_M_2_USABLE);
    }

    @Test
    @Transactional
    public void getAllApartmentsBym2UsableIsLessThanSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where m2Usable less than or equals to DEFAULT_M_2_USABLE
        defaultApartmentShouldNotBeFound("m2Usable.lessThan=" + DEFAULT_M_2_USABLE);

        // Get all the apartmentList where m2Usable less than or equals to UPDATED_M_2_USABLE
        defaultApartmentShouldBeFound("m2Usable.lessThan=" + UPDATED_M_2_USABLE);
    }


    @Test
    @Transactional
    public void getAllApartmentsByHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where height equals to DEFAULT_HEIGHT
        defaultApartmentShouldBeFound("height.equals=" + DEFAULT_HEIGHT);

        // Get all the apartmentList where height equals to UPDATED_HEIGHT
        defaultApartmentShouldNotBeFound("height.equals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllApartmentsByHeightIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where height in DEFAULT_HEIGHT or UPDATED_HEIGHT
        defaultApartmentShouldBeFound("height.in=" + DEFAULT_HEIGHT + "," + UPDATED_HEIGHT);

        // Get all the apartmentList where height equals to UPDATED_HEIGHT
        defaultApartmentShouldNotBeFound("height.in=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllApartmentsByHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where height is not null
        defaultApartmentShouldBeFound("height.specified=true");

        // Get all the apartmentList where height is null
        defaultApartmentShouldNotBeFound("height.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsByHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where height greater than or equals to DEFAULT_HEIGHT
        defaultApartmentShouldBeFound("height.greaterOrEqualThan=" + DEFAULT_HEIGHT);

        // Get all the apartmentList where height greater than or equals to UPDATED_HEIGHT
        defaultApartmentShouldNotBeFound("height.greaterOrEqualThan=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllApartmentsByHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where height less than or equals to DEFAULT_HEIGHT
        defaultApartmentShouldNotBeFound("height.lessThan=" + DEFAULT_HEIGHT);

        // Get all the apartmentList where height less than or equals to UPDATED_HEIGHT
        defaultApartmentShouldBeFound("height.lessThan=" + UPDATED_HEIGHT);
    }


    @Test
    @Transactional
    public void getAllApartmentsByElevatorIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where elevator equals to DEFAULT_ELEVATOR
        defaultApartmentShouldBeFound("elevator.equals=" + DEFAULT_ELEVATOR);

        // Get all the apartmentList where elevator equals to UPDATED_ELEVATOR
        defaultApartmentShouldNotBeFound("elevator.equals=" + UPDATED_ELEVATOR);
    }

    @Test
    @Transactional
    public void getAllApartmentsByElevatorIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where elevator in DEFAULT_ELEVATOR or UPDATED_ELEVATOR
        defaultApartmentShouldBeFound("elevator.in=" + DEFAULT_ELEVATOR + "," + UPDATED_ELEVATOR);

        // Get all the apartmentList where elevator equals to UPDATED_ELEVATOR
        defaultApartmentShouldNotBeFound("elevator.in=" + UPDATED_ELEVATOR);
    }

    @Test
    @Transactional
    public void getAllApartmentsByElevatorIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where elevator is not null
        defaultApartmentShouldBeFound("elevator.specified=true");

        // Get all the apartmentList where elevator is null
        defaultApartmentShouldNotBeFound("elevator.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsByAcIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where ac equals to DEFAULT_AC
        defaultApartmentShouldBeFound("ac.equals=" + DEFAULT_AC);

        // Get all the apartmentList where ac equals to UPDATED_AC
        defaultApartmentShouldNotBeFound("ac.equals=" + UPDATED_AC);
    }

    @Test
    @Transactional
    public void getAllApartmentsByAcIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where ac in DEFAULT_AC or UPDATED_AC
        defaultApartmentShouldBeFound("ac.in=" + DEFAULT_AC + "," + UPDATED_AC);

        // Get all the apartmentList where ac equals to UPDATED_AC
        defaultApartmentShouldNotBeFound("ac.in=" + UPDATED_AC);
    }

    @Test
    @Transactional
    public void getAllApartmentsByAcIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where ac is not null
        defaultApartmentShouldBeFound("ac.specified=true");

        // Get all the apartmentList where ac is null
        defaultApartmentShouldNotBeFound("ac.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsByHeatIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where heat equals to DEFAULT_HEAT
        defaultApartmentShouldBeFound("heat.equals=" + DEFAULT_HEAT);

        // Get all the apartmentList where heat equals to UPDATED_HEAT
        defaultApartmentShouldNotBeFound("heat.equals=" + UPDATED_HEAT);
    }

    @Test
    @Transactional
    public void getAllApartmentsByHeatIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where heat in DEFAULT_HEAT or UPDATED_HEAT
        defaultApartmentShouldBeFound("heat.in=" + DEFAULT_HEAT + "," + UPDATED_HEAT);

        // Get all the apartmentList where heat equals to UPDATED_HEAT
        defaultApartmentShouldNotBeFound("heat.in=" + UPDATED_HEAT);
    }

    @Test
    @Transactional
    public void getAllApartmentsByHeatIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where heat is not null
        defaultApartmentShouldBeFound("heat.specified=true");

        // Get all the apartmentList where heat is null
        defaultApartmentShouldNotBeFound("heat.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsByParkingIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where parking equals to DEFAULT_PARKING
        defaultApartmentShouldBeFound("parking.equals=" + DEFAULT_PARKING);

        // Get all the apartmentList where parking equals to UPDATED_PARKING
        defaultApartmentShouldNotBeFound("parking.equals=" + UPDATED_PARKING);
    }

    @Test
    @Transactional
    public void getAllApartmentsByParkingIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where parking in DEFAULT_PARKING or UPDATED_PARKING
        defaultApartmentShouldBeFound("parking.in=" + DEFAULT_PARKING + "," + UPDATED_PARKING);

        // Get all the apartmentList where parking equals to UPDATED_PARKING
        defaultApartmentShouldNotBeFound("parking.in=" + UPDATED_PARKING);
    }

    @Test
    @Transactional
    public void getAllApartmentsByParkingIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where parking is not null
        defaultApartmentShouldBeFound("parking.specified=true");

        // Get all the apartmentList where parking is null
        defaultApartmentShouldNotBeFound("parking.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsByTerraceIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where terrace equals to DEFAULT_TERRACE
        defaultApartmentShouldBeFound("terrace.equals=" + DEFAULT_TERRACE);

        // Get all the apartmentList where terrace equals to UPDATED_TERRACE
        defaultApartmentShouldNotBeFound("terrace.equals=" + UPDATED_TERRACE);
    }

    @Test
    @Transactional
    public void getAllApartmentsByTerraceIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where terrace in DEFAULT_TERRACE or UPDATED_TERRACE
        defaultApartmentShouldBeFound("terrace.in=" + DEFAULT_TERRACE + "," + UPDATED_TERRACE);

        // Get all the apartmentList where terrace equals to UPDATED_TERRACE
        defaultApartmentShouldNotBeFound("terrace.in=" + UPDATED_TERRACE);
    }

    @Test
    @Transactional
    public void getAllApartmentsByTerraceIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where terrace is not null
        defaultApartmentShouldBeFound("terrace.specified=true");

        // Get all the apartmentList where terrace is null
        defaultApartmentShouldNotBeFound("terrace.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsByBalconyIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where balcony equals to DEFAULT_BALCONY
        defaultApartmentShouldBeFound("balcony.equals=" + DEFAULT_BALCONY);

        // Get all the apartmentList where balcony equals to UPDATED_BALCONY
        defaultApartmentShouldNotBeFound("balcony.equals=" + UPDATED_BALCONY);
    }

    @Test
    @Transactional
    public void getAllApartmentsByBalconyIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where balcony in DEFAULT_BALCONY or UPDATED_BALCONY
        defaultApartmentShouldBeFound("balcony.in=" + DEFAULT_BALCONY + "," + UPDATED_BALCONY);

        // Get all the apartmentList where balcony equals to UPDATED_BALCONY
        defaultApartmentShouldNotBeFound("balcony.in=" + UPDATED_BALCONY);
    }

    @Test
    @Transactional
    public void getAllApartmentsByBalconyIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where balcony is not null
        defaultApartmentShouldBeFound("balcony.specified=true");

        // Get all the apartmentList where balcony is null
        defaultApartmentShouldNotBeFound("balcony.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsBySurfaceTerraceIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where surfaceTerrace equals to DEFAULT_SURFACE_TERRACE
        defaultApartmentShouldBeFound("surfaceTerrace.equals=" + DEFAULT_SURFACE_TERRACE);

        // Get all the apartmentList where surfaceTerrace equals to UPDATED_SURFACE_TERRACE
        defaultApartmentShouldNotBeFound("surfaceTerrace.equals=" + UPDATED_SURFACE_TERRACE);
    }

    @Test
    @Transactional
    public void getAllApartmentsBySurfaceTerraceIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where surfaceTerrace in DEFAULT_SURFACE_TERRACE or UPDATED_SURFACE_TERRACE
        defaultApartmentShouldBeFound("surfaceTerrace.in=" + DEFAULT_SURFACE_TERRACE + "," + UPDATED_SURFACE_TERRACE);

        // Get all the apartmentList where surfaceTerrace equals to UPDATED_SURFACE_TERRACE
        defaultApartmentShouldNotBeFound("surfaceTerrace.in=" + UPDATED_SURFACE_TERRACE);
    }

    @Test
    @Transactional
    public void getAllApartmentsBySurfaceTerraceIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where surfaceTerrace is not null
        defaultApartmentShouldBeFound("surfaceTerrace.specified=true");

        // Get all the apartmentList where surfaceTerrace is null
        defaultApartmentShouldNotBeFound("surfaceTerrace.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsBySurfaceTerraceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where surfaceTerrace greater than or equals to DEFAULT_SURFACE_TERRACE
        defaultApartmentShouldBeFound("surfaceTerrace.greaterOrEqualThan=" + DEFAULT_SURFACE_TERRACE);

        // Get all the apartmentList where surfaceTerrace greater than or equals to UPDATED_SURFACE_TERRACE
        defaultApartmentShouldNotBeFound("surfaceTerrace.greaterOrEqualThan=" + UPDATED_SURFACE_TERRACE);
    }

    @Test
    @Transactional
    public void getAllApartmentsBySurfaceTerraceIsLessThanSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where surfaceTerrace less than or equals to DEFAULT_SURFACE_TERRACE
        defaultApartmentShouldNotBeFound("surfaceTerrace.lessThan=" + DEFAULT_SURFACE_TERRACE);

        // Get all the apartmentList where surfaceTerrace less than or equals to UPDATED_SURFACE_TERRACE
        defaultApartmentShouldBeFound("surfaceTerrace.lessThan=" + UPDATED_SURFACE_TERRACE);
    }


    @Test
    @Transactional
    public void getAllApartmentsBySurfaceSaloonIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where surfaceSaloon equals to DEFAULT_SURFACE_SALOON
        defaultApartmentShouldBeFound("surfaceSaloon.equals=" + DEFAULT_SURFACE_SALOON);

        // Get all the apartmentList where surfaceSaloon equals to UPDATED_SURFACE_SALOON
        defaultApartmentShouldNotBeFound("surfaceSaloon.equals=" + UPDATED_SURFACE_SALOON);
    }

    @Test
    @Transactional
    public void getAllApartmentsBySurfaceSaloonIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where surfaceSaloon in DEFAULT_SURFACE_SALOON or UPDATED_SURFACE_SALOON
        defaultApartmentShouldBeFound("surfaceSaloon.in=" + DEFAULT_SURFACE_SALOON + "," + UPDATED_SURFACE_SALOON);

        // Get all the apartmentList where surfaceSaloon equals to UPDATED_SURFACE_SALOON
        defaultApartmentShouldNotBeFound("surfaceSaloon.in=" + UPDATED_SURFACE_SALOON);
    }

    @Test
    @Transactional
    public void getAllApartmentsBySurfaceSaloonIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where surfaceSaloon is not null
        defaultApartmentShouldBeFound("surfaceSaloon.specified=true");

        // Get all the apartmentList where surfaceSaloon is null
        defaultApartmentShouldNotBeFound("surfaceSaloon.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsBySurfaceSaloonIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where surfaceSaloon greater than or equals to DEFAULT_SURFACE_SALOON
        defaultApartmentShouldBeFound("surfaceSaloon.greaterOrEqualThan=" + DEFAULT_SURFACE_SALOON);

        // Get all the apartmentList where surfaceSaloon greater than or equals to UPDATED_SURFACE_SALOON
        defaultApartmentShouldNotBeFound("surfaceSaloon.greaterOrEqualThan=" + UPDATED_SURFACE_SALOON);
    }

    @Test
    @Transactional
    public void getAllApartmentsBySurfaceSaloonIsLessThanSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where surfaceSaloon less than or equals to DEFAULT_SURFACE_SALOON
        defaultApartmentShouldNotBeFound("surfaceSaloon.lessThan=" + DEFAULT_SURFACE_SALOON);

        // Get all the apartmentList where surfaceSaloon less than or equals to UPDATED_SURFACE_SALOON
        defaultApartmentShouldBeFound("surfaceSaloon.lessThan=" + UPDATED_SURFACE_SALOON);
    }


    @Test
    @Transactional
    public void getAllApartmentsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where type equals to DEFAULT_TYPE
        defaultApartmentShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the apartmentList where type equals to UPDATED_TYPE
        defaultApartmentShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllApartmentsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultApartmentShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the apartmentList where type equals to UPDATED_TYPE
        defaultApartmentShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllApartmentsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where type is not null
        defaultApartmentShouldBeFound("type.specified=true");

        // Get all the apartmentList where type is null
        defaultApartmentShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsByOfficeIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where office equals to DEFAULT_OFFICE
        defaultApartmentShouldBeFound("office.equals=" + DEFAULT_OFFICE);

        // Get all the apartmentList where office equals to UPDATED_OFFICE
        defaultApartmentShouldNotBeFound("office.equals=" + UPDATED_OFFICE);
    }

    @Test
    @Transactional
    public void getAllApartmentsByOfficeIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where office in DEFAULT_OFFICE or UPDATED_OFFICE
        defaultApartmentShouldBeFound("office.in=" + DEFAULT_OFFICE + "," + UPDATED_OFFICE);

        // Get all the apartmentList where office equals to UPDATED_OFFICE
        defaultApartmentShouldNotBeFound("office.in=" + UPDATED_OFFICE);
    }

    @Test
    @Transactional
    public void getAllApartmentsByOfficeIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where office is not null
        defaultApartmentShouldBeFound("office.specified=true");

        // Get all the apartmentList where office is null
        defaultApartmentShouldNotBeFound("office.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsByKitchenOfficeIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where kitchenOffice equals to DEFAULT_KITCHEN_OFFICE
        defaultApartmentShouldBeFound("kitchenOffice.equals=" + DEFAULT_KITCHEN_OFFICE);

        // Get all the apartmentList where kitchenOffice equals to UPDATED_KITCHEN_OFFICE
        defaultApartmentShouldNotBeFound("kitchenOffice.equals=" + UPDATED_KITCHEN_OFFICE);
    }

    @Test
    @Transactional
    public void getAllApartmentsByKitchenOfficeIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where kitchenOffice in DEFAULT_KITCHEN_OFFICE or UPDATED_KITCHEN_OFFICE
        defaultApartmentShouldBeFound("kitchenOffice.in=" + DEFAULT_KITCHEN_OFFICE + "," + UPDATED_KITCHEN_OFFICE);

        // Get all the apartmentList where kitchenOffice equals to UPDATED_KITCHEN_OFFICE
        defaultApartmentShouldNotBeFound("kitchenOffice.in=" + UPDATED_KITCHEN_OFFICE);
    }

    @Test
    @Transactional
    public void getAllApartmentsByKitchenOfficeIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where kitchenOffice is not null
        defaultApartmentShouldBeFound("kitchenOffice.specified=true");

        // Get all the apartmentList where kitchenOffice is null
        defaultApartmentShouldNotBeFound("kitchenOffice.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsByStorageIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where storage equals to DEFAULT_STORAGE
        defaultApartmentShouldBeFound("storage.equals=" + DEFAULT_STORAGE);

        // Get all the apartmentList where storage equals to UPDATED_STORAGE
        defaultApartmentShouldNotBeFound("storage.equals=" + UPDATED_STORAGE);
    }

    @Test
    @Transactional
    public void getAllApartmentsByStorageIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where storage in DEFAULT_STORAGE or UPDATED_STORAGE
        defaultApartmentShouldBeFound("storage.in=" + DEFAULT_STORAGE + "," + UPDATED_STORAGE);

        // Get all the apartmentList where storage equals to UPDATED_STORAGE
        defaultApartmentShouldNotBeFound("storage.in=" + UPDATED_STORAGE);
    }

    @Test
    @Transactional
    public void getAllApartmentsByStorageIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where storage is not null
        defaultApartmentShouldBeFound("storage.specified=true");

        // Get all the apartmentList where storage is null
        defaultApartmentShouldNotBeFound("storage.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsBySharedPoolIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where sharedPool equals to DEFAULT_SHARED_POOL
        defaultApartmentShouldBeFound("sharedPool.equals=" + DEFAULT_SHARED_POOL);

        // Get all the apartmentList where sharedPool equals to UPDATED_SHARED_POOL
        defaultApartmentShouldNotBeFound("sharedPool.equals=" + UPDATED_SHARED_POOL);
    }

    @Test
    @Transactional
    public void getAllApartmentsBySharedPoolIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where sharedPool in DEFAULT_SHARED_POOL or UPDATED_SHARED_POOL
        defaultApartmentShouldBeFound("sharedPool.in=" + DEFAULT_SHARED_POOL + "," + UPDATED_SHARED_POOL);

        // Get all the apartmentList where sharedPool equals to UPDATED_SHARED_POOL
        defaultApartmentShouldNotBeFound("sharedPool.in=" + UPDATED_SHARED_POOL);
    }

    @Test
    @Transactional
    public void getAllApartmentsBySharedPoolIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where sharedPool is not null
        defaultApartmentShouldBeFound("sharedPool.specified=true");

        // Get all the apartmentList where sharedPool is null
        defaultApartmentShouldNotBeFound("sharedPool.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsByNearTransportIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where nearTransport equals to DEFAULT_NEAR_TRANSPORT
        defaultApartmentShouldBeFound("nearTransport.equals=" + DEFAULT_NEAR_TRANSPORT);

        // Get all the apartmentList where nearTransport equals to UPDATED_NEAR_TRANSPORT
        defaultApartmentShouldNotBeFound("nearTransport.equals=" + UPDATED_NEAR_TRANSPORT);
    }

    @Test
    @Transactional
    public void getAllApartmentsByNearTransportIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where nearTransport in DEFAULT_NEAR_TRANSPORT or UPDATED_NEAR_TRANSPORT
        defaultApartmentShouldBeFound("nearTransport.in=" + DEFAULT_NEAR_TRANSPORT + "," + UPDATED_NEAR_TRANSPORT);

        // Get all the apartmentList where nearTransport equals to UPDATED_NEAR_TRANSPORT
        defaultApartmentShouldNotBeFound("nearTransport.in=" + UPDATED_NEAR_TRANSPORT);
    }

    @Test
    @Transactional
    public void getAllApartmentsByNearTransportIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where nearTransport is not null
        defaultApartmentShouldBeFound("nearTransport.specified=true");

        // Get all the apartmentList where nearTransport is null
        defaultApartmentShouldNotBeFound("nearTransport.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsByReformedIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where reformed equals to DEFAULT_REFORMED
        defaultApartmentShouldBeFound("reformed.equals=" + DEFAULT_REFORMED);

        // Get all the apartmentList where reformed equals to UPDATED_REFORMED
        defaultApartmentShouldNotBeFound("reformed.equals=" + UPDATED_REFORMED);
    }

    @Test
    @Transactional
    public void getAllApartmentsByReformedIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where reformed in DEFAULT_REFORMED or UPDATED_REFORMED
        defaultApartmentShouldBeFound("reformed.in=" + DEFAULT_REFORMED + "," + UPDATED_REFORMED);

        // Get all the apartmentList where reformed equals to UPDATED_REFORMED
        defaultApartmentShouldNotBeFound("reformed.in=" + UPDATED_REFORMED);
    }

    @Test
    @Transactional
    public void getAllApartmentsByReformedIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where reformed is not null
        defaultApartmentShouldBeFound("reformed.specified=true");

        // Get all the apartmentList where reformed is null
        defaultApartmentShouldNotBeFound("reformed.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsByEnergyCertificateIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where energyCertificate equals to DEFAULT_ENERGY_CERTIFICATE
        defaultApartmentShouldBeFound("energyCertificate.equals=" + DEFAULT_ENERGY_CERTIFICATE);

        // Get all the apartmentList where energyCertificate equals to UPDATED_ENERGY_CERTIFICATE
        defaultApartmentShouldNotBeFound("energyCertificate.equals=" + UPDATED_ENERGY_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllApartmentsByEnergyCertificateIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where energyCertificate in DEFAULT_ENERGY_CERTIFICATE or UPDATED_ENERGY_CERTIFICATE
        defaultApartmentShouldBeFound("energyCertificate.in=" + DEFAULT_ENERGY_CERTIFICATE + "," + UPDATED_ENERGY_CERTIFICATE);

        // Get all the apartmentList where energyCertificate equals to UPDATED_ENERGY_CERTIFICATE
        defaultApartmentShouldNotBeFound("energyCertificate.in=" + UPDATED_ENERGY_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllApartmentsByEnergyCertificateIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where energyCertificate is not null
        defaultApartmentShouldBeFound("energyCertificate.specified=true");

        // Get all the apartmentList where energyCertificate is null
        defaultApartmentShouldNotBeFound("energyCertificate.specified=false");
    }

    @Test
    @Transactional
    public void getAllApartmentsByCertificateHabitabilityIsEqualToSomething() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where certificateHabitability equals to DEFAULT_CERTIFICATE_HABITABILITY
        defaultApartmentShouldBeFound("certificateHabitability.equals=" + DEFAULT_CERTIFICATE_HABITABILITY);

        // Get all the apartmentList where certificateHabitability equals to UPDATED_CERTIFICATE_HABITABILITY
        defaultApartmentShouldNotBeFound("certificateHabitability.equals=" + UPDATED_CERTIFICATE_HABITABILITY);
    }

    @Test
    @Transactional
    public void getAllApartmentsByCertificateHabitabilityIsInShouldWork() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where certificateHabitability in DEFAULT_CERTIFICATE_HABITABILITY or UPDATED_CERTIFICATE_HABITABILITY
        defaultApartmentShouldBeFound("certificateHabitability.in=" + DEFAULT_CERTIFICATE_HABITABILITY + "," + UPDATED_CERTIFICATE_HABITABILITY);

        // Get all the apartmentList where certificateHabitability equals to UPDATED_CERTIFICATE_HABITABILITY
        defaultApartmentShouldNotBeFound("certificateHabitability.in=" + UPDATED_CERTIFICATE_HABITABILITY);
    }

    @Test
    @Transactional
    public void getAllApartmentsByCertificateHabitabilityIsNullOrNotNull() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartmentList where certificateHabitability is not null
        defaultApartmentShouldBeFound("certificateHabitability.specified=true");

        // Get all the apartmentList where certificateHabitability is null
        defaultApartmentShouldNotBeFound("certificateHabitability.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultApartmentShouldBeFound(String filter) throws Exception {
        restApartmentMockMvc.perform(get("/api/apartments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apartment.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberBedrooms").value(hasItem(DEFAULT_NUMBER_BEDROOMS)))
            .andExpect(jsonPath("$.[*].numberBathrooms").value(hasItem(DEFAULT_NUMBER_BATHROOMS)))
            .andExpect(jsonPath("$.[*].m2Edified").value(hasItem(DEFAULT_M_2_EDIFIED)))
            .andExpect(jsonPath("$.[*].m2Usable").value(hasItem(DEFAULT_M_2_USABLE)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].elevator").value(hasItem(DEFAULT_ELEVATOR.toString())))
            .andExpect(jsonPath("$.[*].ac").value(hasItem(DEFAULT_AC.toString())))
            .andExpect(jsonPath("$.[*].heat").value(hasItem(DEFAULT_HEAT.toString())))
            .andExpect(jsonPath("$.[*].parking").value(hasItem(DEFAULT_PARKING.toString())))
            .andExpect(jsonPath("$.[*].terrace").value(hasItem(DEFAULT_TERRACE.toString())))
            .andExpect(jsonPath("$.[*].balcony").value(hasItem(DEFAULT_BALCONY.toString())))
            .andExpect(jsonPath("$.[*].surfaceTerrace").value(hasItem(DEFAULT_SURFACE_TERRACE)))
            .andExpect(jsonPath("$.[*].surfaceSaloon").value(hasItem(DEFAULT_SURFACE_SALOON)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].office").value(hasItem(DEFAULT_OFFICE.toString())))
            .andExpect(jsonPath("$.[*].kitchenOffice").value(hasItem(DEFAULT_KITCHEN_OFFICE.toString())))
            .andExpect(jsonPath("$.[*].storage").value(hasItem(DEFAULT_STORAGE.toString())))
            .andExpect(jsonPath("$.[*].sharedPool").value(hasItem(DEFAULT_SHARED_POOL.toString())))
            .andExpect(jsonPath("$.[*].nearTransport").value(hasItem(DEFAULT_NEAR_TRANSPORT.toString())))
            .andExpect(jsonPath("$.[*].reformed").value(hasItem(DEFAULT_REFORMED.toString())))
            .andExpect(jsonPath("$.[*].energyCertificate").value(hasItem(DEFAULT_ENERGY_CERTIFICATE.toString())))
            .andExpect(jsonPath("$.[*].certificateHabitability").value(hasItem(DEFAULT_CERTIFICATE_HABITABILITY.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultApartmentShouldNotBeFound(String filter) throws Exception {
        restApartmentMockMvc.perform(get("/api/apartments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingApartment() throws Exception {
        // Get the apartment
        restApartmentMockMvc.perform(get("/api/apartments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApartment() throws Exception {
        // Initialize the database
        apartmentService.save(apartment);

        int databaseSizeBeforeUpdate = apartmentRepository.findAll().size();

        // Update the apartment
        Apartment updatedApartment = apartmentRepository.findOne(apartment.getId());
        // Disconnect from session so that the updates on updatedApartment are not directly saved in db
        em.detach(updatedApartment);
        updatedApartment
            .numberBedrooms(UPDATED_NUMBER_BEDROOMS)
            .numberBathrooms(UPDATED_NUMBER_BATHROOMS)
            .m2Edified(UPDATED_M_2_EDIFIED)
            .m2Usable(UPDATED_M_2_USABLE)
            .height(UPDATED_HEIGHT)
            .elevator(UPDATED_ELEVATOR)
            .ac(UPDATED_AC)
            .heat(UPDATED_HEAT)
            .parking(UPDATED_PARKING)
            .terrace(UPDATED_TERRACE)
            .balcony(UPDATED_BALCONY)
            .surfaceTerrace(UPDATED_SURFACE_TERRACE)
            .surfaceSaloon(UPDATED_SURFACE_SALOON)
            .type(UPDATED_TYPE)
            .office(UPDATED_OFFICE)
            .kitchenOffice(UPDATED_KITCHEN_OFFICE)
            .storage(UPDATED_STORAGE)
            .sharedPool(UPDATED_SHARED_POOL)
            .nearTransport(UPDATED_NEAR_TRANSPORT)
            .reformed(UPDATED_REFORMED)
            .energyCertificate(UPDATED_ENERGY_CERTIFICATE)
            .certificateHabitability(UPDATED_CERTIFICATE_HABITABILITY);

        restApartmentMockMvc.perform(put("/api/apartments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApartment)))
            .andExpect(status().isOk());

        // Validate the Apartment in the database
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeUpdate);
        Apartment testApartment = apartmentList.get(apartmentList.size() - 1);
        assertThat(testApartment.getNumberBedrooms()).isEqualTo(UPDATED_NUMBER_BEDROOMS);
        assertThat(testApartment.getNumberBathrooms()).isEqualTo(UPDATED_NUMBER_BATHROOMS);
        assertThat(testApartment.getm2Edified()).isEqualTo(UPDATED_M_2_EDIFIED);
        assertThat(testApartment.getm2Usable()).isEqualTo(UPDATED_M_2_USABLE);
        assertThat(testApartment.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testApartment.getElevator()).isEqualTo(UPDATED_ELEVATOR);
        assertThat(testApartment.getAc()).isEqualTo(UPDATED_AC);
        assertThat(testApartment.getHeat()).isEqualTo(UPDATED_HEAT);
        assertThat(testApartment.getParking()).isEqualTo(UPDATED_PARKING);
        assertThat(testApartment.getTerrace()).isEqualTo(UPDATED_TERRACE);
        assertThat(testApartment.getBalcony()).isEqualTo(UPDATED_BALCONY);
        assertThat(testApartment.getSurfaceTerrace()).isEqualTo(UPDATED_SURFACE_TERRACE);
        assertThat(testApartment.getSurfaceSaloon()).isEqualTo(UPDATED_SURFACE_SALOON);
        assertThat(testApartment.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testApartment.getOffice()).isEqualTo(UPDATED_OFFICE);
        assertThat(testApartment.getKitchenOffice()).isEqualTo(UPDATED_KITCHEN_OFFICE);
        assertThat(testApartment.getStorage()).isEqualTo(UPDATED_STORAGE);
        assertThat(testApartment.getSharedPool()).isEqualTo(UPDATED_SHARED_POOL);
        assertThat(testApartment.getNearTransport()).isEqualTo(UPDATED_NEAR_TRANSPORT);
        assertThat(testApartment.getReformed()).isEqualTo(UPDATED_REFORMED);
        assertThat(testApartment.getEnergyCertificate()).isEqualTo(UPDATED_ENERGY_CERTIFICATE);
        assertThat(testApartment.getCertificateHabitability()).isEqualTo(UPDATED_CERTIFICATE_HABITABILITY);
    }

    @Test
    @Transactional
    public void updateNonExistingApartment() throws Exception {
        int databaseSizeBeforeUpdate = apartmentRepository.findAll().size();

        // Create the Apartment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApartmentMockMvc.perform(put("/api/apartments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apartment)))
            .andExpect(status().isCreated());

        // Validate the Apartment in the database
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteApartment() throws Exception {
        // Initialize the database
        apartmentService.save(apartment);

        int databaseSizeBeforeDelete = apartmentRepository.findAll().size();

        // Get the apartment
        restApartmentMockMvc.perform(delete("/api/apartments/{id}", apartment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Apartment> apartmentList = apartmentRepository.findAll();
        assertThat(apartmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Apartment.class);
        Apartment apartment1 = new Apartment();
        apartment1.setId(1L);
        Apartment apartment2 = new Apartment();
        apartment2.setId(apartment1.getId());
        assertThat(apartment1).isEqualTo(apartment2);
        apartment2.setId(2L);
        assertThat(apartment1).isNotEqualTo(apartment2);
        apartment1.setId(null);
        assertThat(apartment1).isNotEqualTo(apartment2);
    }
}
