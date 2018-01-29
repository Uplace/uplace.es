package com.arnaugarcia.uplace.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.arnaugarcia.uplace.domain.Hotel;
import com.arnaugarcia.uplace.domain.*; // for static metamodels
import com.arnaugarcia.uplace.repository.HotelRepository;
import com.arnaugarcia.uplace.service.dto.HotelCriteria;

import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;

/**
 * Service for executing complex queries for Hotel entities in the database.
 * The main input is a {@link HotelCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Hotel} or a {@link Page} of {@link Hotel} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HotelQueryService extends QueryService<Hotel> {

    private final Logger log = LoggerFactory.getLogger(HotelQueryService.class);


    private final HotelRepository hotelRepository;

    public HotelQueryService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    /**
     * Return a {@link List} of {@link Hotel} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Hotel> findByCriteria(HotelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Hotel> specification = createSpecification(criteria);
        return hotelRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Hotel} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Hotel> findByCriteria(HotelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Hotel> specification = createSpecification(criteria);
        return hotelRepository.findAll(specification, page);
    }

    /**
     * Function to convert HotelCriteria to a {@link Specifications}
     */
    private Specifications<Hotel> createSpecification(HotelCriteria criteria) {
        Specifications<Hotel> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Hotel_.id));
            }
            if (criteria.getSolarSurface() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSolarSurface(), Hotel_.solarSurface));
            }
            if (criteria.getm2Edified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getm2Edified(), Hotel_.m2Edified));
            }
            if (criteria.getNumberRooms() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberRooms(), Hotel_.numberRooms));
            }
            if (criteria.getOperator() != null) {
                specification = specification.and(buildSpecification(criteria.getOperator(), Hotel_.operator));
            }
            if (criteria.getPool() != null) {
                specification = specification.and(buildSpecification(criteria.getPool(), Hotel_.pool));
            }
            if (criteria.getSpa() != null) {
                specification = specification.and(buildSpecification(criteria.getSpa(), Hotel_.spa));
            }
            if (criteria.getConferenceRoom() != null) {
                specification = specification.and(buildSpecification(criteria.getConferenceRoom(), Hotel_.conferenceRoom));
            }
            if (criteria.getFloorsSR() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFloorsSR(), Hotel_.floorsSR));
            }
            if (criteria.getFloorsBR() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFloorsBR(), Hotel_.floorsBR));
            }
            if (criteria.getEnergyCertificate() != null) {
                specification = specification.and(buildSpecification(criteria.getEnergyCertificate(), Hotel_.energyCertificate));
            }
        }
        return specification;
    }

}
