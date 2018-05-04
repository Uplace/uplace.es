package com.arnaugarcia.uplace.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.arnaugarcia.uplace.domain.enumeration.BuildingType;

import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;
import io.swagger.annotations.ApiModel;

/**
 * A Building.
 */
@Entity
@DiscriminatorValue("Building")
@ApiModel(value="Building")
public class Building extends Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "up_type")
    private BuildingType type;

    @Column(name = "solar_surface")
    private Integer solarSurface;

    @Column(name = "m_2_edified")
    private Integer m2Edified;

    @Column(name = "floors_sr")
    private Integer floorsSR;

    @Column(name = "floors_br")
    private Integer floorsBR;

    @Enumerated(EnumType.STRING)
    @Column(name = "energy_certificate")
    private EnergyCertificate energyCertificate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BuildingType getType() {
        return type;
    }

    public Building type(BuildingType type) {
        this.type = type;
        return this;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

    public Integer getSolarSurface() {
        return solarSurface;
    }

    public Building solarSurface(Integer solarSurface) {
        this.solarSurface = solarSurface;
        return this;
    }

    public void setSolarSurface(Integer solarSurface) {
        this.solarSurface = solarSurface;
    }

    public Integer getm2Edified() {
        return m2Edified;
    }

    public Building m2Edified(Integer m2Edified) {
        this.m2Edified = m2Edified;
        return this;
    }

    public void setm2Edified(Integer m2Edified) {
        this.m2Edified = m2Edified;
    }

    public Integer getFloorsSR() {
        return floorsSR;
    }

    public Building floorsSR(Integer floorsSR) {
        this.floorsSR = floorsSR;
        return this;
    }

    public void setFloorsSR(Integer floorsSR) {
        this.floorsSR = floorsSR;
    }

    public Integer getFloorsBR() {
        return floorsBR;
    }

    public Building floorsBR(Integer floorsBR) {
        this.floorsBR = floorsBR;
        return this;
    }

    public void setFloorsBR(Integer floorsBR) {
        this.floorsBR = floorsBR;
    }

    public EnergyCertificate getEnergyCertificate() {
        return energyCertificate;
    }

    public Building energyCertificate(EnergyCertificate energyCertificate) {
        this.energyCertificate = energyCertificate;
        return this;
    }

    public void setEnergyCertificate(EnergyCertificate energyCertificate) {
        this.energyCertificate = energyCertificate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Building building = (Building) o;
        if (building.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), building.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Building{" +
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
