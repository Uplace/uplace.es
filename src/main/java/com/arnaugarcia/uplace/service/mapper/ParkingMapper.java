package com.arnaugarcia.uplace.service.mapper;

import com.arnaugarcia.uplace.domain.*;
import com.arnaugarcia.uplace.service.dto.ParkingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Parking and its DTO ParkingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ParkingMapper extends EntityMapper<ParkingDTO, Parking> {



    default Parking fromId(Long id) {
        if (id == null) {
            return null;
        }
        Parking parking = new Parking();
        parking.setId(id);
        return parking;
    }
}
