package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.domain.IndustrialPlant;
import com.arnaugarcia.uplace.domain.Terrain;
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

    private final PropertyQueryService propertyQueryService;

    private final PropertyService propertyService;

    public PropertyResource(PropertyQueryService propertyQueryService, PropertyService propertyService) {
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
    @PostMapping("/properties")
    @Timed
    public ResponseEntity<Property> createProperty(@Valid @RequestBody Property property) throws URISyntaxException {
        log.debug("REST request to save Property : {}", property);
        if (property instanceof Terrain) {
            System.out.println("Is a Terrain");
        }
        if (property.getId() != null) {
            throw new BadRequestAlertException("A new property cannot already have an ID", property.getPropertyType(), "idexists");
        }
        Property result = propertyService.save(property);
        return ResponseEntity.created(new URI("/api/properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(property.getPropertyType(), result.getId().toString()))
            .body(result);
    }

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
    public List<Property> getAllProperties() {
        log.debug("REST request to get all Properties");
        return propertyService.findAll();
    }

    /**
     * GET  /properties/{reference} : get the property by reference
     *
     * @return the ResponseEntity with status 200 (OK) and the property in body
     */
    @GetMapping("/properties/{reference}")
    @Timed
    public Property getAllProperties(@PathVariable String reference) {
        log.debug("REST request to get all Properties");
        return propertyService.findOne(reference);
    }

    @GetMapping("/properties/criteria")
    @Timed
    public ResponseEntity<List<Property>> getAllPropertiesCriteria(PropertyCriteria criteria) {
        log.debug("REST request to get Properties by criteria: {}", criteria);
        List<Property> entityList = propertyQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    @GetMapping("/properties/last/{size}")
    @Timed
    public List<Property> getLastProperties(@PathVariable Integer size) {
        log.debug("Request to get last " + size + " Properties");
        return propertyService.getLastProperties(size);
    }

}
