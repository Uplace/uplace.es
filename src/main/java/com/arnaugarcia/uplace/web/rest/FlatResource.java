package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.domain.Apartment;
import com.arnaugarcia.uplace.domain.Gallery;
import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.domain.enumeration.ApartmentType;
import com.arnaugarcia.uplace.repository.ApartmentRepository;
import com.arnaugarcia.uplace.repository.GalleryRepository;
import com.arnaugarcia.uplace.service.util.RandomUtil;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Flat.
 */
@RestController
@RequestMapping("/api")
public class FlatResource {

    private final Logger log = LoggerFactory.getLogger(FlatResource.class);

    private static final String ENTITY_NAME = "flat";

    private final ApartmentRepository apartmentRepository;

    private final GalleryRepository galleryRepository;

    public FlatResource(ApartmentRepository apartmentRepository, GalleryRepository galleryRepository) {
        this.apartmentRepository = apartmentRepository;
        this.galleryRepository = galleryRepository;
    }

    /**
     * POST  /flats : Create a new flat.
     *
     * @param flat the flat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new property, or with status 400 (Bad Request) if the property has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/flats")
    @Timed
    public ResponseEntity<Property> createFlat(@RequestBody Apartment flat) throws URISyntaxException {
        log.debug("REST request to save a new Flat : {}", flat);
        if (flat.getId() != null) {
            throw new BadRequestAlertException("A new flat cannot already have an ID", ENTITY_NAME, "idexists");
        } else if (!flat.getPropertyType().equals(ApartmentType.FLAT)) {
            //If the user is trying to delete other property
            throw new BadRequestAlertException("The propertyType must be 'FLAT' in order to create a new FLAT",ENTITY_NAME,"badtype");
        }

        //Set the created to now()
        flat.setCreated(ZonedDateTime.now());

        //Create and save a new empty gallery
        Gallery gallery = galleryRepository.save(new Gallery());
        flat.setGallery(gallery);
        flat.propertyType(ApartmentType.FLAT);
        //Generate the random reference
        flat.setReference(RandomUtil.generateReference().toUpperCase());
        Apartment result = apartmentRepository.save(flat);
        result.getGallery().setProperty(result);
        /*
         * Updates the apartment in order to set the correct gallery with the correct ID of the gallery otherwise,
         * the gallery assigned will not have an ID
         */
        apartmentRepository.save(result);

        return ResponseEntity.created(new URI("/api/flat/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /flats : Updates an existing flat.
     *
     * @param flat the flat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated flat,
     * or with status 400 (Bad Request) if the flat is not valid,
     * or with status 500 (Internal Server Error) if the flat couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/flats")
    @Timed
    public ResponseEntity<Property> updateFlat (@RequestBody Apartment flat) throws URISyntaxException {
        log.debug("REST request to update Flat : {}", flat);
        if (flat.getId() == null) {
            return createFlat(flat);
        } else if (!flat.getPropertyType().equals(ApartmentType.FLAT)) {
            throw new BadRequestAlertException("The propertyType must be 'FLAT' in order to update a FLAT",ENTITY_NAME,"badType");
        }
        // Set updated to now()
        flat.setUpdated(ZonedDateTime.now());
        Property result = apartmentRepository.save(flat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, flat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /flats : get all the properties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of flats in body
     */
    @GetMapping("/flats")
    @Timed
    public List<Apartment> getAllFlats() {
        log.debug("REST request to get all flats");
        return apartmentRepository.findAllByPropertyType(ApartmentType.FLAT);
    }

    /*@GetMapping("/flats/{id}")
    @Timed
    public ResponseEntity<Apartment> getFlatByReference(@PathVariable Long id) {
        log.debug("REST request to get flat : {}", id);
        Apartment apartment = apartmentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(apartment));
    }*/

    /**
     * GET  /properties/:reference : get the "id" property.
     *
     * @param reference the reference of the property to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the property, or with status 404 (Not Found)
     */
    @GetMapping("/flats/{reference}")
    @Timed
    public ResponseEntity<Property> getFlat(@PathVariable String reference) {
        log.debug("REST request to get Property : {}", reference);
        Apartment apartment = apartmentRepository.findFirstByReference(reference);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(apartment));
    }

    /**
     * DELETE  /flats/:reference : delete the "reference" flat.
     *
     * @param reference the reference of the flat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/flats/{reference}")
    @Timed
    @Transactional
    public ResponseEntity<Void> deleteProperty(@PathVariable String reference) {
        log.debug("REST request to delete a Flat : {}", reference);
        Apartment apartment = apartmentRepository.findFirstByReference(reference);
        if (apartment.getId() == null) {
            throw new BadRequestAlertException("The referenced Flat doesn't exists", ENTITY_NAME, "idnoexists");
        } else if (apartment.getGallery() != null) {
            // Deletes the gallery if exists
            galleryRepository.delete(apartment.getGallery());
        }
        apartmentRepository.deleteByReference(reference);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, reference)).build();
    }
}
