package com.arnaugarcia.uplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.uplace.domain.Establishment;

import com.arnaugarcia.uplace.repository.EstablishmentRepository;
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
 * REST controller for managing Establishment.
 */
@RestController
@RequestMapping("/api")
public class EstablishmentResource {

    private final Logger log = LoggerFactory.getLogger(EstablishmentResource.class);

    private static final String ENTITY_NAME = "establishment";

    private final EstablishmentRepository establishmentRepository;

    public EstablishmentResource(EstablishmentRepository establishmentRepository) {
        this.establishmentRepository = establishmentRepository;
    }

    /**
     * POST  /establishments : Create a new establishment.
     *
     * @param establishment the establishment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new establishment, or with status 400 (Bad Request) if the establishment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/establishments")
    @Timed
    public ResponseEntity<Establishment> createEstablishment(@RequestBody Establishment establishment) throws URISyntaxException {
        log.debug("REST request to save Establishment : {}", establishment);
        if (establishment.getId() != null) {
            throw new BadRequestAlertException("A new establishment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Establishment result = establishmentRepository.save(establishment);
        return ResponseEntity.created(new URI("/api/establishments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /establishments : Updates an existing establishment.
     *
     * @param establishment the establishment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated establishment,
     * or with status 400 (Bad Request) if the establishment is not valid,
     * or with status 500 (Internal Server Error) if the establishment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/establishments")
    @Timed
    public ResponseEntity<Establishment> updateEstablishment(@RequestBody Establishment establishment) throws URISyntaxException {
        log.debug("REST request to update Establishment : {}", establishment);
        if (establishment.getId() == null) {
            return createEstablishment(establishment);
        }
        Establishment result = establishmentRepository.save(establishment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, establishment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /establishments : get all the establishments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of establishments in body
     */
    @GetMapping("/establishments")
    @Timed
    public List<Establishment> getAllEstablishments() {
        log.debug("REST request to get all Establishments");
        return establishmentRepository.findAll();
        }

    /**
     * GET  /establishments/:id : get the "id" establishment.
     *
     * @param id the id of the establishment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the establishment, or with status 404 (Not Found)
     */
    @GetMapping("/establishments/{id}")
    @Timed
    public ResponseEntity<Establishment> getEstablishment(@PathVariable Long id) {
        log.debug("REST request to get Establishment : {}", id);
        Establishment establishment = establishmentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(establishment));
    }

    /**
     * DELETE  /establishments/:id : delete the "id" establishment.
     *
     * @param id the id of the establishment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/establishments/{id}")
    @Timed
    public ResponseEntity<Void> deleteEstablishment(@PathVariable Long id) {
        log.debug("REST request to delete Establishment : {}", id);
        establishmentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
