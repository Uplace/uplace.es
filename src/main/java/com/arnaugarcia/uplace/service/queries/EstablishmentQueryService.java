package com.arnaugarcia.uplace.service.queries;

import java.util.List;

import com.arnaugarcia.uplace.repository.PropertyRepository;
import com.arnaugarcia.uplace.service.dto.EstablishmentCriteria;
import io.github.jhipster.service.filter.StringFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.arnaugarcia.uplace.domain.Establishment;
import com.arnaugarcia.uplace.domain.*; // for static metamodels

/**
 * Service for executing complex queries for Establishment entities in the database.
 * The main input is a {@link EstablishmentCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Establishment} or a {@link Page} of {@link Establishment} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EstablishmentQueryService extends QueryService<Establishment> {

    private final Logger log = LoggerFactory.getLogger(EstablishmentQueryService.class);


    private final PropertyRepository<Establishment> propertyRepository;

    public EstablishmentQueryService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /**
     * Return a {@link Page} of {@link Establishment} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Establishment> findByCriteria(EstablishmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        criteria.setPropertyType(new StringFilter().setContains(Establishment.class.getSimpleName()));
        final Specifications<Establishment> specification = createSpecification(criteria);
        return propertyRepository.findAll(specification, page);
    }

    /**
     * Function to convert EstablishmentCriteria to a {@link Specifications}
     */
    private Specifications<Establishment> createSpecification(EstablishmentCriteria criteria) {
        Specifications<Establishment> specification = Specifications.where(null);
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
            if (criteria.getm2Facade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getm2Facade(), Establishment_.m2Facade));
            }
            if (criteria.getBathroom() != null) {
                specification = specification.and(buildSpecification(criteria.getBathroom(), Establishment_.bathroom));
            }
            if (criteria.getUse() != null) {
                specification = specification.and(buildSpecification(criteria.getUse(), Establishment_.use));
            }
            if (criteria.getEnergyCertificate() != null) {
                specification = specification.and(buildSpecification(criteria.getEnergyCertificate(), Establishment_.energyCertificate));
            }
        }
        return specification;
    }

}
