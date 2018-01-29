package com.arnaugarcia.uplace.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.arnaugarcia.uplace.domain.enumeration.TerrainType;

/**
 * A Terrain.
 */
@Entity
@DiscriminatorValue("Terrain")
public class Terrain extends Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "m_2_buildable")
    private Integer m2Buildable;

    @Column(name = "buildable_depth")
    private Integer buildableDepth;

    @Column(name = "floors_sr")
    private Integer floorsSR;

    @Column(name = "floors_br")
    private Integer floorsBR;

    @Column(name = "construction_coefficient")
    private Double constructionCoefficient;

    @Enumerated(EnumType.STRING)
    @Column(name = "up_type")
    private TerrainType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getm2Buildable() {
        return m2Buildable;
    }

    public Terrain m2Buildable(Integer m2Buildable) {
        this.m2Buildable = m2Buildable;
        return this;
    }

    public void setm2Buildable(Integer m2Buildable) {
        this.m2Buildable = m2Buildable;
    }

    public Integer getBuildableDepth() {
        return buildableDepth;
    }

    public Terrain buildableDepth(Integer buildableDepth) {
        this.buildableDepth = buildableDepth;
        return this;
    }

    public void setBuildableDepth(Integer buildableDepth) {
        this.buildableDepth = buildableDepth;
    }

    public Integer getFloorsSR() {
        return floorsSR;
    }

    public Terrain floorsSR(Integer floorsSR) {
        this.floorsSR = floorsSR;
        return this;
    }

    public void setFloorsSR(Integer floorsSR) {
        this.floorsSR = floorsSR;
    }

    public Integer getFloorsBR() {
        return floorsBR;
    }

    public Terrain floorsBR(Integer floorsBR) {
        this.floorsBR = floorsBR;
        return this;
    }

    public void setFloorsBR(Integer floorsBR) {
        this.floorsBR = floorsBR;
    }

    public Double getConstructionCoefficient() {
        return constructionCoefficient;
    }

    public Terrain constructionCoefficient(Double constructionCoefficient) {
        this.constructionCoefficient = constructionCoefficient;
        return this;
    }

    public void setConstructionCoefficient(Double constructionCoefficient) {
        this.constructionCoefficient = constructionCoefficient;
    }

    public TerrainType getType() {
        return type;
    }

    public Terrain type(TerrainType type) {
        this.type = type;
        return this;
    }

    public void setType(TerrainType type) {
        this.type = type;
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
        Terrain terrain = (Terrain) o;
        if (terrain.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), terrain.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Terrain{" +
            "id=" + getId() +
            ", m2Buildable=" + getm2Buildable() +
            ", buildableDepth=" + getBuildableDepth() +
            ", floorsSR=" + getFloorsSR() +
            ", floorsBR=" + getFloorsBR() +
            ", constructionCoefficient=" + getConstructionCoefficient() +
            ", type='" + getType() + "'" +
            "}";
    }
}
