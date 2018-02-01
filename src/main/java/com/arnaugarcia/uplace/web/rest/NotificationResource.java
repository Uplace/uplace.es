package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.domain.User;
import com.arnaugarcia.uplace.domain.enumeration.NotificationType;
import com.arnaugarcia.uplace.repository.UserRepository;
import com.arnaugarcia.uplace.security.AuthoritiesConstants;
import com.arnaugarcia.uplace.security.SecurityUtils;
import com.arnaugarcia.uplace.service.NotificationService;
import com.arnaugarcia.uplace.service.UserService;
import com.arnaugarcia.uplace.web.rest.errors.ErrorConstants;
import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.uplace.domain.Notification;

import com.arnaugarcia.uplace.repository.NotificationRepository;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.util.HeaderUtil;
import com.arnaugarcia.uplace.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Notification.
 */
@RestController
@RequestMapping("/api")
@Secured(AuthoritiesConstants.USER)
public class NotificationResource {

    private final Logger log = LoggerFactory.getLogger(NotificationResource.class);

    private static final String ENTITY_NAME = "notification";

    private final NotificationService notificationService;

    public NotificationResource(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * POST  /notifications : Create a new notification.
     *
     * @param notification the notification to create
     * @return the ResponseEntity with status 201 (Created) and with body the new notification, or with status 400 (Bad Request) if the notification has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/notifications")
    @Timed
    public ResponseEntity<Notification> createNotification(@Valid @RequestBody Notification notification) throws URISyntaxException {
        log.debug("REST request to save Notification : {}", notification);

        if (notification.getId() != null) {
            throw new BadRequestAlertException("A new notification cannot already have an ID", ENTITY_NAME, ErrorConstants.ERR_ID_EXISTS);
        }
        //If is a new notification the date must be now
        notification.setCreation(ZonedDateTime.now());

        Notification result = notificationService.saveNotification(notification);

        return ResponseEntity.created(new URI("/api/notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /notifications : Updates an existing notification.
     *
     * @param notification the notification to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated notification,
     * or with status 400 (Bad Request) if the notification is not valid,
     * or with status 500 (Internal Server Error) if the notification couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    /*@PutMapping("/notifications")
    @Timed
    public ResponseEntity<Notification> updateNotification(@Valid @RequestBody Notification notification) throws URISyntaxException {
        log.debug("REST request to update Notification : {}", notification);
        if (notification.getId() == null) {
            return createNotification(notification);
        }

        Notification result = notificationService.saveNotification(notification);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, notification.getId().toString()))
            .body(result);
    }*/

    /**
     * PUT  /notifications : Updates notifications.
     *
     * @param notifications the notification to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated notification,
     * or with status 400 (Bad Request) if the notification is not valid,
     * or with status 500 (Internal Server Error) if the notification couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/notifications")
    @Timed
    public ResponseEntity<List<Notification>> updateNotifications(@Valid @RequestBody List<Notification> notifications) throws URISyntaxException {
        log.debug("REST request to update Notifications : {}", notifications.size());

        List<Notification> result = notificationService.updates(notifications);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.toString()))
            .body(result);
    }

    /**
     * GET  /notifications : get all the notifications.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of notifications in body
     */
    @GetMapping("/notifications")
    @Timed
    public ResponseEntity<List<Notification>> getAllNotifications(Pageable pageable) {
        log.debug("REST request to get a page of Notifications");

        Page<Notification> page = notificationService.findAllByType(NotificationType.NOTIFICATION, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/notifications");

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /notifications/:id : get the "id" notification.
     *
     * @param id the id of the notification to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the notification, or with status 404 (Not Found)
     */
    @GetMapping("/notifications/{id}")
    @Timed
    public ResponseEntity<Notification> getNotification(@PathVariable Long id) {
        log.debug("REST request to get Notification : {}", id);

        Notification notification = notificationService.findOneByType(NotificationType.NOTIFICATION, id);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(notification));
    }

    /**
     * GET  /notifications/read
     * @return the ResponseEntity with status 200 (OK) and with body the notifications updated, or with status 404 (Not Found)
     */

    /*@PutMapping("/notifications/read")
    @Timed
    public List<Notification> markNotificationsAsRead(@RequestParam List<Notification> notificationsList) {
        log.debug("REST request to mark all the Notifications as read : {}");

        return notificationService.markAs(notificationsList, true);

    }*/

    /**
     * GET  /notifications/unread
     * @return the List<Notification> with status 200 (OK) and with body the notifications updated, or with status 404 (Not Found)
     */

    /*@GetMapping("/notifications/unread")
    @Timed
    public List<Notification> markNotificationsAsUnRead(@RequestParam List<Notification> notificationsList) {
        log.debug("REST request to mark all the Notifications as unread : {}");

        return notificationService.markAs(notificationsList, false);
    }*/

    /**
     * DELETE  /notifications/:id : delete the "id" notification.
     *
     * @param id the id of the notification to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/notifications/{id}")
    @Timed
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        log.debug("REST request to delete Notification : {}", id);

        Notification notification = notificationService.findOneByType(NotificationType.NOTIFICATION, id);

        if (notification != null) {
            notificationService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } else {
            throw new BadRequestAlertException("The requested notification doesn't exists", ENTITY_NAME, ErrorConstants.ERR_BAD_ID);
        }

    }

    @DeleteMapping("/notifications/")
    @Timed
    public ResponseEntity<Void> deleteNotifications(@RequestBody List<Notification> notifications) {
        log.debug("REST request to delete notifications: {}", notifications.size());

        notificationService.deletes(notifications);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "delete")).build();
    }
}
