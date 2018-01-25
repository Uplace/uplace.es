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

import com.arnaugarcia.uplace.domain.Parking;
import com.arnaugarcia.uplace.domain.*; // for static metamodels
import com.arnaugarcia.uplace.repository.ParkingRepository;
import com.arnaugarcia.uplace.service.dto.ParkingCriteria;

import com.arnaugarcia.uplace.domain.enumeration.ParkingType;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;

/**
 * Service for executing complex queries for Parking entities in the database.
 * The main input is a {@link ParkingCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Parking} or a {@link Page} of {@link Parking} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParkingQueryService extends QueryService<Parking> {

    private final Logger log = LoggerFactory.getLogger(ParkingQueryService.class);


    private final ParkingRepository parkingRepository;

    public ParkingQueryService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    /**
     * Return a {@link List} of {@link Parking} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Parking> findByCriteria(ParkingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Parking> specification = createSpecification(criteria);
        return parkingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Parking} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Parking> findByCriteria(ParkingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Parking> specification = createSpecification(criteria);
        return parkingRepository.findAll(specification, page);
    }

    /**
     * Function to convert ParkingCriteria to a {@link Specifications}
     */
    private Specifications<Parking> createSpecification(ParkingCriteria criteria) {
        Specifications<Parking> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Parking_.id));
            }
            if (criteria.getParkingType() != null) {
                specification = specification.and(buildSpecification(criteria.getParkingType(), Parking_.parkingType));
            }
            if (criteria.getNearTransport() != null) {
                specification = specification.and(buildSpecification(criteria.getNearTransport(), Parking_.nearTransport));
            }
            if (criteria.getSurveillance() != null) {
                specification = specification.and(buildSpecification(criteria.getSurveillance(), Parking_.surveillance));
            }
        }
        return specification;
    }

}
