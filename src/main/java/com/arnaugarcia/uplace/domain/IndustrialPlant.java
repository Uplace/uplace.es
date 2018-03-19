package com.arnaugarcia.uplace.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;

/**
 * A IndustrialPlant.
 */
@Entity
@Table(name = "industrial_plant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IndustrialPlant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "solar_surface")
    private Integer solarSurface;

    @Column(name = "number_rooms")
    private Integer numberRooms;

    @Column(name = "m_2_offices")
    private Integer m2Offices;

    @Column(name = "m_2_storage")
    private Integer m2Storage;

    @Column(name = "height_free")
    private Integer heightFree;

    @Column(name = "number_docks")
    private Integer numberDocks;

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

    public Integer getSolarSurface() {
        return solarSurface;
    }

    public IndustrialPlant solarSurface(Integer solarSurface) {
        this.solarSurface = solarSurface;
        return this;
    }

    public void setSolarSurface(Integer solarSurface) {
        this.solarSurface = solarSurface;
    }

    public Integer getNumberRooms() {
        return numberRooms;
    }

    public IndustrialPlant numberRooms(Integer numberRooms) {
        this.numberRooms = numberRooms;
        return this;
    }

    public void setNumberRooms(Integer numberRooms) {
        this.numberRooms = numberRooms;
    }

    public Integer getm2Offices() {
        return m2Offices;
    }

    public IndustrialPlant m2Offices(Integer m2Offices) {
        this.m2Offices = m2Offices;
        return this;
    }

    public void setm2Offices(Integer m2Offices) {
        this.m2Offices = m2Offices;
    }

    public Integer getm2Storage() {
        return m2Storage;
    }

    public IndustrialPlant m2Storage(Integer m2Storage) {
        this.m2Storage = m2Storage;
        return this;
    }

    public void setm2Storage(Integer m2Storage) {
        this.m2Storage = m2Storage;
    }

    public Integer getHeightFree() {
        return heightFree;
    }

    public IndustrialPlant heightFree(Integer heightFree) {
        this.heightFree = heightFree;
        return this;
    }

    public void setHeightFree(Integer heightFree) {
        this.heightFree = heightFree;
    }

    public Integer getNumberDocks() {
        return numberDocks;
    }

    public IndustrialPlant numberDocks(Integer numberDocks) {
        this.numberDocks = numberDocks;
        return this;
    }

    public void setNumberDocks(Integer numberDocks) {
        this.numberDocks = numberDocks;
    }

    public EnergyCertificate getEnergyCertificate() {
        return energyCertificate;
    }

    public IndustrialPlant energyCertificate(EnergyCertificate energyCertificate) {
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
        IndustrialPlant industrialPlant = (IndustrialPlant) o;
        if (industrialPlant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), industrialPlant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IndustrialPlant{" +
            "id=" + getId() +
            ", solarSurface=" + getSolarSurface() +
            ", numberRooms=" + getNumberRooms() +
            ", m2Offices=" + getm2Offices() +
            ", m2Storage=" + getm2Storage() +
            ", heightFree=" + getHeightFree() +
            ", numberDocks=" + getNumberDocks() +
            ", energyCertificate='" + getEnergyCertificate() + "'" +
            "}";
    }
}
