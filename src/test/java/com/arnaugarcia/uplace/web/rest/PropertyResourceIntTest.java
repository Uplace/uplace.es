package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;
import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.repository.PropertyRepository;
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
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static com.arnaugarcia.uplace.web.rest.TestUtil.createFormattingConversionService;
import static com.arnaugarcia.uplace.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPropertyMockMvc;

    private Property property;

    /*@Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PropertyResource propertyResource = new PropertyResource(propertyRepository);
        this.restPropertyMockMvc = MockMvcBuilders.standaloneSetup(propertyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }*/

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    /*public static Property createEntity(EntityManager em) {
        Property property = new Property()
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE)
            .created(DEFAULT_CREATED)
            .updated(DEFAULT_UPDATED)
            .description(DEFAULT_DESCRIPTION)
            .reference(DEFAULT_REFERENCE)
            .priceSell(DEFAULT_PRICE_SELL)
            .priceRent(DEFAULT_PRICE_RENT)
            .yearConstruction(DEFAULT_YEAR_CONSTRUCTION)
            .newCreation(DEFAULT_NEW_CREATION)
            .visible(DEFAULT_VISIBLE)
            .surface(DEFAULT_SURFACE);
        return property;
    }*/

    /*@Before
    public void initTest() {
        property = createEntity(em);
    }*/

    /*@Test
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
        assertThat(testProperty.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testProperty.getPriceSell()).isEqualTo(DEFAULT_PRICE_SELL);
        assertThat(testProperty.getPriceRent()).isEqualTo(DEFAULT_PRICE_RENT);
        assertThat(testProperty.getYearConstruction()).isEqualTo(DEFAULT_YEAR_CONSTRUCTION);
        assertThat(testProperty.isNewCreation()).isEqualTo(DEFAULT_NEW_CREATION);
        assertThat(testProperty.isVisible()).isEqualTo(DEFAULT_VISIBLE);
        assertThat(testProperty.getSurface()).isEqualTo(DEFAULT_SURFACE);
    }*/

    /*@Test
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
    }*/

    /*@Test
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
    }*/

    /*@Test
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
    }*/

    /*@Test
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
    }*/

    /*@Test
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
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].priceSell").value(hasItem(DEFAULT_PRICE_SELL.doubleValue())))
            .andExpect(jsonPath("$.[*].priceRent").value(hasItem(DEFAULT_PRICE_RENT.doubleValue())))
            .andExpect(jsonPath("$.[*].yearConstruction").value(hasItem(DEFAULT_YEAR_CONSTRUCTION)))
            .andExpect(jsonPath("$.[*].newCreation").value(hasItem(DEFAULT_NEW_CREATION.booleanValue())))
            .andExpect(jsonPath("$.[*].visible").value(hasItem(DEFAULT_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].surface").value(hasItem(DEFAULT_SURFACE)));
    }*/

    /*@Test
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
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.priceSell").value(DEFAULT_PRICE_SELL.doubleValue()))
            .andExpect(jsonPath("$.priceRent").value(DEFAULT_PRICE_RENT.doubleValue()))
            .andExpect(jsonPath("$.yearConstruction").value(DEFAULT_YEAR_CONSTRUCTION))
            .andExpect(jsonPath("$.newCreation").value(DEFAULT_NEW_CREATION.booleanValue()))
            .andExpect(jsonPath("$.visible").value(DEFAULT_VISIBLE.booleanValue()))
            .andExpect(jsonPath("$.surface").value(DEFAULT_SURFACE));
    }*/

    @Test
    @Transactional
    public void getNonExistingProperty() throws Exception {
        // Get the property
        restPropertyMockMvc.perform(get("/api/properties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    /*@Test
    @Transactional
    public void updateProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);
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
        assertThat(testProperty.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testProperty.getPriceSell()).isEqualTo(UPDATED_PRICE_SELL);
        assertThat(testProperty.getPriceRent()).isEqualTo(UPDATED_PRICE_RENT);
        assertThat(testProperty.getYearConstruction()).isEqualTo(UPDATED_YEAR_CONSTRUCTION);
        assertThat(testProperty.isNewCreation()).isEqualTo(UPDATED_NEW_CREATION);
        assertThat(testProperty.isVisible()).isEqualTo(UPDATED_VISIBLE);
        assertThat(testProperty.getSurface()).isEqualTo(UPDATED_SURFACE);
    }*/

    /*@Test
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
    }*/

    /*@Test
    @Transactional
    public void deleteProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);
        int databaseSizeBeforeDelete = propertyRepository.findAll().size();

        // Get the property
        restPropertyMockMvc.perform(delete("/api/properties/{id}", property.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeDelete - 1);
    }*/

    /*@Test
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
    }*/
}
