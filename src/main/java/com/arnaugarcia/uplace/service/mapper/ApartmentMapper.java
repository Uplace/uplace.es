package com.arnaugarcia.uplace.service.mapper;

import com.arnaugarcia.uplace.domain.*;
import com.arnaugarcia.uplace.service.dto.ApartmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Apartment and its DTO ApartmentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApartmentMapper extends EntityMapper<ApartmentDTO, Apartment> {



    default Apartment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Apartment apartment = new Apartment();
        apartment.setId(id);
        return apartment;
    }
}
