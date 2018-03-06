package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Property;
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

/**
 * Service Implementation for managing Property.
 */
@Service
@Transactional
public class PropertyService<T extends Property> {

    private final Logger log = LoggerFactory.getLogger(PropertyService.class);

    private final PropertyRepository<T> propertyRepository;

    public PropertyService(PropertyRepository<T> propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /**
     * Save a property.
     *
     * @param property the entity to save
     * @return the persisted entity
     */
    public T save(T property) {
        log.debug("Request to save Property : {}", property);
        property.setReference(this.createReference());
        return  propertyRepository.save(property);
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
        return propertyRepository.findByReference(reference);
    }

    /**
     * Delete the property by reference.
     *
     * @param reference the reference of the entity
     */
    public void delete(String reference) {
        log.debug("Request to delete Property : {}", reference);
        T property = propertyRepository.findByReference(reference);
        if (property == null) {
            throw new BadRequestAlertException("Reference not found", "PROPERTY", ErrorConstants.ERR_BAD_REFERENCE);
        }
        propertyRepository.delete(property.getId());
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
