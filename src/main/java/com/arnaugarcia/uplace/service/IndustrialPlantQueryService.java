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

import com.arnaugarcia.uplace.domain.IndustrialPlant;
import com.arnaugarcia.uplace.domain.*; // for static metamodels
import com.arnaugarcia.uplace.repository.IndustrialPlantRepository;
import com.arnaugarcia.uplace.service.dto.IndustrialPlantCriteria;

import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;

/**
 * Service for executing complex queries for IndustrialPlant entities in the database.
 * The main input is a {@link IndustrialPlantCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IndustrialPlant} or a {@link Page} of {@link IndustrialPlant} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IndustrialPlantQueryService extends QueryService<IndustrialPlant> {

    private final Logger log = LoggerFactory.getLogger(IndustrialPlantQueryService.class);


    private final IndustrialPlantRepository industrialPlantRepository;

    public IndustrialPlantQueryService(IndustrialPlantRepository industrialPlantRepository) {
        this.industrialPlantRepository = industrialPlantRepository;
    }

    /**
     * Return a {@link List} of {@link IndustrialPlant} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IndustrialPlant> findByCriteria(IndustrialPlantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<IndustrialPlant> specification = createSpecification(criteria);
        return industrialPlantRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link IndustrialPlant} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IndustrialPlant> findByCriteria(IndustrialPlantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<IndustrialPlant> specification = createSpecification(criteria);
        return industrialPlantRepository.findAll(specification, page);
    }

    /**
     * Function to convert IndustrialPlantCriteria to a {@link Specifications}
     */
    private Specifications<IndustrialPlant> createSpecification(IndustrialPlantCriteria criteria) {
        Specifications<IndustrialPlant> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), IndustrialPlant_.id));
            }
            if (criteria.getSolarSurface() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSolarSurface(), IndustrialPlant_.solarSurface));
            }
            if (criteria.getNumberRooms() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberRooms(), IndustrialPlant_.numberRooms));
            }
            if (criteria.getm2Offices() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getm2Offices(), IndustrialPlant_.m2Offices));
            }
            if (criteria.getm2Storage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getm2Storage(), IndustrialPlant_.m2Storage));
            }
            if (criteria.getHeightFree() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeightFree(), IndustrialPlant_.heightFree));
            }
            if (criteria.getNumberDocks() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberDocks(), IndustrialPlant_.numberDocks));
            }
            if (criteria.getEnergyCertificate() != null) {
                specification = specification.and(buildSpecification(criteria.getEnergyCertificate(), IndustrialPlant_.energyCertificate));
            }
        }
        return specification;
    }

}