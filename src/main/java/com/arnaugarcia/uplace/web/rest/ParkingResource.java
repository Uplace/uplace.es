package com.arnaugarcia.uplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.uplace.domain.Parking;

import com.arnaugarcia.uplace.repository.ParkingRepository;
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
 * REST controller for managing Parking.
 */
@RestController
@RequestMapping("/api")
public class ParkingResource {

    private final Logger log = LoggerFactory.getLogger(ParkingResource.class);

    private static final String ENTITY_NAME = "parking";

    private final ParkingRepository parkingRepository;

    public ParkingResource(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    /**
     * POST  /parkings : Create a new parking.
     *
     * @param parking the parking to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parking, or with status 400 (Bad Request) if the parking has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parkings")
    @Timed
    public ResponseEntity<Parking> createParking(@RequestBody Parking parking) throws URISyntaxException {
        log.debug("REST request to save Parking : {}", parking);
        if (parking.getId() != null) {
            throw new BadRequestAlertException("A new parking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Parking result = parkingRepository.save(parking);
        return ResponseEntity.created(new URI("/api/parkings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parkings : Updates an existing parking.
     *
     * @param parking the parking to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parking,
     * or with status 400 (Bad Request) if the parking is not valid,
     * or with status 500 (Internal Server Error) if the parking couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parkings")
    @Timed
    public ResponseEntity<Parking> updateParking(@RequestBody Parking parking) throws URISyntaxException {
        log.debug("REST request to update Parking : {}", parking);
        if (parking.getId() == null) {
            return createParking(parking);
        }
        Parking result = parkingRepository.save(parking);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parking.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parkings : get all the parkings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of parkings in body
     */
    @GetMapping("/parkings")
    @Timed
    public List<Parking> getAllParkings() {
        log.debug("REST request to get all Parkings");
        return parkingRepository.findAll();
        }

    /**
     * GET  /parkings/:id : get the "id" parking.
     *
     * @param id the id of the parking to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parking, or with status 404 (Not Found)
     */
    @GetMapping("/parkings/{id}")
    @Timed
    public ResponseEntity<Parking> getParking(@PathVariable Long id) {
        log.debug("REST request to get Parking : {}", id);
        Parking parking = parkingRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(parking));
    }

    /**
     * DELETE  /parkings/:id : delete the "id" parking.
     *
     * @param id the id of the parking to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parkings/{id}")
    @Timed
    public ResponseEntity<Void> deleteParking(@PathVariable Long id) {
        log.debug("REST request to delete Parking : {}", id);
        parkingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
