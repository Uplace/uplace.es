package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Apartment;
import com.arnaugarcia.uplace.domain.enumeration.ApartmentType;
import com.arnaugarcia.uplace.repository.ApartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Service Implementation for managing Apartment.
 */
@Service
@Transactional
public class ApartmentService {

    private final Logger log = LoggerFactory.getLogger(ApartmentService.class);

    private final ApartmentRepository apartmentRepository;

    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    /**
     * Save a apartment.
     *
     * @param apartment the entity to save
     * @return the persisted entity
     */
    public Apartment save(Apartment apartment) {
        log.debug("Request to save Apartment : {}", apartment);
        return apartmentRepository.save(apartment);
    }

    /**
     * Get all the apartments.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Apartment> findAll() {
        log.debug("Request to get all Apartments");
        return apartmentRepository.findAll();
    }

    /**
     * Get all the flats
     *
     * @return the list of flats
     */
    @Transactional(readOnly = true)
    public Page<Apartment> findAllFlats(Pageable pageable) {
        log.debug("Request to get all flats");
        return apartmentRepository.findAllByPropertyType(ApartmentType.FLAT, pageable);
    }

    /**
     * Get a flat
     *
     * @return the flat of with the requested reference
     */
    @Transactional(readOnly = true)
    public Apartment findFlatByReference(String reference) {
        log.debug("Request to get all flats");
        return apartmentRepository.findFirstByReference(reference);
    }

    /**
     * Get one apartment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Apartment findOne(Long id) {
        log.debug("Request to get Apartment : {}", id);
        return apartmentRepository.findOne(id);
    }

    /**
     * Delete the apartment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Apartment : {}", id);
        apartmentRepository.delete(id);
    }

    /**
     * Delete the apartment by id.
     *
     * @param reference the reference of the apartment
     */
    public void deleteByReference(String reference) {
        log.debug("Request to delete Apartment : {}", reference);
        apartmentRepository.deleteByReference(reference);
    }
}
