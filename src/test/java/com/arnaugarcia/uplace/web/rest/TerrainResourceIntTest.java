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
import java.util.List;

import static com.arnaugarcia.uplace.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.arnaugarcia.uplace.domain.enumeration.TerrainType;
/**
 * Test class for the TerrainResource REST controller.
 *
 * @see TerrainResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UplaceApp.class)
public class TerrainResourceIntTest {

    private static final Integer DEFAULT_M_2_BUILDABLE = 1;
    private static final Integer UPDATED_M_2_BUILDABLE = 2;

    private static final Boolean DEFAULT_BUILDABLE = false;
    private static final Boolean UPDATED_BUILDABLE = true;

    private static final Integer DEFAULT_BUILDABLE_DEPTH = 1;
    private static final Integer UPDATED_BUILDABLE_DEPTH = 2;

    private static final Integer DEFAULT_FLOORS_SR = 1;
    private static final Integer UPDATED_FLOORS_SR = 2;

    private static final Integer DEFAULT_FLOORS_BR = 1;
    private static final Integer UPDATED_FLOORS_BR = 2;

    private static final Double DEFAULT_CONSTRUCTION_COEFFICIENT = 1D;
    private static final Double UPDATED_CONSTRUCTION_COEFFICIENT = 2D;

    private static final TerrainType DEFAULT_TYPE = TerrainType.RESIDENTIAL;
    private static final TerrainType UPDATED_TYPE = TerrainType.URBAN;

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
        Terrain terrain = new Terrain()
            .m2Buildable(DEFAULT_M_2_BUILDABLE)
            .buildable(DEFAULT_BUILDABLE)
            .buildableDepth(DEFAULT_BUILDABLE_DEPTH)
            .floorsSR(DEFAULT_FLOORS_SR)
            .floorsBR(DEFAULT_FLOORS_BR)
            .constructionCoefficient(DEFAULT_CONSTRUCTION_COEFFICIENT)
            .type(DEFAULT_TYPE);
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
        assertThat(testTerrain.getm2Buildable()).isEqualTo(DEFAULT_M_2_BUILDABLE);
        assertThat(testTerrain.isBuildable()).isEqualTo(DEFAULT_BUILDABLE);
        assertThat(testTerrain.getBuildableDepth()).isEqualTo(DEFAULT_BUILDABLE_DEPTH);
        assertThat(testTerrain.getFloorsSR()).isEqualTo(DEFAULT_FLOORS_SR);
        assertThat(testTerrain.getFloorsBR()).isEqualTo(DEFAULT_FLOORS_BR);
        assertThat(testTerrain.getConstructionCoefficient()).isEqualTo(DEFAULT_CONSTRUCTION_COEFFICIENT);
        assertThat(testTerrain.getType()).isEqualTo(DEFAULT_TYPE);
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
            .andExpect(jsonPath("$.[*].m2Buildable").value(hasItem(DEFAULT_M_2_BUILDABLE)))
            .andExpect(jsonPath("$.[*].buildable").value(hasItem(DEFAULT_BUILDABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].buildableDepth").value(hasItem(DEFAULT_BUILDABLE_DEPTH)))
            .andExpect(jsonPath("$.[*].floorsSR").value(hasItem(DEFAULT_FLOORS_SR)))
            .andExpect(jsonPath("$.[*].floorsBR").value(hasItem(DEFAULT_FLOORS_BR)))
            .andExpect(jsonPath("$.[*].constructionCoefficient").value(hasItem(DEFAULT_CONSTRUCTION_COEFFICIENT.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
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
            .andExpect(jsonPath("$.m2Buildable").value(DEFAULT_M_2_BUILDABLE))
            .andExpect(jsonPath("$.buildable").value(DEFAULT_BUILDABLE.booleanValue()))
            .andExpect(jsonPath("$.buildableDepth").value(DEFAULT_BUILDABLE_DEPTH))
            .andExpect(jsonPath("$.floorsSR").value(DEFAULT_FLOORS_SR))
            .andExpect(jsonPath("$.floorsBR").value(DEFAULT_FLOORS_BR))
            .andExpect(jsonPath("$.constructionCoefficient").value(DEFAULT_CONSTRUCTION_COEFFICIENT.doubleValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllTerrainsBym2BuildableIsEqualToSomething() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where m2Buildable equals to DEFAULT_M_2_BUILDABLE
        defaultTerrainShouldBeFound("m2Buildable.equals=" + DEFAULT_M_2_BUILDABLE);

        // Get all the terrainList where m2Buildable equals to UPDATED_M_2_BUILDABLE
        defaultTerrainShouldNotBeFound("m2Buildable.equals=" + UPDATED_M_2_BUILDABLE);
    }

    @Test
    @Transactional
    public void getAllTerrainsBym2BuildableIsInShouldWork() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where m2Buildable in DEFAULT_M_2_BUILDABLE or UPDATED_M_2_BUILDABLE
        defaultTerrainShouldBeFound("m2Buildable.in=" + DEFAULT_M_2_BUILDABLE + "," + UPDATED_M_2_BUILDABLE);

        // Get all the terrainList where m2Buildable equals to UPDATED_M_2_BUILDABLE
        defaultTerrainShouldNotBeFound("m2Buildable.in=" + UPDATED_M_2_BUILDABLE);
    }

    @Test
    @Transactional
    public void getAllTerrainsBym2BuildableIsNullOrNotNull() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where m2Buildable is not null
        defaultTerrainShouldBeFound("m2Buildable.specified=true");

        // Get all the terrainList where m2Buildable is null
        defaultTerrainShouldNotBeFound("m2Buildable.specified=false");
    }

    @Test
    @Transactional
    public void getAllTerrainsBym2BuildableIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where m2Buildable greater than or equals to DEFAULT_M_2_BUILDABLE
        defaultTerrainShouldBeFound("m2Buildable.greaterOrEqualThan=" + DEFAULT_M_2_BUILDABLE);

        // Get all the terrainList where m2Buildable greater than or equals to UPDATED_M_2_BUILDABLE
        defaultTerrainShouldNotBeFound("m2Buildable.greaterOrEqualThan=" + UPDATED_M_2_BUILDABLE);
    }

    @Test
    @Transactional
    public void getAllTerrainsBym2BuildableIsLessThanSomething() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where m2Buildable less than or equals to DEFAULT_M_2_BUILDABLE
        defaultTerrainShouldNotBeFound("m2Buildable.lessThan=" + DEFAULT_M_2_BUILDABLE);

        // Get all the terrainList where m2Buildable less than or equals to UPDATED_M_2_BUILDABLE
        defaultTerrainShouldBeFound("m2Buildable.lessThan=" + UPDATED_M_2_BUILDABLE);
    }


    @Test
    @Transactional
    public void getAllTerrainsByBuildableIsEqualToSomething() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where buildable equals to DEFAULT_BUILDABLE
        defaultTerrainShouldBeFound("buildable.equals=" + DEFAULT_BUILDABLE);

        // Get all the terrainList where buildable equals to UPDATED_BUILDABLE
        defaultTerrainShouldNotBeFound("buildable.equals=" + UPDATED_BUILDABLE);
    }

    @Test
    @Transactional
    public void getAllTerrainsByBuildableIsInShouldWork() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where buildable in DEFAULT_BUILDABLE or UPDATED_BUILDABLE
        defaultTerrainShouldBeFound("buildable.in=" + DEFAULT_BUILDABLE + "," + UPDATED_BUILDABLE);

        // Get all the terrainList where buildable equals to UPDATED_BUILDABLE
        defaultTerrainShouldNotBeFound("buildable.in=" + UPDATED_BUILDABLE);
    }

    @Test
    @Transactional
    public void getAllTerrainsByBuildableIsNullOrNotNull() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where buildable is not null
        defaultTerrainShouldBeFound("buildable.specified=true");

        // Get all the terrainList where buildable is null
        defaultTerrainShouldNotBeFound("buildable.specified=false");
    }

    @Test
    @Transactional
    public void getAllTerrainsByBuildableDepthIsEqualToSomething() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where buildableDepth equals to DEFAULT_BUILDABLE_DEPTH
        defaultTerrainShouldBeFound("buildableDepth.equals=" + DEFAULT_BUILDABLE_DEPTH);

        // Get all the terrainList where buildableDepth equals to UPDATED_BUILDABLE_DEPTH
        defaultTerrainShouldNotBeFound("buildableDepth.equals=" + UPDATED_BUILDABLE_DEPTH);
    }

    @Test
    @Transactional
    public void getAllTerrainsByBuildableDepthIsInShouldWork() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where buildableDepth in DEFAULT_BUILDABLE_DEPTH or UPDATED_BUILDABLE_DEPTH
        defaultTerrainShouldBeFound("buildableDepth.in=" + DEFAULT_BUILDABLE_DEPTH + "," + UPDATED_BUILDABLE_DEPTH);

        // Get all the terrainList where buildableDepth equals to UPDATED_BUILDABLE_DEPTH
        defaultTerrainShouldNotBeFound("buildableDepth.in=" + UPDATED_BUILDABLE_DEPTH);
    }

    @Test
    @Transactional
    public void getAllTerrainsByBuildableDepthIsNullOrNotNull() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where buildableDepth is not null
        defaultTerrainShouldBeFound("buildableDepth.specified=true");

        // Get all the terrainList where buildableDepth is null
        defaultTerrainShouldNotBeFound("buildableDepth.specified=false");
    }

    @Test
    @Transactional
    public void getAllTerrainsByBuildableDepthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where buildableDepth greater than or equals to DEFAULT_BUILDABLE_DEPTH
        defaultTerrainShouldBeFound("buildableDepth.greaterOrEqualThan=" + DEFAULT_BUILDABLE_DEPTH);

        // Get all the terrainList where buildableDepth greater than or equals to UPDATED_BUILDABLE_DEPTH
        defaultTerrainShouldNotBeFound("buildableDepth.greaterOrEqualThan=" + UPDATED_BUILDABLE_DEPTH);
    }

    @Test
    @Transactional
    public void getAllTerrainsByBuildableDepthIsLessThanSomething() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where buildableDepth less than or equals to DEFAULT_BUILDABLE_DEPTH
        defaultTerrainShouldNotBeFound("buildableDepth.lessThan=" + DEFAULT_BUILDABLE_DEPTH);

        // Get all the terrainList where buildableDepth less than or equals to UPDATED_BUILDABLE_DEPTH
        defaultTerrainShouldBeFound("buildableDepth.lessThan=" + UPDATED_BUILDABLE_DEPTH);
    }


    @Test
    @Transactional
    public void getAllTerrainsByFloorsSRIsEqualToSomething() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where floorsSR equals to DEFAULT_FLOORS_SR
        defaultTerrainShouldBeFound("floorsSR.equals=" + DEFAULT_FLOORS_SR);

        // Get all the terrainList where floorsSR equals to UPDATED_FLOORS_SR
        defaultTerrainShouldNotBeFound("floorsSR.equals=" + UPDATED_FLOORS_SR);
    }

    @Test
    @Transactional
    public void getAllTerrainsByFloorsSRIsInShouldWork() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where floorsSR in DEFAULT_FLOORS_SR or UPDATED_FLOORS_SR
        defaultTerrainShouldBeFound("floorsSR.in=" + DEFAULT_FLOORS_SR + "," + UPDATED_FLOORS_SR);

        // Get all the terrainList where floorsSR equals to UPDATED_FLOORS_SR
        defaultTerrainShouldNotBeFound("floorsSR.in=" + UPDATED_FLOORS_SR);
    }

    @Test
    @Transactional
    public void getAllTerrainsByFloorsSRIsNullOrNotNull() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where floorsSR is not null
        defaultTerrainShouldBeFound("floorsSR.specified=true");

        // Get all the terrainList where floorsSR is null
        defaultTerrainShouldNotBeFound("floorsSR.specified=false");
    }

    @Test
    @Transactional
    public void getAllTerrainsByFloorsSRIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where floorsSR greater than or equals to DEFAULT_FLOORS_SR
        defaultTerrainShouldBeFound("floorsSR.greaterOrEqualThan=" + DEFAULT_FLOORS_SR);

        // Get all the terrainList where floorsSR greater than or equals to UPDATED_FLOORS_SR
        defaultTerrainShouldNotBeFound("floorsSR.greaterOrEqualThan=" + UPDATED_FLOORS_SR);
    }

    @Test
    @Transactional
    public void getAllTerrainsByFloorsSRIsLessThanSomething() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where floorsSR less than or equals to DEFAULT_FLOORS_SR
        defaultTerrainShouldNotBeFound("floorsSR.lessThan=" + DEFAULT_FLOORS_SR);

        // Get all the terrainList where floorsSR less than or equals to UPDATED_FLOORS_SR
        defaultTerrainShouldBeFound("floorsSR.lessThan=" + UPDATED_FLOORS_SR);
    }


    @Test
    @Transactional
    public void getAllTerrainsByFloorsBRIsEqualToSomething() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where floorsBR equals to DEFAULT_FLOORS_BR
        defaultTerrainShouldBeFound("floorsBR.equals=" + DEFAULT_FLOORS_BR);

        // Get all the terrainList where floorsBR equals to UPDATED_FLOORS_BR
        defaultTerrainShouldNotBeFound("floorsBR.equals=" + UPDATED_FLOORS_BR);
    }

    @Test
    @Transactional
    public void getAllTerrainsByFloorsBRIsInShouldWork() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where floorsBR in DEFAULT_FLOORS_BR or UPDATED_FLOORS_BR
        defaultTerrainShouldBeFound("floorsBR.in=" + DEFAULT_FLOORS_BR + "," + UPDATED_FLOORS_BR);

        // Get all the terrainList where floorsBR equals to UPDATED_FLOORS_BR
        defaultTerrainShouldNotBeFound("floorsBR.in=" + UPDATED_FLOORS_BR);
    }

    @Test
    @Transactional
    public void getAllTerrainsByFloorsBRIsNullOrNotNull() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where floorsBR is not null
        defaultTerrainShouldBeFound("floorsBR.specified=true");

        // Get all the terrainList where floorsBR is null
        defaultTerrainShouldNotBeFound("floorsBR.specified=false");
    }

    @Test
    @Transactional
    public void getAllTerrainsByFloorsBRIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where floorsBR greater than or equals to DEFAULT_FLOORS_BR
        defaultTerrainShouldBeFound("floorsBR.greaterOrEqualThan=" + DEFAULT_FLOORS_BR);

        // Get all the terrainList where floorsBR greater than or equals to UPDATED_FLOORS_BR
        defaultTerrainShouldNotBeFound("floorsBR.greaterOrEqualThan=" + UPDATED_FLOORS_BR);
    }

    @Test
    @Transactional
    public void getAllTerrainsByFloorsBRIsLessThanSomething() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where floorsBR less than or equals to DEFAULT_FLOORS_BR
        defaultTerrainShouldNotBeFound("floorsBR.lessThan=" + DEFAULT_FLOORS_BR);

        // Get all the terrainList where floorsBR less than or equals to UPDATED_FLOORS_BR
        defaultTerrainShouldBeFound("floorsBR.lessThan=" + UPDATED_FLOORS_BR);
    }


    @Test
    @Transactional
    public void getAllTerrainsByConstructionCoefficientIsEqualToSomething() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where constructionCoefficient equals to DEFAULT_CONSTRUCTION_COEFFICIENT
        defaultTerrainShouldBeFound("constructionCoefficient.equals=" + DEFAULT_CONSTRUCTION_COEFFICIENT);

        // Get all the terrainList where constructionCoefficient equals to UPDATED_CONSTRUCTION_COEFFICIENT
        defaultTerrainShouldNotBeFound("constructionCoefficient.equals=" + UPDATED_CONSTRUCTION_COEFFICIENT);
    }

    @Test
    @Transactional
    public void getAllTerrainsByConstructionCoefficientIsInShouldWork() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where constructionCoefficient in DEFAULT_CONSTRUCTION_COEFFICIENT or UPDATED_CONSTRUCTION_COEFFICIENT
        defaultTerrainShouldBeFound("constructionCoefficient.in=" + DEFAULT_CONSTRUCTION_COEFFICIENT + "," + UPDATED_CONSTRUCTION_COEFFICIENT);

        // Get all the terrainList where constructionCoefficient equals to UPDATED_CONSTRUCTION_COEFFICIENT
        defaultTerrainShouldNotBeFound("constructionCoefficient.in=" + UPDATED_CONSTRUCTION_COEFFICIENT);
    }

    @Test
    @Transactional
    public void getAllTerrainsByConstructionCoefficientIsNullOrNotNull() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where constructionCoefficient is not null
        defaultTerrainShouldBeFound("constructionCoefficient.specified=true");

        // Get all the terrainList where constructionCoefficient is null
        defaultTerrainShouldNotBeFound("constructionCoefficient.specified=false");
    }

    @Test
    @Transactional
    public void getAllTerrainsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where type equals to DEFAULT_TYPE
        defaultTerrainShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the terrainList where type equals to UPDATED_TYPE
        defaultTerrainShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTerrainsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultTerrainShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the terrainList where type equals to UPDATED_TYPE
        defaultTerrainShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTerrainsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList where type is not null
        defaultTerrainShouldBeFound("type.specified=true");

        // Get all the terrainList where type is null
        defaultTerrainShouldNotBeFound("type.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTerrainShouldBeFound(String filter) throws Exception {
        restTerrainMockMvc.perform(get("/api/terrains?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(terrain.getId().intValue())))
            .andExpect(jsonPath("$.[*].m2Buildable").value(hasItem(DEFAULT_M_2_BUILDABLE)))
            .andExpect(jsonPath("$.[*].buildable").value(hasItem(DEFAULT_BUILDABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].buildableDepth").value(hasItem(DEFAULT_BUILDABLE_DEPTH)))
            .andExpect(jsonPath("$.[*].floorsSR").value(hasItem(DEFAULT_FLOORS_SR)))
            .andExpect(jsonPath("$.[*].floorsBR").value(hasItem(DEFAULT_FLOORS_BR)))
            .andExpect(jsonPath("$.[*].constructionCoefficient").value(hasItem(DEFAULT_CONSTRUCTION_COEFFICIENT.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
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
            .m2Buildable(UPDATED_M_2_BUILDABLE)
            .buildable(UPDATED_BUILDABLE)
            .buildableDepth(UPDATED_BUILDABLE_DEPTH)
            .floorsSR(UPDATED_FLOORS_SR)
            .floorsBR(UPDATED_FLOORS_BR)
            .constructionCoefficient(UPDATED_CONSTRUCTION_COEFFICIENT)
            .type(UPDATED_TYPE);

        restTerrainMockMvc.perform(put("/api/terrains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTerrain)))
            .andExpect(status().isOk());

        // Validate the Terrain in the database
        List<Terrain> terrainList = terrainRepository.findAll();
        assertThat(terrainList).hasSize(databaseSizeBeforeUpdate);
        Terrain testTerrain = terrainList.get(terrainList.size() - 1);
        assertThat(testTerrain.getm2Buildable()).isEqualTo(UPDATED_M_2_BUILDABLE);
        assertThat(testTerrain.isBuildable()).isEqualTo(UPDATED_BUILDABLE);
        assertThat(testTerrain.getBuildableDepth()).isEqualTo(UPDATED_BUILDABLE_DEPTH);
        assertThat(testTerrain.getFloorsSR()).isEqualTo(UPDATED_FLOORS_SR);
        assertThat(testTerrain.getFloorsBR()).isEqualTo(UPDATED_FLOORS_BR);
        assertThat(testTerrain.getConstructionCoefficient()).isEqualTo(UPDATED_CONSTRUCTION_COEFFICIENT);
        assertThat(testTerrain.getType()).isEqualTo(UPDATED_TYPE);
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
