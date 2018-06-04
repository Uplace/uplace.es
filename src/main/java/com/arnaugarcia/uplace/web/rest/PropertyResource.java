package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.domain.*;
import com.arnaugarcia.uplace.service.InquireService;
import com.arnaugarcia.uplace.service.PropertyService;
import com.arnaugarcia.uplace.service.dto.SearchDTO;
import com.arnaugarcia.uplace.service.PropertyQueryService;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.errors.ErrorConstants;
import com.arnaugarcia.uplace.web.rest.util.CriteriaUtil;
import com.arnaugarcia.uplace.web.rest.util.HeaderUtil;
import com.arnaugarcia.uplace.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing Property.
 */
@Api(description = "Endpoint to interact with all the properties")
@RestController
@RequestMapping("/api")
public class PropertyResource<T extends Property> {

    private final Logger log = LoggerFactory.getLogger(PropertyResource.class);

    private final PropertyQueryService<T> propertyQueryService;

    private final InquireService inquireService;

    private final PropertyService<T> propertyService;

    private final CriteriaUtil criteriaUtil;

    public PropertyResource(PropertyQueryService<T> propertyQueryService, InquireService inquireService, PropertyService<T> propertyService, CriteriaUtil criteriaUtil) {
        this.propertyQueryService = propertyQueryService;
        this.inquireService = inquireService;
        this.propertyService = propertyService;
        this.criteriaUtil = criteriaUtil;
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
    public ResponseEntity<T> createProperty(@Valid @RequestBody T property) throws URISyntaxException {
        log.debug("REST request to save Property : {}", property);

        if (property.getReference() != null) {
            throw new BadRequestAlertException("A new property cannot already have a Reference", "PROPERTY", ErrorConstants.ERR_BAD_REFERENCE);
        }

        // TODO: Implement DTOS in order to validate Entity
        T result = propertyService.save(property);
        return ResponseEntity.created(new URI("/api/properties/" + result.getReference()))
            .headers(HeaderUtil.createEntityCreationAlert(property.getReference(), result.getId().toString()))
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
    @PutMapping("/properties")
    @Timed
    public ResponseEntity<T> updateProperty(@Valid @RequestBody T property) throws URISyntaxException {
        log.debug("REST request to update Property : {}", property);

        if (property.getId() == null) {
            return createProperty(property);
        }

        T result = propertyService.save(property);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(property.getPropertyType(), property.getReference()))
            .body(result);
    }

    /**
     * GET  /properties : get all the properties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of properties in body
     */
    @ApiOperation(value = "This endpoint wil get a page of properties", notes = "You can filter using search endpoints")
    @GetMapping("/properties")
    @Timed
    public ResponseEntity<Page<T>> getAllProperties(SearchDTO searchDTO, Pageable pageable) {
        log.debug("REST request to get all Properties");
        Page<T> page = propertyQueryService.findByCriteria(searchDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/properties");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }


    /**
     * GET  /properties/{reference} : get the property by reference
     *
     * @return the ResponseEntity with status 200 (OK) and the property in body
     */
    @GetMapping("/properties/{reference}")
    @Timed
    public T getAllProperties(@PathVariable String reference) {
        log.debug("REST request to get all Properties");
        return propertyService.findOne(reference);
    }

    /**
     * DELETE  /properties/{reference} : delete the property by reference
     * <p>
     * void the ResponseEntity with status 200 (OK) and the property in body
     */
    @DeleteMapping("/properties/{references}")
    @Timed
    public void removeProperty(@PathVariable String references) {
        log.debug("REST request to delete a property by reference");
        propertyService.delete(references);
    }

    /**
     * POST  /properties/{reference}/inquire : create a inquire request
     * <p>
     * void the ResponseEntity with status 200 (OK) and the inquire in body
     */
    @PostMapping("/properties/{reference}/inquire")
    @Timed
    public void sendInquire(@PathVariable String reference, @RequestBody Request request) {
        log.debug("REST request to create inquire by reference " + reference);
        inquireService.sendInquire(reference, request);
    }


}
