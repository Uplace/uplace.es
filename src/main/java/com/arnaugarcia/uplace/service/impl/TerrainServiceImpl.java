package com.arnaugarcia.uplace.service.impl;

import com.arnaugarcia.uplace.service.TerrainService;
import com.arnaugarcia.uplace.domain.Terrain;
import com.arnaugarcia.uplace.repository.TerrainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Terrain.
 */
@Service
@Transactional
public class TerrainServiceImpl implements TerrainService {

    private final Logger log = LoggerFactory.getLogger(TerrainServiceImpl.class);

    private final TerrainRepository terrainRepository;

    public TerrainServiceImpl(TerrainRepository terrainRepository) {
        this.terrainRepository = terrainRepository;
    }

    /**
     * Save a terrain.
     *
     * @param terrain the entity to save
     * @return the persisted entity
     */
    @Override
    public Terrain save(Terrain terrain) {
        log.debug("Request to save Terrain : {}", terrain);
        return terrainRepository.save(terrain);
    }

    /**
     * Get all the terrains.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Terrain> findAll() {
        log.debug("Request to get all Terrains");
        return terrainRepository.findAll();
    }

    /**
     * Get one terrain by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Terrain findOne(Long id) {
        log.debug("Request to get Terrain : {}", id);
        return terrainRepository.findOne(id);
    }

    /**
     * Delete the terrain by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Terrain : {}", id);
        terrainRepository.delete(id);
    }
}
