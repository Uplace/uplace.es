package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Hotel;
import com.arnaugarcia.uplace.repository.HotelRepository;
import com.arnaugarcia.uplace.repository.PropertyRepository;
import com.arnaugarcia.uplace.service.dto.MarkerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing Hotel.
 */
@Service
public class MarkerService {

    private final Logger log = LoggerFactory.getLogger(MarkerService.class);

    private final PropertyRepository propertyRepository;

    public MarkerService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Transactional(readOnly = true)
    public List<MarkerDTO> getAllMarkers() {
        return propertyRepository.findAllMarkers();
    }
}
