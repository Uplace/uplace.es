package com.arnaugarcia.uplace.service.impl;

import com.arnaugarcia.uplace.service.ParkingService;
import com.arnaugarcia.uplace.domain.Parking;
import com.arnaugarcia.uplace.repository.ParkingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Parking.
 */
@Service
@Transactional
public class ParkingServiceImpl implements ParkingService {

    private final Logger log = LoggerFactory.getLogger(ParkingServiceImpl.class);

    private final ParkingRepository parkingRepository;

    public ParkingServiceImpl(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    /**
     * Save a parking.
     *
     * @param parking the entity to save
     * @return the persisted entity
     */
    @Override
    public Parking save(Parking parking) {
        log.debug("Request to save Parking : {}", parking);
        return parkingRepository.save(parking);
    }

    /**
     * Get all the parkings.
     *
     * @return the list of entities
     */
    @Override
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
    @Override
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
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Parking : {}", id);
        parkingRepository.delete(id);
    }
}
