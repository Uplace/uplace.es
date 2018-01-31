package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.UplaceApp;

import com.arnaugarcia.uplace.domain.Notification;
import com.arnaugarcia.uplace.domain.User;
import com.arnaugarcia.uplace.service.NotificationService;
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

import com.arnaugarcia.uplace.domain.enumeration.NotificationType;
/**
 * Test class for the NotificationResource REST controller.
 *
 * @see NotificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UplaceApp.class)
public class NotificationResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final NotificationType DEFAULT_TYPE = NotificationType.NOTIFICATION;
    private static final NotificationType UPDATED_TYPE = NotificationType.REQUEST;

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_READ = false;
    private static final Boolean UPDATED_READ = true;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNotificationMockMvc;

    private Notification notification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NotificationResource notificationResource = new NotificationResource(notificationService);
        this.restNotificationMockMvc = MockMvcBuilders.standaloneSetup(notificationResource)
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
    public static Notification createEntity(EntityManager em) {
        Notification notification = new Notification()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .creation(DEFAULT_CREATION)
            .type(DEFAULT_TYPE)
            .token(DEFAULT_TOKEN)
            .read(DEFAULT_READ);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        notification.setUser(user);
        return notification;
    }

    @Before
    public void initTest() {
        notification = createEntity(em);
    }

    /*@Test
    @Transactional
    public void createNotification() throws Exception {
        int databaseSizeBeforeCreate = notificationService.findAllNotifications().size();

        // Create the Notification
        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notification)))
            .andExpect(status().isCreated());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationService.findAllNotifications();
        assertThat(notificationList).hasSize(databaseSizeBeforeCreate + 1);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testNotification.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testNotification.getCreation()).isEqualTo(DEFAULT_CREATION);
        assertThat(testNotification.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testNotification.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testNotification.isRead()).isEqualTo(DEFAULT_READ);
    }*/

    @Test
    @Transactional
    public void createNotificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificationService.findAllNotifications().size();

        // Create the Notification with an existing ID
        notification.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notification)))
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationService.findAllNotifications();
        assertThat(notificationList).hasSize(databaseSizeBeforeCreate);
    }

    /*@Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationService.findAllNotifications().size();
        // set the field null
        notification.setTitle(null);

        // Create the Notification, which fails.

        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notification)))
            .andExpect(status().isBadRequest());

        List<Notification> notificationList = notificationService.findAllNotifications();
        assertThat(notificationList).hasSize(databaseSizeBeforeTest);
    }*/

    /*@Test
    @Transactional
    public void checkCreationIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationService.findAllNotifications().size();
        // set the field null
        notification.setCreation(null);

        // Create the Notification, which fails.

        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notification)))
            .andExpect(status().isBadRequest());

        List<Notification> notificationList = notificationService.findAllNotifications();
        assertThat(notificationList).hasSize(databaseSizeBeforeTest);
    }*/

   /* @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationService.findAllNotifications().size();
        // set the field null
        notification.setType(null);

        // Create the Notification, which fails.

        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notification)))
            .andExpect(status().isBadRequest());

        List<Notification> notificationList = notificationService.findAllNotifications();
        assertThat(notificationList).hasSize(databaseSizeBeforeTest);
    }*/

    /*@Test
    @Transactional
    public void checkReadIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationService.findAllNotifications().size();
        // set the field null
        notification.setRead(null);

        // Create the Notification, which fails.

        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notification)))
            .andExpect(status().isBadRequest());

        List<Notification> notificationList = notificationService.findAllNotifications();
        assertThat(notificationList).hasSize(databaseSizeBeforeTest);
    }*/

   /* @Test
    @Transactional
    public void getAllNotifications() throws Exception {
        // Initialize the database
        notificationService.saveNotification(notification);

        // Get all the notificationList
        restNotificationMockMvc.perform(get("/api/notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notification.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].creation").value(hasItem(sameInstant(DEFAULT_CREATION))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].read").value(hasItem(DEFAULT_READ.booleanValue())));
    }*/

    /*@Test
    @Transactional
    public void getNotification() throws Exception {
        // Initialize the database
            notificationService.saveNotification(notification);

        // Get the notification
        restNotificationMockMvc.perform(get("/api/notifications/{id}", notification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(notification.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.creation").value(sameInstant(DEFAULT_CREATION)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()))
            .andExpect(jsonPath("$.read").value(DEFAULT_READ.booleanValue()));
    }*/

    @Test
    @Transactional
    public void getNonExistingNotification() throws Exception {
        // Get the notification
        restNotificationMockMvc.perform(get("/api/notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    /*@Test
    @Transactional
    public void updateNotification() throws Exception {
        // Initialize the database
        notificationService.saveNotification(notification);
        int databaseSizeBeforeUpdate = notificationService.findAllNotifications().size();

        // Update the notification
        Notification updatedNotification = notificationService.findOneNotification(notification.getId());
        // Disconnect from session so that the updates on updatedNotification are not directly saved in db
        em.detach(updatedNotification);
        updatedNotification
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .creation(UPDATED_CREATION)
            .type(UPDATED_TYPE)
            .token(UPDATED_TOKEN)
            .read(UPDATED_READ);

        restNotificationMockMvc.perform(put("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNotification)))
            .andExpect(status().isOk());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationService.findAllNotifications();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNotification.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testNotification.getCreation()).isEqualTo(UPDATED_CREATION);
        assertThat(testNotification.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNotification.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testNotification.isRead()).isEqualTo(UPDATED_READ);
    }*/

    /*@Test
    @Transactional
    public void updateNonExistingNotification() throws Exception {
        int databaseSizeBeforeUpdate = notificationService.findAllNotifications().size();

        // Create the Notification

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNotificationMockMvc.perform(put("/api/notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notification)))
            .andExpect(status().isCreated());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationService.findAllNotifications();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate + 1);
    }*/

    /*@Test
    @Transactional
    public void deleteNotification() throws Exception {
        // Initialize the database
        notificationService.saveNotification(notification);
        int databaseSizeBeforeDelete = notificationService.findAllNotifications().size();

        // Get the notification
        restNotificationMockMvc.perform(delete("/api/notifications/{id}", notification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Notification> notificationList = notificationService.findAllNotifications();
        assertThat(notificationList).hasSize(databaseSizeBeforeDelete - 1);
    }*/

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notification.class);
        Notification notification1 = new Notification();
        notification1.setId(1L);
        Notification notification2 = new Notification();
        notification2.setId(notification1.getId());
        assertThat(notification1).isEqualTo(notification2);
        notification2.setId(2L);
        assertThat(notification1).isNotEqualTo(notification2);
        notification1.setId(null);
        assertThat(notification1).isNotEqualTo(notification2);
    }
}
