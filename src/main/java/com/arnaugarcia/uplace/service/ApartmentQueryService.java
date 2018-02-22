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

import com.arnaugarcia.uplace.domain.Apartment;
import com.arnaugarcia.uplace.domain.*; // for static metamodels
import com.arnaugarcia.uplace.repository.ApartmentRepository;
import com.arnaugarcia.uplace.service.dto.ApartmentCriteria;

import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.ApartmentType;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;
import com.arnaugarcia.uplace.domain.enumeration.Select;

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


    private final ApartmentRepository apartmentRepository;

    public ApartmentQueryService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    /**
     * Return a {@link List} of {@link Apartment} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Apartment> findByCriteria(ApartmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Apartment> specification = createSpecification(criteria);
        return apartmentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Apartment} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Apartment> findByCriteria(ApartmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Apartment> specification = createSpecification(criteria);
        return apartmentRepository.findAll(specification, page);
    }

    /**
     * Function to convert ApartmentCriteria to a {@link Specifications}
     */
    private Specifications<Apartment> createSpecification(ApartmentCriteria criteria) {
        Specifications<Apartment> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Apartment_.id));
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
