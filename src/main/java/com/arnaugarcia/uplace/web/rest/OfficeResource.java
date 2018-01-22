package com.arnaugarcia.uplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.uplace.domain.Office;

import com.arnaugarcia.uplace.repository.OfficeRepository;
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
 * REST controller for managing Office.
 */
@RestController
@RequestMapping("/api")
public class OfficeResource {

    private final Logger log = LoggerFactory.getLogger(OfficeResource.class);

    private static final String ENTITY_NAME = "office";

    private final OfficeRepository officeRepository;

    public OfficeResource(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    /**
     * POST  /offices : Create a new office.
     *
     * @param office the office to create
     * @return the ResponseEntity with status 201 (Created) and with body the new office, or with status 400 (Bad Request) if the office has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/offices")
    @Timed
    public ResponseEntity<Office> createOffice(@RequestBody Office office) throws URISyntaxException {
        log.debug("REST request to save Office : {}", office);
        if (office.getId() != null) {
            throw new BadRequestAlertException("A new office cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Office result = officeRepository.save(office);
        return ResponseEntity.created(new URI("/api/offices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /offices : Updates an existing office.
     *
     * @param office the office to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated office,
     * or with status 400 (Bad Request) if the office is not valid,
     * or with status 500 (Internal Server Error) if the office couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/offices")
    @Timed
    public ResponseEntity<Office> updateOffice(@RequestBody Office office) throws URISyntaxException {
        log.debug("REST request to update Office : {}", office);
        if (office.getId() == null) {
            return createOffice(office);
        }
        Office result = officeRepository.save(office);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, office.getId().toString()))
            .body(result);
    }

    /**
     * GET  /offices : get all the offices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of offices in body
     */
    @GetMapping("/offices")
    @Timed
    public List<Office> getAllOffices() {
        log.debug("REST request to get all Offices");
        return officeRepository.findAll();
        }

    /**
     * GET  /offices/:id : get the "id" office.
     *
     * @param id the id of the office to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the office, or with status 404 (Not Found)
     */
    @GetMapping("/offices/{id}")
    @Timed
    public ResponseEntity<Office> getOffice(@PathVariable Long id) {
        log.debug("REST request to get Office : {}", id);
        Office office = officeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(office));
    }

    /**
     * DELETE  /offices/:id : delete the "id" office.
     *
     * @param id the id of the office to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/offices/{id}")
    @Timed
    public ResponseEntity<Void> deleteOffice(@PathVariable Long id) {
        log.debug("REST request to delete Office : {}", id);
        officeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /office/reference/:reference : get the office by reference.
     *
     * @param reference the id of the office to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("/office/reference/{reference}")
    @Timed
    public ResponseEntity<Office> getOfficeByReference(@PathVariable String reference) {
        log.debug("REST request to get Office : {}", reference);
        Office office = officeRepository.findFirstByReference(reference);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(office));
    }
}
