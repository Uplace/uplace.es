package com.arnaugarcia.uplace.service.dto;


import java.io.Serializable;
import java.util.Objects;
import com.arnaugarcia.uplace.domain.enumeration.TerrainType;
import com.arnaugarcia.uplace.domain.enumeration.Select;

/**
 * A DTO for the Terrain entity.
 */
public class TerrainDTO implements Serializable {

    private Long id;

    private TerrainType terrainType;

    private Select nearTransport;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }

    public Select getNearTransport() {
        return nearTransport;
    }

    public void setNearTransport(Select nearTransport) {
        this.nearTransport = nearTransport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TerrainDTO terrainDTO = (TerrainDTO) o;
        if(terrainDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), terrainDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TerrainDTO{" +
            "id=" + getId() +
            ", terrainType='" + getTerrainType() + "'" +
            ", nearTransport='" + getNearTransport() + "'" +
            "}";
    }
}
