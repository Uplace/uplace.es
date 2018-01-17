package com.arnaugarcia.uplace.service.mapper;

import com.arnaugarcia.uplace.domain.*;
import com.arnaugarcia.uplace.service.dto.BussinessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Bussiness and its DTO BussinessDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BussinessMapper extends EntityMapper<BussinessDTO, Bussiness> {



    default Bussiness fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bussiness bussiness = new Bussiness();
        bussiness.setId(id);
        return bussiness;
    }
}
