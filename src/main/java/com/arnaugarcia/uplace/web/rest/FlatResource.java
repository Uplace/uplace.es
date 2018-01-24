package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.domain.Agent;
import com.arnaugarcia.uplace.domain.Apartment;
import com.arnaugarcia.uplace.domain.Photo;
import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.domain.enumeration.ApartmentType;
import com.arnaugarcia.uplace.repository.ApartmentRepository;
import com.arnaugarcia.uplace.repository.PhotoRepository;
import com.arnaugarcia.uplace.service.util.RandomUtil;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Authorization;
import io.undertow.util.BadRequestException;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Flat.
 */
@RestController
@RequestMapping("/api")
public class FlatResource {

    private final Logger log = LoggerFactory.getLogger(FlatResource.class);

    private static final String ENTITY_NAME = "flat";

    private final ApartmentRepository apartmentRepository;

    private final PhotoRepository photoRepository;


    public FlatResource(ApartmentRepository apartmentRepository, PhotoRepository photoRepository) {
        this.apartmentRepository = apartmentRepository;
        this.photoRepository = photoRepository;
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

        flat.propertyType(ApartmentType.FLAT);
        //Generate the random reference
        flat.setReference(RandomUtil.generateReference().toUpperCase());
        Apartment result = apartmentRepository.save(flat);

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
            throw new BadRequestAlertException("The propertyType must be 'FLAT' in order to update a FLAT", ENTITY_NAME ,"badType");
        }
        // Set updated to now()
        flat.setUpdated(ZonedDateTime.now());
        Property result = apartmentRepository.save(flat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, flat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /flats : get all flats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of flats in body
     */
    @GetMapping("/flats")
    @Timed
    @Transactional(readOnly = true)
    public Page<Apartment> getAllFlats(Pageable pageable) {
        log.debug("REST request to get all flats");
        return apartmentRepository.findAllByPropertyType(ApartmentType.FLAT, pageable);
    }

    /**
     * GET  /flats : get all the properties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of flats in body
     */
    @GetMapping("/flats/{reference}/agents")
    @Timed
    @Transactional(readOnly = true)
    public Set<Agent> getAgentsOfFlat(@PathVariable String reference) {
        log.debug("REST request to get all flats");
        Apartment apartment = apartmentRepository.findFirstByReference(reference);
        if (apartment == null || apartment.getManagers() == null) {
            throw new BadRequestAlertException("The Flat with this reference doesn't exists or we have an error with our agents", ENTITY_NAME, "badagent");
        }
        return apartment.getManagers();
    }

    /**
     * GET  /flats/photos : create and assign all the photos to the Flat
     *
     * @return the ResponseEntity with status 200 (OK) and the list of photos in body
     */
    @GetMapping("/flats/{reference}/photos")
    @Timed
    @Transactional(readOnly = true)
    public Set<Photo> getPhotosOfFlat(@PathVariable String reference) {
        log.debug("REST request to get all flats");
        Apartment flat = this.apartmentRepository.findFirstByReference(reference);

        if (!flat.getPropertyType().equals(ApartmentType.FLAT)) {
            throw new BadRequestAlertException("The propertyType must be 'FLAT' in order to add photos to FLAT",ENTITY_NAME,"badtype");
        }

        return flat.getPhotos();
    }

    /**
     * GET  /flats/:reference : get the "id" property.
     *
     * @param reference the reference of the property to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the property, or with status 404 (Not Found)
     */
    @GetMapping("/flats/{reference}")
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<Property> getFlat(@PathVariable String reference) {
        log.debug("REST request to get Property : {}", reference);
        Apartment apartment = apartmentRepository.findFirstByReference(reference);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(apartment));
    }

    /**
     * GET  /properties/:reference : get the "id" property.
     *
     * @param reference the reference of the property to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the property, or with status 404 (Not Found)
     */
    @DeleteMapping("/flats/{reference}/photo/{id}")
    @Timed
    @Transactional(readOnly = true)
    public Set<Photo> deletePhotoFlat(@PathVariable String reference, @PathVariable Long id) {
        log.debug("REST request to delete Photo of a Flat : {}", reference);
        Apartment apartment = apartmentRepository.findFirstByReference(reference);
        Photo photo = photoRepository.findOne(id);
        if (!apartment.getPhotos().contains(photo)) {
            throw new BadRequestAlertException("This id doesn't exists or don't belong to this Flat", ENTITY_NAME, "badphoto");
        }
        photoRepository.delete(id);
        return getPhotosOfFlat(reference);
    }

    /**
     * GET  /properties/:reference : get the "id" property.
     *
     * @param reference the reference of the property to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the property, or with status 404 (Not Found)
     */
    @PutMapping("/flats/{reference}/photo")
    @Timed
    @Transactional(readOnly = true)
    public Set<Photo> UpdatePhotoFlat(@PathVariable String reference, @RequestBody Photo photo) {
        log.debug("REST request to update Photo of a Flat : {}", reference);
        Apartment apartment = apartmentRepository.findFirstByReference(reference);
        if (!apartment.getPhotos().contains(photo)) {
            throw new BadRequestAlertException("This id doesn't exists or don't belong to this Flat", ENTITY_NAME, "badphoto");
        }
        photoRepository.save(photo);
        return getPhotosOfFlat(reference);
    }

    /**
     * POST  /flats/{reference}/photos : get the "id" property.
     *
     * @param reference the reference of the property to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the property, or with status 404 (Not Found)
     */
    @PostMapping("/flats/{reference}/photos")
    @Timed
    public List<Photo> addPhotosToFlat(@PathVariable String reference, @RequestBody List<Photo> photos) {
        log.debug("REST request to get all flats");
        Apartment flat = this.apartmentRepository.findFirstByReference(reference);

        if (!flat.getPropertyType().equals(ApartmentType.FLAT)) {
            throw new BadRequestAlertException("The propertyType must be 'FLAT' in order to add photos to FLAT",ENTITY_NAME,"badtype");
        }

        photos.forEach((photo -> photo.setProperty(flat)));

        return photoRepository.save(photos);
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
        }
        apartmentRepository.deleteByReference(reference);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, reference)).build();
    }
}
