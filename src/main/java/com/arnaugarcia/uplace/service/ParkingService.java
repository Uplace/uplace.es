package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Parking;
import com.arnaugarcia.uplace.repository.ParkingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Implementation for managing Parking.
 */
@Service
@Transactional
public class ParkingService {

    private final Logger log = LoggerFactory.getLogger(ParkingService.class);

    private final ParkingRepository parkingRepository;

    private final PropertyService propertyService;

    public ParkingService(ParkingRepository parkingRepository, PropertyService propertyService) {
        this.parkingRepository = parkingRepository;
        this.propertyService = propertyService;
    }

    /**
     * Save a parking.
     *
     * @param parking the entity to save
     * @return the persisted entity
     */
    public Parking save(Parking parking) {
        log.debug("Request to save Parking : {}", parking);

        parking.setReference(propertyService.createReference());

        // Si tiene id o no haces el created o no
        if (parking.getId() != null) { //Tiene id
            parking.setUpdated(ZonedDateTime.now());
        } else { //No tiene id
            parking.setCreated(ZonedDateTime.now());
        }
        return parkingRepository.save(parking);
    }

    /**
     * Get all the parkings.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Parking> findAll() {
        log.debug("Request to get all Parkings");
        return parkingRepository.findAll();
    }

    /**
     * Get one parking by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Parking findOne(Long id) {
        log.debug("Request to get Parking : {}", id);
        return parkingRepository.findOne(id);
    }

    /**
     * Delete the parking by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Parking : {}", id);
        parkingRepository.delete(id);
    }
}
