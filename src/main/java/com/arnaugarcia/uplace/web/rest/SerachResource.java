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
    public ResponseEntity<Page<Building>> searchApartments(BuildingCriteria buildingCriteria, Pageable pageable){
        Page<Building> page = buildingQueryService.findByCriteria(buildingCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/search/buildings");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    @GetMapping("/search/business")
    public ResponseEntity<Page<Business>> searchBusiness(BusinessCriteria businessCriteria, Pageable pageable){
        Page<Business> page = businessQueryService.findByCriteria(businessCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/search/business");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    @GetMapping("/search/establishments")
    public ResponseEntity<Page<Establishment>> searchBEstablishments(EstablishmentCriteria establishmentCriteria, Pageable pageable){
        Page<Establishment> page = establishmentQueryService.findByCriteria(establishmentCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/search/establishments");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    @GetMapping("/search/hotels")
    public ResponseEntity<Page<Hotel>> searchHotels(HotelCriteria hotelCriteria, Pageable pageable){
        Page<Hotel> page = hotelQueryService.findByCriteria(hotelCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/search/hotels");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    @GetMapping("/search/industrial-plants")
    public ResponseEntity<Page<IndustrialPlant>> searchIndustrialPlants(IndustrialPlantCriteria industrialPlantCriteria, Pageable pageable){
        Page<IndustrialPlant> page = industrialPlantQueryService.findByCriteria(industrialPlantCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/search/industrial-plants");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    @GetMapping("/search/offices")
    public ResponseEntity<Page<Office>> searchOffices(OfficeCriteria officeCriteria, Pageable pageable){
        Page<Office> page = officeQueryService.findByCriteria(officeCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/search/offices");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    @GetMapping("/search/parkings")
    public ResponseEntity<Page<Parking>> searchParkings(ParkingCriteria parkingCriteria, Pageable pageable){
        Page<Parking> page = parkingQueryService.findByCriteria(parkingCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/search/parkings");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    @GetMapping("/search/terrains")
    public ResponseEntity<Page<Terrain>> searchTerrains(TerrainCriteria terrainCriteria, Pageable pageable){
        Page<Terrain> page = terrainQueryService.findByCriteria(terrainCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/search/terrains");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }
}
