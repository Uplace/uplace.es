package com.arnaugarcia.uplace.service.queries;

import com.arnaugarcia.uplace.domain.Parking;
import com.arnaugarcia.uplace.domain.Parking_;
import com.arnaugarcia.uplace.domain.Property_;
import com.arnaugarcia.uplace.repository.PropertyRepository;
import com.arnaugarcia.uplace.service.dto.ParkingCriteria;
import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.StringFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for Parking entities in the database.
 * The main input is a {@link ParkingCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Parking} or a {@link Page} of {@link Parking} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParkingQueryService extends QueryService<Parking> {

    private final Logger log = LoggerFactory.getLogger(ParkingQueryService.class);

    private final PropertyRepository<Parking> propertyRepository;

    public ParkingQueryService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /**
     * Return a {@link Page} of {@link Parking} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Parking> findByCriteria(ParkingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        criteria.setPropertyType(new StringFilter().setContains(Parking.class.getSimpleName()));
        final Specifications<Parking> specification = createSpecification(criteria);
        return propertyRepository.findAll(specification, page);
    }

    /**
     * Function to convert ParkingCriteria to a {@link Specifications}
     */
    private Specifications<Parking> createSpecification(ParkingCriteria criteria) {
        Specifications<Parking> specification = Specifications.where(null);
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
            if (criteria.getParkingType() != null) {
                specification = specification.and(buildSpecification(criteria.getParkingType(), Parking_.parkingType));
            }
            if (criteria.getNearTransport() != null) {
                specification = specification.and(buildSpecification(criteria.getNearTransport(), Parking_.nearTransport));
            }
            if (criteria.getSurveillance() != null) {
                specification = specification.and(buildSpecification(criteria.getSurveillance(), Parking_.surveillance));
            }
        }
        return specification;
    }

}