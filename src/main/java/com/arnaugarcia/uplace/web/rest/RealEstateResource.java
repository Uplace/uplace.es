package com.arnaugarcia.uplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.uplace.domain.RealEstate;

import com.arnaugarcia.uplace.repository.RealEstateRepository;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RealEstate.
 */
@RestController
@RequestMapping("/api")
public class RealEstateResource {

    private final Logger log = LoggerFactory.getLogger(RealEstateResource.class);

    private static final String ENTITY_NAME = "realEstate";

    private final RealEstateRepository realEstateRepository;

    public RealEstateResource(RealEstateRepository realEstateRepository) {
        this.realEstateRepository = realEstateRepository;
    }

    /**
     * POST  /real-estates : Create a new realEstate.
     *
     * @param realEstate the realEstate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new realEstate, or with status 400 (Bad Request) if the realEstate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/real-estates")
    @Timed
    public ResponseEntity<RealEstate> createRealEstate(@Valid @RequestBody RealEstate realEstate) throws URISyntaxException {
        log.debug("REST request to save RealEstate : {}", realEstate);
        if (realEstate.getId() != null) {
            throw new BadRequestAlertException("A new realEstate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RealEstate result = realEstateRepository.save(realEstate);
        return ResponseEntity.created(new URI("/api/real-estates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /real-estates : Updates an existing realEstate.
     *
     * @param realEstate the realEstate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated realEstate,
     * or with status 400 (Bad Request) if the realEstate is not valid,
     * or with status 500 (Internal Server Error) if the realEstate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/real-estates")
    @Timed
    public ResponseEntity<RealEstate> updateRealEstate(@Valid @RequestBody RealEstate realEstate) throws URISyntaxException {
        log.debug("REST request to update RealEstate : {}", realEstate);
        if (realEstate.getId() == null) {
            return createRealEstate(realEstate);
        }
        RealEstate result = realEstateRepository.save(realEstate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, realEstate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /real-estates : get all the realEstates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of realEstates in body
     */
    @GetMapping("/real-estates")
    @Timed
    public List<RealEstate> getAllRealEstates() {
        log.debug("REST request to get all RealEstates");
        return realEstateRepository.findAll();
        }

    /**
     * GET  /real-estates/:id : get the "id" realEstate.
     *
     * @param id the id of the realEstate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the realEstate, or with status 404 (Not Found)
     */
    @GetMapping("/real-estates/{id}")
    @Timed
    public ResponseEntity<RealEstate> getRealEstate(@PathVariable Long id) {
        log.debug("REST request to get RealEstate : {}", id);
        RealEstate realEstate = realEstateRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(realEstate));
    }

    /**
     * DELETE  /real-estates/:id : delete the "id" realEstate.
     *
     * @param id the id of the realEstate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/real-estates/{id}")
    @Timed
    public ResponseEntity<Void> deleteRealEstate(@PathVariable Long id) {
        log.debug("REST request to delete RealEstate : {}", id);
        realEstateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
