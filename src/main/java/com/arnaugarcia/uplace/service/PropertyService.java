package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.repository.PropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Property.
 */
@Service
@Transactional
public class PropertyService {

    private final Logger log = LoggerFactory.getLogger(PropertyService.class);

    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /**
     * Save a property.
     *
     * @param property the entity to save
     * @return the persisted entity
     */
    public Property save(Property property) {
        log.debug("Request to save Property : {}", property);
        return propertyRepository.save(property);
    }

    /**
     * Get all the properties.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Property> findAll() {
        log.debug("Request to get all Properties");
        return propertyRepository.findAllWithEagerRelationships();
    }

    /**
     * Get one property by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Property findOne(Long id) {
        log.debug("Request to get Property : {}", id);
        return propertyRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the property by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Property : {}", id);
        propertyRepository.delete(id);
    }
}
