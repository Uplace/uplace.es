package com.arnaugarcia.uplace.service.queries;


import java.util.List;

import com.arnaugarcia.uplace.repository.PropertyRepository;
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
import com.arnaugarcia.uplace.service.dto.BusinessCriteria;

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


    private final PropertyRepository<Business> propertyRepository;

    public BusinessQueryService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
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
        return propertyRepository.findAll(specification, page);
    }

    /**
     * Function to convert BusinessCriteria to a {@link Specifications}
     */
    private Specifications<Business> createSpecification(BusinessCriteria criteria) {
        Specifications<Business> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Property_.title));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Property_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Property_.updated));
            }
            if (criteria.getPropertyType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPropertyType(), Property_.propertyType));
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReference(), Property_.reference));
            }
            if (criteria.getPriceSell() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriceSell(), Property_.priceSell));
            }
            if (criteria.getPriceRent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriceRent(), Property_.priceRent));
            }
            if (criteria.getYearConstruction() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYearConstruction(), Property_.yearConstruction));
            }
            if (criteria.getNewCreation() != null) {
                specification = specification.and(buildSpecification(criteria.getNewCreation(), Property_.newCreation));
            }
            if (criteria.getVisible() != null) {
                specification = specification.and(buildSpecification(criteria.getVisible(), Property_.visible));
            }
            if (criteria.getSurface() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSurface(), Property_.surface));
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
