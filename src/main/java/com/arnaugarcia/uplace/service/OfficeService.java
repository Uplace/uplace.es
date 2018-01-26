package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Office;
import com.arnaugarcia.uplace.repository.OfficeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Office.
 */
@Service
@Transactional
public class OfficeService {

    private final Logger log = LoggerFactory.getLogger(OfficeService.class);

    private final OfficeRepository officeRepository;

    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    /**
     * Save a office.
     *
     * @param office the entity to save
     * @return the persisted entity
     */
    public Office save(Office office) {
        log.debug("Request to save Office : {}", office);
        return officeRepository.save(office);
    }

    /**
     * Get all the offices.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Office> findAll() {
        log.debug("Request to get all Offices");
        return officeRepository.findAll();
    }

    /**
     * Get one office by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Office findOne(Long id) {
        log.debug("Request to get Office : {}", id);
        return officeRepository.findOne(id);
    }

    /**
     * Delete the office by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Office : {}", id);
        officeRepository.delete(id);
    }
}