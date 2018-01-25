package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Apartment;
import java.util.List;

/**
 * Service Interface for managing Apartment.
 */
public interface ApartmentService {

    /**
     * Save a apartment.
     *
     * @param apartment the entity to save
     * @return the persisted entity
     */
    Apartment save(Apartment apartment);

    /**
     * Get all the apartments.
     *
     * @return the list of entities
     */
    List<Apartment> findAll();

    /**
     * Get the "id" apartment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Apartment findOne(Long id);

    /**
     * Delete the "id" apartment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
