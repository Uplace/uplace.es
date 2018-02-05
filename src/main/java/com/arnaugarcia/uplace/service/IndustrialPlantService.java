package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.IndustrialPlant;
import com.arnaugarcia.uplace.repository.IndustrialPlantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Implementation for managing IndustrialPlant.
 */
@Service
@Transactional
public class IndustrialPlantService {

    private final Logger log = LoggerFactory.getLogger(IndustrialPlantService.class);

    private final IndustrialPlantRepository industrialPlantRepository;

    private final PropertyService propertyService;

    public IndustrialPlantService(IndustrialPlantRepository industrialPlantRepository, PropertyService propertyService) {
        this.industrialPlantRepository = industrialPlantRepository;
        this.propertyService = propertyService;
    }

    /**
     * Save a industrialPlant.
     *
     * @param industrialPlant the entity to save
     * @return the persisted entity
     */
    public IndustrialPlant save(IndustrialPlant industrialPlant) {
        log.debug("Request to save IndustrialPlant : {}", industrialPlant);

        industrialPlant.setReference(propertyService.createReference());

        // Si tiene id o no haces el created o no
        if (industrialPlant.getId() != null) { //Tiene id
            industrialPlant.setUpdated(ZonedDateTime.now());
        } else { //No tiene id
            industrialPlant.setCreated(ZonedDateTime.now());
        }
        return industrialPlantRepository.save(industrialPlant);
    }

    /**
     * Get all the industrialPlants.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<IndustrialPlant> findAll() {
        log.debug("Request to get all IndustrialPlants");
        return industrialPlantRepository.findAll();
    }

    /**
     * Get one industrialPlant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public IndustrialPlant findOne(Long id) {
        log.debug("Request to get IndustrialPlant : {}", id);
        return industrialPlantRepository.findOne(id);
    }

    /**
     * Delete the industrialPlant by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete IndustrialPlant : {}", id);
        industrialPlantRepository.delete(id);
    }
}
