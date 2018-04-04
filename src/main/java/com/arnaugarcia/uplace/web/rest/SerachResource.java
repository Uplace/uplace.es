package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.domain.*;
import com.arnaugarcia.uplace.service.dto.*;
import com.arnaugarcia.uplace.service.queries.*;
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

    private final EstablishmentQueryService establishmentQueryService;

    private final HotelQueryService hotelQueryService;

    public SerachResource(PropertyQueryService<T> propertyQueryService, ApartmentQueryService apartmentQueryService, BuildingQueryService buildingQueryService, BusinessQueryService businessQueryService, EstablishmentQueryService establishmentQueryService, HotelQueryService hotelQueryService) {
        this.propertyQueryService = propertyQueryService;
        this.apartmentQueryService = apartmentQueryService;
        this.buildingQueryService = buildingQueryService;
        this.businessQueryService = businessQueryService;
        this.establishmentQueryService = establishmentQueryService;
        this.hotelQueryService = hotelQueryService;
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
        return businessQueryService.findByCriteria(businessCriteria, pageable);
    }

    @GetMapping("/search/establishments")
    public Page<Establishment> searchBEstablishments(EstablishmentCriteria establishmentCriteria, Pageable pageable){
        return establishmentQueryService.findByCriteria(establishmentCriteria, pageable);
    }

    @GetMapping("/search/hotels")
    public Page<Hotel> searchBEstablishments(HotelCriteria hotelCriteria, Pageable pageable){
        return hotelQueryService.findByCriteria(hotelCriteria, pageable);
    }
}
