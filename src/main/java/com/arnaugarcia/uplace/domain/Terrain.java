package com.arnaugarcia.uplace.domain;

import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.TerrainType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "terrain_type")
    private TerrainType terrainType;

    @Enumerated(EnumType.STRING)
    @Column(name = "near_transport")
    private Select nearTransport;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public Terrain terrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
        return this;
    }

    public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }

    public Select getNearTransport() {
        return nearTransport;
    }

    public Terrain nearTransport(Select nearTransport) {
        this.nearTransport = nearTransport;
        return this;
    }

    public void setNearTransport(Select nearTransport) {
        this.nearTransport = nearTransport;
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
            ", terrainType='" + getTerrainType() + "'" +
            ", nearTransport='" + getNearTransport() + "'" +
            "}";
    }
}
