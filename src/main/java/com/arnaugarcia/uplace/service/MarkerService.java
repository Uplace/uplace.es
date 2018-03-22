package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Marker;
import com.arnaugarcia.uplace.domain.Photo;
import com.arnaugarcia.uplace.repository.PropertyRepository;
import com.arnaugarcia.uplace.service.dto.MarkerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.arnaugarcia.uplace.service.util.TransformMarkerToMarkerDTO.markerToMarkerDTO;

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
        List<Marker> markerList = propertyRepository.findAllMarkers();

        List<MarkerDTO> markerDTOS = markerList.parallelStream()
            .map(markerToMarkerDTO)
            .collect(Collectors.toList());

        // This can be made with JPA 1.7 findTop or inner join query
        Pageable limit = new PageRequest(0, 1);

        markerDTOS.parallelStream().forEach((markerDTO -> {
                List<Photo> photos = propertyRepository.findThumbnailByReference(markerDTO.getPropertyReference(), limit);
                markerDTO.setPhoto(photos.get(0));
            }
        ));
        /*markerDTOList.parallelStream().forEach((markerDTO -> {
            if (markerDTO.getDate() != null) {
                LocalDate localDate =  markerDTO.getDate().toLocalDate();
                LocalDate today = LocalDate.now();
                Period period = Period.between(localDate, today);
                if (period.getMonths() >= 1) {
                    markerDTO.setNew(false);
                } else {
                    markerDTO.setNew(true);
                }
            } else {
                markerDTO.setNew(false);
            }
        }));
        List<MarkerDTO> result = markerDTOList.parallelStream()
            .filter(markerDTO -> Objects.nonNull(markerDTO.getLatitude()))
            .filter(markerDTO -> markerDTO.getLatitude() > 0)
            .filter(markerDTO -> Objects.nonNull(markerDTO.getLongitude()))
            .filter(markerDTO -> markerDTO.getLongitude() > 0)
            .collect(Collectors.toList());*/
        return markerDTOS;
    }
}
