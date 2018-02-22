package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;

import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.domain.Location;
import com.arnaugarcia.uplace.domain.Photo;
import com.arnaugarcia.uplace.domain.Agent;
import com.arnaugarcia.uplace.repository.PropertyRepository;
import com.arnaugarcia.uplace.service.PropertyService;
import com.arnaugarcia.uplace.web.rest.errors.ExceptionTranslator;
import com.arnaugarcia.uplace.service.dto.PropertyCriteria;
import com.arnaugarcia.uplace.service.PropertyQueryService;

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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.arnaugarcia.uplace.web.rest.TestUtil.sameInstant;
import static com.arnaugarcia.uplace.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.arnaugarcia.uplace.domain.enumeration.TransactionType;
/**
 * Test class for the PropertyResource REST controller.
 *
 * @see PropertyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UplaceApp.class)
public class PropertyResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final TransactionType DEFAULT_TRANSACTION = TransactionType.RENT;
    private static final TransactionType UPDATED_TRANSACTION = TransactionType.BUY;

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE_SELL = 1D;
    private static final Double UPDATED_PRICE_SELL = 2D;

    private static final Double DEFAULT_PRICE_RENT = 1D;
    private static final Double UPDATED_PRICE_RENT = 2D;

    private static final Integer DEFAULT_YEAR_CONSTRUCTION = 500;
    private static final Integer UPDATED_YEAR_CONSTRUCTION = 501;

    private static final Boolean DEFAULT_NEW_CREATION = false;
    private static final Boolean UPDATED_NEW_CREATION = true;

    private static final Boolean DEFAULT_VISIBLE = false;
    private static final Boolean UPDATED_VISIBLE = true;

    private static final Integer DEFAULT_SURFACE = 1;
    private static final Integer UPDATED_SURFACE = 2;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyQueryService propertyQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPropertyMockMvc;

    private Property property;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PropertyResource propertyResource = new PropertyResource(propertyService, propertyQueryService);
        this.restPropertyMockMvc = MockMvcBuilders.standaloneSetup(propertyResource)
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
    public static Property createEntity(EntityManager em) {
        Property property = new Property()
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED)
            .description(DEFAULT_DESCRIPTION)
            .transaction(DEFAULT_TRANSACTION)
            .reference(DEFAULT_REFERENCE)
            .priceSell(DEFAULT_PRICE_SELL)
            .priceRent(DEFAULT_PRICE_RENT)
            .yearConstruction(DEFAULT_YEAR_CONSTRUCTION)
            .newCreation(DEFAULT_NEW_CREATION)
            .visible(DEFAULT_VISIBLE)
            .surface(DEFAULT_SURFACE);
        return property;
    }

    @Before
    public void initTest() {
        property = createEntity(em);
    }

    @Test
    @Transactional
    public void createProperty() throws Exception {
        int databaseSizeBeforeCreate = propertyRepository.findAll().size();

        // Create the Property
        restPropertyMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(property)))
            .andExpect(status().isCreated());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeCreate + 1);
        Property testProperty = propertyList.get(propertyList.size() - 1);
        assertThat(testProperty.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProperty.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProperty.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testProperty.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        assertThat(testProperty.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProperty.getTransaction()).isEqualTo(DEFAULT_TRANSACTION);
        assertThat(testProperty.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testProperty.getPriceSell()).isEqualTo(DEFAULT_PRICE_SELL);
        assertThat(testProperty.getPriceRent()).isEqualTo(DEFAULT_PRICE_RENT);
        assertThat(testProperty.getYearConstruction()).isEqualTo(DEFAULT_YEAR_CONSTRUCTION);
        assertThat(testProperty.isNewCreation()).isEqualTo(DEFAULT_NEW_CREATION);
        assertThat(testProperty.isVisible()).isEqualTo(DEFAULT_VISIBLE);
        assertThat(testProperty.getSurface()).isEqualTo(DEFAULT_SURFACE);
    }

    @Test
    @Transactional
    public void createPropertyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propertyRepository.findAll().size();

        // Create the Property with an existing ID
        property.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropertyMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(property)))
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = propertyRepository.findAll().size();
        // set the field null
        property.setTitle(null);

        // Create the Property, which fails.

        restPropertyMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(property)))
            .andExpect(status().isBadRequest());

        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = propertyRepository.findAll().size();
        // set the field null
        property.setPrice(null);

        // Create the Property, which fails.

        restPropertyMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(property)))
            .andExpect(status().isBadRequest());

        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = propertyRepository.findAll().size();
        // set the field null
        property.setCreated(null);

        // Create the Property, which fails.

        restPropertyMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(property)))
            .andExpect(status().isBadRequest());

        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionIsRequired() throws Exception {
        int databaseSizeBeforeTest = propertyRepository.findAll().size();
        // set the field null
        property.setTransaction(null);

        // Create the Property, which fails.

        restPropertyMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(property)))
            .andExpect(status().isBadRequest());

        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = propertyRepository.findAll().size();
        // set the field null
        property.setReference(null);

        // Create the Property, which fails.

        restPropertyMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(property)))
            .andExpect(status().isBadRequest());

        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProperties() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList
        restPropertyMockMvc.perform(get("/api/properties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(property.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(sameInstant(DEFAULT_UPDATED))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].transaction").value(hasItem(DEFAULT_TRANSACTION.toString())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].priceSell").value(hasItem(DEFAULT_PRICE_SELL.doubleValue())))
            .andExpect(jsonPath("$.[*].priceRent").value(hasItem(DEFAULT_PRICE_RENT.doubleValue())))
            .andExpect(jsonPath("$.[*].yearConstruction").value(hasItem(DEFAULT_YEAR_CONSTRUCTION)))
            .andExpect(jsonPath("$.[*].newCreation").value(hasItem(DEFAULT_NEW_CREATION.booleanValue())))
            .andExpect(jsonPath("$.[*].visible").value(hasItem(DEFAULT_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].surface").value(hasItem(DEFAULT_SURFACE)));
    }

    @Test
    @Transactional
    public void getProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get the property
        restPropertyMockMvc.perform(get("/api/properties/{id}", property.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(property.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.updated").value(sameInstant(DEFAULT_UPDATED)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.transaction").value(DEFAULT_TRANSACTION.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.priceSell").value(DEFAULT_PRICE_SELL.doubleValue()))
            .andExpect(jsonPath("$.priceRent").value(DEFAULT_PRICE_RENT.doubleValue()))
            .andExpect(jsonPath("$.yearConstruction").value(DEFAULT_YEAR_CONSTRUCTION))
            .andExpect(jsonPath("$.newCreation").value(DEFAULT_NEW_CREATION.booleanValue()))
            .andExpect(jsonPath("$.visible").value(DEFAULT_VISIBLE.booleanValue()))
            .andExpect(jsonPath("$.surface").value(DEFAULT_SURFACE));
    }

    @Test
    @Transactional
    public void getAllPropertiesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where title equals to DEFAULT_TITLE
        defaultPropertyShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the propertyList where title equals to UPDATED_TITLE
        defaultPropertyShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllPropertiesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultPropertyShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the propertyList where title equals to UPDATED_TITLE
        defaultPropertyShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllPropertiesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where title is not null
        defaultPropertyShouldBeFound("title.specified=true");

        // Get all the propertyList where title is null
        defaultPropertyShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropertiesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where price equals to DEFAULT_PRICE
        defaultPropertyShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the propertyList where price equals to UPDATED_PRICE
        defaultPropertyShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllPropertiesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultPropertyShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the propertyList where price equals to UPDATED_PRICE
        defaultPropertyShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllPropertiesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where price is not null
        defaultPropertyShouldBeFound("price.specified=true");

        // Get all the propertyList where price is null
        defaultPropertyShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropertiesByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where created equals to DEFAULT_CREATED
        defaultPropertyShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the propertyList where created equals to UPDATED_CREATED
        defaultPropertyShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPropertiesByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultPropertyShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the propertyList where created equals to UPDATED_CREATED
        defaultPropertyShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPropertiesByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where created is not null
        defaultPropertyShouldBeFound("created.specified=true");

        // Get all the propertyList where created is null
        defaultPropertyShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropertiesByCreatedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where created greater than or equals to DEFAULT_CREATED
        defaultPropertyShouldBeFound("created.greaterOrEqualThan=" + DEFAULT_CREATED);

        // Get all the propertyList where created greater than or equals to UPDATED_CREATED
        defaultPropertyShouldNotBeFound("created.greaterOrEqualThan=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    public void getAllPropertiesByCreatedIsLessThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where created less than or equals to DEFAULT_CREATED
        defaultPropertyShouldNotBeFound("created.lessThan=" + DEFAULT_CREATED);

        // Get all the propertyList where created less than or equals to UPDATED_CREATED
        defaultPropertyShouldBeFound("created.lessThan=" + UPDATED_CREATED);
    }


    @Test
    @Transactional
    public void getAllPropertiesByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where updated equals to DEFAULT_UPDATED
        defaultPropertyShouldBeFound("updated.equals=" + DEFAULT_UPDATED);

        // Get all the propertyList where updated equals to UPDATED_UPDATED
        defaultPropertyShouldNotBeFound("updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPropertiesByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where updated in DEFAULT_UPDATED or UPDATED_UPDATED
        defaultPropertyShouldBeFound("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED);

        // Get all the propertyList where updated equals to UPDATED_UPDATED
        defaultPropertyShouldNotBeFound("updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPropertiesByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where updated is not null
        defaultPropertyShouldBeFound("updated.specified=true");

        // Get all the propertyList where updated is null
        defaultPropertyShouldNotBeFound("updated.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropertiesByUpdatedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where updated greater than or equals to DEFAULT_UPDATED
        defaultPropertyShouldBeFound("updated.greaterOrEqualThan=" + DEFAULT_UPDATED);

        // Get all the propertyList where updated greater than or equals to UPDATED_UPDATED
        defaultPropertyShouldNotBeFound("updated.greaterOrEqualThan=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPropertiesByUpdatedIsLessThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where updated less than or equals to DEFAULT_UPDATED
        defaultPropertyShouldNotBeFound("updated.lessThan=" + DEFAULT_UPDATED);

        // Get all the propertyList where updated less than or equals to UPDATED_UPDATED
        defaultPropertyShouldBeFound("updated.lessThan=" + UPDATED_UPDATED);
    }


    @Test
    @Transactional
    public void getAllPropertiesByTransactionIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where transaction equals to DEFAULT_TRANSACTION
        defaultPropertyShouldBeFound("transaction.equals=" + DEFAULT_TRANSACTION);

        // Get all the propertyList where transaction equals to UPDATED_TRANSACTION
        defaultPropertyShouldNotBeFound("transaction.equals=" + UPDATED_TRANSACTION);
    }

    @Test
    @Transactional
    public void getAllPropertiesByTransactionIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where transaction in DEFAULT_TRANSACTION or UPDATED_TRANSACTION
        defaultPropertyShouldBeFound("transaction.in=" + DEFAULT_TRANSACTION + "," + UPDATED_TRANSACTION);

        // Get all the propertyList where transaction equals to UPDATED_TRANSACTION
        defaultPropertyShouldNotBeFound("transaction.in=" + UPDATED_TRANSACTION);
    }

    @Test
    @Transactional
    public void getAllPropertiesByTransactionIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where transaction is not null
        defaultPropertyShouldBeFound("transaction.specified=true");

        // Get all the propertyList where transaction is null
        defaultPropertyShouldNotBeFound("transaction.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropertiesByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where reference equals to DEFAULT_REFERENCE
        defaultPropertyShouldBeFound("reference.equals=" + DEFAULT_REFERENCE);

        // Get all the propertyList where reference equals to UPDATED_REFERENCE
        defaultPropertyShouldNotBeFound("reference.equals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllPropertiesByReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where reference in DEFAULT_REFERENCE or UPDATED_REFERENCE
        defaultPropertyShouldBeFound("reference.in=" + DEFAULT_REFERENCE + "," + UPDATED_REFERENCE);

        // Get all the propertyList where reference equals to UPDATED_REFERENCE
        defaultPropertyShouldNotBeFound("reference.in=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllPropertiesByReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where reference is not null
        defaultPropertyShouldBeFound("reference.specified=true");

        // Get all the propertyList where reference is null
        defaultPropertyShouldNotBeFound("reference.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropertiesByPriceSellIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where priceSell equals to DEFAULT_PRICE_SELL
        defaultPropertyShouldBeFound("priceSell.equals=" + DEFAULT_PRICE_SELL);

        // Get all the propertyList where priceSell equals to UPDATED_PRICE_SELL
        defaultPropertyShouldNotBeFound("priceSell.equals=" + UPDATED_PRICE_SELL);
    }

    @Test
    @Transactional
    public void getAllPropertiesByPriceSellIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where priceSell in DEFAULT_PRICE_SELL or UPDATED_PRICE_SELL
        defaultPropertyShouldBeFound("priceSell.in=" + DEFAULT_PRICE_SELL + "," + UPDATED_PRICE_SELL);

        // Get all the propertyList where priceSell equals to UPDATED_PRICE_SELL
        defaultPropertyShouldNotBeFound("priceSell.in=" + UPDATED_PRICE_SELL);
    }

    @Test
    @Transactional
    public void getAllPropertiesByPriceSellIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where priceSell is not null
        defaultPropertyShouldBeFound("priceSell.specified=true");

        // Get all the propertyList where priceSell is null
        defaultPropertyShouldNotBeFound("priceSell.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropertiesByPriceRentIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where priceRent equals to DEFAULT_PRICE_RENT
        defaultPropertyShouldBeFound("priceRent.equals=" + DEFAULT_PRICE_RENT);

        // Get all the propertyList where priceRent equals to UPDATED_PRICE_RENT
        defaultPropertyShouldNotBeFound("priceRent.equals=" + UPDATED_PRICE_RENT);
    }

    @Test
    @Transactional
    public void getAllPropertiesByPriceRentIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where priceRent in DEFAULT_PRICE_RENT or UPDATED_PRICE_RENT
        defaultPropertyShouldBeFound("priceRent.in=" + DEFAULT_PRICE_RENT + "," + UPDATED_PRICE_RENT);

        // Get all the propertyList where priceRent equals to UPDATED_PRICE_RENT
        defaultPropertyShouldNotBeFound("priceRent.in=" + UPDATED_PRICE_RENT);
    }

    @Test
    @Transactional
    public void getAllPropertiesByPriceRentIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where priceRent is not null
        defaultPropertyShouldBeFound("priceRent.specified=true");

        // Get all the propertyList where priceRent is null
        defaultPropertyShouldNotBeFound("priceRent.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropertiesByYearConstructionIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where yearConstruction equals to DEFAULT_YEAR_CONSTRUCTION
        defaultPropertyShouldBeFound("yearConstruction.equals=" + DEFAULT_YEAR_CONSTRUCTION);

        // Get all the propertyList where yearConstruction equals to UPDATED_YEAR_CONSTRUCTION
        defaultPropertyShouldNotBeFound("yearConstruction.equals=" + UPDATED_YEAR_CONSTRUCTION);
    }

    @Test
    @Transactional
    public void getAllPropertiesByYearConstructionIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where yearConstruction in DEFAULT_YEAR_CONSTRUCTION or UPDATED_YEAR_CONSTRUCTION
        defaultPropertyShouldBeFound("yearConstruction.in=" + DEFAULT_YEAR_CONSTRUCTION + "," + UPDATED_YEAR_CONSTRUCTION);

        // Get all the propertyList where yearConstruction equals to UPDATED_YEAR_CONSTRUCTION
        defaultPropertyShouldNotBeFound("yearConstruction.in=" + UPDATED_YEAR_CONSTRUCTION);
    }

    @Test
    @Transactional
    public void getAllPropertiesByYearConstructionIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where yearConstruction is not null
        defaultPropertyShouldBeFound("yearConstruction.specified=true");

        // Get all the propertyList where yearConstruction is null
        defaultPropertyShouldNotBeFound("yearConstruction.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropertiesByYearConstructionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where yearConstruction greater than or equals to DEFAULT_YEAR_CONSTRUCTION
        defaultPropertyShouldBeFound("yearConstruction.greaterOrEqualThan=" + DEFAULT_YEAR_CONSTRUCTION);

        // Get all the propertyList where yearConstruction greater than or equals to UPDATED_YEAR_CONSTRUCTION
        defaultPropertyShouldNotBeFound("yearConstruction.greaterOrEqualThan=" + UPDATED_YEAR_CONSTRUCTION);
    }

    @Test
    @Transactional
    public void getAllPropertiesByYearConstructionIsLessThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where yearConstruction less than or equals to DEFAULT_YEAR_CONSTRUCTION
        defaultPropertyShouldNotBeFound("yearConstruction.lessThan=" + DEFAULT_YEAR_CONSTRUCTION);

        // Get all the propertyList where yearConstruction less than or equals to UPDATED_YEAR_CONSTRUCTION
        defaultPropertyShouldBeFound("yearConstruction.lessThan=" + UPDATED_YEAR_CONSTRUCTION);
    }


    @Test
    @Transactional
    public void getAllPropertiesByNewCreationIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where newCreation equals to DEFAULT_NEW_CREATION
        defaultPropertyShouldBeFound("newCreation.equals=" + DEFAULT_NEW_CREATION);

        // Get all the propertyList where newCreation equals to UPDATED_NEW_CREATION
        defaultPropertyShouldNotBeFound("newCreation.equals=" + UPDATED_NEW_CREATION);
    }

    @Test
    @Transactional
    public void getAllPropertiesByNewCreationIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where newCreation in DEFAULT_NEW_CREATION or UPDATED_NEW_CREATION
        defaultPropertyShouldBeFound("newCreation.in=" + DEFAULT_NEW_CREATION + "," + UPDATED_NEW_CREATION);

        // Get all the propertyList where newCreation equals to UPDATED_NEW_CREATION
        defaultPropertyShouldNotBeFound("newCreation.in=" + UPDATED_NEW_CREATION);
    }

    @Test
    @Transactional
    public void getAllPropertiesByNewCreationIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where newCreation is not null
        defaultPropertyShouldBeFound("newCreation.specified=true");

        // Get all the propertyList where newCreation is null
        defaultPropertyShouldNotBeFound("newCreation.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropertiesByVisibleIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where visible equals to DEFAULT_VISIBLE
        defaultPropertyShouldBeFound("visible.equals=" + DEFAULT_VISIBLE);

        // Get all the propertyList where visible equals to UPDATED_VISIBLE
        defaultPropertyShouldNotBeFound("visible.equals=" + UPDATED_VISIBLE);
    }

    @Test
    @Transactional
    public void getAllPropertiesByVisibleIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where visible in DEFAULT_VISIBLE or UPDATED_VISIBLE
        defaultPropertyShouldBeFound("visible.in=" + DEFAULT_VISIBLE + "," + UPDATED_VISIBLE);

        // Get all the propertyList where visible equals to UPDATED_VISIBLE
        defaultPropertyShouldNotBeFound("visible.in=" + UPDATED_VISIBLE);
    }

    @Test
    @Transactional
    public void getAllPropertiesByVisibleIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where visible is not null
        defaultPropertyShouldBeFound("visible.specified=true");

        // Get all the propertyList where visible is null
        defaultPropertyShouldNotBeFound("visible.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropertiesBySurfaceIsEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where surface equals to DEFAULT_SURFACE
        defaultPropertyShouldBeFound("surface.equals=" + DEFAULT_SURFACE);

        // Get all the propertyList where surface equals to UPDATED_SURFACE
        defaultPropertyShouldNotBeFound("surface.equals=" + UPDATED_SURFACE);
    }

    @Test
    @Transactional
    public void getAllPropertiesBySurfaceIsInShouldWork() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where surface in DEFAULT_SURFACE or UPDATED_SURFACE
        defaultPropertyShouldBeFound("surface.in=" + DEFAULT_SURFACE + "," + UPDATED_SURFACE);

        // Get all the propertyList where surface equals to UPDATED_SURFACE
        defaultPropertyShouldNotBeFound("surface.in=" + UPDATED_SURFACE);
    }

    @Test
    @Transactional
    public void getAllPropertiesBySurfaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where surface is not null
        defaultPropertyShouldBeFound("surface.specified=true");

        // Get all the propertyList where surface is null
        defaultPropertyShouldNotBeFound("surface.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropertiesBySurfaceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where surface greater than or equals to DEFAULT_SURFACE
        defaultPropertyShouldBeFound("surface.greaterOrEqualThan=" + DEFAULT_SURFACE);

        // Get all the propertyList where surface greater than or equals to UPDATED_SURFACE
        defaultPropertyShouldNotBeFound("surface.greaterOrEqualThan=" + UPDATED_SURFACE);
    }

    @Test
    @Transactional
    public void getAllPropertiesBySurfaceIsLessThanSomething() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList where surface less than or equals to DEFAULT_SURFACE
        defaultPropertyShouldNotBeFound("surface.lessThan=" + DEFAULT_SURFACE);

        // Get all the propertyList where surface less than or equals to UPDATED_SURFACE
        defaultPropertyShouldBeFound("surface.lessThan=" + UPDATED_SURFACE);
    }


    @Test
    @Transactional
    public void getAllPropertiesByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        Location location = LocationResourceIntTest.createEntity(em);
        em.persist(location);
        em.flush();
        property.setLocation(location);
        propertyRepository.saveAndFlush(property);
        Long locationId = location.getId();

        // Get all the propertyList where location equals to locationId
        defaultPropertyShouldBeFound("locationId.equals=" + locationId);

        // Get all the propertyList where location equals to locationId + 1
        defaultPropertyShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }


    @Test
    @Transactional
    public void getAllPropertiesByPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        Photo photo = PhotoResourceIntTest.createEntity(em);
        em.persist(photo);
        em.flush();
        property.addPhoto(photo);
        propertyRepository.saveAndFlush(property);
        Long photoId = photo.getId();

        // Get all the propertyList where photo equals to photoId
        defaultPropertyShouldBeFound("photoId.equals=" + photoId);

        // Get all the propertyList where photo equals to photoId + 1
        defaultPropertyShouldNotBeFound("photoId.equals=" + (photoId + 1));
    }


    @Test
    @Transactional
    public void getAllPropertiesByManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        Agent manager = AgentResourceIntTest.createEntity(em);
        em.persist(manager);
        em.flush();
        property.addManager(manager);
        propertyRepository.saveAndFlush(property);
        Long managerId = manager.getId();

        // Get all the propertyList where manager equals to managerId
        defaultPropertyShouldBeFound("managerId.equals=" + managerId);

        // Get all the propertyList where manager equals to managerId + 1
        defaultPropertyShouldNotBeFound("managerId.equals=" + (managerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPropertyShouldBeFound(String filter) throws Exception {
        restPropertyMockMvc.perform(get("/api/properties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(property.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(sameInstant(DEFAULT_UPDATED))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].transaction").value(hasItem(DEFAULT_TRANSACTION.toString())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].priceSell").value(hasItem(DEFAULT_PRICE_SELL.doubleValue())))
            .andExpect(jsonPath("$.[*].priceRent").value(hasItem(DEFAULT_PRICE_RENT.doubleValue())))
            .andExpect(jsonPath("$.[*].yearConstruction").value(hasItem(DEFAULT_YEAR_CONSTRUCTION)))
            .andExpect(jsonPath("$.[*].newCreation").value(hasItem(DEFAULT_NEW_CREATION.booleanValue())))
            .andExpect(jsonPath("$.[*].visible").value(hasItem(DEFAULT_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].surface").value(hasItem(DEFAULT_SURFACE)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPropertyShouldNotBeFound(String filter) throws Exception {
        restPropertyMockMvc.perform(get("/api/properties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingProperty() throws Exception {
        // Get the property
        restPropertyMockMvc.perform(get("/api/properties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProperty() throws Exception {
        // Initialize the database
        propertyService.save(property);

        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();

        // Update the property
        Property updatedProperty = propertyRepository.findOne(property.getId());
        // Disconnect from session so that the updates on updatedProperty are not directly saved in db
        em.detach(updatedProperty);
        updatedProperty
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE)
            .created(UPDATED_CREATED)
            .updated(UPDATED_UPDATED)
            .description(UPDATED_DESCRIPTION)
            .transaction(UPDATED_TRANSACTION)
            .reference(UPDATED_REFERENCE)
            .priceSell(UPDATED_PRICE_SELL)
            .priceRent(UPDATED_PRICE_RENT)
            .yearConstruction(UPDATED_YEAR_CONSTRUCTION)
            .newCreation(UPDATED_NEW_CREATION)
            .visible(UPDATED_VISIBLE)
            .surface(UPDATED_SURFACE);

        restPropertyMockMvc.perform(put("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProperty)))
            .andExpect(status().isOk());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate);
        Property testProperty = propertyList.get(propertyList.size() - 1);
        assertThat(testProperty.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProperty.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProperty.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testProperty.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testProperty.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProperty.getTransaction()).isEqualTo(UPDATED_TRANSACTION);
        assertThat(testProperty.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testProperty.getPriceSell()).isEqualTo(UPDATED_PRICE_SELL);
        assertThat(testProperty.getPriceRent()).isEqualTo(UPDATED_PRICE_RENT);
        assertThat(testProperty.getYearConstruction()).isEqualTo(UPDATED_YEAR_CONSTRUCTION);
        assertThat(testProperty.isNewCreation()).isEqualTo(UPDATED_NEW_CREATION);
        assertThat(testProperty.isVisible()).isEqualTo(UPDATED_VISIBLE);
        assertThat(testProperty.getSurface()).isEqualTo(UPDATED_SURFACE);
    }

    @Test
    @Transactional
    public void updateNonExistingProperty() throws Exception {
        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();

        // Create the Property

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPropertyMockMvc.perform(put("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(property)))
            .andExpect(status().isCreated());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProperty() throws Exception {
        // Initialize the database
        propertyService.save(property);

        int databaseSizeBeforeDelete = propertyRepository.findAll().size();

        // Get the property
        restPropertyMockMvc.perform(delete("/api/properties/{id}", property.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Property.class);
        Property property1 = new Property();
        property1.setId(1L);
        Property property2 = new Property();
        property2.setId(property1.getId());
        assertThat(property1).isEqualTo(property2);
        property2.setId(2L);
        assertThat(property1).isNotEqualTo(property2);
        property1.setId(null);
        assertThat(property1).isNotEqualTo(property2);
    }
}
