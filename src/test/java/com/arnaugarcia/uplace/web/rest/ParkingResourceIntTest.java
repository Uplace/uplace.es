package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;

import com.arnaugarcia.uplace.domain.Parking;
import com.arnaugarcia.uplace.repository.ParkingRepository;
import com.arnaugarcia.uplace.service.ParkingService;
import com.arnaugarcia.uplace.web.rest.errors.ExceptionTranslator;
import com.arnaugarcia.uplace.service.dto.ParkingCriteria;
import com.arnaugarcia.uplace.service.ParkingQueryService;

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

import com.arnaugarcia.uplace.domain.enumeration.ParkingType;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
/**
 * Test class for the ParkingResource REST controller.
 *
 * @see ParkingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UplaceApp.class)
public class ParkingResourceIntTest {

    private static final ParkingType DEFAULT_PARKING_TYPE = ParkingType.CAR;
    private static final ParkingType UPDATED_PARKING_TYPE = ParkingType.MOTO;

    private static final Select DEFAULT_NEAR_TRANSPORT = Select.YES;
    private static final Select UPDATED_NEAR_TRANSPORT = Select.NO;

    private static final Select DEFAULT_SURVEILLANCE = Select.YES;
    private static final Select UPDATED_SURVEILLANCE = Select.NO;

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private ParkingQueryService parkingQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restParkingMockMvc;

    private Parking parking;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParkingResource parkingResource = new ParkingResource(parkingService, parkingQueryService);
        this.restParkingMockMvc = MockMvcBuilders.standaloneSetup(parkingResource)
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
    public static Parking createEntity(EntityManager em) {
        Parking parking = new Parking()
            .parkingType(DEFAULT_PARKING_TYPE)
            .nearTransport(DEFAULT_NEAR_TRANSPORT)
            .surveillance(DEFAULT_SURVEILLANCE);
        return parking;
    }

    @Before
    public void initTest() {
        parking = createEntity(em);
    }

    @Test
    @Transactional
    public void createParking() throws Exception {
        int databaseSizeBeforeCreate = parkingRepository.findAll().size();

        // Create the Parking
        restParkingMockMvc.perform(post("/api/parkings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parking)))
            .andExpect(status().isCreated());

        // Validate the Parking in the database
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeCreate + 1);
        Parking testParking = parkingList.get(parkingList.size() - 1);
        assertThat(testParking.getParkingType()).isEqualTo(DEFAULT_PARKING_TYPE);
        assertThat(testParking.getNearTransport()).isEqualTo(DEFAULT_NEAR_TRANSPORT);
        assertThat(testParking.getSurveillance()).isEqualTo(DEFAULT_SURVEILLANCE);
    }

    @Test
    @Transactional
    public void createParkingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parkingRepository.findAll().size();

        // Create the Parking with an existing ID
        parking.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParkingMockMvc.perform(post("/api/parkings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parking)))
            .andExpect(status().isBadRequest());

        // Validate the Parking in the database
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllParkings() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList
        restParkingMockMvc.perform(get("/api/parkings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parking.getId().intValue())))
            .andExpect(jsonPath("$.[*].parkingType").value(hasItem(DEFAULT_PARKING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].nearTransport").value(hasItem(DEFAULT_NEAR_TRANSPORT.toString())))
            .andExpect(jsonPath("$.[*].surveillance").value(hasItem(DEFAULT_SURVEILLANCE.toString())));
    }

    @Test
    @Transactional
    public void getParking() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get the parking
        restParkingMockMvc.perform(get("/api/parkings/{id}", parking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parking.getId().intValue()))
            .andExpect(jsonPath("$.parkingType").value(DEFAULT_PARKING_TYPE.toString()))
            .andExpect(jsonPath("$.nearTransport").value(DEFAULT_NEAR_TRANSPORT.toString()))
            .andExpect(jsonPath("$.surveillance").value(DEFAULT_SURVEILLANCE.toString()));
    }

    @Test
    @Transactional
    public void getAllParkingsByParkingTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingType equals to DEFAULT_PARKING_TYPE
        defaultParkingShouldBeFound("parkingType.equals=" + DEFAULT_PARKING_TYPE);

        // Get all the parkingList where parkingType equals to UPDATED_PARKING_TYPE
        defaultParkingShouldNotBeFound("parkingType.equals=" + UPDATED_PARKING_TYPE);
    }

    @Test
    @Transactional
    public void getAllParkingsByParkingTypeIsInShouldWork() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingType in DEFAULT_PARKING_TYPE or UPDATED_PARKING_TYPE
        defaultParkingShouldBeFound("parkingType.in=" + DEFAULT_PARKING_TYPE + "," + UPDATED_PARKING_TYPE);

        // Get all the parkingList where parkingType equals to UPDATED_PARKING_TYPE
        defaultParkingShouldNotBeFound("parkingType.in=" + UPDATED_PARKING_TYPE);
    }

    @Test
    @Transactional
    public void getAllParkingsByParkingTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where parkingType is not null
        defaultParkingShouldBeFound("parkingType.specified=true");

        // Get all the parkingList where parkingType is null
        defaultParkingShouldNotBeFound("parkingType.specified=false");
    }

    @Test
    @Transactional
    public void getAllParkingsByNearTransportIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where nearTransport equals to DEFAULT_NEAR_TRANSPORT
        defaultParkingShouldBeFound("nearTransport.equals=" + DEFAULT_NEAR_TRANSPORT);

        // Get all the parkingList where nearTransport equals to UPDATED_NEAR_TRANSPORT
        defaultParkingShouldNotBeFound("nearTransport.equals=" + UPDATED_NEAR_TRANSPORT);
    }

    @Test
    @Transactional
    public void getAllParkingsByNearTransportIsInShouldWork() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where nearTransport in DEFAULT_NEAR_TRANSPORT or UPDATED_NEAR_TRANSPORT
        defaultParkingShouldBeFound("nearTransport.in=" + DEFAULT_NEAR_TRANSPORT + "," + UPDATED_NEAR_TRANSPORT);

        // Get all the parkingList where nearTransport equals to UPDATED_NEAR_TRANSPORT
        defaultParkingShouldNotBeFound("nearTransport.in=" + UPDATED_NEAR_TRANSPORT);
    }

    @Test
    @Transactional
    public void getAllParkingsByNearTransportIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where nearTransport is not null
        defaultParkingShouldBeFound("nearTransport.specified=true");

        // Get all the parkingList where nearTransport is null
        defaultParkingShouldNotBeFound("nearTransport.specified=false");
    }

    @Test
    @Transactional
    public void getAllParkingsBySurveillanceIsEqualToSomething() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where surveillance equals to DEFAULT_SURVEILLANCE
        defaultParkingShouldBeFound("surveillance.equals=" + DEFAULT_SURVEILLANCE);

        // Get all the parkingList where surveillance equals to UPDATED_SURVEILLANCE
        defaultParkingShouldNotBeFound("surveillance.equals=" + UPDATED_SURVEILLANCE);
    }

    @Test
    @Transactional
    public void getAllParkingsBySurveillanceIsInShouldWork() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where surveillance in DEFAULT_SURVEILLANCE or UPDATED_SURVEILLANCE
        defaultParkingShouldBeFound("surveillance.in=" + DEFAULT_SURVEILLANCE + "," + UPDATED_SURVEILLANCE);

        // Get all the parkingList where surveillance equals to UPDATED_SURVEILLANCE
        defaultParkingShouldNotBeFound("surveillance.in=" + UPDATED_SURVEILLANCE);
    }

    @Test
    @Transactional
    public void getAllParkingsBySurveillanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        parkingRepository.saveAndFlush(parking);

        // Get all the parkingList where surveillance is not null
        defaultParkingShouldBeFound("surveillance.specified=true");

        // Get all the parkingList where surveillance is null
        defaultParkingShouldNotBeFound("surveillance.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultParkingShouldBeFound(String filter) throws Exception {
        restParkingMockMvc.perform(get("/api/parkings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parking.getId().intValue())))
            .andExpect(jsonPath("$.[*].parkingType").value(hasItem(DEFAULT_PARKING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].nearTransport").value(hasItem(DEFAULT_NEAR_TRANSPORT.toString())))
            .andExpect(jsonPath("$.[*].surveillance").value(hasItem(DEFAULT_SURVEILLANCE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultParkingShouldNotBeFound(String filter) throws Exception {
        restParkingMockMvc.perform(get("/api/parkings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingParking() throws Exception {
        // Get the parking
        restParkingMockMvc.perform(get("/api/parkings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParking() throws Exception {
        // Initialize the database
        parkingService.save(parking);

        int databaseSizeBeforeUpdate = parkingRepository.findAll().size();

        // Update the parking
        Parking updatedParking = parkingRepository.findOne(parking.getId());
        // Disconnect from session so that the updates on updatedParking are not directly saved in db
        em.detach(updatedParking);
        updatedParking
            .parkingType(UPDATED_PARKING_TYPE)
            .nearTransport(UPDATED_NEAR_TRANSPORT)
            .surveillance(UPDATED_SURVEILLANCE);

        restParkingMockMvc.perform(put("/api/parkings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParking)))
            .andExpect(status().isOk());

        // Validate the Parking in the database
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeUpdate);
        Parking testParking = parkingList.get(parkingList.size() - 1);
        assertThat(testParking.getParkingType()).isEqualTo(UPDATED_PARKING_TYPE);
        assertThat(testParking.getNearTransport()).isEqualTo(UPDATED_NEAR_TRANSPORT);
        assertThat(testParking.getSurveillance()).isEqualTo(UPDATED_SURVEILLANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingParking() throws Exception {
        int databaseSizeBeforeUpdate = parkingRepository.findAll().size();

        // Create the Parking

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParkingMockMvc.perform(put("/api/parkings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parking)))
            .andExpect(status().isCreated());

        // Validate the Parking in the database
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParking() throws Exception {
        // Initialize the database
        parkingService.save(parking);

        int databaseSizeBeforeDelete = parkingRepository.findAll().size();

        // Get the parking
        restParkingMockMvc.perform(delete("/api/parkings/{id}", parking.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Parking> parkingList = parkingRepository.findAll();
        assertThat(parkingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parking.class);
        Parking parking1 = new Parking();
        parking1.setId(1L);
        Parking parking2 = new Parking();
        parking2.setId(parking1.getId());
        assertThat(parking1).isEqualTo(parking2);
        parking2.setId(2L);
        assertThat(parking1).isNotEqualTo(parking2);
        parking1.setId(null);
        assertThat(parking1).isNotEqualTo(parking2);
    }
}
