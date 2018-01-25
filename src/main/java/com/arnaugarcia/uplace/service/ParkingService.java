package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Parking;
import java.util.List;

/**
 * Service Interface for managing Parking.
 */
public interface ParkingService {

    /**
     * Save a parking.
     *
     * @param parking the entity to save
     * @return the persisted entity
     */
    Parking save(Parking parking);

    /**
     * Get all the parkings.
     *
     * @return the list of entities
     */
    List<Parking> findAll();

    /**
     * Get the "id" parking.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Parking findOne(Long id);

    /**
     * Delete the "id" parking.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
