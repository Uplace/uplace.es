package com.arnaugarcia.uplace.service.queries;

import com.arnaugarcia.uplace.domain.Location;
import com.arnaugarcia.uplace.domain.Location_;
import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.repository.PropertyRepository;
import com.arnaugarcia.uplace.service.dto.SearchDTO;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for executing complex queries for Property entities in the database.
 * The main input is a {@link PropertyCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PropertyDTO} or a {@link Page} of {@link PropertyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PropertyQueryService<T extends Property> extends QueryService<T> {

    private final Logger log = LoggerFactory.getLogger(PropertyQueryService.class);

    private final PropertyRepository<T> propertyRepository;

    public PropertyQueryService(PropertyRepository<T> propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /**
     * Return a {@link Page} of {@link filter} which matches the criteria from the database
     * @param filter The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<T> findByCriteria(SearchDTO filter, Pageable page) {
        log.debug("find by criteria : {}, page: {}", filter, page);

        Page<T> properties = propertyRepository.findAll((root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // If designation is specified in filter, add equal where clause
            /*if (filter.getCity() != null) {
                Root<Location> locationRoot = query.from(Location.class);
                predicates.add(cb.equal(locationRoot.get("city"), filter.getCity()));
            }*/

            if (filter.getCategory() != null) {
                predicates.add(cb.equal(root.get("propertyType"), filter.getCategory()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, page);

        return properties;
    }

}
