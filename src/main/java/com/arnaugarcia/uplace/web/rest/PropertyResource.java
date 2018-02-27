package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.domain.IndustrialPlant;
import com.arnaugarcia.uplace.domain.Terrain;
import com.arnaugarcia.uplace.repository.*;
import com.arnaugarcia.uplace.service.*;
import com.arnaugarcia.uplace.service.dto.PropertyCriteria;
import com.arnaugarcia.uplace.service.dto.PropertyDTO;
import com.arnaugarcia.uplace.web.rest.errors.ErrorConstants;
import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.uplace.domain.Property;

import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class PropertyResource<T extends Property> {

    private final Logger log = LoggerFactory.getLogger(PropertyResource.class);

    private final PropertyQueryService propertyQueryService;

    private final PropertyRepository propertyRepository;

    public PropertyResource(PropertyQueryService propertyQueryService, PropertyRepository propertyRepository) {
        this.propertyQueryService = propertyQueryService;
        this.propertyRepository = propertyRepository;
    }

    /**
     * POST  /properties : Create a new property.
     *
     * @param propertyDTO the property to create
     * @return the ResponseEntity with status 201 (Created) and with body the new property, or with status 400 (Bad Request) if the property has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    /*@PostMapping("/properties")
    @Timed
    public ResponseEntity<T> createProperty(@Valid @RequestBody T propertyDTO) throws URISyntaxException {
        log.debug("REST request to save Property : {}", propertyDTO);

        if (propertyDTO.getReference() != null) {
            throw new BadRequestAlertException("A new property cannot already have a Reference", propertyDTO.getReference(), ErrorConstants.ERR_BAD_REFERENCE);
        }

        T result = propertyRepository.save(propertyDTO);
        return ResponseEntity.created(new URI("/api/properties/" + result.getReference()))
            .headers(HeaderUtil.createEntityCreationAlert(propertyDTO.getReference(), result.getId().toString()))
            .body(result);
    }*/

    /**
     * PUT  /properties : Updates an existing property.
     *
     * @param propertyDTO the property to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated property,
     * or with status 400 (Bad Request) if the property is not valid,
     * or with status 500 (Internal Server Error) if the property couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    /*@PutMapping("/properties")
    @Timed
    public ResponseEntity<PropertyDTO> updateProperty(@Valid @RequestBody PropertyDTO propertyDTO) throws URISyntaxException {
        log.debug("REST request to update Property : {}", propertyDTO);
        if (propertyDTO.getId() == null) {
            return createProperty(propertyDTO);
        }
        PropertyDTO result = propertyService.save(propertyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(propertyDTO.getPropertyType(), propertyDTO.getReference()))
            .body(result);
    }*/

    /**
     * GET  /properties : get all the properties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of properties in body
     */
    @GetMapping("/properties")
    @Timed
    public ResponseEntity<List<T>> getAllProperties() {
        log.debug("REST request to get all Properties");
        List<T> entityList = propertyRepository.findAll();
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /properties/{reference} : get the property by reference
     *
     * @return the ResponseEntity with status 200 (OK) and the property in body
     */
    /*@GetMapping("/properties/{reference}")
    @Timed
    public Property getAllProperties(@PathVariable String reference) {
        log.debug("REST request to get all Properties");
        return propertyService.findOne(reference);
    }

    @GetMapping("/properties/last/{size}")
    @Timed
    public List<Property> getLastProperties(@PathVariable Integer size) {
        log.debug("Request to get last " + size + " Properties");
        return propertyService.getLastProperties(size);
    }*/

}
