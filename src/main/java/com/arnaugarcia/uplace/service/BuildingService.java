package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Building;
import com.arnaugarcia.uplace.repository.BuildingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Building.
 */
@Service
@Transactional
public class BuildingService {

    private final Logger log = LoggerFactory.getLogger(BuildingService.class);

    private final BuildingRepository buildingRepository;

    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    /**
     * Save a building.
     *
     * @param building the entity to save
     * @return the persisted entity
     */
    public Building save(Building building) {
        log.debug("Request to save Building : {}", building);
        return buildingRepository.save(building);
    }

    /**
     * Get all the buildings.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Building> findAll() {
        log.debug("Request to get all Buildings");
        return buildingRepository.findAll();
    }

    /**
     * Get one building by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Building findOne(Long id) {
        log.debug("Request to get Building : {}", id);
        return buildingRepository.findOne(id);
    }

    /**
     * Delete the building by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Building : {}", id);
        buildingRepository.delete(id);
    }
}
