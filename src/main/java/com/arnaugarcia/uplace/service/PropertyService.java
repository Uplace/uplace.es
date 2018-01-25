package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Property;
import java.util.List;

/**
 * Service Interface for managing Property.
 */
public interface PropertyService {

    /**
     * Save a property.
     *
     * @param property the entity to save
     * @return the persisted entity
     */
    Property save(Property property);

    /**
     * Get all the properties.
     *
     * @return the list of entities
     */
    List<Property> findAll();

    /**
     * Get the "id" property.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Property findOne(Long id);

    /**
     * Delete the "id" property.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
