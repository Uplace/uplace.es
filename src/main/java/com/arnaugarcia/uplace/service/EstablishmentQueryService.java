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

import com.arnaugarcia.uplace.domain.Establishment;
import com.arnaugarcia.uplace.domain.*; // for static metamodels
import com.arnaugarcia.uplace.repository.EstablishmentRepository;
import com.arnaugarcia.uplace.service.dto.EstablishmentCriteria;

import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.UseEstablishment;
import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;

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


    private final EstablishmentRepository establishmentRepository;

    public EstablishmentQueryService(EstablishmentRepository establishmentRepository) {
        this.establishmentRepository = establishmentRepository;
    }

    /**
     * Return a {@link List} of {@link Establishment} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Establishment> findByCriteria(EstablishmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Establishment> specification = createSpecification(criteria);
        return establishmentRepository.findAll(specification);
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
        final Specifications<Establishment> specification = createSpecification(criteria);
        return establishmentRepository.findAll(specification, page);
    }

    /**
     * Function to convert EstablishmentCriteria to a {@link Specifications}
     */
    private Specifications<Establishment> createSpecification(EstablishmentCriteria criteria) {
        Specifications<Establishment> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Establishment_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Property_.title));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Property_.price));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Property_.created));
            }
            if (criteria.getUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdated(), Property_.updated));
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
