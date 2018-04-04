package com.arnaugarcia.uplace.service.queries;


import java.util.List;

import com.arnaugarcia.uplace.repository.PropertyRepository;
import io.github.jhipster.service.filter.StringFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.arnaugarcia.uplace.domain.Building;
import com.arnaugarcia.uplace.domain.*; // for static metamodels
import com.arnaugarcia.uplace.service.dto.BuildingCriteria;

/**
 * Service for executing complex queries for Building entities in the database.
 * The main input is a {@link BuildingCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Building} or a {@link Page} of {@link Building} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BuildingQueryService extends QueryService<Building> {

    private final Logger log = LoggerFactory.getLogger(BuildingQueryService.class);


    private final PropertyRepository<Building> buildingRepository;

    public BuildingQueryService(PropertyRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    /**
     * Return a {@link Page} of {@link Building} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Building> findByCriteria(BuildingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        criteria.setPropertyType(new StringFilter().setContains(Building.class.getSimpleName()));
        final Specifications<Building> specification = createSpecification(criteria);
        return buildingRepository.findAll(specification, page);
    }

    /**
     * Function to convert BuildingCriteria to a {@link Specifications}
     */
    private Specifications<Building> createSpecification(BuildingCriteria criteria) {
        Specifications<Building> specification = Specifications.where(null);
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
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Property_.description));
            }
            if (criteria.getTransaction() != null) {
                specification = specification.and(buildSpecification(criteria.getTransaction(), Property_.transaction));
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReference(), Property_.reference));
            }
            if (criteria.getPriceTransfer() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriceTransfer(), Property_.priceTransfer));
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
                specification = specification.and(buildSpecification(criteria.getType(), Building_.type));
            }
            if (criteria.getSolarSurface() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSolarSurface(), Building_.solarSurface));
            }
            if (criteria.getm2Edified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getm2Edified(), Building_.m2Edified));
            }
            if (criteria.getFloorsSR() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFloorsSR(), Building_.floorsSR));
            }
            if (criteria.getFloorsBR() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFloorsBR(), Building_.floorsBR));
            }
            if (criteria.getEnergyCertificate() != null) {
                specification = specification.and(buildSpecification(criteria.getEnergyCertificate(), Building_.energyCertificate));
            }
        }
        return specification;
    }

}
