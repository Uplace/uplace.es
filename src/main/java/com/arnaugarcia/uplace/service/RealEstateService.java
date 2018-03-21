package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.domain.RealEstate;
import com.arnaugarcia.uplace.repository.RealEstateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Service Implementation for managing RealEstate.
 */
@Service
@Transactional
public class RealEstateService<T extends Property> {

    private final Logger log = LoggerFactory.getLogger(RealEstateService.class);

    private final RealEstateRepository<T> realEstateRepository;

    public RealEstateService(RealEstateRepository<T> realEstateRepository) {
        this.realEstateRepository = realEstateRepository;
    }

    /**
     * Save a realEstate.
     *
     * @param realEstate the entity to save
     * @return the persisted entity
     */
    public RealEstate save(RealEstate realEstate) {
        log.debug("Request to save RealEstate : {}", realEstate);
        return realEstateRepository.save(realEstate);
    }

    /**
     * Get all the realEstates.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RealEstate> findAll() {
        log.debug("Request to get all RealEstates");
        return realEstateRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<T> findProperties(String reference){
        return realEstateRepository.findPropertiesByReference(reference);
    }

    /**
     * Get one realEstate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RealEstate findOne(Long id) {
        log.debug("Request to get RealEstate : {}", id);
        return realEstateRepository.findOne(id);
    }

    /**
     * Delete the realEstate by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RealEstate : {}", id);
        realEstateRepository.delete(id);
    }
}
