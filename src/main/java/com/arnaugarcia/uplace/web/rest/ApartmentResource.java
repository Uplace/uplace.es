package com.arnaugarcia.uplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.uplace.domain.Apartment;

import com.arnaugarcia.uplace.repository.ApartmentRepository;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.util.HeaderUtil;
import com.arnaugarcia.uplace.service.dto.ApartmentDTO;
import com.arnaugarcia.uplace.service.mapper.ApartmentMapper;
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
 * REST controller for managing Apartment.
 */
@RestController
@RequestMapping("/api")
public class ApartmentResource {

    private final Logger log = LoggerFactory.getLogger(ApartmentResource.class);

    private static final String ENTITY_NAME = "apartment";

    private final ApartmentRepository apartmentRepository;

    private final ApartmentMapper apartmentMapper;

    public ApartmentResource(ApartmentRepository apartmentRepository, ApartmentMapper apartmentMapper) {
        this.apartmentRepository = apartmentRepository;
        this.apartmentMapper = apartmentMapper;
    }

    /**
     * POST  /apartments : Create a new apartment.
     *
     * @param apartmentDTO the apartmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new apartmentDTO, or with status 400 (Bad Request) if the apartment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/apartments")
    @Timed
    public ResponseEntity<ApartmentDTO> createApartment(@RequestBody ApartmentDTO apartmentDTO) throws URISyntaxException {
        log.debug("REST request to save Apartment : {}", apartmentDTO);
        if (apartmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new apartment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Apartment apartment = apartmentMapper.toEntity(apartmentDTO);
        apartment = apartmentRepository.save(apartment);
        ApartmentDTO result = apartmentMapper.toDto(apartment);
        return ResponseEntity.created(new URI("/api/apartments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /apartments : Updates an existing apartment.
     *
     * @param apartmentDTO the apartmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apartmentDTO,
     * or with status 400 (Bad Request) if the apartmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the apartmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/apartments")
    @Timed
    public ResponseEntity<ApartmentDTO> updateApartment(@RequestBody ApartmentDTO apartmentDTO) throws URISyntaxException {
        log.debug("REST request to update Apartment : {}", apartmentDTO);
        if (apartmentDTO.getId() == null) {
            return createApartment(apartmentDTO);
        }
        Apartment apartment = apartmentMapper.toEntity(apartmentDTO);
        apartment = apartmentRepository.save(apartment);
        ApartmentDTO result = apartmentMapper.toDto(apartment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, apartmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /apartments : get all the apartments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of apartments in body
     */
    @GetMapping("/apartments")
    @Timed
    public List<ApartmentDTO> getAllApartments() {
        log.debug("REST request to get all Apartments");
        List<Apartment> apartments = apartmentRepository.findAll();
        return apartmentMapper.toDto(apartments);
        }

    /**
     * GET  /apartments/:id : get the "id" apartment.
     *
     * @param id the id of the apartmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the apartmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/apartments/{id}")
    @Timed
    public ResponseEntity<ApartmentDTO> getApartment(@PathVariable Long id) {
        log.debug("REST request to get Apartment : {}", id);
        Apartment apartment = apartmentRepository.findOne(id);
        ApartmentDTO apartmentDTO = apartmentMapper.toDto(apartment);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(apartmentDTO));
    }

    /**
     * DELETE  /apartments/:id : delete the "id" apartment.
     *
     * @param id the id of the apartmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/apartments/{id}")
    @Timed
    public ResponseEntity<Void> deleteApartment(@PathVariable Long id) {
        log.debug("REST request to delete Apartment : {}", id);
        apartmentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
