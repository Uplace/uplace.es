package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;

import com.arnaugarcia.uplace.domain.Terrain;
import com.arnaugarcia.uplace.repository.TerrainRepository;
import com.arnaugarcia.uplace.service.TerrainService;
import com.arnaugarcia.uplace.web.rest.errors.ExceptionTranslator;
import com.arnaugarcia.uplace.service.dto.TerrainCriteria;
import com.arnaugarcia.uplace.service.TerrainQueryService;

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

import com.arnaugarcia.uplace.domain.enumeration.TerrainType;
import com.arnaugarcia.uplace.domain.enumeration.Select;
/**
 * Test class for the TerrainResource REST controller.
 *
 * @see TerrainResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UplaceApp.class)
public class TerrainResourceIntTest {

    private static final String DEFAULT_TITLE = "TEST Apartment";

    private static final Double DEFAULT_PRICE = 0.0;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.now();

    private static final TerrainType DEFAULT_TERRAIN_TYPE = TerrainType.RESIDENTIAL;
    private static final TerrainType UPDATED_TERRAIN_TYPE = TerrainType.URBAN;

    private static final Select DEFAULT_NEAR_TRANSPORT = Select.YES;
    private static final Select UPDATED_NEAR_TRANSPORT = Select.NO;

    @Autowired
    private TerrainRepository terrainRepository;

    @Autowired
    private TerrainService terrainService;

    @Autowired
    private TerrainQueryService terrainQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTerrainMockMvc;

    private Terrain terrain;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TerrainResource terrainResource = new TerrainResource(terrainService, terrainQueryService);
        this.restTerrainMockMvc = MockMvcBuilders.standaloneSetup(terrainResource)
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
    public static Terrain createEntity(EntityManager em) {
        Terrain terrain = (Terrain) new Terrain()
            .terrainType(DEFAULT_TERRAIN_TYPE)
            .nearTransport(DEFAULT_NEAR_TRANSPORT)
            .created(DEFAULT_CREATED)
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE);
        return terrain;
    }

    @Before
    public void initTest() {
        terrain = createEntity(em);
    }

    @Test
    @Transactional
    public void createTerrain() throws Exception {
        int databaseSizeBeforeCreate = terrainRepository.findAll().size();

        // Create the Terrain
        restTerrainMockMvc.perform(post("/api/terrains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(terrain)))
            .andExpect(status().isCreated());

        // Validate the Terrain in the database
        List<Terrain> terrainList = terrainRepository.findAll();
        assertThat(terrainList).hasSize(databaseSizeBeforeCreate + 1);
        Terrain testTerrain = terrainList.get(terrainList.size() - 1);
        assertThat(testTerrain.getTerrainType()).isEqualTo(DEFAULT_TERRAIN_TYPE);
        assertThat(testTerrain.getNearTransport()).isEqualTo(DEFAULT_NEAR_TRANSPORT);
    }

    @Test
    @Transactional
    public void createTerrainWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = terrainRepository.findAll().size();

        // Create the Terrain with an existing ID
        terrain.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTerrainMockMvc.perform(post("/api/terrains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(terrain)))
            .andExpect(status().isBadRequest());

        // Validate the Terrain in the database
        List<Terrain> terrainList = terrainRepository.findAll();
        assertThat(terrainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTerrains() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList
        restTerrainMockMvc.perform(get("/api/terrains?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(terrain.getId().intValue())))
            .andExpect(jsonPath("$.[*].terrainType").value(hasItem(DEFAULT_TERRAIN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].nearTransport").value(hasItem(DEFAULT_NEAR_TRANSPORT.toString())));
    }

    @Test
    @Transactional
    public void getTerrain() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get the terrain
        restTerrainMockMvc.perform(get("/api/terrains/{id}", terrain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(terrain.getId().intValue()))
            .andExpect(jsonPath("$.terrainType").value(DEFAULT_TERRAIN_TYPE.toString()))
            .andExpect(jsonPath("$.nearTransport").value(DEFAULT_NEAR_TRANSPORT.toString()));
    }

    @Test
    @Transactional
    public void getAllTerrainsByTerrainTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where terrainType equals to DEFAULT_TERRAIN_TYPE
        defaultTerrainShouldBeFound("terrainType.equals=" + DEFAULT_TERRAIN_TYPE);

        // Get all the terrainList where terrainType equals to UPDATED_TERRAIN_TYPE
        defaultTerrainShouldNotBeFound("terrainType.equals=" + UPDATED_TERRAIN_TYPE);
    }

    @Test
    @Transactional
    public void getAllTerrainsByTerrainTypeIsInShouldWork() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where terrainType in DEFAULT_TERRAIN_TYPE or UPDATED_TERRAIN_TYPE
        defaultTerrainShouldBeFound("terrainType.in=" + DEFAULT_TERRAIN_TYPE + "," + UPDATED_TERRAIN_TYPE);

        // Get all the terrainList where terrainType equals to UPDATED_TERRAIN_TYPE
        defaultTerrainShouldNotBeFound("terrainType.in=" + UPDATED_TERRAIN_TYPE);
    }

    @Test
    @Transactional
    public void getAllTerrainsByTerrainTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where terrainType is not null
        defaultTerrainShouldBeFound("terrainType.specified=true");

        // Get all the terrainList where terrainType is null
        defaultTerrainShouldNotBeFound("terrainType.specified=false");
    }

    @Test
    @Transactional
    public void getAllTerrainsByNearTransportIsEqualToSomething() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where nearTransport equals to DEFAULT_NEAR_TRANSPORT
        defaultTerrainShouldBeFound("nearTransport.equals=" + DEFAULT_NEAR_TRANSPORT);

        // Get all the terrainList where nearTransport equals to UPDATED_NEAR_TRANSPORT
        defaultTerrainShouldNotBeFound("nearTransport.equals=" + UPDATED_NEAR_TRANSPORT);
    }

    @Test
    @Transactional
    public void getAllTerrainsByNearTransportIsInShouldWork() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where nearTransport in DEFAULT_NEAR_TRANSPORT or UPDATED_NEAR_TRANSPORT
        defaultTerrainShouldBeFound("nearTransport.in=" + DEFAULT_NEAR_TRANSPORT + "," + UPDATED_NEAR_TRANSPORT);

        // Get all the terrainList where nearTransport equals to UPDATED_NEAR_TRANSPORT
        defaultTerrainShouldNotBeFound("nearTransport.in=" + UPDATED_NEAR_TRANSPORT);
    }

    @Test
    @Transactional
    public void getAllTerrainsByNearTransportIsNullOrNotNull() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where nearTransport is not null
        defaultTerrainShouldBeFound("nearTransport.specified=true");

        // Get all the terrainList where nearTransport is null
        defaultTerrainShouldNotBeFound("nearTransport.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTerrainShouldBeFound(String filter) throws Exception {
        restTerrainMockMvc.perform(get("/api/terrains?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(terrain.getId().intValue())))
            .andExpect(jsonPath("$.[*].terrainType").value(hasItem(DEFAULT_TERRAIN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].nearTransport").value(hasItem(DEFAULT_NEAR_TRANSPORT.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTerrainShouldNotBeFound(String filter) throws Exception {
        restTerrainMockMvc.perform(get("/api/terrains?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingTerrain() throws Exception {
        // Get the terrain
        restTerrainMockMvc.perform(get("/api/terrains/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTerrain() throws Exception {
        // Initialize the database
        terrainService.save(terrain);

        int databaseSizeBeforeUpdate = terrainRepository.findAll().size();

        // Update the terrain
        Terrain updatedTerrain = terrainRepository.findOne(terrain.getId());
        // Disconnect from session so that the updates on updatedTerrain are not directly saved in db
        em.detach(updatedTerrain);
        updatedTerrain
            .terrainType(UPDATED_TERRAIN_TYPE)
            .nearTransport(UPDATED_NEAR_TRANSPORT);

        restTerrainMockMvc.perform(put("/api/terrains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTerrain)))
            .andExpect(status().isOk());

        // Validate the Terrain in the database
        List<Terrain> terrainList = terrainRepository.findAll();
        assertThat(terrainList).hasSize(databaseSizeBeforeUpdate);
        Terrain testTerrain = terrainList.get(terrainList.size() - 1);
        assertThat(testTerrain.getTerrainType()).isEqualTo(UPDATED_TERRAIN_TYPE);
        assertThat(testTerrain.getNearTransport()).isEqualTo(UPDATED_NEAR_TRANSPORT);
    }

    @Test
    @Transactional
    public void updateNonExistingTerrain() throws Exception {
        int databaseSizeBeforeUpdate = terrainRepository.findAll().size();

        // Create the Terrain

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTerrainMockMvc.perform(put("/api/terrains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(terrain)))
            .andExpect(status().isCreated());

        // Validate the Terrain in the database
        List<Terrain> terrainList = terrainRepository.findAll();
        assertThat(terrainList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTerrain() throws Exception {
        // Initialize the database
        terrainService.save(terrain);

        int databaseSizeBeforeDelete = terrainRepository.findAll().size();

        // Get the terrain
        restTerrainMockMvc.perform(delete("/api/terrains/{id}", terrain.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Terrain> terrainList = terrainRepository.findAll();
        assertThat(terrainList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Terrain.class);
        Terrain terrain1 = new Terrain();
        terrain1.setId(1L);
        Terrain terrain2 = new Terrain();
        terrain2.setId(terrain1.getId());
        assertThat(terrain1).isEqualTo(terrain2);
        terrain2.setId(2L);
        assertThat(terrain1).isNotEqualTo(terrain2);
        terrain1.setId(null);
        assertThat(terrain1).isNotEqualTo(terrain2);
    }
}
