package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;

import com.arnaugarcia.uplace.domain.Building;
import com.arnaugarcia.uplace.repository.BuildingRepository;
import com.arnaugarcia.uplace.service.BuildingService;
import com.arnaugarcia.uplace.web.rest.errors.ExceptionTranslator;
import com.arnaugarcia.uplace.service.dto.BuildingCriteria;
import com.arnaugarcia.uplace.service.BuildingQueryService;

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

import com.arnaugarcia.uplace.domain.enumeration.BuildingType;
import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;
/**
 * Test class for the BuildingResource REST controller.
 *
 * @see BuildingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UplaceApp.class)
public class BuildingResourceIntTest {

    private static final String DEFAULT_TITLE = "TEST Apartment";

    private static final Double DEFAULT_PRICE = 0.0;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.now();

    private static final BuildingType DEFAULT_TYPE = BuildingType.RESIDENTIAL;
    private static final BuildingType UPDATED_TYPE = BuildingType.HOTEL;

    private static final Integer DEFAULT_SOLAR_SURFACE = 1;
    private static final Integer UPDATED_SOLAR_SURFACE = 2;

    private static final Integer DEFAULT_M_2_EDIFIED = 1;
    private static final Integer UPDATED_M_2_EDIFIED = 2;

    private static final Integer DEFAULT_FLOORS_SR = 1;
    private static final Integer UPDATED_FLOORS_SR = 2;

    private static final Integer DEFAULT_FLOORS_BR = 1;
    private static final Integer UPDATED_FLOORS_BR = 2;

    private static final EnergyCertificate DEFAULT_ENERGY_CERTIFICATE = EnergyCertificate.A;
    private static final EnergyCertificate UPDATED_ENERGY_CERTIFICATE = EnergyCertificate.B;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private BuildingQueryService buildingQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBuildingMockMvc;

    private Building building;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BuildingResource buildingResource = new BuildingResource(buildingService, buildingQueryService);
        this.restBuildingMockMvc = MockMvcBuilders.standaloneSetup(buildingResource)
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
    public static Building createEntity(EntityManager em) {
        Building building = (Building) new Building()
            .type(DEFAULT_TYPE)
            .solarSurface(DEFAULT_SOLAR_SURFACE)
            .m2Edified(DEFAULT_M_2_EDIFIED)
            .floorsSR(DEFAULT_FLOORS_SR)
            .floorsBR(DEFAULT_FLOORS_BR)
            .energyCertificate(DEFAULT_ENERGY_CERTIFICATE)
            .created(DEFAULT_CREATED)
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE);
        return building;
    }

    @Before
    public void initTest() {
        building = createEntity(em);
    }

    @Test
    @Transactional
    public void createBuilding() throws Exception {
        int databaseSizeBeforeCreate = buildingRepository.findAll().size();

        // Create the Building
        restBuildingMockMvc.perform(post("/api/buildings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(building)))
            .andExpect(status().isCreated());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeCreate + 1);
        Building testBuilding = buildingList.get(buildingList.size() - 1);
        assertThat(testBuilding.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBuilding.getSolarSurface()).isEqualTo(DEFAULT_SOLAR_SURFACE);
        assertThat(testBuilding.getm2Edified()).isEqualTo(DEFAULT_M_2_EDIFIED);
        assertThat(testBuilding.getFloorsSR()).isEqualTo(DEFAULT_FLOORS_SR);
        assertThat(testBuilding.getFloorsBR()).isEqualTo(DEFAULT_FLOORS_BR);
        assertThat(testBuilding.getEnergyCertificate()).isEqualTo(DEFAULT_ENERGY_CERTIFICATE);
    }

    @Test
    @Transactional
    public void createBuildingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = buildingRepository.findAll().size();

        // Create the Building with an existing ID
        building.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuildingMockMvc.perform(post("/api/buildings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(building)))
            .andExpect(status().isBadRequest());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBuildings() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList
        restBuildingMockMvc.perform(get("/api/buildings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(building.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].solarSurface").value(hasItem(DEFAULT_SOLAR_SURFACE)))
            .andExpect(jsonPath("$.[*].m2Edified").value(hasItem(DEFAULT_M_2_EDIFIED)))
            .andExpect(jsonPath("$.[*].floorsSR").value(hasItem(DEFAULT_FLOORS_SR)))
            .andExpect(jsonPath("$.[*].floorsBR").value(hasItem(DEFAULT_FLOORS_BR)))
            .andExpect(jsonPath("$.[*].energyCertificate").value(hasItem(DEFAULT_ENERGY_CERTIFICATE.toString())));
    }

    @Test
    @Transactional
    public void getBuilding() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get the building
        restBuildingMockMvc.perform(get("/api/buildings/{id}", building.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(building.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.solarSurface").value(DEFAULT_SOLAR_SURFACE))
            .andExpect(jsonPath("$.m2Edified").value(DEFAULT_M_2_EDIFIED))
            .andExpect(jsonPath("$.floorsSR").value(DEFAULT_FLOORS_SR))
            .andExpect(jsonPath("$.floorsBR").value(DEFAULT_FLOORS_BR))
            .andExpect(jsonPath("$.energyCertificate").value(DEFAULT_ENERGY_CERTIFICATE.toString()));
    }

    @Test
    @Transactional
    public void getAllBuildingsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where type equals to DEFAULT_TYPE
        defaultBuildingShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the buildingList where type equals to UPDATED_TYPE
        defaultBuildingShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllBuildingsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultBuildingShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the buildingList where type equals to UPDATED_TYPE
        defaultBuildingShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllBuildingsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where type is not null
        defaultBuildingShouldBeFound("type.specified=true");

        // Get all the buildingList where type is null
        defaultBuildingShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllBuildingsBySolarSurfaceIsEqualToSomething() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where solarSurface equals to DEFAULT_SOLAR_SURFACE
        defaultBuildingShouldBeFound("solarSurface.equals=" + DEFAULT_SOLAR_SURFACE);

        // Get all the buildingList where solarSurface equals to UPDATED_SOLAR_SURFACE
        defaultBuildingShouldNotBeFound("solarSurface.equals=" + UPDATED_SOLAR_SURFACE);
    }

    @Test
    @Transactional
    public void getAllBuildingsBySolarSurfaceIsInShouldWork() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where solarSurface in DEFAULT_SOLAR_SURFACE or UPDATED_SOLAR_SURFACE
        defaultBuildingShouldBeFound("solarSurface.in=" + DEFAULT_SOLAR_SURFACE + "," + UPDATED_SOLAR_SURFACE);

        // Get all the buildingList where solarSurface equals to UPDATED_SOLAR_SURFACE
        defaultBuildingShouldNotBeFound("solarSurface.in=" + UPDATED_SOLAR_SURFACE);
    }

    @Test
    @Transactional
    public void getAllBuildingsBySolarSurfaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where solarSurface is not null
        defaultBuildingShouldBeFound("solarSurface.specified=true");

        // Get all the buildingList where solarSurface is null
        defaultBuildingShouldNotBeFound("solarSurface.specified=false");
    }

    @Test
    @Transactional
    public void getAllBuildingsBySolarSurfaceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where solarSurface greater than or equals to DEFAULT_SOLAR_SURFACE
        defaultBuildingShouldBeFound("solarSurface.greaterOrEqualThan=" + DEFAULT_SOLAR_SURFACE);

        // Get all the buildingList where solarSurface greater than or equals to UPDATED_SOLAR_SURFACE
        defaultBuildingShouldNotBeFound("solarSurface.greaterOrEqualThan=" + UPDATED_SOLAR_SURFACE);
    }

    @Test
    @Transactional
    public void getAllBuildingsBySolarSurfaceIsLessThanSomething() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where solarSurface less than or equals to DEFAULT_SOLAR_SURFACE
        defaultBuildingShouldNotBeFound("solarSurface.lessThan=" + DEFAULT_SOLAR_SURFACE);

        // Get all the buildingList where solarSurface less than or equals to UPDATED_SOLAR_SURFACE
        defaultBuildingShouldBeFound("solarSurface.lessThan=" + UPDATED_SOLAR_SURFACE);
    }


    @Test
    @Transactional
    public void getAllBuildingsBym2EdifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where m2Edified equals to DEFAULT_M_2_EDIFIED
        defaultBuildingShouldBeFound("m2Edified.equals=" + DEFAULT_M_2_EDIFIED);

        // Get all the buildingList where m2Edified equals to UPDATED_M_2_EDIFIED
        defaultBuildingShouldNotBeFound("m2Edified.equals=" + UPDATED_M_2_EDIFIED);
    }

    @Test
    @Transactional
    public void getAllBuildingsBym2EdifiedIsInShouldWork() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where m2Edified in DEFAULT_M_2_EDIFIED or UPDATED_M_2_EDIFIED
        defaultBuildingShouldBeFound("m2Edified.in=" + DEFAULT_M_2_EDIFIED + "," + UPDATED_M_2_EDIFIED);

        // Get all the buildingList where m2Edified equals to UPDATED_M_2_EDIFIED
        defaultBuildingShouldNotBeFound("m2Edified.in=" + UPDATED_M_2_EDIFIED);
    }

    @Test
    @Transactional
    public void getAllBuildingsBym2EdifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where m2Edified is not null
        defaultBuildingShouldBeFound("m2Edified.specified=true");

        // Get all the buildingList where m2Edified is null
        defaultBuildingShouldNotBeFound("m2Edified.specified=false");
    }

    @Test
    @Transactional
    public void getAllBuildingsBym2EdifiedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where m2Edified greater than or equals to DEFAULT_M_2_EDIFIED
        defaultBuildingShouldBeFound("m2Edified.greaterOrEqualThan=" + DEFAULT_M_2_EDIFIED);

        // Get all the buildingList where m2Edified greater than or equals to UPDATED_M_2_EDIFIED
        defaultBuildingShouldNotBeFound("m2Edified.greaterOrEqualThan=" + UPDATED_M_2_EDIFIED);
    }

    @Test
    @Transactional
    public void getAllBuildingsBym2EdifiedIsLessThanSomething() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where m2Edified less than or equals to DEFAULT_M_2_EDIFIED
        defaultBuildingShouldNotBeFound("m2Edified.lessThan=" + DEFAULT_M_2_EDIFIED);

        // Get all the buildingList where m2Edified less than or equals to UPDATED_M_2_EDIFIED
        defaultBuildingShouldBeFound("m2Edified.lessThan=" + UPDATED_M_2_EDIFIED);
    }


    @Test
    @Transactional
    public void getAllBuildingsByFloorsSRIsEqualToSomething() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where floorsSR equals to DEFAULT_FLOORS_SR
        defaultBuildingShouldBeFound("floorsSR.equals=" + DEFAULT_FLOORS_SR);

        // Get all the buildingList where floorsSR equals to UPDATED_FLOORS_SR
        defaultBuildingShouldNotBeFound("floorsSR.equals=" + UPDATED_FLOORS_SR);
    }

    @Test
    @Transactional
    public void getAllBuildingsByFloorsSRIsInShouldWork() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where floorsSR in DEFAULT_FLOORS_SR or UPDATED_FLOORS_SR
        defaultBuildingShouldBeFound("floorsSR.in=" + DEFAULT_FLOORS_SR + "," + UPDATED_FLOORS_SR);

        // Get all the buildingList where floorsSR equals to UPDATED_FLOORS_SR
        defaultBuildingShouldNotBeFound("floorsSR.in=" + UPDATED_FLOORS_SR);
    }

    @Test
    @Transactional
    public void getAllBuildingsByFloorsSRIsNullOrNotNull() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where floorsSR is not null
        defaultBuildingShouldBeFound("floorsSR.specified=true");

        // Get all the buildingList where floorsSR is null
        defaultBuildingShouldNotBeFound("floorsSR.specified=false");
    }

    @Test
    @Transactional
    public void getAllBuildingsByFloorsSRIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where floorsSR greater than or equals to DEFAULT_FLOORS_SR
        defaultBuildingShouldBeFound("floorsSR.greaterOrEqualThan=" + DEFAULT_FLOORS_SR);

        // Get all the buildingList where floorsSR greater than or equals to UPDATED_FLOORS_SR
        defaultBuildingShouldNotBeFound("floorsSR.greaterOrEqualThan=" + UPDATED_FLOORS_SR);
    }

    @Test
    @Transactional
    public void getAllBuildingsByFloorsSRIsLessThanSomething() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where floorsSR less than or equals to DEFAULT_FLOORS_SR
        defaultBuildingShouldNotBeFound("floorsSR.lessThan=" + DEFAULT_FLOORS_SR);

        // Get all the buildingList where floorsSR less than or equals to UPDATED_FLOORS_SR
        defaultBuildingShouldBeFound("floorsSR.lessThan=" + UPDATED_FLOORS_SR);
    }


    @Test
    @Transactional
    public void getAllBuildingsByFloorsBRIsEqualToSomething() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where floorsBR equals to DEFAULT_FLOORS_BR
        defaultBuildingShouldBeFound("floorsBR.equals=" + DEFAULT_FLOORS_BR);

        // Get all the buildingList where floorsBR equals to UPDATED_FLOORS_BR
        defaultBuildingShouldNotBeFound("floorsBR.equals=" + UPDATED_FLOORS_BR);
    }

    @Test
    @Transactional
    public void getAllBuildingsByFloorsBRIsInShouldWork() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where floorsBR in DEFAULT_FLOORS_BR or UPDATED_FLOORS_BR
        defaultBuildingShouldBeFound("floorsBR.in=" + DEFAULT_FLOORS_BR + "," + UPDATED_FLOORS_BR);

        // Get all the buildingList where floorsBR equals to UPDATED_FLOORS_BR
        defaultBuildingShouldNotBeFound("floorsBR.in=" + UPDATED_FLOORS_BR);
    }

    @Test
    @Transactional
    public void getAllBuildingsByFloorsBRIsNullOrNotNull() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where floorsBR is not null
        defaultBuildingShouldBeFound("floorsBR.specified=true");

        // Get all the buildingList where floorsBR is null
        defaultBuildingShouldNotBeFound("floorsBR.specified=false");
    }

    @Test
    @Transactional
    public void getAllBuildingsByFloorsBRIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where floorsBR greater than or equals to DEFAULT_FLOORS_BR
        defaultBuildingShouldBeFound("floorsBR.greaterOrEqualThan=" + DEFAULT_FLOORS_BR);

        // Get all the buildingList where floorsBR greater than or equals to UPDATED_FLOORS_BR
        defaultBuildingShouldNotBeFound("floorsBR.greaterOrEqualThan=" + UPDATED_FLOORS_BR);
    }

    @Test
    @Transactional
    public void getAllBuildingsByFloorsBRIsLessThanSomething() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where floorsBR less than or equals to DEFAULT_FLOORS_BR
        defaultBuildingShouldNotBeFound("floorsBR.lessThan=" + DEFAULT_FLOORS_BR);

        // Get all the buildingList where floorsBR less than or equals to UPDATED_FLOORS_BR
        defaultBuildingShouldBeFound("floorsBR.lessThan=" + UPDATED_FLOORS_BR);
    }


    @Test
    @Transactional
    public void getAllBuildingsByEnergyCertificateIsEqualToSomething() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where energyCertificate equals to DEFAULT_ENERGY_CERTIFICATE
        defaultBuildingShouldBeFound("energyCertificate.equals=" + DEFAULT_ENERGY_CERTIFICATE);

        // Get all the buildingList where energyCertificate equals to UPDATED_ENERGY_CERTIFICATE
        defaultBuildingShouldNotBeFound("energyCertificate.equals=" + UPDATED_ENERGY_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllBuildingsByEnergyCertificateIsInShouldWork() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where energyCertificate in DEFAULT_ENERGY_CERTIFICATE or UPDATED_ENERGY_CERTIFICATE
        defaultBuildingShouldBeFound("energyCertificate.in=" + DEFAULT_ENERGY_CERTIFICATE + "," + UPDATED_ENERGY_CERTIFICATE);

        // Get all the buildingList where energyCertificate equals to UPDATED_ENERGY_CERTIFICATE
        defaultBuildingShouldNotBeFound("energyCertificate.in=" + UPDATED_ENERGY_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllBuildingsByEnergyCertificateIsNullOrNotNull() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildingList where energyCertificate is not null
        defaultBuildingShouldBeFound("energyCertificate.specified=true");

        // Get all the buildingList where energyCertificate is null
        defaultBuildingShouldNotBeFound("energyCertificate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBuildingShouldBeFound(String filter) throws Exception {
        restBuildingMockMvc.perform(get("/api/buildings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(building.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].solarSurface").value(hasItem(DEFAULT_SOLAR_SURFACE)))
            .andExpect(jsonPath("$.[*].m2Edified").value(hasItem(DEFAULT_M_2_EDIFIED)))
            .andExpect(jsonPath("$.[*].floorsSR").value(hasItem(DEFAULT_FLOORS_SR)))
            .andExpect(jsonPath("$.[*].floorsBR").value(hasItem(DEFAULT_FLOORS_BR)))
            .andExpect(jsonPath("$.[*].energyCertificate").value(hasItem(DEFAULT_ENERGY_CERTIFICATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBuildingShouldNotBeFound(String filter) throws Exception {
        restBuildingMockMvc.perform(get("/api/buildings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingBuilding() throws Exception {
        // Get the building
        restBuildingMockMvc.perform(get("/api/buildings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBuilding() throws Exception {
        // Initialize the database
        buildingService.save(building);

        int databaseSizeBeforeUpdate = buildingRepository.findAll().size();

        // Update the building
        Building updatedBuilding = buildingRepository.findOne(building.getId());
        // Disconnect from session so that the updates on updatedBuilding are not directly saved in db
        em.detach(updatedBuilding);
        updatedBuilding
            .type(UPDATED_TYPE)
            .solarSurface(UPDATED_SOLAR_SURFACE)
            .m2Edified(UPDATED_M_2_EDIFIED)
            .floorsSR(UPDATED_FLOORS_SR)
            .floorsBR(UPDATED_FLOORS_BR)
            .energyCertificate(UPDATED_ENERGY_CERTIFICATE);

        restBuildingMockMvc.perform(put("/api/buildings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBuilding)))
            .andExpect(status().isOk());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeUpdate);
        Building testBuilding = buildingList.get(buildingList.size() - 1);
        assertThat(testBuilding.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBuilding.getSolarSurface()).isEqualTo(UPDATED_SOLAR_SURFACE);
        assertThat(testBuilding.getm2Edified()).isEqualTo(UPDATED_M_2_EDIFIED);
        assertThat(testBuilding.getFloorsSR()).isEqualTo(UPDATED_FLOORS_SR);
        assertThat(testBuilding.getFloorsBR()).isEqualTo(UPDATED_FLOORS_BR);
        assertThat(testBuilding.getEnergyCertificate()).isEqualTo(UPDATED_ENERGY_CERTIFICATE);
    }

    @Test
    @Transactional
    public void updateNonExistingBuilding() throws Exception {
        int databaseSizeBeforeUpdate = buildingRepository.findAll().size();

        // Create the Building

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBuildingMockMvc.perform(put("/api/buildings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(building)))
            .andExpect(status().isCreated());

        // Validate the Building in the database
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBuilding() throws Exception {
        // Initialize the database
        buildingService.save(building);

        int databaseSizeBeforeDelete = buildingRepository.findAll().size();

        // Get the building
        restBuildingMockMvc.perform(delete("/api/buildings/{id}", building.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Building> buildingList = buildingRepository.findAll();
        assertThat(buildingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Building.class);
        Building building1 = new Building();
        building1.setId(1L);
        Building building2 = new Building();
        building2.setId(building1.getId());
        assertThat(building1).isEqualTo(building2);
        building2.setId(2L);
        assertThat(building1).isNotEqualTo(building2);
        building1.setId(null);
        assertThat(building1).isNotEqualTo(building2);
    }
}
