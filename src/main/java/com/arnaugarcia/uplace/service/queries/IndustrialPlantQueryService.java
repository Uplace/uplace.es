package com.arnaugarcia.uplace.service.queries;

import com.arnaugarcia.uplace.domain.IndustrialPlant;
import com.arnaugarcia.uplace.domain.IndustrialPlant_;
import com.arnaugarcia.uplace.domain.Property_;
import com.arnaugarcia.uplace.repository.PropertyRepository;
import com.arnaugarcia.uplace.service.dto.IndustrialPlantCriteria;
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
 * Service for executing complex queries for IndustrialPlant entities in the database.
 * The main input is a {@link IndustrialPlantCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IndustrialPlant} or a {@link Page} of {@link IndustrialPlant} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IndustrialPlantQueryService extends QueryService<IndustrialPlant> {

    private final Logger log = LoggerFactory.getLogger(IndustrialPlantQueryService.class);


    private final PropertyRepository<IndustrialPlant> propertyRepository;

    public IndustrialPlantQueryService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
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
        criteria.setPropertyType(new StringFilter().setContains(IndustrialPlant.class.getSimpleName()));
        final Specifications<IndustrialPlant> specification = createSpecification(criteria);
        return propertyRepository.findAll(specification, page);
    }

    /**
     * Function to convert IndustrialPlantCriteria to a {@link Specifications}
     */
    private Specifications<IndustrialPlant> createSpecification(IndustrialPlantCriteria criteria) {
        Specifications<IndustrialPlant> specification = Specifications.where(null);
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