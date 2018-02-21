package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.domain.IndustrialPlant;
import com.arnaugarcia.uplace.service.IndustrialPlantQueryService;
import com.arnaugarcia.uplace.service.IndustrialPlantService;
import com.arnaugarcia.uplace.service.dto.IndustrialPlantCriteria;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing IndustrialPlant.
 */
@RestController
@RequestMapping("/api")
public class IndustrialPlantResource {

    private final Logger log = LoggerFactory.getLogger(IndustrialPlantResource.class);

    private static final String ENTITY_NAME = "industrialPlant";

    private final IndustrialPlantService industrialPlantService;

    private final IndustrialPlantQueryService industrialPlantQueryService;

    public IndustrialPlantResource(IndustrialPlantService industrialPlantService, IndustrialPlantQueryService industrialPlantQueryService) {
        this.industrialPlantService = industrialPlantService;
        this.industrialPlantQueryService = industrialPlantQueryService;
    }

    /**
     * POST  /industrial-plants : Create a new industrialPlant.
     *
     * @param industrialPlant the industrialPlant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new industrialPlant, or with status 400 (Bad Request) if the industrialPlant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/industrial-plants")
    @Timed
    public ResponseEntity<IndustrialPlant> createIndustrialPlant(@RequestBody IndustrialPlant industrialPlant) throws URISyntaxException {
        log.debug("REST request to save IndustrialPlant : {}", industrialPlant);
        if (industrialPlant.getId() != null) {
            throw new BadRequestAlertException("A new industrialPlant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndustrialPlant result = industrialPlantService.save(industrialPlant);
        return ResponseEntity.created(new URI("/api/industrial-plants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /industrial-plants : Updates an existing industrialPlant.
     *
     * @param industrialPlant the industrialPlant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated industrialPlant,
     * or with status 400 (Bad Request) if the industrialPlant is not valid,
     * or with status 500 (Internal Server Error) if the industrialPlant couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/industrial-plants")
    @Timed
    public ResponseEntity<IndustrialPlant> updateIndustrialPlant(@RequestBody IndustrialPlant industrialPlant) throws URISyntaxException {
        log.debug("REST request to update IndustrialPlant : {}", industrialPlant);
        if (industrialPlant.getId() == null) {
            return createIndustrialPlant(industrialPlant);
        }
        IndustrialPlant result = industrialPlantService.save(industrialPlant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, industrialPlant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /industrial-plants : get all the industrialPlants.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of industrialPlants in body
     */
    @GetMapping("/industrial-plants")
    @Timed
    public ResponseEntity<List<IndustrialPlant>> getAllIndustrialPlants(IndustrialPlantCriteria criteria) {
        log.debug("REST request to get IndustrialPlants by criteria: {}", criteria);
        List<IndustrialPlant> entityList = industrialPlantQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /industrial-plants/:id : get the "id" industrialPlant.
     *
     * @param id the id of the industrialPlant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the industrialPlant, or with status 404 (Not Found)
     */
    @GetMapping("/industrial-plants/{id}")
    @Timed
    public ResponseEntity<IndustrialPlant> getIndustrialPlant(@PathVariable Long id) {
        log.debug("REST request to get IndustrialPlant : {}", id);
        IndustrialPlant industrialPlant = industrialPlantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(industrialPlant));
    }

    /**
     * DELETE  /industrial-plants/:id : delete the "id" industrialPlant.
     *
     * @param id the id of the industrialPlant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/industrial-plants/{id}")
    @Timed
    public ResponseEntity<Void> deleteIndustrialPlant(@PathVariable Long id) {
        log.debug("REST request to delete IndustrialPlant : {}", id);
        industrialPlantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
