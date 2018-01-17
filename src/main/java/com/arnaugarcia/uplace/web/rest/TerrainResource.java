package com.arnaugarcia.uplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.uplace.domain.Terrain;

import com.arnaugarcia.uplace.repository.TerrainRepository;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.util.HeaderUtil;
import com.arnaugarcia.uplace.service.dto.TerrainDTO;
import com.arnaugarcia.uplace.service.mapper.TerrainMapper;
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

    private final TerrainMapper terrainMapper;

    public TerrainResource(TerrainRepository terrainRepository, TerrainMapper terrainMapper) {
        this.terrainRepository = terrainRepository;
        this.terrainMapper = terrainMapper;
    }

    /**
     * POST  /terrains : Create a new terrain.
     *
     * @param terrainDTO the terrainDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new terrainDTO, or with status 400 (Bad Request) if the terrain has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/terrains")
    @Timed
    public ResponseEntity<TerrainDTO> createTerrain(@RequestBody TerrainDTO terrainDTO) throws URISyntaxException {
        log.debug("REST request to save Terrain : {}", terrainDTO);
        if (terrainDTO.getId() != null) {
            throw new BadRequestAlertException("A new terrain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Terrain terrain = terrainMapper.toEntity(terrainDTO);
        terrain = terrainRepository.save(terrain);
        TerrainDTO result = terrainMapper.toDto(terrain);
        return ResponseEntity.created(new URI("/api/terrains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /terrains : Updates an existing terrain.
     *
     * @param terrainDTO the terrainDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated terrainDTO,
     * or with status 400 (Bad Request) if the terrainDTO is not valid,
     * or with status 500 (Internal Server Error) if the terrainDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/terrains")
    @Timed
    public ResponseEntity<TerrainDTO> updateTerrain(@RequestBody TerrainDTO terrainDTO) throws URISyntaxException {
        log.debug("REST request to update Terrain : {}", terrainDTO);
        if (terrainDTO.getId() == null) {
            return createTerrain(terrainDTO);
        }
        Terrain terrain = terrainMapper.toEntity(terrainDTO);
        terrain = terrainRepository.save(terrain);
        TerrainDTO result = terrainMapper.toDto(terrain);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, terrainDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /terrains : get all the terrains.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of terrains in body
     */
    @GetMapping("/terrains")
    @Timed
    public List<TerrainDTO> getAllTerrains() {
        log.debug("REST request to get all Terrains");
        List<Terrain> terrains = terrainRepository.findAll();
        return terrainMapper.toDto(terrains);
        }

    /**
     * GET  /terrains/:id : get the "id" terrain.
     *
     * @param id the id of the terrainDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the terrainDTO, or with status 404 (Not Found)
     */
    @GetMapping("/terrains/{id}")
    @Timed
    public ResponseEntity<TerrainDTO> getTerrain(@PathVariable Long id) {
        log.debug("REST request to get Terrain : {}", id);
        Terrain terrain = terrainRepository.findOne(id);
        TerrainDTO terrainDTO = terrainMapper.toDto(terrain);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(terrainDTO));
    }

    /**
     * DELETE  /terrains/:id : delete the "id" terrain.
     *
     * @param id the id of the terrainDTO to delete
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
