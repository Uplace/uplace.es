package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.domain.Notification;
import com.arnaugarcia.uplace.domain.enumeration.NotificationType;
import com.arnaugarcia.uplace.security.AuthoritiesConstants;
import com.arnaugarcia.uplace.service.NotificationService;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Notification.
 */
@RestController
@RequestMapping("/api")
@Secured(AuthoritiesConstants.USER)
public class RequestResource {

    private final Logger log = LoggerFactory.getLogger(RequestResource.class);

    private static final String ENTITY_NAME = "request";

    private final NotificationService notificationService;

    public RequestResource(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * POST  /requests : Create a new request.
     *
     * @param request the request to create
     * @return the ResponseEntity with status 201 (Created) and with body the new request, or with status 400 (Bad Request) if the request has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/requests")
    @Timed
    public ResponseEntity<Notification> createRequest(@Valid @RequestBody Notification request) throws URISyntaxException {
        log.debug("REST request to save Request : {}", request);

        if (request.getId() != null) {
            throw new BadRequestAlertException("A new request cannot already have an ID", ENTITY_NAME, "idexists");
        }
        //If is a new request the date must be now
        request.setCreation(ZonedDateTime.now());

        Notification result = notificationService.saveRequest(request);

        return ResponseEntity.created(new URI("/api/requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /requests : Updates requests.
     *
     * @param requests the requests to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated request,
     * or with status 400 (Bad Request) if the request is not valid,
     * or with status 500 (Internal Server Error) if the request couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/requests")
    @Timed
    public ResponseEntity<List<Notification>> updateRequests(@Valid @RequestBody List<Notification> requests) throws URISyntaxException {
        log.debug("REST request to update Notifications : {}", requests.size());

        List<Notification> result = notificationService.updates(requests);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.toString()))
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

        Page<Notification> page = notificationService.findAllByType(NotificationType.REQUEST, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/notifications");

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /requests/:id : get the "id" request.
     *
     * @param id the id of the request to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the request, or with status 404 (Not Found)
     */
    @GetMapping("/requests/{id}")
    @Timed
    public ResponseEntity<Notification> getRequest(@PathVariable Long id) {
        log.debug("REST request to get Request : {}", id);

        Notification notification = notificationService.findOneByType(NotificationType.REQUEST, id);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(notification));
    }

    /**
     * DELETE  /notifications/:id : delete the "id" notification.
     *
     * @param id the id of the notification to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/requests/{id}")
    @Timed
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        log.debug("REST request to delete Request : {}", id);

        Notification notification = notificationService.findOneByType(NotificationType.REQUEST, id);

        if (notification != null) {

            notificationService.delete(id);

            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();

        } else {
            throw new BadRequestAlertException("The requested notification doesn't exists", ENTITY_NAME, "badid");
        }

    }

    @DeleteMapping("/requests/")
    @Timed
    public ResponseEntity<Void> deleteRequests(@RequestBody List<Notification> requests) {
        log.debug("REST request to delete requests: {}", requests.size());

        notificationService.deletes(requests);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "delete")).build();
    }
}
