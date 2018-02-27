package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.repository.LocationRepository;
import com.arnaugarcia.uplace.repository.PropertyRepository;
import com.arnaugarcia.uplace.service.dto.FilterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Hotel.
 */
@Service
public class FilterService {

    private final Logger log = LoggerFactory.getLogger(FilterService.class);

    private final PropertyRepository propertyRepository;

    private final LocationRepository locationRepository;

    public FilterService(PropertyRepository propertyRepository, LocationRepository locationRepository) {
        this.propertyRepository = propertyRepository;
        this.locationRepository = locationRepository;
    }

    /*public FilterDTO getFilters() {

        List<String> cities = locationRepository.findAllCities();

        List<Integer> prices = propertyRepository.findAllPrices().parallelStream().map(number -> {
            return (number.intValue() + 5000) / 10000 * 10000;
        }).collect(Collectors.toList());

        Integer maxPrice = Collections.max(prices);

        Integer minPrice = Collections.min(prices);

        List<String> types = propertyRepository.findAllTypes();

        FilterDTO filterDTO = new FilterDTO(cities, prices, maxPrice, minPrice, types);
        return filterDTO;
    }*/
}
