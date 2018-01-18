package com.arnaugarcia.uplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.uplace.domain.Apartment;

import com.arnaugarcia.uplace.repository.ApartmentRepository;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.util.HeaderUtil;
import com.arnaugarcia.uplace.service.dto.ApartmentDTO;
import com.arnaugarcia.uplace.service.mapper.ApartmentMapper;
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
 * REST controller for managing Apartment.
 */
@RestController
@RequestMapping("/api")
public class ApartmentResource {

    private final Logger log = LoggerFactory.getLogger(ApartmentResource.class);

    private static final String ENTITY_NAME = "apartment";

    private final ApartmentRepository apartmentRepository;

    private final ApartmentMapper apartmentMapper;

    public ApartmentResource(ApartmentRepository apartmentRepository, ApartmentMapper apartmentMapper) {
        this.apartmentRepository = apartmentRepository;
        this.apartmentMapper = apartmentMapper;
    }

    /**
     * POST  /apartments : Create a new apartment.
     *
     * @param apartment the apartment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new apartment, or with status 400 (Bad Request) if the apartment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/apartments")
    @Timed
    public ResponseEntity<Apartment> createApartment(@RequestBody Apartment apartment) throws URISyntaxException {
        log.debug("REST request to save Apartment : {}", apartment);
        if (apartment.getId() != null) {
            throw new BadRequestAlertException("A new apartment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Apartment result = apartmentRepository.save(apartment);
        return ResponseEntity.created(new URI("/api/apartments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /apartments : Updates an existing apartment.
     *
     * @param apartment the apartment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apartmentDTO,
     * or with status 400 (Bad Request) if the apartment is not valid,
     * or with status 500 (Internal Server Error) if the apartment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/apartments")
    @Timed
    public ResponseEntity<Apartment> updateApartment(@RequestBody Apartment apartment) throws URISyntaxException {
        log.debug("REST request to update Apartment : {}", apartment);
        /*if (apartmentDTO.getId() == null) {
            return createApartment(apartmentDTO);
        }*/
        Apartment result = apartmentRepository.save(apartment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, apartment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /apartments : get all the apartments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of apartments in body
     */
    @GetMapping("/apartments")
    @Timed
    public List<Apartment> getAllApartments() {
        log.debug("REST request to get all Apartments");
        List<Apartment> apartments = apartmentRepository.findAll();
        return apartments;
        }

    /**
     * GET  /apartments/:id : get the "id" apartment.
     *
     * @param id the id of the apartment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the apartment, or with status 404 (Not Found)
     */
    @GetMapping("/apartments/{id}")
    @Timed
    public ResponseEntity<Apartment> getApartment(@PathVariable Long id) {
        log.debug("REST request to get Apartment : {}", id);
        Apartment apartment = apartmentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(apartment));
    }

    /**
     * DELETE  /apartments/:id : delete the "id" apartment.
     *
     * @param id the id of the apartmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/apartments/{id}")
    @Timed
    public ResponseEntity<Void> deleteApartment(@PathVariable Long id) {
        log.debug("REST request to delete Apartment : {}", id);
        apartmentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
