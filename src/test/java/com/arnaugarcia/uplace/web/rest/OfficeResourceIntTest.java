package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;

import com.arnaugarcia.uplace.domain.Office;
import com.arnaugarcia.uplace.repository.OfficeRepository;
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
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;
/**
 * Test class for the OfficeResource REST controller.
 *
 * @see OfficeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UplaceApp.class)
public class OfficeResourceIntTest {

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

    private static final EnergyCertificate DEFAULT_ENERGY_CERTIFICATE = EnergyCertificate.A;
    private static final EnergyCertificate UPDATED_ENERGY_CERTIFICATE = EnergyCertificate.B;

    @Autowired
    private OfficeRepository officeRepository;

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
        final OfficeResource officeResource = new OfficeResource(officeRepository);
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
        Office office = new Office()
            .bathrooms(DEFAULT_BATHROOMS)
            .floors(DEFAULT_FLOORS)
            .terrace(DEFAULT_TERRACE)
            .office(DEFAULT_OFFICE)
            .storage(DEFAULT_STORAGE)
            .storageSurface(DEFAULT_STORAGE_SURFACE)
            .officesSurface(DEFAULT_OFFICES_SURFACE)
            .ac(DEFAULT_AC)
            .heat(DEFAULT_HEAT)
            .energyCertificate(DEFAULT_ENERGY_CERTIFICATE);
        return office;
    }

    @Before
    public void initTest() {
        office = createEntity(em);
    }

    @Test
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
        assertThat(testOffice.getTerrace()).isEqualTo(DEFAULT_TERRACE);
        assertThat(testOffice.getOffice()).isEqualTo(DEFAULT_OFFICE);
        assertThat(testOffice.getStorage()).isEqualTo(DEFAULT_STORAGE);
        assertThat(testOffice.getStorageSurface()).isEqualTo(DEFAULT_STORAGE_SURFACE);
        assertThat(testOffice.getOfficesSurface()).isEqualTo(DEFAULT_OFFICES_SURFACE);
        assertThat(testOffice.getAc()).isEqualTo(DEFAULT_AC);
        assertThat(testOffice.getHeat()).isEqualTo(DEFAULT_HEAT);
        assertThat(testOffice.getEnergyCertificate()).isEqualTo(DEFAULT_ENERGY_CERTIFICATE);
    }

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
            .andExpect(jsonPath("$.[*].terrace").value(hasItem(DEFAULT_TERRACE.toString())))
            .andExpect(jsonPath("$.[*].office").value(hasItem(DEFAULT_OFFICE.toString())))
            .andExpect(jsonPath("$.[*].storage").value(hasItem(DEFAULT_STORAGE.toString())))
            .andExpect(jsonPath("$.[*].storageSurface").value(hasItem(DEFAULT_STORAGE_SURFACE)))
            .andExpect(jsonPath("$.[*].officesSurface").value(hasItem(DEFAULT_OFFICES_SURFACE)))
            .andExpect(jsonPath("$.[*].ac").value(hasItem(DEFAULT_AC.toString())))
            .andExpect(jsonPath("$.[*].heat").value(hasItem(DEFAULT_HEAT.toString())))
            .andExpect(jsonPath("$.[*].energyCertificate").value(hasItem(DEFAULT_ENERGY_CERTIFICATE.toString())));
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
            .andExpect(jsonPath("$.terrace").value(DEFAULT_TERRACE.toString()))
            .andExpect(jsonPath("$.office").value(DEFAULT_OFFICE.toString()))
            .andExpect(jsonPath("$.storage").value(DEFAULT_STORAGE.toString()))
            .andExpect(jsonPath("$.storageSurface").value(DEFAULT_STORAGE_SURFACE))
            .andExpect(jsonPath("$.officesSurface").value(DEFAULT_OFFICES_SURFACE))
            .andExpect(jsonPath("$.ac").value(DEFAULT_AC.toString()))
            .andExpect(jsonPath("$.heat").value(DEFAULT_HEAT.toString()))
            .andExpect(jsonPath("$.energyCertificate").value(DEFAULT_ENERGY_CERTIFICATE.toString()));
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
        officeRepository.saveAndFlush(office);
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
            .heat(UPDATED_HEAT)
            .energyCertificate(UPDATED_ENERGY_CERTIFICATE);

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
        assertThat(testOffice.getEnergyCertificate()).isEqualTo(UPDATED_ENERGY_CERTIFICATE);
    }

    @Test
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
    }

    @Test
    @Transactional
    public void deleteOffice() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);
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
