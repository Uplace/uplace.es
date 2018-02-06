package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;

import com.arnaugarcia.uplace.domain.Hotel;
import com.arnaugarcia.uplace.domain.enumeration.TransactionType;
import com.arnaugarcia.uplace.repository.HotelRepository;
import com.arnaugarcia.uplace.service.HotelService;
import com.arnaugarcia.uplace.service.PropertyService;
import com.arnaugarcia.uplace.web.rest.errors.ExceptionTranslator;
import com.arnaugarcia.uplace.service.HotelQueryService;

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
import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;
/**
 * Test class for the HotelResource REST controller.
 *
 * @see HotelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UplaceApp.class)
public class HotelResourceIntTest {

    private static final String DEFAULT_TITLE = "TEST Apartment";

    private static final Double DEFAULT_PRICE = 0.0;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.now();

    private static final TransactionType DEFAULT_TRANSACCTION = TransactionType.RENT_BUY;

    private static final String DEFAULT_REFERENCE = "AAAAAAA";

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
    private HotelService hotelService;

    @Autowired
    private HotelQueryService hotelQueryService;

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
        final HotelResource hotelResource = new HotelResource(hotelService, hotelQueryService);
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
        Hotel hotel = (Hotel) new Hotel()
            .solarSurface(DEFAULT_SOLAR_SURFACE)
            .m2Edified(DEFAULT_M_2_EDIFIED)
            .numberRooms(DEFAULT_NUMBER_ROOMS)
            .operator(DEFAULT_OPERATOR)
            .pool(DEFAULT_POOL)
            .spa(DEFAULT_SPA)
            .conferenceRoom(DEFAULT_CONFERENCE_ROOM)
            .floorsSR(DEFAULT_FLOORS_SR)
            .floorsBR(DEFAULT_FLOORS_BR)
            .energyCertificate(DEFAULT_ENERGY_CERTIFICATE)
            .created(DEFAULT_CREATED)
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE)
            .transaction(DEFAULT_TRANSACCTION)
            .reference(DEFAULT_REFERENCE);
        return hotel;
    }

    @Before
    public void initTest() {
        hotel = createEntity(em);
    }

    /*@Test
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
    }*/

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
    public void getAllHotelsBySolarSurfaceIsEqualToSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where solarSurface equals to DEFAULT_SOLAR_SURFACE
        defaultHotelShouldBeFound("solarSurface.equals=" + DEFAULT_SOLAR_SURFACE);

        // Get all the hotelList where solarSurface equals to UPDATED_SOLAR_SURFACE
        defaultHotelShouldNotBeFound("solarSurface.equals=" + UPDATED_SOLAR_SURFACE);
    }

    @Test
    @Transactional
    public void getAllHotelsBySolarSurfaceIsInShouldWork() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where solarSurface in DEFAULT_SOLAR_SURFACE or UPDATED_SOLAR_SURFACE
        defaultHotelShouldBeFound("solarSurface.in=" + DEFAULT_SOLAR_SURFACE + "," + UPDATED_SOLAR_SURFACE);

        // Get all the hotelList where solarSurface equals to UPDATED_SOLAR_SURFACE
        defaultHotelShouldNotBeFound("solarSurface.in=" + UPDATED_SOLAR_SURFACE);
    }

    @Test
    @Transactional
    public void getAllHotelsBySolarSurfaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where solarSurface is not null
        defaultHotelShouldBeFound("solarSurface.specified=true");

        // Get all the hotelList where solarSurface is null
        defaultHotelShouldNotBeFound("solarSurface.specified=false");
    }

    @Test
    @Transactional
    public void getAllHotelsBySolarSurfaceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where solarSurface greater than or equals to DEFAULT_SOLAR_SURFACE
        defaultHotelShouldBeFound("solarSurface.greaterOrEqualThan=" + DEFAULT_SOLAR_SURFACE);

        // Get all the hotelList where solarSurface greater than or equals to UPDATED_SOLAR_SURFACE
        defaultHotelShouldNotBeFound("solarSurface.greaterOrEqualThan=" + UPDATED_SOLAR_SURFACE);
    }

    @Test
    @Transactional
    public void getAllHotelsBySolarSurfaceIsLessThanSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where solarSurface less than or equals to DEFAULT_SOLAR_SURFACE
        defaultHotelShouldNotBeFound("solarSurface.lessThan=" + DEFAULT_SOLAR_SURFACE);

        // Get all the hotelList where solarSurface less than or equals to UPDATED_SOLAR_SURFACE
        defaultHotelShouldBeFound("solarSurface.lessThan=" + UPDATED_SOLAR_SURFACE);
    }


    @Test
    @Transactional
    public void getAllHotelsBym2EdifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where m2Edified equals to DEFAULT_M_2_EDIFIED
        defaultHotelShouldBeFound("m2Edified.equals=" + DEFAULT_M_2_EDIFIED);

        // Get all the hotelList where m2Edified equals to UPDATED_M_2_EDIFIED
        defaultHotelShouldNotBeFound("m2Edified.equals=" + UPDATED_M_2_EDIFIED);
    }

    @Test
    @Transactional
    public void getAllHotelsBym2EdifiedIsInShouldWork() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where m2Edified in DEFAULT_M_2_EDIFIED or UPDATED_M_2_EDIFIED
        defaultHotelShouldBeFound("m2Edified.in=" + DEFAULT_M_2_EDIFIED + "," + UPDATED_M_2_EDIFIED);

        // Get all the hotelList where m2Edified equals to UPDATED_M_2_EDIFIED
        defaultHotelShouldNotBeFound("m2Edified.in=" + UPDATED_M_2_EDIFIED);
    }

    @Test
    @Transactional
    public void getAllHotelsBym2EdifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where m2Edified is not null
        defaultHotelShouldBeFound("m2Edified.specified=true");

        // Get all the hotelList where m2Edified is null
        defaultHotelShouldNotBeFound("m2Edified.specified=false");
    }

    @Test
    @Transactional
    public void getAllHotelsBym2EdifiedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where m2Edified greater than or equals to DEFAULT_M_2_EDIFIED
        defaultHotelShouldBeFound("m2Edified.greaterOrEqualThan=" + DEFAULT_M_2_EDIFIED);

        // Get all the hotelList where m2Edified greater than or equals to UPDATED_M_2_EDIFIED
        defaultHotelShouldNotBeFound("m2Edified.greaterOrEqualThan=" + UPDATED_M_2_EDIFIED);
    }

    @Test
    @Transactional
    public void getAllHotelsBym2EdifiedIsLessThanSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where m2Edified less than or equals to DEFAULT_M_2_EDIFIED
        defaultHotelShouldNotBeFound("m2Edified.lessThan=" + DEFAULT_M_2_EDIFIED);

        // Get all the hotelList where m2Edified less than or equals to UPDATED_M_2_EDIFIED
        defaultHotelShouldBeFound("m2Edified.lessThan=" + UPDATED_M_2_EDIFIED);
    }


    @Test
    @Transactional
    public void getAllHotelsByNumberRoomsIsEqualToSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where numberRooms equals to DEFAULT_NUMBER_ROOMS
        defaultHotelShouldBeFound("numberRooms.equals=" + DEFAULT_NUMBER_ROOMS);

        // Get all the hotelList where numberRooms equals to UPDATED_NUMBER_ROOMS
        defaultHotelShouldNotBeFound("numberRooms.equals=" + UPDATED_NUMBER_ROOMS);
    }

    @Test
    @Transactional
    public void getAllHotelsByNumberRoomsIsInShouldWork() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where numberRooms in DEFAULT_NUMBER_ROOMS or UPDATED_NUMBER_ROOMS
        defaultHotelShouldBeFound("numberRooms.in=" + DEFAULT_NUMBER_ROOMS + "," + UPDATED_NUMBER_ROOMS);

        // Get all the hotelList where numberRooms equals to UPDATED_NUMBER_ROOMS
        defaultHotelShouldNotBeFound("numberRooms.in=" + UPDATED_NUMBER_ROOMS);
    }

    @Test
    @Transactional
    public void getAllHotelsByNumberRoomsIsNullOrNotNull() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where numberRooms is not null
        defaultHotelShouldBeFound("numberRooms.specified=true");

        // Get all the hotelList where numberRooms is null
        defaultHotelShouldNotBeFound("numberRooms.specified=false");
    }

    @Test
    @Transactional
    public void getAllHotelsByNumberRoomsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where numberRooms greater than or equals to DEFAULT_NUMBER_ROOMS
        defaultHotelShouldBeFound("numberRooms.greaterOrEqualThan=" + DEFAULT_NUMBER_ROOMS);

        // Get all the hotelList where numberRooms greater than or equals to UPDATED_NUMBER_ROOMS
        defaultHotelShouldNotBeFound("numberRooms.greaterOrEqualThan=" + UPDATED_NUMBER_ROOMS);
    }

    @Test
    @Transactional
    public void getAllHotelsByNumberRoomsIsLessThanSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where numberRooms less than or equals to DEFAULT_NUMBER_ROOMS
        defaultHotelShouldNotBeFound("numberRooms.lessThan=" + DEFAULT_NUMBER_ROOMS);

        // Get all the hotelList where numberRooms less than or equals to UPDATED_NUMBER_ROOMS
        defaultHotelShouldBeFound("numberRooms.lessThan=" + UPDATED_NUMBER_ROOMS);
    }


    @Test
    @Transactional
    public void getAllHotelsByOperatorIsEqualToSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where operator equals to DEFAULT_OPERATOR
        defaultHotelShouldBeFound("operator.equals=" + DEFAULT_OPERATOR);

        // Get all the hotelList where operator equals to UPDATED_OPERATOR
        defaultHotelShouldNotBeFound("operator.equals=" + UPDATED_OPERATOR);
    }

    @Test
    @Transactional
    public void getAllHotelsByOperatorIsInShouldWork() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where operator in DEFAULT_OPERATOR or UPDATED_OPERATOR
        defaultHotelShouldBeFound("operator.in=" + DEFAULT_OPERATOR + "," + UPDATED_OPERATOR);

        // Get all the hotelList where operator equals to UPDATED_OPERATOR
        defaultHotelShouldNotBeFound("operator.in=" + UPDATED_OPERATOR);
    }

    @Test
    @Transactional
    public void getAllHotelsByOperatorIsNullOrNotNull() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where operator is not null
        defaultHotelShouldBeFound("operator.specified=true");

        // Get all the hotelList where operator is null
        defaultHotelShouldNotBeFound("operator.specified=false");
    }

    @Test
    @Transactional
    public void getAllHotelsByPoolIsEqualToSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where pool equals to DEFAULT_POOL
        defaultHotelShouldBeFound("pool.equals=" + DEFAULT_POOL);

        // Get all the hotelList where pool equals to UPDATED_POOL
        defaultHotelShouldNotBeFound("pool.equals=" + UPDATED_POOL);
    }

    @Test
    @Transactional
    public void getAllHotelsByPoolIsInShouldWork() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where pool in DEFAULT_POOL or UPDATED_POOL
        defaultHotelShouldBeFound("pool.in=" + DEFAULT_POOL + "," + UPDATED_POOL);

        // Get all the hotelList where pool equals to UPDATED_POOL
        defaultHotelShouldNotBeFound("pool.in=" + UPDATED_POOL);
    }

    @Test
    @Transactional
    public void getAllHotelsByPoolIsNullOrNotNull() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where pool is not null
        defaultHotelShouldBeFound("pool.specified=true");

        // Get all the hotelList where pool is null
        defaultHotelShouldNotBeFound("pool.specified=false");
    }

    @Test
    @Transactional
    public void getAllHotelsBySpaIsEqualToSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where spa equals to DEFAULT_SPA
        defaultHotelShouldBeFound("spa.equals=" + DEFAULT_SPA);

        // Get all the hotelList where spa equals to UPDATED_SPA
        defaultHotelShouldNotBeFound("spa.equals=" + UPDATED_SPA);
    }

    @Test
    @Transactional
    public void getAllHotelsBySpaIsInShouldWork() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where spa in DEFAULT_SPA or UPDATED_SPA
        defaultHotelShouldBeFound("spa.in=" + DEFAULT_SPA + "," + UPDATED_SPA);

        // Get all the hotelList where spa equals to UPDATED_SPA
        defaultHotelShouldNotBeFound("spa.in=" + UPDATED_SPA);
    }

    @Test
    @Transactional
    public void getAllHotelsBySpaIsNullOrNotNull() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where spa is not null
        defaultHotelShouldBeFound("spa.specified=true");

        // Get all the hotelList where spa is null
        defaultHotelShouldNotBeFound("spa.specified=false");
    }

    @Test
    @Transactional
    public void getAllHotelsByConferenceRoomIsEqualToSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where conferenceRoom equals to DEFAULT_CONFERENCE_ROOM
        defaultHotelShouldBeFound("conferenceRoom.equals=" + DEFAULT_CONFERENCE_ROOM);

        // Get all the hotelList where conferenceRoom equals to UPDATED_CONFERENCE_ROOM
        defaultHotelShouldNotBeFound("conferenceRoom.equals=" + UPDATED_CONFERENCE_ROOM);
    }

    @Test
    @Transactional
    public void getAllHotelsByConferenceRoomIsInShouldWork() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where conferenceRoom in DEFAULT_CONFERENCE_ROOM or UPDATED_CONFERENCE_ROOM
        defaultHotelShouldBeFound("conferenceRoom.in=" + DEFAULT_CONFERENCE_ROOM + "," + UPDATED_CONFERENCE_ROOM);

        // Get all the hotelList where conferenceRoom equals to UPDATED_CONFERENCE_ROOM
        defaultHotelShouldNotBeFound("conferenceRoom.in=" + UPDATED_CONFERENCE_ROOM);
    }

    @Test
    @Transactional
    public void getAllHotelsByConferenceRoomIsNullOrNotNull() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where conferenceRoom is not null
        defaultHotelShouldBeFound("conferenceRoom.specified=true");

        // Get all the hotelList where conferenceRoom is null
        defaultHotelShouldNotBeFound("conferenceRoom.specified=false");
    }

    @Test
    @Transactional
    public void getAllHotelsByFloorsSRIsEqualToSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where floorsSR equals to DEFAULT_FLOORS_SR
        defaultHotelShouldBeFound("floorsSR.equals=" + DEFAULT_FLOORS_SR);

        // Get all the hotelList where floorsSR equals to UPDATED_FLOORS_SR
        defaultHotelShouldNotBeFound("floorsSR.equals=" + UPDATED_FLOORS_SR);
    }

    @Test
    @Transactional
    public void getAllHotelsByFloorsSRIsInShouldWork() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where floorsSR in DEFAULT_FLOORS_SR or UPDATED_FLOORS_SR
        defaultHotelShouldBeFound("floorsSR.in=" + DEFAULT_FLOORS_SR + "," + UPDATED_FLOORS_SR);

        // Get all the hotelList where floorsSR equals to UPDATED_FLOORS_SR
        defaultHotelShouldNotBeFound("floorsSR.in=" + UPDATED_FLOORS_SR);
    }

    @Test
    @Transactional
    public void getAllHotelsByFloorsSRIsNullOrNotNull() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where floorsSR is not null
        defaultHotelShouldBeFound("floorsSR.specified=true");

        // Get all the hotelList where floorsSR is null
        defaultHotelShouldNotBeFound("floorsSR.specified=false");
    }

    @Test
    @Transactional
    public void getAllHotelsByFloorsSRIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where floorsSR greater than or equals to DEFAULT_FLOORS_SR
        defaultHotelShouldBeFound("floorsSR.greaterOrEqualThan=" + DEFAULT_FLOORS_SR);

        // Get all the hotelList where floorsSR greater than or equals to UPDATED_FLOORS_SR
        defaultHotelShouldNotBeFound("floorsSR.greaterOrEqualThan=" + UPDATED_FLOORS_SR);
    }

    @Test
    @Transactional
    public void getAllHotelsByFloorsSRIsLessThanSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where floorsSR less than or equals to DEFAULT_FLOORS_SR
        defaultHotelShouldNotBeFound("floorsSR.lessThan=" + DEFAULT_FLOORS_SR);

        // Get all the hotelList where floorsSR less than or equals to UPDATED_FLOORS_SR
        defaultHotelShouldBeFound("floorsSR.lessThan=" + UPDATED_FLOORS_SR);
    }


    @Test
    @Transactional
    public void getAllHotelsByFloorsBRIsEqualToSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where floorsBR equals to DEFAULT_FLOORS_BR
        defaultHotelShouldBeFound("floorsBR.equals=" + DEFAULT_FLOORS_BR);

        // Get all the hotelList where floorsBR equals to UPDATED_FLOORS_BR
        defaultHotelShouldNotBeFound("floorsBR.equals=" + UPDATED_FLOORS_BR);
    }

    @Test
    @Transactional
    public void getAllHotelsByFloorsBRIsInShouldWork() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where floorsBR in DEFAULT_FLOORS_BR or UPDATED_FLOORS_BR
        defaultHotelShouldBeFound("floorsBR.in=" + DEFAULT_FLOORS_BR + "," + UPDATED_FLOORS_BR);

        // Get all the hotelList where floorsBR equals to UPDATED_FLOORS_BR
        defaultHotelShouldNotBeFound("floorsBR.in=" + UPDATED_FLOORS_BR);
    }

    @Test
    @Transactional
    public void getAllHotelsByFloorsBRIsNullOrNotNull() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where floorsBR is not null
        defaultHotelShouldBeFound("floorsBR.specified=true");

        // Get all the hotelList where floorsBR is null
        defaultHotelShouldNotBeFound("floorsBR.specified=false");
    }

    @Test
    @Transactional
    public void getAllHotelsByFloorsBRIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where floorsBR greater than or equals to DEFAULT_FLOORS_BR
        defaultHotelShouldBeFound("floorsBR.greaterOrEqualThan=" + DEFAULT_FLOORS_BR);

        // Get all the hotelList where floorsBR greater than or equals to UPDATED_FLOORS_BR
        defaultHotelShouldNotBeFound("floorsBR.greaterOrEqualThan=" + UPDATED_FLOORS_BR);
    }

    @Test
    @Transactional
    public void getAllHotelsByFloorsBRIsLessThanSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where floorsBR less than or equals to DEFAULT_FLOORS_BR
        defaultHotelShouldNotBeFound("floorsBR.lessThan=" + DEFAULT_FLOORS_BR);

        // Get all the hotelList where floorsBR less than or equals to UPDATED_FLOORS_BR
        defaultHotelShouldBeFound("floorsBR.lessThan=" + UPDATED_FLOORS_BR);
    }


    @Test
    @Transactional
    public void getAllHotelsByEnergyCertificateIsEqualToSomething() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where energyCertificate equals to DEFAULT_ENERGY_CERTIFICATE
        defaultHotelShouldBeFound("energyCertificate.equals=" + DEFAULT_ENERGY_CERTIFICATE);

        // Get all the hotelList where energyCertificate equals to UPDATED_ENERGY_CERTIFICATE
        defaultHotelShouldNotBeFound("energyCertificate.equals=" + UPDATED_ENERGY_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllHotelsByEnergyCertificateIsInShouldWork() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where energyCertificate in DEFAULT_ENERGY_CERTIFICATE or UPDATED_ENERGY_CERTIFICATE
        defaultHotelShouldBeFound("energyCertificate.in=" + DEFAULT_ENERGY_CERTIFICATE + "," + UPDATED_ENERGY_CERTIFICATE);

        // Get all the hotelList where energyCertificate equals to UPDATED_ENERGY_CERTIFICATE
        defaultHotelShouldNotBeFound("energyCertificate.in=" + UPDATED_ENERGY_CERTIFICATE);
    }

    @Test
    @Transactional
    public void getAllHotelsByEnergyCertificateIsNullOrNotNull() throws Exception {
        // Initialize the database
        hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList where energyCertificate is not null
        defaultHotelShouldBeFound("energyCertificate.specified=true");

        // Get all the hotelList where energyCertificate is null
        defaultHotelShouldNotBeFound("energyCertificate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultHotelShouldBeFound(String filter) throws Exception {
        restHotelMockMvc.perform(get("/api/hotels?sort=id,desc&" + filter))
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

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultHotelShouldNotBeFound(String filter) throws Exception {
        restHotelMockMvc.perform(get("/api/hotels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
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
        hotelService.save(hotel);

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

    /*@Test
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
    }*/

    @Test
    @Transactional
    public void deleteHotel() throws Exception {
        // Initialize the database
        hotelService.save(hotel);

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
