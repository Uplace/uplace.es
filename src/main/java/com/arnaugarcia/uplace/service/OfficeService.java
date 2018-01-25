package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Office;
import java.util.List;

/**
 * Service Interface for managing Office.
 */
public interface OfficeService {

    /**
     * Save a office.
     *
     * @param office the entity to save
     * @return the persisted entity
     */
    Office save(Office office);

    /**
     * Get all the offices.
     *
     * @return the list of entities
     */
    List<Office> findAll();

    /**
     * Get the "id" office.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Office findOne(Long id);

    /**
     * Delete the "id" office.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
