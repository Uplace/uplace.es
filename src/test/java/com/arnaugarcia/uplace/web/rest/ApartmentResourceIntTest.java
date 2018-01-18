package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;

import com.arnaugarcia.uplace.domain.Apartment;
import com.arnaugarcia.uplace.repository.ApartmentRepository;
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

import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.ApartmentType;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
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

    private static final Select DEFAULT_ELEVATOR = Select.YES;
    private static final Select UPDATED_ELEVATOR = Select.NO;

    private static final Select DEFAULT_AC = Select.YES;
    private static final Select UPDATED_AC = Select.NO;

    private static final Select DEFAULT_HEAT = Select.YES;
    private static final Select UPDATED_HEAT = Select.NO;

    private static final Integer DEFAULT_SURFACE_TERRACE = 1;
    private static final Integer UPDATED_SURFACE_TERRACE = 2;

    private static final Integer DEFAULT_SURFACE_SALOON = 1;
    private static final Integer UPDATED_SURFACE_SALOON = 2;

    private static final ApartmentType DEFAULT_PROPERTY_TYPE = ApartmentType.HOUSE;
    private static final ApartmentType UPDATED_PROPERTY_TYPE = ApartmentType.RURAL;

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

    @Autowired
    private ApartmentRepository apartmentRepository;

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
        final ApartmentResource apartmentResource = new ApartmentResource(apartmentRepository);
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
            .elevator(DEFAULT_ELEVATOR)
            .ac(DEFAULT_AC)
            .heat(DEFAULT_HEAT)
            .surfaceTerrace(DEFAULT_SURFACE_TERRACE)
            .surfaceSaloon(DEFAULT_SURFACE_SALOON)
            .propertyType(DEFAULT_PROPERTY_TYPE)
            .office(DEFAULT_OFFICE)
            .kitchenOffice(DEFAULT_KITCHEN_OFFICE)
            .storage(DEFAULT_STORAGE)
            .sharedPool(DEFAULT_SHARED_POOL)
            .nearTransport(DEFAULT_NEAR_TRANSPORT);
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
        assertThat(testApartment.getElevator()).isEqualTo(DEFAULT_ELEVATOR);
        assertThat(testApartment.getAc()).isEqualTo(DEFAULT_AC);
        assertThat(testApartment.getHeat()).isEqualTo(DEFAULT_HEAT);
        assertThat(testApartment.getSurfaceTerrace()).isEqualTo(DEFAULT_SURFACE_TERRACE);
        assertThat(testApartment.getSurfaceSaloon()).isEqualTo(DEFAULT_SURFACE_SALOON);
        assertThat(testApartment.getPropertyType()).isEqualTo(DEFAULT_PROPERTY_TYPE);
        assertThat(testApartment.getOffice()).isEqualTo(DEFAULT_OFFICE);
        assertThat(testApartment.getKitchenOffice()).isEqualTo(DEFAULT_KITCHEN_OFFICE);
        assertThat(testApartment.getStorage()).isEqualTo(DEFAULT_STORAGE);
        assertThat(testApartment.getSharedPool()).isEqualTo(DEFAULT_SHARED_POOL);
        assertThat(testApartment.getNearTransport()).isEqualTo(DEFAULT_NEAR_TRANSPORT);
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
            .andExpect(jsonPath("$.[*].elevator").value(hasItem(DEFAULT_ELEVATOR.toString())))
            .andExpect(jsonPath("$.[*].ac").value(hasItem(DEFAULT_AC.toString())))
            .andExpect(jsonPath("$.[*].heat").value(hasItem(DEFAULT_HEAT.toString())))
            .andExpect(jsonPath("$.[*].surfaceTerrace").value(hasItem(DEFAULT_SURFACE_TERRACE)))
            .andExpect(jsonPath("$.[*].surfaceSaloon").value(hasItem(DEFAULT_SURFACE_SALOON)))
            .andExpect(jsonPath("$.[*].propertyType").value(hasItem(DEFAULT_PROPERTY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].office").value(hasItem(DEFAULT_OFFICE.toString())))
            .andExpect(jsonPath("$.[*].kitchenOffice").value(hasItem(DEFAULT_KITCHEN_OFFICE.toString())))
            .andExpect(jsonPath("$.[*].storage").value(hasItem(DEFAULT_STORAGE.toString())))
            .andExpect(jsonPath("$.[*].sharedPool").value(hasItem(DEFAULT_SHARED_POOL.toString())))
            .andExpect(jsonPath("$.[*].nearTransport").value(hasItem(DEFAULT_NEAR_TRANSPORT.toString())));
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
            .andExpect(jsonPath("$.elevator").value(DEFAULT_ELEVATOR.toString()))
            .andExpect(jsonPath("$.ac").value(DEFAULT_AC.toString()))
            .andExpect(jsonPath("$.heat").value(DEFAULT_HEAT.toString()))
            .andExpect(jsonPath("$.surfaceTerrace").value(DEFAULT_SURFACE_TERRACE))
            .andExpect(jsonPath("$.surfaceSaloon").value(DEFAULT_SURFACE_SALOON))
            .andExpect(jsonPath("$.propertyType").value(DEFAULT_PROPERTY_TYPE.toString()))
            .andExpect(jsonPath("$.office").value(DEFAULT_OFFICE.toString()))
            .andExpect(jsonPath("$.kitchenOffice").value(DEFAULT_KITCHEN_OFFICE.toString()))
            .andExpect(jsonPath("$.storage").value(DEFAULT_STORAGE.toString()))
            .andExpect(jsonPath("$.sharedPool").value(DEFAULT_SHARED_POOL.toString()))
            .andExpect(jsonPath("$.nearTransport").value(DEFAULT_NEAR_TRANSPORT.toString()));
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
        apartmentRepository.saveAndFlush(apartment);
        int databaseSizeBeforeUpdate = apartmentRepository.findAll().size();

        // Update the apartment
        Apartment updatedApartment = apartmentRepository.findOne(apartment.getId());
        // Disconnect from session so that the updates on updatedApartment are not directly saved in db
        em.detach(updatedApartment);
        updatedApartment
            .numberBedrooms(UPDATED_NUMBER_BEDROOMS)
            .numberBathrooms(UPDATED_NUMBER_BATHROOMS)
            .elevator(UPDATED_ELEVATOR)
            .ac(UPDATED_AC)
            .heat(UPDATED_HEAT)
            .surfaceTerrace(UPDATED_SURFACE_TERRACE)
            .surfaceSaloon(UPDATED_SURFACE_SALOON)
            .propertyType(UPDATED_PROPERTY_TYPE)
            .office(UPDATED_OFFICE)
            .kitchenOffice(UPDATED_KITCHEN_OFFICE)
            .storage(UPDATED_STORAGE)
            .sharedPool(UPDATED_SHARED_POOL)
            .nearTransport(UPDATED_NEAR_TRANSPORT);

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
        assertThat(testApartment.getElevator()).isEqualTo(UPDATED_ELEVATOR);
        assertThat(testApartment.getAc()).isEqualTo(UPDATED_AC);
        assertThat(testApartment.getHeat()).isEqualTo(UPDATED_HEAT);
        assertThat(testApartment.getSurfaceTerrace()).isEqualTo(UPDATED_SURFACE_TERRACE);
        assertThat(testApartment.getSurfaceSaloon()).isEqualTo(UPDATED_SURFACE_SALOON);
        assertThat(testApartment.getPropertyType()).isEqualTo(UPDATED_PROPERTY_TYPE);
        assertThat(testApartment.getOffice()).isEqualTo(UPDATED_OFFICE);
        assertThat(testApartment.getKitchenOffice()).isEqualTo(UPDATED_KITCHEN_OFFICE);
        assertThat(testApartment.getStorage()).isEqualTo(UPDATED_STORAGE);
        assertThat(testApartment.getSharedPool()).isEqualTo(UPDATED_SHARED_POOL);
        assertThat(testApartment.getNearTransport()).isEqualTo(UPDATED_NEAR_TRANSPORT);
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
        apartmentRepository.saveAndFlush(apartment);
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
