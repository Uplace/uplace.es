package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Establishment;
import com.arnaugarcia.uplace.repository.EstablishmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Establishment.
 */
@Service
@Transactional
public class EstablishmentService {

    private final Logger log = LoggerFactory.getLogger(EstablishmentService.class);

    private final EstablishmentRepository establishmentRepository;

    public EstablishmentService(EstablishmentRepository establishmentRepository) {
        this.establishmentRepository = establishmentRepository;
    }

    /**
     * Save a establishment.
     *
     * @param establishment the entity to save
     * @return the persisted entity
     */
    public Establishment save(Establishment establishment) {
        log.debug("Request to save Establishment : {}", establishment);
        return establishmentRepository.save(establishment);
    }

    /**
     * Get all the establishments.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Establishment> findAll() {
        log.debug("Request to get all Establishments");
        return establishmentRepository.findAll();
    }

    /**
     * Get one establishment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Establishment findOne(Long id) {
        log.debug("Request to get Establishment : {}", id);
        return establishmentRepository.findOne(id);
    }

    /**
     * Delete the establishment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Establishment : {}", id);
        establishmentRepository.delete(id);
    }
}
