package com.arnaugarcia.uplace.service.mapper;

import com.arnaugarcia.uplace.domain.*;
import com.arnaugarcia.uplace.service.dto.TerrainDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Terrain and its DTO TerrainDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TerrainMapper extends EntityMapper<TerrainDTO, Terrain> {



    default Terrain fromId(Long id) {
        if (id == null) {
            return null;
        }
        Terrain terrain = new Terrain();
        terrain.setId(id);
        return terrain;
    }
}
