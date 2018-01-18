package com.arnaugarcia.uplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.uplace.domain.Bussiness;

import com.arnaugarcia.uplace.repository.BussinessRepository;
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
 * REST controller for managing Bussiness.
 */
@RestController
@RequestMapping("/api")
public class BussinessResource {

    private final Logger log = LoggerFactory.getLogger(BussinessResource.class);

    private static final String ENTITY_NAME = "bussiness";

    private final BussinessRepository bussinessRepository;

    public BussinessResource(BussinessRepository bussinessRepository) {
        this.bussinessRepository = bussinessRepository;
    }

    /**
     * POST  /bussinesses : Create a new bussiness.
     *
     * @param bussiness the bussiness to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bussiness, or with status 400 (Bad Request) if the bussiness has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bussinesses")
    @Timed
    public ResponseEntity<Bussiness> createBussiness(@RequestBody Bussiness bussiness) throws URISyntaxException {
        log.debug("REST request to save Bussiness : {}", bussiness);
        if (bussiness.getId() != null) {
            throw new BadRequestAlertException("A new bussiness cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bussiness result = bussinessRepository.save(bussiness);
        return ResponseEntity.created(new URI("/api/bussinesses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bussinesses : Updates an existing bussiness.
     *
     * @param bussiness the bussiness to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bussiness,
     * or with status 400 (Bad Request) if the bussiness is not valid,
     * or with status 500 (Internal Server Error) if the bussiness couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bussinesses")
    @Timed
    public ResponseEntity<Bussiness> updateBussiness(@RequestBody Bussiness bussiness) throws URISyntaxException {
        log.debug("REST request to update Bussiness : {}", bussiness);
        if (bussiness.getId() == null) {
            return createBussiness(bussiness);
        }
        Bussiness result = bussinessRepository.save(bussiness);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bussiness.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bussinesses : get all the bussinesses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bussinesses in body
     */
    @GetMapping("/bussinesses")
    @Timed
    public List<Bussiness> getAllBussinesses() {
        log.debug("REST request to get all Bussinesses");
        return bussinessRepository.findAll();
        }

    /**
     * GET  /bussinesses/:id : get the "id" bussiness.
     *
     * @param id the id of the bussiness to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bussiness, or with status 404 (Not Found)
     */
    @GetMapping("/bussinesses/{id}")
    @Timed
    public ResponseEntity<Bussiness> getBussiness(@PathVariable Long id) {
        log.debug("REST request to get Bussiness : {}", id);
        Bussiness bussiness = bussinessRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bussiness));
    }

    /**
     * DELETE  /bussinesses/:id : delete the "id" bussiness.
     *
     * @param id the id of the bussiness to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bussinesses/{id}")
    @Timed
    public ResponseEntity<Void> deleteBussiness(@PathVariable Long id) {
        log.debug("REST request to delete Bussiness : {}", id);
        bussinessRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
