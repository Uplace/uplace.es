package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.domain.Apartment;
import com.arnaugarcia.uplace.domain.Building;
import com.arnaugarcia.uplace.domain.Business;
import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.service.BuildingQueryService;
import com.arnaugarcia.uplace.service.dto.ApartmentCriteria;
import com.arnaugarcia.uplace.service.dto.BuildingCriteria;
import com.arnaugarcia.uplace.service.dto.BusinessCriteria;
import com.arnaugarcia.uplace.service.queries.ApartmentQueryService;
import com.arnaugarcia.uplace.service.queries.BusinessQueryService;
import com.arnaugarcia.uplace.service.queries.PropertyQueryService;
import com.arnaugarcia.uplace.service.dto.PropertyCriteria;
import io.github.jhipster.service.filter.StringFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SerachResource<T extends Property> {

    private final Logger log = LoggerFactory.getLogger(RequestResource.class);

    private final PropertyQueryService<T> propertyQueryService;

    private final ApartmentQueryService apartmentQueryService;

    private final BuildingQueryService buildingQueryService;

    private final BusinessQueryService businessQueryService;

    public SerachResource(PropertyQueryService<T> propertyQueryService, ApartmentQueryService apartmentQueryService, BuildingQueryService buildingQueryService, BusinessQueryService businessQueryService) {
        this.propertyQueryService = propertyQueryService;
        this.apartmentQueryService = apartmentQueryService;
        this.buildingQueryService = buildingQueryService;
        this.businessQueryService = businessQueryService;
    }

    @GetMapping("/search/properties")
    public Page<T> searchProperties(PropertyCriteria propertyCriteria, Pageable pageable){
        return propertyQueryService.findByCriteria(propertyCriteria, pageable);
    }

    @GetMapping("/search/apartments")
    public Page<Apartment> searchApartments(ApartmentCriteria apartmentCriteria, Pageable pageable){
        return apartmentQueryService.findByCriteria(apartmentCriteria, pageable);
    }

    @GetMapping("/search/buildings")
    public Page<Building> searchApartments(BuildingCriteria buildingCriteria, Pageable pageable){
        return buildingQueryService.findByCriteria(buildingCriteria, pageable);
    }

    @GetMapping("/search/business")
    public Page<Business> searchBusiness(BusinessCriteria businessCriteria, Pageable pageable){
        businessCriteria.setPropertyType(new StringFilter().setContains("Building"));
        return businessQueryService.findByCriteria(businessCriteria, pageable);
    }
}