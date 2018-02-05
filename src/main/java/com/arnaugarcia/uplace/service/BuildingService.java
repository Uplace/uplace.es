package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Building;
import com.arnaugarcia.uplace.repository.BuildingRepository;
import com.arnaugarcia.uplace.repository.PropertyRepository;
import com.arnaugarcia.uplace.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Implementation for managing Building.
 */
@Service
@Transactional
public class BuildingService {

    private final Logger log = LoggerFactory.getLogger(BuildingService.class);

    private final BuildingRepository buildingRepository;
    private final PropertyRepository propertyRepository;

    private final PropertyService propertyService;

    public BuildingService(BuildingRepository buildingRepository, PropertyRepository propertyRepository, PropertyService propertyService) {
        this.buildingRepository = buildingRepository;
        this.propertyRepository = propertyRepository;
        this.propertyService = propertyService;
    }

    /**
     * Save a building.
     *
     * @param building the entity to save
     * @return the persisted entity
     */
    public Building save(Building building) {
        log.debug("Request to save Building : {}", building);

        building.setReference(propertyService.createReference());

        // Si tiene id o no haces el created o no
        if (building.getId() != null) { //Tiene id
            building.setUpdated(ZonedDateTime.now());
        } else { //No tiene id
            building.setCreated(ZonedDateTime.now());
        }
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
}
