package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.repository.*;
import com.arnaugarcia.uplace.service.PropertyQueryService;
import com.arnaugarcia.uplace.service.PropertyService;
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

    private final PropertyRepository propertyRepository;

    private final PropertyQueryService propertyQueryService;

    private final PropertyService propertyService;

    private final ApartmentRepository apartmentRepository;

    private final ParkingRepository parkingRepository;

    private final BusinessRepository businessRepository;

    private final OfficeRepository officeRepository;

    private final TerrainRepository terrainRepository;

    public PropertyResource(PropertyRepository propertyRepository, ApartmentRepository apartmentRepository, ParkingRepository parkingRepository, BusinessRepository businessRepository, OfficeRepository officeRepository, TerrainRepository terrainRepository, PropertyQueryService propertyQueryService, PropertyService propertyService) {
        this.propertyRepository = propertyRepository;
        this.apartmentRepository = apartmentRepository;
        this.parkingRepository = parkingRepository;
        this.businessRepository = businessRepository;
        this.officeRepository = officeRepository;
        this.terrainRepository = terrainRepository;
        this.propertyQueryService = propertyQueryService;
        this.propertyService = propertyService;
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
        properties.addAll(apartmentRepository.findAll());

        //Adds business to list
        properties.addAll(businessRepository.findAll());

        //Adds offices to list
        properties.addAll(officeRepository.findAll());

        //Adds parking to list
        properties.addAll(parkingRepository.findAll());

        //Adds terrain to list
        properties.addAll(terrainRepository.findAll());

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
