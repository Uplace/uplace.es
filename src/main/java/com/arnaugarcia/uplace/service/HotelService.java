package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Hotel;
import com.arnaugarcia.uplace.repository.HotelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Hotel.
 */
@Service
@Transactional
public class HotelService {

    private final Logger log = LoggerFactory.getLogger(HotelService.class);

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    /**
     * Save a hotel.
     *
     * @param hotel the entity to save
     * @return the persisted entity
     */
    public Hotel save(Hotel hotel) {
        log.debug("Request to save Hotel : {}", hotel);
        return hotelRepository.save(hotel);
    }

    /**
     * Get all the hotels.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Hotel> findAll() {
        log.debug("Request to get all Hotels");
        return hotelRepository.findAll();
    }

    /**
     * Get one hotel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Hotel findOne(Long id) {
        log.debug("Request to get Hotel : {}", id);
        return hotelRepository.findOne(id);
    }

    /**
     * Delete the hotel by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Hotel : {}", id);
        hotelRepository.delete(id);
    }
}
