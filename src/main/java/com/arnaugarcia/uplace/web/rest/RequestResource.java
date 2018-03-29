package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.domain.enumeration.RequestStatus;
import com.arnaugarcia.uplace.web.rest.errors.ErrorConstants;
import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.uplace.domain.Request;
import com.arnaugarcia.uplace.service.RequestService;
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
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Request.
 */
@RestController
@RequestMapping("/api")
public class RequestResource {

    private final Logger log = LoggerFactory.getLogger(RequestResource.class);

    private static final String ENTITY_NAME = "request";

    private final RequestService requestService;

    public RequestResource(RequestService requestService) {
        this.requestService = requestService;
    }

    @PatchMapping("/requests/{reference}/{status}")
    @Timed
    public ResponseEntity<Request> updateStatus(@PathVariable String reference, @PathVariable RequestStatus status) {
        Request request = requestService.updateStatus(reference, status);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityPatchAlert(ENTITY_NAME, reference)).body(request);
    }

    /**
     * GET  /requests : get all the requests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of requests in body
     */
    @GetMapping("/requests")
    @Timed
    public ResponseEntity<List<Request>> getAllRequests(Pageable pageable) {
        log.debug("REST request to get a page of Requests");
        Page<Request> page = requestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/requests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * DELETE  /requests/:references : delete the "reference" request.
     *
     * @param reference the id of the request to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/requests/{reference}")
    @Timed
    public ResponseEntity<Void> deleteRequest(@PathVariable String reference) {
        log.debug("REST request to delete Request : {}", reference);
        requestService.delete(reference);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, reference)).build();
    }
}
