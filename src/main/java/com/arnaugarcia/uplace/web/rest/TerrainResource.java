package com.arnaugarcia.uplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.uplace.domain.Terrain;

import com.arnaugarcia.uplace.repository.TerrainRepository;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.util.HeaderUtil;
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
 * REST controller for managing Terrain.
 */
@RestController
@RequestMapping("/api")
public class TerrainResource {

    private final Logger log = LoggerFactory.getLogger(TerrainResource.class);

    private static final String ENTITY_NAME = "terrain";

    private final TerrainRepository terrainRepository;

    public TerrainResource(TerrainRepository terrainRepository) {
        this.terrainRepository = terrainRepository;
    }

    /**
     * POST  /terrains : Create a new terrain.
     *
     * @param terrain the terrain to create
     * @return the ResponseEntity with status 201 (Created) and with body the new terrain, or with status 400 (Bad Request) if the terrain has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/terrains")
    @Timed
    public ResponseEntity<Terrain> createTerrain(@RequestBody Terrain terrain) throws URISyntaxException {
        log.debug("REST request to save Terrain : {}", terrain);
        if (terrain.getId() != null) {
            throw new BadRequestAlertException("A new terrain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Terrain result = terrainRepository.save(terrain);
        return ResponseEntity.created(new URI("/api/terrains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /terrains : Updates an existing terrain.
     *
     * @param terrain the terrain to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated terrain,
     * or with status 400 (Bad Request) if the terrain is not valid,
     * or with status 500 (Internal Server Error) if the terrain couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/terrains")
    @Timed
    public ResponseEntity<Terrain> updateTerrain(@RequestBody Terrain terrain) throws URISyntaxException {
        log.debug("REST request to update Terrain : {}", terrain);
        if (terrain.getId() == null) {
            return createTerrain(terrain);
        }
        Terrain result = terrainRepository.save(terrain);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, terrain.getId().toString()))
            .body(result);
    }

    /**
     * GET  /terrains : get all the terrains.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of terrains in body
     */
    @GetMapping("/terrains")
    @Timed
    public List<Terrain> getAllTerrains() {
        log.debug("REST request to get all Terrains");
        return terrainRepository.findAll();
        }

    /**
     * GET  /terrains/:id : get the "id" terrain.
     *
     * @param id the id of the terrain to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the terrain, or with status 404 (Not Found)
     */
    @GetMapping("/terrains/{id}")
    @Timed
    public ResponseEntity<Terrain> getTerrain(@PathVariable Long id) {
        log.debug("REST request to get Terrain : {}", id);
        Terrain terrain = terrainRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(terrain));
    }

    /**
     * DELETE  /terrains/:id : delete the "id" terrain.
     *
     * @param id the id of the terrain to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/terrains/{id}")
    @Timed
    public ResponseEntity<Void> deleteTerrain(@PathVariable Long id) {
        log.debug("REST request to delete Terrain : {}", id);
        terrainRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
