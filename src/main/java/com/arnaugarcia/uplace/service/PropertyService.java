package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Photo;
import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.repository.PhotoRepository;
import com.arnaugarcia.uplace.repository.PropertyRepository;
import com.arnaugarcia.uplace.service.util.RandomUtil;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.errors.ErrorConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Service Implementation for managing Property.
 */
@Service
@Transactional
public class PropertyService<T extends Property> {

    private final Logger log = LoggerFactory.getLogger(PropertyService.class);

    private final PropertyRepository<T> propertyRepository;

    private final PhotoRepository photoRepository;

    public PropertyService(PropertyRepository<T> propertyRepository, PhotoRepository photoRepository) {
        this.propertyRepository = propertyRepository;
        this.photoRepository = photoRepository;
    }

    /**
     * Save a property.
     *
     * @param property the entity to save
     * @return the persisted entity
     */
    public T save(T property) {
        log.debug("Request to save Property : {}", property);

        if (property.getId() == null) {
            property.setCreated(ZonedDateTime.now());
            property.setReference(this.createReference());
        } else {
            property.setUpdated(ZonedDateTime.now());
        }

        Set<Photo> photos = property.getPhotos();
        T result = propertyRepository.save(property);
        photos.forEach((photo -> photo.setProperty(result)));

        photoRepository.save(photos);

        return result;
    }

    /**
     * Get all the properties.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<T> findAll(Pageable pageable) {
        log.debug("Request to get all Properties");
        return propertyRepository.findAll(pageable);
    }

    /**
     * Get one property by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    /*@Transactional(readOnly = true)
    public Property findOne(Long id) {
        log.debug("Request to get Property : {}", id);
        return propertyRepository.findOneWithEagerRelationships(id);
    }*/

    /**
     * Get one property by reference.
     *
     * @param reference the reference of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public T findOne(String reference) {
        log.debug("Request to get Property : {}", reference);
        T result = propertyRepository.findByReference(reference);
        if (result == null) {
            throw new BadRequestAlertException("Property not found", "Property", ErrorConstants.ERR_BAD_REFERENCE);
        }
        return result;
    }

    /**
     * Delete the property by reference.
     *
     * @param references the references of the entities
     */
    public void delete(String references) {
        log.debug("Request to delete Property : {}", references);
        String[] referencesList = references.split(",");
        List<T> properties = propertyRepository.findByReferenceIn(Arrays.asList(referencesList));
        if (properties.size() <= 0) {
            throw new BadRequestAlertException("Reference not found", "PROPERTY", ErrorConstants.ERR_BAD_REFERENCE);
        }
        propertyRepository.delete(properties);
    }


    /**
     * Create unique reference randomly.
     *
     * @return reference created
     */
    public String createReference() {
        String reference;
        do {
            reference = RandomUtil.generateReference().toUpperCase();
            log.debug("Generating reference: " + reference);
        } while (propertyRepository.findByReference(reference) != null);
        return reference;
    }

    /*@Transactional(readOnly = true)
    public List<Property> getLastProperties(@PathVariable Integer size) {
        PageRequest limit = new PageRequest(0, size);
        return propertyRepository.findLastProperties(limit).getContent();
    }*/

}
