package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;

import com.arnaugarcia.uplace.domain.Establishment;
import com.arnaugarcia.uplace.domain.enumeration.TransactionType;
import com.arnaugarcia.uplace.repository.EstablishmentRepository;
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
import java.time.ZonedDateTime;
import java.util.List;

import static com.arnaugarcia.uplace.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.UseEstablishment;
import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;
/**
 * Test class for the EstablishmentResource REST controller.
 *
 * @see EstablishmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UplaceApp.class)
public class EstablishmentResourceIntTest {

    private static final String DEFAULT_TITLE = "TEST Apartment";

    private static final Double DEFAULT_PRICE = 0.0;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.now();

    private static final TransactionType DEFAULT_TRANSACCTION = TransactionType.RENT_BUY;

    private static final String DEFAULT_REFERENCE = "AAAAAAA";

    private static final Integer DEFAULT_M_2_FACADE = 1;
    private static final Integer UPDATED_M_2_FACADE = 2;

    private static final Select DEFAULT_BATHROOM = Select.YES;
    private static final Select UPDATED_BATHROOM = Select.NO;

    private static final UseEstablishment DEFAULT_USE = UseEstablishment.RESTAURANT;
    private static final UseEstablishment UPDATED_USE = UseEstablishment.PUB;

    private static final EnergyCertificate DEFAULT_ENERGY_CERTIFICATE = EnergyCertificate.A;
    private static final EnergyCertificate UPDATED_ENERGY_CERTIFICATE = EnergyCertificate.B;

    @Autowired
    private EstablishmentRepository establishmentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEstablishmentMockMvc;

    private Establishment establishment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstablishmentResource establishmentResource = new EstablishmentResource(establishmentRepository);
        this.restEstablishmentMockMvc = MockMvcBuilders.standaloneSetup(establishmentResource)
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
    public static Establishment createEntity(EntityManager em) {
        Establishment establishment = (Establishment) new Establishment()
            .m2Facade(DEFAULT_M_2_FACADE)
            .bathroom(DEFAULT_BATHROOM)
            .use(DEFAULT_USE)
            .energyCertificate(DEFAULT_ENERGY_CERTIFICATE)
            .created(DEFAULT_CREATED)
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE)
            .reference(DEFAULT_REFERENCE)
            .transaction(DEFAULT_TRANSACCTION);
        return establishment;
    }

    @Before
    public void initTest() {
        establishment = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstablishment() throws Exception {
        int databaseSizeBeforeCreate = establishmentRepository.findAll().size();

        // Create the Establishment
        restEstablishmentMockMvc.perform(post("/api/establishments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(establishment)))
            .andExpect(status().isCreated());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeCreate + 1);
        Establishment testEstablishment = establishmentList.get(establishmentList.size() - 1);
        assertThat(testEstablishment.getm2Facade()).isEqualTo(DEFAULT_M_2_FACADE);
        assertThat(testEstablishment.getBathroom()).isEqualTo(DEFAULT_BATHROOM);
        assertThat(testEstablishment.getUse()).isEqualTo(DEFAULT_USE);
        assertThat(testEstablishment.getEnergyCertificate()).isEqualTo(DEFAULT_ENERGY_CERTIFICATE);
    }

    @Test
    @Transactional
    public void createEstablishmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = establishmentRepository.findAll().size();

        // Create the Establishment with an existing ID
        establishment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstablishmentMockMvc.perform(post("/api/establishments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(establishment)))
            .andExpect(status().isBadRequest());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEstablishments() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);

        // Get all the establishmentList
        restEstablishmentMockMvc.perform(get("/api/establishments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(establishment.getId().intValue())))
            .andExpect(jsonPath("$.[*].m2Facade").value(hasItem(DEFAULT_M_2_FACADE)))
            .andExpect(jsonPath("$.[*].bathroom").value(hasItem(DEFAULT_BATHROOM.toString())))
            .andExpect(jsonPath("$.[*].use").value(hasItem(DEFAULT_USE.toString())))
            .andExpect(jsonPath("$.[*].energyCertificate").value(hasItem(DEFAULT_ENERGY_CERTIFICATE.toString())));
    }

    @Test
    @Transactional
    public void getEstablishment() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);

        // Get the establishment
        restEstablishmentMockMvc.perform(get("/api/establishments/{id}", establishment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(establishment.getId().intValue()))
            .andExpect(jsonPath("$.m2Facade").value(DEFAULT_M_2_FACADE))
            .andExpect(jsonPath("$.bathroom").value(DEFAULT_BATHROOM.toString()))
            .andExpect(jsonPath("$.use").value(DEFAULT_USE.toString()))
            .andExpect(jsonPath("$.energyCertificate").value(DEFAULT_ENERGY_CERTIFICATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEstablishment() throws Exception {
        // Get the establishment
        restEstablishmentMockMvc.perform(get("/api/establishments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstablishment() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);
        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();

        // Update the establishment
        Establishment updatedEstablishment = establishmentRepository.findOne(establishment.getId());
        // Disconnect from session so that the updates on updatedEstablishment are not directly saved in db
        em.detach(updatedEstablishment);
        updatedEstablishment
            .m2Facade(UPDATED_M_2_FACADE)
            .bathroom(UPDATED_BATHROOM)
            .use(UPDATED_USE)
            .energyCertificate(UPDATED_ENERGY_CERTIFICATE);

        restEstablishmentMockMvc.perform(put("/api/establishments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEstablishment)))
            .andExpect(status().isOk());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
        Establishment testEstablishment = establishmentList.get(establishmentList.size() - 1);
        assertThat(testEstablishment.getm2Facade()).isEqualTo(UPDATED_M_2_FACADE);
        assertThat(testEstablishment.getBathroom()).isEqualTo(UPDATED_BATHROOM);
        assertThat(testEstablishment.getUse()).isEqualTo(UPDATED_USE);
        assertThat(testEstablishment.getEnergyCertificate()).isEqualTo(UPDATED_ENERGY_CERTIFICATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEstablishment() throws Exception {
        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();

        // Create the Establishment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEstablishmentMockMvc.perform(put("/api/establishments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(establishment)))
            .andExpect(status().isCreated());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEstablishment() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);
        int databaseSizeBeforeDelete = establishmentRepository.findAll().size();

        // Get the establishment
        restEstablishmentMockMvc.perform(delete("/api/establishments/{id}", establishment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Establishment.class);
        Establishment establishment1 = new Establishment();
        establishment1.setId(1L);
        Establishment establishment2 = new Establishment();
        establishment2.setId(establishment1.getId());
        assertThat(establishment1).isEqualTo(establishment2);
        establishment2.setId(2L);
        assertThat(establishment1).isNotEqualTo(establishment2);
        establishment1.setId(null);
        assertThat(establishment1).isNotEqualTo(establishment2);
    }
}
