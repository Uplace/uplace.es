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

import com.arnaugarcia.uplace.domain.Office;
import com.arnaugarcia.uplace.domain.*; // for static metamodels
import com.arnaugarcia.uplace.repository.OfficeRepository;
import com.arnaugarcia.uplace.service.dto.OfficeCriteria;

import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;

/**
 * Service for executing complex queries for Office entities in the database.
 * The main input is a {@link OfficeCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Office} or a {@link Page} of {@link Office} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OfficeQueryService extends QueryService<Office> {

    private final Logger log = LoggerFactory.getLogger(OfficeQueryService.class);


    private final OfficeRepository officeRepository;

    public OfficeQueryService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    /**
     * Return a {@link List} of {@link Office} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Office> findByCriteria(OfficeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Office> specification = createSpecification(criteria);
        return officeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Office} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Office> findByCriteria(OfficeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Office> specification = createSpecification(criteria);
        return officeRepository.findAll(specification, page);
    }

    /**
     * Function to convert OfficeCriteria to a {@link Specifications}
     */
    private Specifications<Office> createSpecification(OfficeCriteria criteria) {
        Specifications<Office> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Office_.id));
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
            if (criteria.getSurface() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSurface(), Property_.surface));
            }
            if (criteria.getBathrooms() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBathrooms(), Office_.bathrooms));
            }
            if (criteria.getFloors() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFloors(), Office_.floors));
            }
            if (criteria.getTerrace() != null) {
                specification = specification.and(buildSpecification(criteria.getTerrace(), Office_.terrace));
            }
            if (criteria.getOffice() != null) {
                specification = specification.and(buildSpecification(criteria.getOffice(), Office_.office));
            }
            if (criteria.getStorage() != null) {
                specification = specification.and(buildSpecification(criteria.getStorage(), Office_.storage));
            }
            if (criteria.getStorageSurface() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStorageSurface(), Office_.storageSurface));
            }
            if (criteria.getOfficesSurface() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOfficesSurface(), Office_.officesSurface));
            }
            if (criteria.getAc() != null) {
                specification = specification.and(buildSpecification(criteria.getAc(), Office_.ac));
            }
            if (criteria.getHeat() != null) {
                specification = specification.and(buildSpecification(criteria.getHeat(), Office_.heat));
            }
            if (criteria.getEnergyCertificate() != null) {
                specification = specification.and(buildSpecification(criteria.getEnergyCertificate(), Office_.energyCertificate));
            }
        }
        return specification;
    }

}
