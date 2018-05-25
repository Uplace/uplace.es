package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.*;
import com.arnaugarcia.uplace.repository.PropertyRepository;
import com.arnaugarcia.uplace.security.SecurityUtils;
import com.arnaugarcia.uplace.service.dto.SearchDTO;
import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.BooleanFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Service for executing complex queries for Property entities in the database.
 * The main input is a {@link Property} which get's converted to {@link Predicate},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Property} or a {@link Page} of {@link Property} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PropertyQueryService<T extends Property> extends QueryService {

    private final Logger log = LoggerFactory.getLogger(PropertyQueryService.class);

    private final PropertyRepository<T> propertyRepository;

    public PropertyQueryService(PropertyRepository<T> propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /*@PostConstruct
    public void initCriteria(){
        criteriaBuilder = entityManager.getCriteriaBuilder();

        query = criteriaBuilder.createQuery(Property.class);

        root = query.from( Property.class );
    }*/

    /**
     * Return a {@link Page} of {@link Property} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<T> findByCriteria(SearchDTO criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        boolean userLogged = SecurityUtils.isAuthenticated();

        final Specifications<T> specifications = createSpecification(criteria, userLogged);

        return propertyRepository.findAll(specifications, page);

        /*return propertyRepository.findAll((root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // If designation is specified in filter, add equal where clause
            if (filter.getCity() != null) {
                predicates.add(filterByCity(filter.getCity()));
            }

            *//*if (filter.getCategory() != null) {
                predicates.add(cb.equal(root.get(Property_.propertyType), filter.getCategory()));
            }

            if (filter.getKeywords() != null) {
                predicates.add(cb.like(root.get(Property_.title), "%" + filter.getKeywords() + "%"));
                predicates.add(cb.like(root.get(Property_.description), "%" + filter.getKeywords() + "%"));
            }

            if (filter.getPriceMin() != null && filter.getPriceMax() != null) {

                log.debug("Between price ({} - {})", filter.getPriceMin(), filter.getPriceMax());

                Predicate predicateRent = cb.between(root.get(Property_.priceRent), filter.getPriceMin(), filter.getPriceMax());
                Predicate predicateSell = cb.between(root.get(Property_.priceSell), filter.getPriceMin(), filter.getPriceMax());
                Predicate predicateTransfer = cb.between(root.get(Property_.priceTransfer), filter.getPriceMin(), filter.getPriceMax());

                // Create an OR for all the prices
                predicates.add(cb.or(predicateRent, predicateSell, predicateTransfer));

            } else {

                if (filter.getPriceMax() != null) {

                    log.debug("Max price ({})", filter.getPriceMax());

                    Predicate predicateRent = cb.lessThanOrEqualTo(root.get(Property_.priceRent), filter.getPriceMax());
                    Predicate predicateSell = cb.lessThanOrEqualTo(root.get(Property_.priceSell), filter.getPriceMax());
                    Predicate predicateTransfer = cb.lessThanOrEqualTo(root.get(Property_.priceTransfer), filter.getPriceMax());

                    predicates.add(cb.or(predicateRent, predicateSell, predicateTransfer));
                }

                if (filter.getPriceMin() != null) {

                    log.debug("Min price ({})", filter.getPriceMin());

                    Predicate predicateRent = cb.greaterThanOrEqualTo(root.get(Property_.priceRent), filter.getPriceMin());
                    Predicate predicateSell = cb.greaterThanOrEqualTo(root.get(Property_.priceSell), filter.getPriceMin());
                    Predicate predicateTransfer = cb.greaterThanOrEqualTo(root.get(Property_.priceTransfer), filter.getPriceMin());

                    predicates.add(cb.or(predicateRent, predicateSell, predicateTransfer));

                }

            }*//*
            return cb.and(predicates.toArray(new Predicate[0]));
        }, page);*/

    }

    private Specifications<T> createSpecification(SearchDTO criteria, boolean userLogged) {

        Specifications<T> specification = Specifications.where(null);

        if (criteria != null) {
            if (!userLogged) {
                // If the user isn't logged only will show visible porperties
                specification = specification.and(buildSpecification(new BooleanFilter().setEquals(true), Property_.visible));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategory(), Property_.propertyType));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCity(), Property_.location, Location_.city));
            }
            if (criteria.getKeywords() != null) {
                specification = specification.or(buildStringSpecification(criteria.getKeywords(), Property_.title));
                specification = specification.or(buildStringSpecification(criteria.getKeywords(), Property_.description));
            }
            if (criteria.getBedrooms() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBedrooms(), Apartment_.numberBedrooms));
            }
            if (criteria.getBathrooms() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBathrooms(), Apartment_.numberBathrooms));
            }
            if (criteria.getPrice() != null) {
                specification = specification.or(buildRangeSpecification(criteria.getPrice(), Property_.priceTransfer));
                specification = specification.or(buildRangeSpecification(criteria.getPrice(), Property_.priceRent));
                specification = specification.or(buildRangeSpecification(criteria.getPrice(), Property_.priceSell));
            }

        }

        return specification;
    }

}
