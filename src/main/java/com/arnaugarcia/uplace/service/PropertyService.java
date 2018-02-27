package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.repository.PropertyRepository;
import com.arnaugarcia.uplace.service.dto.PropertyDTO;
import com.arnaugarcia.uplace.service.mapper.AgentMapper;
import com.arnaugarcia.uplace.service.mapper.PropertyMapper;
import com.arnaugarcia.uplace.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Service Implementation for managing Property.
 */
@Service
@Transactional
public class PropertyService {

    private final Logger log = LoggerFactory.getLogger(PropertyService.class);

    private final PropertyRepository propertyRepository;

    private final PropertyMapper propertyMapper;

    public PropertyService(PropertyRepository propertyRepository, PropertyMapper propertyMapper) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
    }

    /**
     * Save a property.
     *
     * @param propertyDTO the entity to save
     * @return the persisted entity
     */
    /*public PropertyDTO save(PropertyDTO propertyDTO) {
        log.debug("Request to save Property : {}", propertyDTO);
        Property property = propertyMapper.toEntity(propertyDTO);
        property.setReference(this.createReference());
        property = propertyRepository.save(property);
        return propertyMapper.toDto(property);
    }*/

    /**
     * Get all the properties.
     *
     * @return the list of entities
     */
    /*@Transactional(readOnly = true)
    public List<Property> findAll() {
        log.debug("Request to get all Properties");
        return propertyRepository.findAllWithEagerRelationships();
    }*/

    /**
     * Get one property by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Property findOne(Long id) {
        log.debug("Request to get Property : {}", id);
        return propertyRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Get one property by reference.
     *
     * @param reference the reference of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Property findOne(String reference) {
        log.debug("Request to get Property : {}", reference);
        return propertyRepository.findByReference(reference);
    }

    /**
     * Delete the property by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Property : {}", id);
        propertyRepository.delete(id);
    }


    /**
     * Create unique reference randomly.
     *
     * @return reference created
     */
    public String createReference() {
        String reference;
        do {
            reference = RandomUtil.generateReference().toUpperCase();
            log.debug("Generating reference: " + reference);
        } while (propertyRepository.findByReference(reference) != null);
        return reference;
    }

    @Transactional(readOnly = true)
    public List<Property> getLastProperties(@PathVariable Integer size) {
        PageRequest limit = new PageRequest(0, size);
        return propertyRepository.findLastProperties(limit).getContent();
    }

}
