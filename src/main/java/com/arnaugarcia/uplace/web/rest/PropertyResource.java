package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.domain.IndustrialPlant;
import com.arnaugarcia.uplace.repository.*;
import com.arnaugarcia.uplace.service.*;
import com.arnaugarcia.uplace.service.dto.PropertyCriteria;
import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.uplace.domain.Property;

import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Property.
 */
@RestController
@RequestMapping("/api")
public class PropertyResource {

    private final Logger log = LoggerFactory.getLogger(PropertyResource.class);

    private static final String ENTITY_NAME = "property";


    private final PropertyQueryService propertyQueryService;

    private final ApartmentService apartmentService;

    private final ParkingService parkingService;

    private final BusinessService businessService;

    private final OfficeService officeService;

    private final TerrainService terrainService;

    private final BuildingService buildingService;

    private final EstablishmentService establishmentService;

    private final IndustrialPlantService industrialPlantService;

    private final HotelService hotelService;

    public PropertyResource(PropertyQueryService propertyQueryService, PropertyService propertyService, ApartmentService apartmentService, ParkingService parkingService, BusinessService businessService, OfficeService officeService, TerrainService terrainService, BuildingService buildingService, EstablishmentService establishmentService, IndustrialPlantService industrialPlantService, HotelService hotelService) {
        this.propertyQueryService = propertyQueryService;
        this.apartmentService = apartmentService;
        this.parkingService = parkingService;
        this.businessService = businessService;
        this.officeService = officeService;
        this.terrainService = terrainService;
        this.buildingService = buildingService;
        this.establishmentService = establishmentService;
        this.industrialPlantService = industrialPlantService;
        this.hotelService = hotelService;
    }

    /**
     * POST  /properties : Create a new property.
     *
     * @param property the property to create
     * @return the ResponseEntity with status 201 (Created) and with body the new property, or with status 400 (Bad Request) if the property has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    /*@PostMapping("/properties")
    @Timed
    public ResponseEntity<Property> createProperty(@Valid @RequestBody Property property) throws URISyntaxException {
        log.debug("REST request to save Property : {}", property);
        if (property.getId() != null) {
            throw new BadRequestAlertException("A new property cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Property result = propertyRepository.save(property);
        return ResponseEntity.created(new URI("/api/properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }*/

    /**
     * PUT  /properties : Updates an existing property.
     *
     * @param property the property to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated property,
     * or with status 400 (Bad Request) if the property is not valid,
     * or with status 500 (Internal Server Error) if the property couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    /*@PutMapping("/properties")
    @Timed
    public ResponseEntity<Property> updateProperty(@Valid @RequestBody Property property) throws URISyntaxException {
        log.debug("REST request to update Property : {}", property);
        if (property.getId() == null) {
            return createProperty(property);
        }
        Property result = propertyRepository.save(property);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, property.getId().toString()))
            .body(result);
    }*/

    /**
     * GET  /properties : get all the properties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of properties in body
     */
    @GetMapping("/properties")
    @Timed
    @Transactional(readOnly = true)
    public List<Property> getAllProperties() {
        List<Property> properties = new ArrayList<>();
        log.debug("REST request to get all Properties");

        //Add all apartments(FLATS, HOUSES, TOWERS, ETC...)
        properties.addAll(apartmentService.findAll());

        //Adds business to list
        properties.addAll(businessService.findAll());

        //Adds offices to list
        properties.addAll(officeService.findAll());

        //Adds parking to list
        properties.addAll(parkingService.findAll());

        //Adds terrain to list
        properties.addAll(terrainService.findAll());

        // Adds hotel to list
        properties.addAll(hotelService.findAll());

        //Adds building to list
        properties.addAll(buildingService.findAll());

        //Adds establishment to list
        properties.addAll(establishmentService.findAll());

        //Adds industrial plant to list
        properties.addAll(industrialPlantService.findAll());

        return properties;
    }

    @GetMapping("/properties/criteria")
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<Property>> getAllPropertiesCriteria(PropertyCriteria criteria) {
        log.debug("REST request to get Properties by criteria: {}", criteria);
        List<Property> entityList = propertyQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }


    /**
     * GET  /properties/:id : get the "id" property.
     *
     * @param id the id of the property to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the property, or with status 404 (Not Found)
     */
    /*@GetMapping("/properties/{id}")
    @Timed
    public ResponseEntity<Property> getProperty(@PathVariable Long id) {
        log.debug("REST request to get Property : {}", id);
        Property property = propertyRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(property));
    }*/

    /**
     * DELETE  /properties/:id : delete the "id" property.
     *
     * @param id the id of the property to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    /*@DeleteMapping("/properties/{id}")
    @Timed
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        log.debug("REST request to delete Property : {}", id);
        propertyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }*/
}
