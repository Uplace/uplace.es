package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Business;
import java.util.List;

/**
 * Service Interface for managing Business.
 */
public interface BusinessService {

    /**
     * Save a business.
     *
     * @param business the entity to save
     * @return the persisted entity
     */
    Business save(Business business);

    /**
     * Get all the businesses.
     *
     * @return the list of entities
     */
    List<Business> findAll();

    /**
     * Get the "id" business.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Business findOne(Long id);

    /**
     * Delete the "id" business.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
