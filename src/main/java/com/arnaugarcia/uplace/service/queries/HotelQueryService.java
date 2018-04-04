package com.arnaugarcia.uplace.service.queries;

import java.util.List;

import com.arnaugarcia.uplace.repository.PropertyRepository;
import com.arnaugarcia.uplace.service.dto.HotelCriteria;
import io.github.jhipster.service.filter.StringFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.arnaugarcia.uplace.domain.Hotel;
import com.arnaugarcia.uplace.domain.*; // for static metamodels

/**
 * Service for executing complex queries for Hotel entities in the database.
 * The main input is a {@link HotelCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Hotel} or a {@link Page} of {@link Hotel} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HotelQueryService extends QueryService<Hotel> {

    private final Logger log = LoggerFactory.getLogger(HotelQueryService.class);


    private final PropertyRepository<Hotel> propertyRepository;

    public HotelQueryService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /**
     * Return a {@link Page} of {@link Hotel} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Hotel> findByCriteria(HotelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        criteria.setPropertyType(new StringFilter().setContains(Hotel.class.getSimpleName()));
        final Specifications<Hotel> specification = createSpecification(criteria);
        return propertyRepository.findAll(specification, page);
    }

    /**
     * Function to convert HotelCriteria to a {@link Specifications}
     */
    private Specifications<Hotel> createSpecification(HotelCriteria criteria) {
        Specifications<Hotel> specification = Specifications.where(null);
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
            if (criteria.getSolarSurface() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSolarSurface(), Hotel_.solarSurface));
            }
            if (criteria.getm2Edified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getm2Edified(), Hotel_.m2Edified));
            }
            if (criteria.getNumberRooms() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberRooms(), Hotel_.numberRooms));
            }
            if (criteria.getOperator() != null) {
                specification = specification.and(buildSpecification(criteria.getOperator(), Hotel_.operator));
            }
            if (criteria.getPool() != null) {
                specification = specification.and(buildSpecification(criteria.getPool(), Hotel_.pool));
            }
            if (criteria.getSpa() != null) {
                specification = specification.and(buildSpecification(criteria.getSpa(), Hotel_.spa));
            }
            if (criteria.getConferenceRoom() != null) {
                specification = specification.and(buildSpecification(criteria.getConferenceRoom(), Hotel_.conferenceRoom));
            }
            if (criteria.getFloorsSR() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFloorsSR(), Hotel_.floorsSR));
            }
            if (criteria.getFloorsBR() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFloorsBR(), Hotel_.floorsBR));
            }
            if (criteria.getEnergyCertificate() != null) {
                specification = specification.and(buildSpecification(criteria.getEnergyCertificate(), Hotel_.energyCertificate));
            }
        }
        return specification;
    }

}
