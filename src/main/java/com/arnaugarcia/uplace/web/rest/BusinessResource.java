package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.service.PropertyService;
import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.uplace.domain.Business;
import com.arnaugarcia.uplace.service.BusinessService;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Business.
 */
@RestController
@RequestMapping("/api")
public class BusinessResource {

    private final Logger log = LoggerFactory.getLogger(BusinessResource.class);

    private static final String ENTITY_NAME = "business";

    private final BusinessService businessService;

    public BusinessResource(BusinessService businessService) {
        this.businessService = businessService;
    }

    /**
     * POST  /businesses : Create a new business.
     *
     * @param business the business to create
     * @return the ResponseEntity with status 201 (Created) and with body the new business, or with status 400 (Bad Request) if the business has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/businesses")
    @Timed
    public ResponseEntity<Business> createBusiness(@RequestBody Business business) throws URISyntaxException {
        log.debug("REST request to save Business : {}", business);
        if (business.getId() != null) {
            throw new BadRequestAlertException("A new business cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Business result = businessService.save(business);
        return ResponseEntity.created(new URI("/api/businesses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /businesses : Updates an existing business.
     *
     * @param business the business to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated business,
     * or with status 400 (Bad Request) if the business is not valid,
     * or with status 500 (Internal Server Error) if the business couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/businesses")
    @Timed
    public ResponseEntity<Business> updateBusiness(@RequestBody Business business) throws URISyntaxException {
        log.debug("REST request to update Business : {}", business);
        if (business.getId() == null) {
            return createBusiness(business);
        }
        Business result = businessService.save(business);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, business.getId().toString()))
            .body(result);
    }

    /**
     * GET  /businesses : get all the businesses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of businesses in body
     */
    @GetMapping("/businesses")
    @Timed
    public List<Business> getAllBusinesses() {
        log.debug("REST request to get all Businesses");
        return businessService.findAll();
    }

    /**
     * GET  /businesses/:id : get the "id" business.
     *
     * @param id the id of the business to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the business, or with status 404 (Not Found)
     */
    @GetMapping("/businesses/{id}")
    @Timed
    public ResponseEntity<Business> getBusiness(@PathVariable Long id) {
        log.debug("REST request to get Business : {}", id);
        Business business = businessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(business));
    }

    /**
     * DELETE  /businesses/:id : delete the "id" business.
     *
     * @param id the id of the business to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/businesses/{id}")
    @Timed
    public ResponseEntity<Void> deleteBusiness(@PathVariable Long id) {
        log.debug("REST request to delete Business : {}", id);
        businessService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
