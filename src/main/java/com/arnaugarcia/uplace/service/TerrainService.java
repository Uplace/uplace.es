package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Terrain;
import java.util.List;

/**
 * Service Interface for managing Terrain.
 */
public interface TerrainService {

    /**
     * Save a terrain.
     *
     * @param terrain the entity to save
     * @return the persisted entity
     */
    Terrain save(Terrain terrain);

    /**
     * Get all the terrains.
     *
     * @return the list of entities
     */
    List<Terrain> findAll();

    /**
     * Get the "id" terrain.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Terrain findOne(Long id);

    /**
     * Delete the "id" terrain.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
