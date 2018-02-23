package com.arnaugarcia.uplace.service;


import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.domain.*; // for static metamodels
import com.arnaugarcia.uplace.repository.PropertyRepository;
import com.arnaugarcia.uplace.service.dto.PropertyCriteria;

import com.arnaugarcia.uplace.service.dto.PropertyDTO;
import com.arnaugarcia.uplace.service.mapper.PropertyMapper;
import com.arnaugarcia.uplace.domain.enumeration.TransactionType;

/**
 * Service for executing complex queries for Property entities in the database.
 * The main input is a {@link PropertyCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PropertyDTO} or a {@link Page} of {@link PropertyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PropertyQueryService extends QueryService<Property> {

    private final Logger log = LoggerFactory.getLogger(PropertyQueryService.class);


    private final PropertyRepository propertyRepository;

    private final PropertyMapper propertyMapper;

    public PropertyQueryService(PropertyRepository propertyRepository, PropertyMapper propertyMapper) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
    }

    /**
     * Return a {@link List} of {@link PropertyDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PropertyDTO> findByCriteria(PropertyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Property> specification = createSpecification(criteria);
        return propertyMapper.toDto(propertyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PropertyDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PropertyDTO> findByCriteria(PropertyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Property> specification = createSpecification(criteria);
        final Page<Property> result = propertyRepository.findAll(specification, page);
        return result.map(propertyMapper::toDto);
    }

    /**
     * Function to convert PropertyCriteria to a {@link Specifications}
     */
    private Specifications<Property> createSpecification(PropertyCriteria criteria) {
        Specifications<Property> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Property_.id));
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
            if (criteria.getTransaction() != null) {
                specification = specification.and(buildSpecification(criteria.getTransaction(), Property_.transaction));
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
            if (criteria.getLocationId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getLocationId(), Property_.location, Location_.id));
            }
            if (criteria.getPhotoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getPhotoId(), Property_.photos, Photo_.id));
            }
            if (criteria.getManagerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getManagerId(), Property_.managers, Agent_.id));
            }
        }
        return specification;
    }

}
