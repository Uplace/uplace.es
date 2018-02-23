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

import com.arnaugarcia.uplace.domain.Building;
import com.arnaugarcia.uplace.domain.*; // for static metamodels
import com.arnaugarcia.uplace.repository.BuildingRepository;
import com.arnaugarcia.uplace.service.dto.BuildingCriteria;

import com.arnaugarcia.uplace.service.dto.BuildingDTO;
import com.arnaugarcia.uplace.service.mapper.BuildingMapper;
import com.arnaugarcia.uplace.domain.enumeration.BuildingType;
import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;

/**
 * Service for executing complex queries for Building entities in the database.
 * The main input is a {@link BuildingCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BuildingDTO} or a {@link Page} of {@link BuildingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BuildingQueryService extends QueryService<Building> {

    private final Logger log = LoggerFactory.getLogger(BuildingQueryService.class);


    private final BuildingRepository buildingRepository;

    private final BuildingMapper buildingMapper;

    public BuildingQueryService(BuildingRepository buildingRepository, BuildingMapper buildingMapper) {
        this.buildingRepository = buildingRepository;
        this.buildingMapper = buildingMapper;
    }

    /**
     * Return a {@link List} of {@link BuildingDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BuildingDTO> findByCriteria(BuildingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Building> specification = createSpecification(criteria);
        return buildingMapper.toDto(buildingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BuildingDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BuildingDTO> findByCriteria(BuildingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Building> specification = createSpecification(criteria);
        final Page<Building> result = buildingRepository.findAll(specification, page);
        return result.map(buildingMapper::toDto);
    }

    /**
     * Function to convert BuildingCriteria to a {@link Specifications}
     */
    private Specifications<Building> createSpecification(BuildingCriteria criteria) {
        Specifications<Building> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Building_.id));
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
