package com.arnaugarcia.uplace.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.arnaugarcia.uplace.domain.enumeration.BussinessType;

import com.arnaugarcia.uplace.domain.enumeration.Select;

/**
 * A Bussiness.
 */
@Entity
@Table(name = "bussiness")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bussiness implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "bussiness_type")
    private BussinessType bussinessType;

    @Column(name = "number_bathrooms")
    private Integer numberBathrooms;

    @Enumerated(EnumType.STRING)
    @Column(name = "elevator")
    private Select elevator;

    @Enumerated(EnumType.STRING)
    @Column(name = "ac")
    private Select ac;

    @Enumerated(EnumType.STRING)
    @Column(name = "heat")
    private Select heat;

    @Column(name = "surface_terrace")
    private Integer surfaceTerrace;

    @Column(name = "surface_garden")
    private Integer surfaceGarden;

    @Column(name = "number_office")
    private Integer numberOffice;

    @Column(name = "surface_saloon")
    private Integer surfaceSaloon;

    @Column(name = "height")
    private Integer height;

    @Enumerated(EnumType.STRING)
    @Column(name = "pool")
    private Select pool;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BussinessType getBussinessType() {
        return bussinessType;
    }

    public Bussiness bussinessType(BussinessType bussinessType) {
        this.bussinessType = bussinessType;
        return this;
    }

    public void setBussinessType(BussinessType bussinessType) {
        this.bussinessType = bussinessType;
    }

    public Integer getNumberBathrooms() {
        return numberBathrooms;
    }

    public Bussiness numberBathrooms(Integer numberBathrooms) {
        this.numberBathrooms = numberBathrooms;
        return this;
    }

    public void setNumberBathrooms(Integer numberBathrooms) {
        this.numberBathrooms = numberBathrooms;
    }

    public Select getElevator() {
        return elevator;
    }

    public Bussiness elevator(Select elevator) {
        this.elevator = elevator;
        return this;
    }

    public void setElevator(Select elevator) {
        this.elevator = elevator;
    }

    public Select getAc() {
        return ac;
    }

    public Bussiness ac(Select ac) {
        this.ac = ac;
        return this;
    }

    public void setAc(Select ac) {
        this.ac = ac;
    }

    public Select getHeat() {
        return heat;
    }

    public Bussiness heat(Select heat) {
        this.heat = heat;
        return this;
    }

    public void setHeat(Select heat) {
        this.heat = heat;
    }

    public Integer getSurfaceTerrace() {
        return surfaceTerrace;
    }

    public Bussiness surfaceTerrace(Integer surfaceTerrace) {
        this.surfaceTerrace = surfaceTerrace;
        return this;
    }

    public void setSurfaceTerrace(Integer surfaceTerrace) {
        this.surfaceTerrace = surfaceTerrace;
    }

    public Integer getSurfaceGarden() {
        return surfaceGarden;
    }

    public Bussiness surfaceGarden(Integer surfaceGarden) {
        this.surfaceGarden = surfaceGarden;
        return this;
    }

    public void setSurfaceGarden(Integer surfaceGarden) {
        this.surfaceGarden = surfaceGarden;
    }

    public Integer getNumberOffice() {
        return numberOffice;
    }

    public Bussiness numberOffice(Integer numberOffice) {
        this.numberOffice = numberOffice;
        return this;
    }

    public void setNumberOffice(Integer numberOffice) {
        this.numberOffice = numberOffice;
    }

    public Integer getSurfaceSaloon() {
        return surfaceSaloon;
    }

    public Bussiness surfaceSaloon(Integer surfaceSaloon) {
        this.surfaceSaloon = surfaceSaloon;
        return this;
    }

    public void setSurfaceSaloon(Integer surfaceSaloon) {
        this.surfaceSaloon = surfaceSaloon;
    }

    public Integer getHeight() {
        return height;
    }

    public Bussiness height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Select getPool() {
        return pool;
    }

    public Bussiness pool(Select pool) {
        this.pool = pool;
        return this;
    }

    public void setPool(Select pool) {
        this.pool = pool;
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
        Bussiness bussiness = (Bussiness) o;
        if (bussiness.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bussiness.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bussiness{" +
            "id=" + getId() +
            ", bussinessType='" + getBussinessType() + "'" +
            ", numberBathrooms=" + getNumberBathrooms() +
            ", elevator='" + getElevator() + "'" +
            ", ac='" + getAc() + "'" +
            ", heat='" + getHeat() + "'" +
            ", surfaceTerrace=" + getSurfaceTerrace() +
            ", surfaceGarden=" + getSurfaceGarden() +
            ", numberOffice=" + getNumberOffice() +
            ", surfaceSaloon=" + getSurfaceSaloon() +
            ", height=" + getHeight() +
            ", pool='" + getPool() + "'" +
            "}";
    }
}
