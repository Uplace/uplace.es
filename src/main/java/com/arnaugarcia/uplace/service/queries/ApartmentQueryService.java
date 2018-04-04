package com.arnaugarcia.uplace.service.queries;


import java.util.List;

import com.arnaugarcia.uplace.repository.PropertyRepository;
import com.arnaugarcia.uplace.service.dto.ApartmentCriteria;
import io.github.jhipster.service.filter.StringFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.arnaugarcia.uplace.domain.Apartment;
import com.arnaugarcia.uplace.domain.*; // for static metamodels

/**
 * Service for executing complex queries for Apartment entities in the database.
 * The main input is a {@link ApartmentCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Apartment} or a {@link Page} of {@link Apartment} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApartmentQueryService extends QueryService<Apartment> {

    private final Logger log = LoggerFactory.getLogger(ApartmentQueryService.class);


    private final PropertyRepository<Apartment> propertyRepository;

    public ApartmentQueryService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /**
     * Return a {@link List} of {@link Apartment} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Apartment> findByCriteria(ApartmentCriteria criteria, Pageable pageable) {
        log.debug("find by criteria : {}", criteria);
        criteria.setPropertyType(new StringFilter().setContains(Apartment.class.getSimpleName()));
        final Specifications<Apartment> specification = createSpecification(criteria);
        return propertyRepository.findAll(specification, pageable);
    }


    /**
     * Function to convert ApartmentCriteria to a {@link Specifications}
     */
    private Specifications<Apartment> createSpecification(ApartmentCriteria criteria) {
        Specifications<Apartment> specification = Specifications.where(null);
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
            if (criteria.getNumberBedrooms() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberBedrooms(), Apartment_.numberBedrooms));
            }
            if (criteria.getNumberBathrooms() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberBathrooms(), Apartment_.numberBathrooms));
            }
            if (criteria.getm2Edified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getm2Edified(), Apartment_.m2Edified));
            }
            if (criteria.getm2Usable() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getm2Usable(), Apartment_.m2Usable));
            }
            if (criteria.getHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeight(), Apartment_.height));
            }
            if (criteria.getElevator() != null) {
                specification = specification.and(buildSpecification(criteria.getElevator(), Apartment_.elevator));
            }
            if (criteria.getAc() != null) {
                specification = specification.and(buildSpecification(criteria.getAc(), Apartment_.ac));
            }
            if (criteria.getHeat() != null) {
                specification = specification.and(buildSpecification(criteria.getHeat(), Apartment_.heat));
            }
            if (criteria.getParking() != null) {
                specification = specification.and(buildSpecification(criteria.getParking(), Apartment_.parking));
            }
            if (criteria.getTerrace() != null) {
                specification = specification.and(buildSpecification(criteria.getTerrace(), Apartment_.terrace));
            }
            if (criteria.getBalcony() != null) {
                specification = specification.and(buildSpecification(criteria.getBalcony(), Apartment_.balcony));
            }
            if (criteria.getSurfaceTerrace() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSurfaceTerrace(), Apartment_.surfaceTerrace));
            }
            if (criteria.getSurfaceSaloon() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSurfaceSaloon(), Apartment_.surfaceSaloon));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Apartment_.type));
            }
            if (criteria.getOffice() != null) {
                specification = specification.and(buildSpecification(criteria.getOffice(), Apartment_.office));
            }
            if (criteria.getKitchenOffice() != null) {
                specification = specification.and(buildSpecification(criteria.getKitchenOffice(), Apartment_.kitchenOffice));
            }
            if (criteria.getStorage() != null) {
                specification = specification.and(buildSpecification(criteria.getStorage(), Apartment_.storage));
            }
            if (criteria.getSharedPool() != null) {
                specification = specification.and(buildSpecification(criteria.getSharedPool(), Apartment_.sharedPool));
            }
            if (criteria.getNearTransport() != null) {
                specification = specification.and(buildSpecification(criteria.getNearTransport(), Apartment_.nearTransport));
            }
            if (criteria.getReformed() != null) {
                specification = specification.and(buildSpecification(criteria.getReformed(), Apartment_.reformed));
            }
            if (criteria.getEnergyCertificate() != null) {
                specification = specification.and(buildSpecification(criteria.getEnergyCertificate(), Apartment_.energyCertificate));
            }
            if (criteria.getCertificateHabitability() != null) {
                specification = specification.and(buildSpecification(criteria.getCertificateHabitability(), Apartment_.certificateHabitability));
            }
        }
        return specification;
    }

}
