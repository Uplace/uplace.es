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

import com.arnaugarcia.uplace.domain.Terrain;
import com.arnaugarcia.uplace.domain.*; // for static metamodels
import com.arnaugarcia.uplace.repository.TerrainRepository;
import com.arnaugarcia.uplace.service.dto.TerrainCriteria;

import com.arnaugarcia.uplace.domain.enumeration.TerrainType;

/**
 * Service for executing complex queries for Terrain entities in the database.
 * The main input is a {@link TerrainCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Terrain} or a {@link Page} of {@link Terrain} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TerrainQueryService extends QueryService<Terrain> {

    private final Logger log = LoggerFactory.getLogger(TerrainQueryService.class);


    private final TerrainRepository terrainRepository;

    public TerrainQueryService(TerrainRepository terrainRepository) {
        this.terrainRepository = terrainRepository;
    }

    /**
     * Return a {@link List} of {@link Terrain} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Terrain> findByCriteria(TerrainCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Terrain> specification = createSpecification(criteria);
        return terrainRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Terrain} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Terrain> findByCriteria(TerrainCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Terrain> specification = createSpecification(criteria);
        return terrainRepository.findAll(specification, page);
    }

    /**
     * Function to convert TerrainCriteria to a {@link Specifications}
     */
    private Specifications<Terrain> createSpecification(TerrainCriteria criteria) {
        Specifications<Terrain> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Terrain_.id));
            }
            if (criteria.getm2Buildable() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getm2Buildable(), Terrain_.m2Buildable));
            }
            if (criteria.getBuildable() != null) {
                specification = specification.and(buildSpecification(criteria.getBuildable(), Terrain_.buildable));
            }
            if (criteria.getBuildableDepth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBuildableDepth(), Terrain_.buildableDepth));
            }
            if (criteria.getFloorsSR() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFloorsSR(), Terrain_.floorsSR));
            }
            if (criteria.getFloorsBR() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFloorsBR(), Terrain_.floorsBR));
            }
            if (criteria.getConstructionCoefficient() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConstructionCoefficient(), Terrain_.constructionCoefficient));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Terrain_.type));
            }
        }
        return specification;
    }

}
