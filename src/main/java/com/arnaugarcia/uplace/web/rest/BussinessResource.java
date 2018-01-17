package com.arnaugarcia.uplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.uplace.domain.Bussiness;

import com.arnaugarcia.uplace.repository.BussinessRepository;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.util.HeaderUtil;
import com.arnaugarcia.uplace.service.dto.BussinessDTO;
import com.arnaugarcia.uplace.service.mapper.BussinessMapper;
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
 * REST controller for managing Bussiness.
 */
@RestController
@RequestMapping("/api")
public class BussinessResource {

    private final Logger log = LoggerFactory.getLogger(BussinessResource.class);

    private static final String ENTITY_NAME = "bussiness";

    private final BussinessRepository bussinessRepository;

    private final BussinessMapper bussinessMapper;

    public BussinessResource(BussinessRepository bussinessRepository, BussinessMapper bussinessMapper) {
        this.bussinessRepository = bussinessRepository;
        this.bussinessMapper = bussinessMapper;
    }

    /**
     * POST  /bussinesses : Create a new bussiness.
     *
     * @param bussinessDTO the bussinessDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bussinessDTO, or with status 400 (Bad Request) if the bussiness has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bussinesses")
    @Timed
    public ResponseEntity<BussinessDTO> createBussiness(@RequestBody BussinessDTO bussinessDTO) throws URISyntaxException {
        log.debug("REST request to save Bussiness : {}", bussinessDTO);
        if (bussinessDTO.getId() != null) {
            throw new BadRequestAlertException("A new bussiness cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bussiness bussiness = bussinessMapper.toEntity(bussinessDTO);
        bussiness = bussinessRepository.save(bussiness);
        BussinessDTO result = bussinessMapper.toDto(bussiness);
        return ResponseEntity.created(new URI("/api/bussinesses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bussinesses : Updates an existing bussiness.
     *
     * @param bussinessDTO the bussinessDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bussinessDTO,
     * or with status 400 (Bad Request) if the bussinessDTO is not valid,
     * or with status 500 (Internal Server Error) if the bussinessDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bussinesses")
    @Timed
    public ResponseEntity<BussinessDTO> updateBussiness(@RequestBody BussinessDTO bussinessDTO) throws URISyntaxException {
        log.debug("REST request to update Bussiness : {}", bussinessDTO);
        if (bussinessDTO.getId() == null) {
            return createBussiness(bussinessDTO);
        }
        Bussiness bussiness = bussinessMapper.toEntity(bussinessDTO);
        bussiness = bussinessRepository.save(bussiness);
        BussinessDTO result = bussinessMapper.toDto(bussiness);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bussinessDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bussinesses : get all the bussinesses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bussinesses in body
     */
    @GetMapping("/bussinesses")
    @Timed
    public List<BussinessDTO> getAllBussinesses() {
        log.debug("REST request to get all Bussinesses");
        List<Bussiness> bussinesses = bussinessRepository.findAll();
        return bussinessMapper.toDto(bussinesses);
        }

    /**
     * GET  /bussinesses/:id : get the "id" bussiness.
     *
     * @param id the id of the bussinessDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bussinessDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bussinesses/{id}")
    @Timed
    public ResponseEntity<BussinessDTO> getBussiness(@PathVariable Long id) {
        log.debug("REST request to get Bussiness : {}", id);
        Bussiness bussiness = bussinessRepository.findOne(id);
        BussinessDTO bussinessDTO = bussinessMapper.toDto(bussiness);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bussinessDTO));
    }

    /**
     * DELETE  /bussinesses/:id : delete the "id" bussiness.
     *
     * @param id the id of the bussinessDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bussinesses/{id}")
    @Timed
    public ResponseEntity<Void> deleteBussiness(@PathVariable Long id) {
        log.debug("REST request to delete Bussiness : {}", id);
        bussinessRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
