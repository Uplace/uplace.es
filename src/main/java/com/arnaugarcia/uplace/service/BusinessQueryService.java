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

import com.arnaugarcia.uplace.domain.Business;
import com.arnaugarcia.uplace.domain.*; // for static metamodels
import com.arnaugarcia.uplace.repository.BusinessRepository;
import com.arnaugarcia.uplace.service.dto.BusinessCriteria;

import com.arnaugarcia.uplace.domain.enumeration.BusinessType;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;

/**
 * Service for executing complex queries for Business entities in the database.
 * The main input is a {@link BusinessCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Business} or a {@link Page} of {@link Business} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BusinessQueryService extends QueryService<Business> {

    private final Logger log = LoggerFactory.getLogger(BusinessQueryService.class);


    private final BusinessRepository businessRepository;

    public BusinessQueryService(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    /**
     * Return a {@link List} of {@link Business} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Business> findByCriteria(BusinessCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Business> specification = createSpecification(criteria);
        return businessRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Business} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Business> findByCriteria(BusinessCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Business> specification = createSpecification(criteria);
        return businessRepository.findAll(specification, page);
    }

    /**
     * Function to convert BusinessCriteria to a {@link Specifications}
     */
    private Specifications<Business> createSpecification(BusinessCriteria criteria) {
        Specifications<Business> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Business_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Business_.type));
            }
            if (criteria.getNumberBathrooms() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberBathrooms(), Business_.numberBathrooms));
            }
            if (criteria.getElevator() != null) {
                specification = specification.and(buildSpecification(criteria.getElevator(), Business_.elevator));
            }
            if (criteria.getAc() != null) {
                specification = specification.and(buildSpecification(criteria.getAc(), Business_.ac));
            }
            if (criteria.getHeat() != null) {
                specification = specification.and(buildSpecification(criteria.getHeat(), Business_.heat));
            }
            if (criteria.getSurfaceTerrace() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSurfaceTerrace(), Business_.surfaceTerrace));
            }
            if (criteria.getSurfaceGarden() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSurfaceGarden(), Business_.surfaceGarden));
            }
            if (criteria.getNumberOffice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOffice(), Business_.numberOffice));
            }
            if (criteria.getSurfaceSaloon() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSurfaceSaloon(), Business_.surfaceSaloon));
            }
            if (criteria.getHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeight(), Business_.height));
            }
            if (criteria.getPool() != null) {
                specification = specification.and(buildSpecification(criteria.getPool(), Business_.pool));
            }
        }
        return specification;
    }

}
