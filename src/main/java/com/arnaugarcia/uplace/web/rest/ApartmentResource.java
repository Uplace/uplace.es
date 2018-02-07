package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.domain.enumeration.ApartmentType;
import com.arnaugarcia.uplace.web.rest.errors.ErrorConstants;
import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.uplace.domain.Apartment;
import com.arnaugarcia.uplace.service.ApartmentService;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.util.HeaderUtil;
import com.arnaugarcia.uplace.service.dto.ApartmentCriteria;
import com.arnaugarcia.uplace.service.ApartmentQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    private final ApartmentService apartmentService;

    private final ApartmentQueryService apartmentQueryService;

    public ApartmentResource(ApartmentService apartmentService, ApartmentQueryService apartmentQueryService) {
        this.apartmentService = apartmentService;
        this.apartmentQueryService = apartmentQueryService;
    }

    // TODO: POST AND PUT PHOTOS
    // TODO: THUMBNAIL BY DTO? OR endpoint

    /**
     * POST  /{apartmentType} : Create a new apartment.
     *
     * @param apartment the entity of the apartment
     * @param apartmentType the apartment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new apartment, or with status 400 (Bad Request) if the apartment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/{apartmentType}")
    @Timed
    public ResponseEntity<Apartment> createApartment(@PathVariable ApartmentType apartmentType, @Valid @RequestBody Apartment apartment) throws URISyntaxException {
        log.debug("REST request to save Apartment : {}", apartment);
        if (apartment.getId() != null) {
            throw new BadRequestAlertException("A new apartment cannot already have an ID", ENTITY_NAME, ErrorConstants.ERR_ID_EXISTS);
        }
        // MAKE A UPDATE SERVICE AND CREATE por que el
        // id si no existe sigue siendo valido y crea el apartment (control)
        // no pasar referencia
        Apartment result = apartmentService.save(apartmentType, apartment);
        return ResponseEntity.created(new URI("/api/apartments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /:apartmentType : Updates an existing apartment.
     *
     * @param apartmentType the type of the apartment to update
     * @param apartment the apartment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apartment,
     * or with status 400 (Bad Request) if the apartment is not valid,
     * or with status 500 (Internal Server Error) if the apartment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/{apartmentType}")
    @Timed
    public ResponseEntity<Apartment> updateApartment(@PathVariable String apartmentType, @RequestBody Apartment apartment) throws URISyntaxException {
        log.debug("REST request to update Apartment : {}", apartment);
        ApartmentType apartmentTypeConverted = ApartmentType.fromTypeName(apartmentType);

        if (apartment.getId() == null) {
            return createApartment(apartmentTypeConverted, apartment);
        }

        Apartment result = apartmentService.save(apartmentTypeConverted, apartment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, apartment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /:apartmentType : get all the apartments.
     *
     * @param apartmentType the type of the apartment
     * @return the ResponseEntity with status 200 (OK) and the list of apartments in body
     */
    @GetMapping("/{apartmentType}")
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<Apartment>> getAllApartments(@PathVariable String apartmentType) {
        log.debug("REST request to get " + apartmentType + ": {}", apartmentType);

        // If the reference doesn't fit it will throw an Exception
        ApartmentType apartmentTypeConverted = ApartmentType.fromTypeName(apartmentType);

        List<Apartment> apartmentList = apartmentService.findAllByApartmentType(apartmentTypeConverted);
        return ResponseEntity.ok().body(apartmentList);
    }

    /**
     * GET  /:apartmentType : get all the apartments.
     *
     * @param apartmentCriteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of apartments in body
     */
    @GetMapping("/apartments")
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<Page<Apartment>> getAllApartmentsByCriteria(ApartmentCriteria apartmentCriteria, Pageable pageable) {
        log.debug("REST request to get apartments with criteria: {}", apartmentCriteria.toString());

        Page<Apartment> apartmentList = apartmentQueryService.findByCriteria(apartmentCriteria, pageable);
        return ResponseEntity.ok().body(apartmentList);
    }

    /**
     * GET  /{apartmentType}/{reference} : get the apartment with the selected reference
     *
     * @param apartmentType the type of the apartment
     * @param reference the reference of the apartment (must be unique on the DB)
     * @return the ResponseEntity with status 200 (OK) and with body the apartment, or with status 404 (Not Found)
     * if the service found an Apartment with the same ID will return the first
     */
    @GetMapping("/{apartmentType}/{reference}")
    @Timed
    public ResponseEntity<Apartment> getApartment(@PathVariable String apartmentType, @PathVariable String reference) {
        log.debug("REST request to get an " + apartmentType + " with the reference: {}", reference);

        ApartmentType apartmentTypeConverted = ApartmentType.fromTypeName(apartmentType);

        Apartment apartment = apartmentService.findByReference(apartmentTypeConverted, reference);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(apartment));
    }

    /**
     * DELETE  /:apartmentReference/:id : delete the apartment with the reference
     *
     * @param apartmentType the type of the apartment
     * @param reference the reference of the apartment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{apartmentType}/{reference}")
    @Timed
    public ResponseEntity<Void> deleteApartment(@PathVariable String apartmentType, @PathVariable  String reference) {
        log.debug("REST request to delete " + apartmentType +" with reference " + reference + ": {}");

        ApartmentType apartmentTypeConverted = ApartmentType.fromTypeName(apartmentType);

        apartmentService.deleteByReference(apartmentTypeConverted, reference);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, reference)).build();
    }

}
