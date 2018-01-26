package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Business;
import com.arnaugarcia.uplace.repository.BusinessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Business.
 */
@Service
@Transactional
public class BusinessService {

    private final Logger log = LoggerFactory.getLogger(BusinessService.class);

    private final BusinessRepository businessRepository;

    public BusinessService(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    /**
     * Save a business.
     *
     * @param business the entity to save
     * @return the persisted entity
     */
    public Business save(Business business) {
        log.debug("Request to save Business : {}", business);
        return businessRepository.save(business);
    }

    /**
     * Get all the businesses.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Business> findAll() {
        log.debug("Request to get all Businesses");
        return businessRepository.findAll();
    }

    /**
     * Get one business by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Business findOne(Long id) {
        log.debug("Request to get Business : {}", id);
        return businessRepository.findOne(id);
    }

    /**
     * Delete the business by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Business : {}", id);
        businessRepository.delete(id);
    }
}