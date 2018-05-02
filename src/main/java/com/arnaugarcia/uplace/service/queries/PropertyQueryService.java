package com.arnaugarcia.uplace.service.queries;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.arnaugarcia.uplace.service.dto.SearchDTO;
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
     * Return a {@link Page} of {@link PropertyDTO} which matches the criteria from the database
     * @param searchDTO The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<T> findByCriteria(SearchDTO searchDTO, Pageable page) {
        log.debug("find by criteria : {}, page: {}", searchDTO, page);
        Arrays.stream(searchDTO.getClass().getFields()).forEach(System.out::println);
        return propertyRepository.findAll(page);
    }

}
