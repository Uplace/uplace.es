package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;

import com.arnaugarcia.uplace.domain.RealEstate;
import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.repository.RealEstateRepository;
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

/**
 * Test class for the RealEstateResource REST controller.
 *
 * @see RealEstateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UplaceApp.class)
public class RealEstateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NIF = "AAAAAAAAAA";
    private static final String UPDATED_NIF = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    @Autowired
    private RealEstateRepository realEstateRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRealEstateMockMvc;

    private RealEstate realEstate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RealEstateResource realEstateResource = new RealEstateResource(realEstateRepository);
        this.restRealEstateMockMvc = MockMvcBuilders.standaloneSetup(realEstateResource)
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
    public static RealEstate createEntity(EntityManager em) {
        RealEstate realEstate = new RealEstate()
            .name(DEFAULT_NAME)
            .nif(DEFAULT_NIF)
            .reference(DEFAULT_REFERENCE);
        // Add required entity
        Property property = PropertyResourceIntTest.createEntity(em);
        em.persist(property);
        em.flush();
        realEstate.getProperties().add(property);
        return realEstate;
    }

    @Before
    public void initTest() {
        realEstate = createEntity(em);
    }

    @Test
    @Transactional
    public void createRealEstate() throws Exception {
        int databaseSizeBeforeCreate = realEstateRepository.findAll().size();

        // Create the RealEstate
        restRealEstateMockMvc.perform(post("/api/real-estates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(realEstate)))
            .andExpect(status().isCreated());

        // Validate the RealEstate in the database
        List<RealEstate> realEstateList = realEstateRepository.findAll();
        assertThat(realEstateList).hasSize(databaseSizeBeforeCreate + 1);
        RealEstate testRealEstate = realEstateList.get(realEstateList.size() - 1);
        assertThat(testRealEstate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRealEstate.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testRealEstate.getReference()).isEqualTo(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void createRealEstateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = realEstateRepository.findAll().size();

        // Create the RealEstate with an existing ID
        realEstate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRealEstateMockMvc.perform(post("/api/real-estates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(realEstate)))
            .andExpect(status().isBadRequest());

        // Validate the RealEstate in the database
        List<RealEstate> realEstateList = realEstateRepository.findAll();
        assertThat(realEstateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = realEstateRepository.findAll().size();
        // set the field null
        realEstate.setName(null);

        // Create the RealEstate, which fails.

        restRealEstateMockMvc.perform(post("/api/real-estates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(realEstate)))
            .andExpect(status().isBadRequest());

        List<RealEstate> realEstateList = realEstateRepository.findAll();
        assertThat(realEstateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRealEstates() throws Exception {
        // Initialize the database
        realEstateRepository.saveAndFlush(realEstate);

        // Get all the realEstateList
        restRealEstateMockMvc.perform(get("/api/real-estates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(realEstate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF.toString())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())));
    }

    @Test
    @Transactional
    public void getRealEstate() throws Exception {
        // Initialize the database
        realEstateRepository.saveAndFlush(realEstate);

        // Get the realEstate
        restRealEstateMockMvc.perform(get("/api/real-estates/{id}", realEstate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(realEstate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRealEstate() throws Exception {
        // Get the realEstate
        restRealEstateMockMvc.perform(get("/api/real-estates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRealEstate() throws Exception {
        // Initialize the database
        realEstateRepository.saveAndFlush(realEstate);
        int databaseSizeBeforeUpdate = realEstateRepository.findAll().size();

        // Update the realEstate
        RealEstate updatedRealEstate = realEstateRepository.findOne(realEstate.getId());
        // Disconnect from session so that the updates on updatedRealEstate are not directly saved in db
        em.detach(updatedRealEstate);
        updatedRealEstate
            .name(UPDATED_NAME)
            .nif(UPDATED_NIF)
            .reference(UPDATED_REFERENCE);

        restRealEstateMockMvc.perform(put("/api/real-estates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRealEstate)))
            .andExpect(status().isOk());

        // Validate the RealEstate in the database
        List<RealEstate> realEstateList = realEstateRepository.findAll();
        assertThat(realEstateList).hasSize(databaseSizeBeforeUpdate);
        RealEstate testRealEstate = realEstateList.get(realEstateList.size() - 1);
        assertThat(testRealEstate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRealEstate.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testRealEstate.getReference()).isEqualTo(UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingRealEstate() throws Exception {
        int databaseSizeBeforeUpdate = realEstateRepository.findAll().size();

        // Create the RealEstate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRealEstateMockMvc.perform(put("/api/real-estates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(realEstate)))
            .andExpect(status().isCreated());

        // Validate the RealEstate in the database
        List<RealEstate> realEstateList = realEstateRepository.findAll();
        assertThat(realEstateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRealEstate() throws Exception {
        // Initialize the database
        realEstateRepository.saveAndFlush(realEstate);
        int databaseSizeBeforeDelete = realEstateRepository.findAll().size();

        // Get the realEstate
        restRealEstateMockMvc.perform(delete("/api/real-estates/{id}", realEstate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RealEstate> realEstateList = realEstateRepository.findAll();
        assertThat(realEstateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RealEstate.class);
        RealEstate realEstate1 = new RealEstate();
        realEstate1.setId(1L);
        RealEstate realEstate2 = new RealEstate();
        realEstate2.setId(realEstate1.getId());
        assertThat(realEstate1).isEqualTo(realEstate2);
        realEstate2.setId(2L);
        assertThat(realEstate1).isNotEqualTo(realEstate2);
        realEstate1.setId(null);
        assertThat(realEstate1).isNotEqualTo(realEstate2);
    }
}
