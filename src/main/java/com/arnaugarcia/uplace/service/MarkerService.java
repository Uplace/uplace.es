package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.repository.PropertyRepository;
import com.arnaugarcia.uplace.service.dto.MarkerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        List<MarkerDTO> markerDTOList = propertyRepository.findAllMarkers();
        List<MarkerDTO> result = markerDTOList.parallelStream()
            .filter(markerDTO -> Objects.nonNull(markerDTO.getLatitude()))
            .filter(markerDTO -> markerDTO.getLatitude() > 0)
            .filter(markerDTO -> Objects.nonNull(markerDTO.getLongitude()))
            .filter(markerDTO -> markerDTO.getLongitude() > 0)
            .collect(Collectors.toList());
        return result;
    }
}
