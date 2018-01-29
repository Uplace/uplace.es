package com.arnaugarcia.uplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.uplace.domain.Hotel;
import com.arnaugarcia.uplace.service.HotelService;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.util.HeaderUtil;
import com.arnaugarcia.uplace.service.dto.HotelCriteria;
import com.arnaugarcia.uplace.service.HotelQueryService;
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
 * REST controller for managing Hotel.
 */
@RestController
@RequestMapping("/api")
public class HotelResource {

    private final Logger log = LoggerFactory.getLogger(HotelResource.class);

    private static final String ENTITY_NAME = "hotel";

    private final HotelService hotelService;

    private final HotelQueryService hotelQueryService;

    public HotelResource(HotelService hotelService, HotelQueryService hotelQueryService) {
        this.hotelService = hotelService;
        this.hotelQueryService = hotelQueryService;
    }

    /**
     * POST  /hotels : Create a new hotel.
     *
     * @param hotel the hotel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hotel, or with status 400 (Bad Request) if the hotel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hotels")
    @Timed
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) throws URISyntaxException {
        log.debug("REST request to save Hotel : {}", hotel);
        if (hotel.getId() != null) {
            throw new BadRequestAlertException("A new hotel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hotel result = hotelService.save(hotel);
        return ResponseEntity.created(new URI("/api/hotels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hotels : Updates an existing hotel.
     *
     * @param hotel the hotel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hotel,
     * or with status 400 (Bad Request) if the hotel is not valid,
     * or with status 500 (Internal Server Error) if the hotel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hotels")
    @Timed
    public ResponseEntity<Hotel> updateHotel(@RequestBody Hotel hotel) throws URISyntaxException {
        log.debug("REST request to update Hotel : {}", hotel);
        if (hotel.getId() == null) {
            return createHotel(hotel);
        }
        Hotel result = hotelService.save(hotel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hotel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hotels : get all the hotels.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of hotels in body
     */
    @GetMapping("/hotels")
    @Timed
    public ResponseEntity<List<Hotel>> getAllHotels(HotelCriteria criteria) {
        log.debug("REST request to get Hotels by criteria: {}", criteria);
        List<Hotel> entityList = hotelQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /hotels/:id : get the "id" hotel.
     *
     * @param id the id of the hotel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hotel, or with status 404 (Not Found)
     */
    @GetMapping("/hotels/{id}")
    @Timed
    public ResponseEntity<Hotel> getHotel(@PathVariable Long id) {
        log.debug("REST request to get Hotel : {}", id);
        Hotel hotel = hotelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hotel));
    }

    /**
     * DELETE  /hotels/:id : delete the "id" hotel.
     *
     * @param id the id of the hotel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hotels/{id}")
    @Timed
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        log.debug("REST request to delete Hotel : {}", id);
        hotelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
