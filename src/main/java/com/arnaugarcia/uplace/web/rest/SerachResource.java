package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.domain.*;
import com.arnaugarcia.uplace.service.dto.*;
import com.arnaugarcia.uplace.service.queries.*;
import com.arnaugarcia.uplace.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final IndustrialPlantQueryService industrialPlantQueryService;

    private final OfficeQueryService officeQueryService;

    private final ParkingQueryService parkingQueryService;

    private final TerrainQueryService terrainQueryService;

    public SerachResource(PropertyQueryService<T> propertyQueryService, ApartmentQueryService apartmentQueryService, BuildingQueryService buildingQueryService, BusinessQueryService businessQueryService, EstablishmentQueryService establishmentQueryService, HotelQueryService hotelQueryService, IndustrialPlantQueryService industrialPlantQueryService, OfficeQueryService officeQueryService, ParkingQueryService parkingQueryService, TerrainQueryService terrainQueryService) {
        this.propertyQueryService = propertyQueryService;
        this.apartmentQueryService = apartmentQueryService;
        this.buildingQueryService = buildingQueryService;
        this.businessQueryService = businessQueryService;
        this.establishmentQueryService = establishmentQueryService;
        this.hotelQueryService = hotelQueryService;
        this.industrialPlantQueryService = industrialPlantQueryService;
        this.officeQueryService = officeQueryService;
        this.parkingQueryService = parkingQueryService;
        this.terrainQueryService = terrainQueryService;
    }

    @GetMapping("/search/properties")
    public ResponseEntity<Page<T>> searchProperties(PropertyCriteria propertyCriteria, Pageable pageable){
        Page<T> page = propertyQueryService.findByCriteria(propertyCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/search/properties");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    @GetMapping("/search/apartments")
    public ResponseEntity<Page<Apartment>> searchApartments(ApartmentCriteria apartmentCriteria, Pageable pageable){
        Page<Apartment> page = apartmentQueryService.findByCriteria(apartmentCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/search/apartments");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
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
    public Page<Hotel> searchHotels(HotelCriteria hotelCriteria, Pageable pageable){
        return hotelQueryService.findByCriteria(hotelCriteria, pageable);
    }

    @GetMapping("/search/industrial-plants")
    public Page<IndustrialPlant> searchIndustrialPlants(IndustrialPlantCriteria industrialPlantCriteria, Pageable pageable){
        return industrialPlantQueryService.findByCriteria(industrialPlantCriteria, pageable);
    }

    @GetMapping("/search/offices")
    public Page<Office> searchOffices(OfficeCriteria officeCriteria, Pageable pageable){
        return officeQueryService.findByCriteria(officeCriteria, pageable);
    }

    @GetMapping("/search/parkings")
    public Page<Parking> searchParkings(ParkingCriteria parkingCriteria, Pageable pageable){
        return parkingQueryService.findByCriteria(parkingCriteria, pageable);
    }

    @GetMapping("/search/terrains")
    public Page<Terrain> searchTerrains(TerrainCriteria terrainCriteria, Pageable pageable){
        return terrainQueryService.findByCriteria(terrainCriteria, pageable);
    }
}
