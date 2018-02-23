package com.arnaugarcia.uplace.service.mapper;

import com.arnaugarcia.uplace.domain.*;
import com.arnaugarcia.uplace.service.dto.PropertyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Property and its DTO PropertyDTO.
 */
@Mapper(componentModel = "spring", uses = {LocationMapper.class, AgentMapper.class})
public interface PropertyMapper extends EntityMapper<PropertyDTO, Property> {

    @Mapping(source = "location.id", target = "locationId")
    PropertyDTO toDto(Property property);

    @Mapping(source = "locationId", target = "location")
    @Mapping(target = "photos", ignore = true)
    Property toEntity(PropertyDTO propertyDTO);

    default Property fromId(Long id) {
        if (id == null) {
            return null;
        }
        Property property = new Property();
        property.setId(id);
        return property;
    }
}
