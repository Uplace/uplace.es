package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;

import com.arnaugarcia.uplace.domain.Terrain;
import com.arnaugarcia.uplace.repository.TerrainRepository;
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
        final TerrainResource terrainResource = new TerrainResource(terrainRepository);
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
    public void getNonExistingTerrain() throws Exception {
        // Get the terrain
        restTerrainMockMvc.perform(get("/api/terrains/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTerrain() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);
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
        terrainRepository.saveAndFlush(terrain);
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
