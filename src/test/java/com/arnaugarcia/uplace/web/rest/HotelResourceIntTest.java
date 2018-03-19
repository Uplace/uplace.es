package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;

import com.arnaugarcia.uplace.domain.Hotel;
import com.arnaugarcia.uplace.repository.HotelRepository;
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
import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;
/**
 * Test class for the HotelResource REST controller.
 *
 * @see HotelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UplaceApp.class)
public class HotelResourceIntTest {

    private static final Integer DEFAULT_SOLAR_SURFACE = 1;
    private static final Integer UPDATED_SOLAR_SURFACE = 2;

    private static final Integer DEFAULT_M_2_EDIFIED = 1;
    private static final Integer UPDATED_M_2_EDIFIED = 2;

    private static final Integer DEFAULT_NUMBER_ROOMS = 1;
    private static final Integer UPDATED_NUMBER_ROOMS = 2;

    private static final Select DEFAULT_OPERATOR = Select.YES;
    private static final Select UPDATED_OPERATOR = Select.NO;

    private static final Select DEFAULT_POOL = Select.YES;
    private static final Select UPDATED_POOL = Select.NO;

    private static final Select DEFAULT_SPA = Select.YES;
    private static final Select UPDATED_SPA = Select.NO;

    private static final Select DEFAULT_CONFERENCE_ROOM = Select.YES;
    private static final Select UPDATED_CONFERENCE_ROOM = Select.NO;

    private static final Integer DEFAULT_FLOORS_SR = 1;
    private static final Integer UPDATED_FLOORS_SR = 2;

    private static final Integer DEFAULT_FLOORS_BR = 1;
    private static final Integer UPDATED_FLOORS_BR = 2;

    private static final EnergyCertificate DEFAULT_ENERGY_CERTIFICATE = EnergyCertificate.A;
    private static final EnergyCertificate UPDATED_ENERGY_CERTIFICATE = EnergyCertificate.B;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHotelMockMvc;

    private Hotel hotel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HotelResource hotelResource = new HotelResource(hotelRepository);
        this.restHotelMockMvc = MockMvcBuilders.standaloneSetup(hotelResource)
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
    public static Hotel createEntity(EntityManager em) {
        Hotel hotel = new Hotel()
            .solarSurface(DEFAULT_SOLAR_SURFACE)
            .m2Edified(DEFAULT_M_2_EDIFIED)
            .numberRooms(DEFAULT_NUMBER_ROOMS)
            .operator(DEFAULT_OPERATOR)
            .pool(DEFAULT_POOL)
            .spa(DEFAULT_SPA)
            .conferenceRoom(DEFAULT_CONFERENCE_ROOM)
            .floorsSR(DEFAULT_FLOORS_SR)
            .floorsBR(DEFAULT_FLOORS_BR)
            .energyCertificate(DEFAULT_ENERGY_CERTIFICATE);
        return hotel;
    }

    @Before
    public void initTest() {
        hotel = createEntity(em);
    }

    @Test
    @Transactional
    public void createHotel() throws Exception {
        int databaseSizeBeforeCreate = hotelRepository.findAll().size();

        // Create the Hotel
        restHotelMockMvc.perform(post("/api/hotels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotel)))
            .andExpect(status().isCreated());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeCreate + 1);
        Hotel testHotel = hotelList.get(hotelList.size() - 1);
        assertThat(testHotel.getSolarSurface()).isEqualTo(DEFAULT_SOLAR_SURFACE);
        assertThat(testHotel.getm2Edified()).isEqualTo(DEFAULT_M_2_EDIFIED);
        assertThat(testHotel.getNumberRooms()).isEqualTo(DEFAULT_NUMBER_ROOMS);
        assertThat(testHotel.getOperator()).isEqualTo(DEFAULT_OPERATOR);
        assertThat(testHotel.getPool()).isEqualTo(DEFAULT_POOL);
        assertThat(testHotel.getSpa()).isEqualTo(DEFAULT_SPA);
        assertThat(testHotel.getConferenceRoom()).isEqualTo(DEFAULT_CONFERENCE_ROOM);
        assertThat(testHotel.getFloorsSR()).isEqualTo(DEFAULT_FLOORS_SR);
        assertThat(testHotel.getFloorsBR()).isEqualTo(DEFAULT_FLOORS_BR);
        assertThat(testHotel.getEnergyCertificate()).isEqualTo(DEFAULT_ENERGY_CERTIFICATE);
    }

    @Test
    @Transactional
    public void createHotelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hotelRepository.findAll().size();

        // Create the Hotel with an existing ID
        hotel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHotelMockMvc.perform(post("/api/hotels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotel)))
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHotels() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList
        restHotelMockMvc.perform(get("/api/hotels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotel.getId().intValue())))
            .andExpect(jsonPath("$.[*].solarSurface").value(hasItem(DEFAULT_SOLAR_SURFACE)))
            .andExpect(jsonPath("$.[*].m2Edified").value(hasItem(DEFAULT_M_2_EDIFIED)))
            .andExpect(jsonPath("$.[*].numberRooms").value(hasItem(DEFAULT_NUMBER_ROOMS)))
            .andExpect(jsonPath("$.[*].operator").value(hasItem(DEFAULT_OPERATOR.toString())))
            .andExpect(jsonPath("$.[*].pool").value(hasItem(DEFAULT_POOL.toString())))
            .andExpect(jsonPath("$.[*].spa").value(hasItem(DEFAULT_SPA.toString())))
            .andExpect(jsonPath("$.[*].conferenceRoom").value(hasItem(DEFAULT_CONFERENCE_ROOM.toString())))
            .andExpect(jsonPath("$.[*].floorsSR").value(hasItem(DEFAULT_FLOORS_SR)))
            .andExpect(jsonPath("$.[*].floorsBR").value(hasItem(DEFAULT_FLOORS_BR)))
            .andExpect(jsonPath("$.[*].energyCertificate").value(hasItem(DEFAULT_ENERGY_CERTIFICATE.toString())));
    }

    @Test
    @Transactional
    public void getHotel() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get the hotel
        restHotelMockMvc.perform(get("/api/hotels/{id}", hotel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hotel.getId().intValue()))
            .andExpect(jsonPath("$.solarSurface").value(DEFAULT_SOLAR_SURFACE))
            .andExpect(jsonPath("$.m2Edified").value(DEFAULT_M_2_EDIFIED))
            .andExpect(jsonPath("$.numberRooms").value(DEFAULT_NUMBER_ROOMS))
            .andExpect(jsonPath("$.operator").value(DEFAULT_OPERATOR.toString()))
            .andExpect(jsonPath("$.pool").value(DEFAULT_POOL.toString()))
            .andExpect(jsonPath("$.spa").value(DEFAULT_SPA.toString()))
            .andExpect(jsonPath("$.conferenceRoom").value(DEFAULT_CONFERENCE_ROOM.toString()))
            .andExpect(jsonPath("$.floorsSR").value(DEFAULT_FLOORS_SR))
            .andExpect(jsonPath("$.floorsBR").value(DEFAULT_FLOORS_BR))
            .andExpect(jsonPath("$.energyCertificate").value(DEFAULT_ENERGY_CERTIFICATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHotel() throws Exception {
        // Get the hotel
        restHotelMockMvc.perform(get("/api/hotels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHotel() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);
        int databaseSizeBeforeUpdate = hotelRepository.findAll().size();

        // Update the hotel
        Hotel updatedHotel = hotelRepository.findOne(hotel.getId());
        // Disconnect from session so that the updates on updatedHotel are not directly saved in db
        em.detach(updatedHotel);
        updatedHotel
            .solarSurface(UPDATED_SOLAR_SURFACE)
            .m2Edified(UPDATED_M_2_EDIFIED)
            .numberRooms(UPDATED_NUMBER_ROOMS)
            .operator(UPDATED_OPERATOR)
            .pool(UPDATED_POOL)
            .spa(UPDATED_SPA)
            .conferenceRoom(UPDATED_CONFERENCE_ROOM)
            .floorsSR(UPDATED_FLOORS_SR)
            .floorsBR(UPDATED_FLOORS_BR)
            .energyCertificate(UPDATED_ENERGY_CERTIFICATE);

        restHotelMockMvc.perform(put("/api/hotels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHotel)))
            .andExpect(status().isOk());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeUpdate);
        Hotel testHotel = hotelList.get(hotelList.size() - 1);
        assertThat(testHotel.getSolarSurface()).isEqualTo(UPDATED_SOLAR_SURFACE);
        assertThat(testHotel.getm2Edified()).isEqualTo(UPDATED_M_2_EDIFIED);
        assertThat(testHotel.getNumberRooms()).isEqualTo(UPDATED_NUMBER_ROOMS);
        assertThat(testHotel.getOperator()).isEqualTo(UPDATED_OPERATOR);
        assertThat(testHotel.getPool()).isEqualTo(UPDATED_POOL);
        assertThat(testHotel.getSpa()).isEqualTo(UPDATED_SPA);
        assertThat(testHotel.getConferenceRoom()).isEqualTo(UPDATED_CONFERENCE_ROOM);
        assertThat(testHotel.getFloorsSR()).isEqualTo(UPDATED_FLOORS_SR);
        assertThat(testHotel.getFloorsBR()).isEqualTo(UPDATED_FLOORS_BR);
        assertThat(testHotel.getEnergyCertificate()).isEqualTo(UPDATED_ENERGY_CERTIFICATE);
    }

    @Test
    @Transactional
    public void updateNonExistingHotel() throws Exception {
        int databaseSizeBeforeUpdate = hotelRepository.findAll().size();

        // Create the Hotel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHotelMockMvc.perform(put("/api/hotels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hotel)))
            .andExpect(status().isCreated());

        // Validate the Hotel in the database
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHotel() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);
        int databaseSizeBeforeDelete = hotelRepository.findAll().size();

        // Get the hotel
        restHotelMockMvc.perform(delete("/api/hotels/{id}", hotel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Hotel> hotelList = hotelRepository.findAll();
        assertThat(hotelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hotel.class);
        Hotel hotel1 = new Hotel();
        hotel1.setId(1L);
        Hotel hotel2 = new Hotel();
        hotel2.setId(hotel1.getId());
        assertThat(hotel1).isEqualTo(hotel2);
        hotel2.setId(2L);
        assertThat(hotel1).isNotEqualTo(hotel2);
        hotel1.setId(null);
        assertThat(hotel1).isNotEqualTo(hotel2);
    }
}
