package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;

import com.arnaugarcia.uplace.domain.Business;
import com.arnaugarcia.uplace.repository.BusinessRepository;
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
        final BusinessResource businessResource = new BusinessResource(businessRepository);
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
    public void getNonExistingBusiness() throws Exception {
        // Get the business
        restBusinessMockMvc.perform(get("/api/businesses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusiness() throws Exception {
        // Initialize the database
        businessRepository.saveAndFlush(business);
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
        businessRepository.saveAndFlush(business);
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
