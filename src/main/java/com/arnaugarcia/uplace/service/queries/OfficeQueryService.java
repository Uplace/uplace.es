package com.arnaugarcia.uplace.service.queries;

import com.arnaugarcia.uplace.domain.Office;
import com.arnaugarcia.uplace.domain.Office_;
import com.arnaugarcia.uplace.domain.Property_;
import com.arnaugarcia.uplace.repository.PropertyRepository;
import com.arnaugarcia.uplace.service.dto.OfficeCriteria;
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
 * Service for executing complex queries for Office entities in the database.
 * The main input is a {@link OfficeCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Office} or a {@link Page} of {@link Office} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OfficeQueryService extends QueryService<Office> {

    private final Logger log = LoggerFactory.getLogger(OfficeQueryService.class);


    private final PropertyRepository<Office> propertyRepository;

    public OfficeQueryService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
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
        criteria.setPropertyType(new StringFilter().setContains(Office.class.getSimpleName()));
        final Specifications<Office> specification = createSpecification(criteria);
        return propertyRepository.findAll(specification, page);
    }

    /**
     * Function to convert OfficeCriteria to a {@link Specifications}
     */
    private Specifications<Office> createSpecification(OfficeCriteria criteria) {
        Specifications<Office> specification = Specifications.where(null);
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
