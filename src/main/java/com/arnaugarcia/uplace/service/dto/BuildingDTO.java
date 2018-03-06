package com.arnaugarcia.uplace.service.dto;


import java.io.Serializable;
import java.util.Objects;
import com.arnaugarcia.uplace.domain.enumeration.BuildingType;
import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;

/**
 * A DTO for the Building entity.
 */
public class BuildingDTO extends PropertyDTO implements Serializable {

    private Long id;

    private BuildingType type;

    private Integer solarSurface;

    private Integer m2Edified;

    private Integer floorsSR;

    private Integer floorsBR;

    private EnergyCertificate energyCertificate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BuildingType getType() {
        return type;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

    public Integer getSolarSurface() {
        return solarSurface;
    }

    public void setSolarSurface(Integer solarSurface) {
        this.solarSurface = solarSurface;
    }

    public Integer getm2Edified() {
        return m2Edified;
    }

    public void setm2Edified(Integer m2Edified) {
        this.m2Edified = m2Edified;
    }

    public Integer getFloorsSR() {
        return floorsSR;
    }

    public void setFloorsSR(Integer floorsSR) {
        this.floorsSR = floorsSR;
    }

    public Integer getFloorsBR() {
        return floorsBR;
    }

    public void setFloorsBR(Integer floorsBR) {
        this.floorsBR = floorsBR;
    }

    public EnergyCertificate getEnergyCertificate() {
        return energyCertificate;
    }

    public void setEnergyCertificate(EnergyCertificate energyCertificate) {
        this.energyCertificate = energyCertificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BuildingDTO buildingDTO = (BuildingDTO) o;
        if(buildingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), buildingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BuildingDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", solarSurface=" + getSolarSurface() +
            ", m2Edified=" + getm2Edified() +
            ", floorsSR=" + getFloorsSR() +
            ", floorsBR=" + getFloorsBR() +
            ", energyCertificate='" + getEnergyCertificate() + "'" +
            "}";
    }
}
