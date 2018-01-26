package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.domain.Notification;
import com.arnaugarcia.uplace.domain.User;
import com.arnaugarcia.uplace.domain.enumeration.NotificationType;
import com.arnaugarcia.uplace.repository.NotificationRepository;
import com.arnaugarcia.uplace.repository.UserRepository;
import com.arnaugarcia.uplace.security.SecurityUtils;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.util.HeaderUtil;
import com.arnaugarcia.uplace.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
public class RequestResource {

    private final Logger log = LoggerFactory.getLogger(RequestResource.class);

    private static final String ENTITY_NAME = "request";

    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    public RequestResource(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /request : Create a new request.
     *
     * @param request the request to create
     * @return the ResponseEntity with status 201 (Created) and with body the new request, or with status 400 (Bad Request) if the request has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/requests")
    @Timed
    public ResponseEntity<Notification> createRequest(@RequestBody Notification request) throws URISyntaxException {
        log.debug("REST request to save a request : {}", request);

        request.setType(NotificationType.REQUEST);

        if (request.getId() != null) {
            throw new BadRequestAlertException("A new request cannot already have an ID", ENTITY_NAME, "idexists");
        }

        request.setCreation(ZonedDateTime.now());

        Notification result = notificationRepository.save(request);

        return ResponseEntity.created(new URI("/api/notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /request : Updates an existing request.
     *
     * @param request the request to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated request,
     * or with status 400 (Bad Request) if the request is not valid,
     * or with status 500 (Internal Server Error) if the request couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/request")
    @Timed
    public ResponseEntity<Notification> updateRequest(@RequestBody Notification request) throws URISyntaxException {
        log.debug("REST request to update Notification : {}", request);
        if (request.getId() == null || request.getType().equals(NotificationType.REQUEST)) {
            return createRequest(request);
        }
        Notification result = notificationRepository.save(request);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, request.getId().toString()))
            .body(result);
    }

    /**
     * GET  /requests : get all the requests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of notifications in body
     */
    @GetMapping("/requests")
    @Timed
    public ResponseEntity<List<Notification>> getAllRequests(Pageable pageable) {
        log.debug("REST request to get a page of Requests");
        Page<Notification> page;
        if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            page = notificationRepository.findAll(pageable);
        } else {
            page = notificationRepository.findAllByType(NotificationType.REQUEST, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/notifications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /notifications/:id : get the "id" notification.
     *
     * @param id the id of the notification to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the notification, or with status 404 (Not Found)
     */
    /*@GetMapping("/notifications/{id}")
    @Timed
    public ResponseEntity<Notification> getNotification(@PathVariable Long id) {
        log.debug("REST request to get Notification : {}", id);
        Notification notification = notificationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(notification));
    }*/

    /**
     * GET  /notifications/read
     * @return the ResponseEntity with status 200 (OK) and with body the notifications updated, or with status 404 (Not Found)
     */

    /*@GetMapping("/notifications/read")
    @Timed
    public List<Notification> updateNotificationsAsRead() {
        log.debug("REST request to mark all the Notifications as read : {}");
        List<Notification> notifications = notificationRepository.findByUserIsCurrentUser();
        notifications.forEach((notification -> notification.setRead(true)));
        return notificationRepository.save(notifications);
    }*/

    /**
     * GET  /notifications/unread
     * @return the List<Notification> with status 200 (OK) and with body the notifications updated, or with status 404 (Not Found)
     */

    /*@GetMapping("/notifications/unread")
    @Timed
    public List<Notification> updateNotificationsAsUnRead() {
        log.debug("REST request to mark all the Notifications as unread : {}");
        List<Notification> notifications = notificationRepository.findByUserIsCurrentUser();
        notifications.forEach((notification -> notification.setRead(false)));
        return notificationRepository.save(notifications);
    }*/

    /**
     * GET  /notifications/:id/read : get the "id" notification.
     *
     * @param id the id of the notification to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the notification, or with status 404 (Not Found)
     */
    /*@GetMapping("/notifications/{id}/read")
    @Timed
    public ResponseEntity<Notification> updateNotificationAsRead(@PathVariable Long id) {
        log.debug("REST request to get Notification : {}", id);
        Notification notification = notificationRepository.findOne(id);
        if (notification != null) {
            notification.setRead(true);
        } else {
            throw new BadRequestAlertException("Notification don't exists", ENTITY_NAME, "notexists");
        }
        notification = notificationRepository.save(notification);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(notification));
    }*/

    /**
     * GET  /notifications/:id/unread : get the "id" notification.
     *
     * @param id the id of the notification to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the notification, or with status 404 (Not Found)
     */
    /*@GetMapping("/notifications/{id}/unread")
    @Timed
    public ResponseEntity<Notification> updateNotificationAsUnRead(@PathVariable Long id) {
        log.debug("REST request to get Notification : {}", id);
        Notification notification = notificationRepository.findOne(id);
        if (notification != null) {
            notification.setRead(false);
        } else {
            throw new BadRequestAlertException("Notification don't exists", ENTITY_NAME, "notexists");
        }
        notification = notificationRepository.save(notification);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(notification));
    }*/

    /**
     * DELETE  /notifications/:id : delete the "id" notification.
     *
     * @param id the id of the notification to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    /*@DeleteMapping("/notifications/{id}")
    @Timed
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        log.debug("REST request to delete Notification : {}", id);
        Notification notification = notificationRepository.findOne(id);
        if (notification != null) {
            notificationRepository.delete(notification);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
        } else {
            throw new BadRequestAlertException("The requested notification doesn't exists", ENTITY_NAME, "badid");
        }
    }*/

    /*@DeleteMapping("/notifications/")
    @Timed
    public ResponseEntity<Void> deleteNotifications(@RequestBody List<Notification> notifications) {
        log.debug("REST request to delete notifications: {}", notifications.size());
        List<Notification> result = new ArrayList<>();
        notifications.forEach(notification -> {
            if (notification.getId() != null) {
                result.add(notification);
            } else {
                throw new BadRequestAlertException("Some notification doesn't have a valid ID or it's malformed", ENTITY_NAME, "badid");
            }
        });
        notificationRepository.delete(result);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "delete")).build();
    }*/
}
