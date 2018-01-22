package com.arnaugarcia.uplace.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.arnaugarcia.uplace.domain.enumeration.BusinessType;

import com.arnaugarcia.uplace.domain.enumeration.Select;

/**
 * A Business.
 */
@Entity
@DiscriminatorValue("Business")
public class Business extends Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_type")
    private BusinessType businessType;

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

    public BusinessType getBusinessType() {
        return businessType;
    }

    public Business businessType(BusinessType businessType) {
        this.businessType = businessType;
        return this;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }

    public Integer getNumberBathrooms() {
        return numberBathrooms;
    }

    public Business numberBathrooms(Integer numberBathrooms) {
        this.numberBathrooms = numberBathrooms;
        return this;
    }

    public void setNumberBathrooms(Integer numberBathrooms) {
        this.numberBathrooms = numberBathrooms;
    }

    public Select getElevator() {
        return elevator;
    }

    public Business elevator(Select elevator) {
        this.elevator = elevator;
        return this;
    }

    public void setElevator(Select elevator) {
        this.elevator = elevator;
    }

    public Select getAc() {
        return ac;
    }

    public Business ac(Select ac) {
        this.ac = ac;
        return this;
    }

    public void setAc(Select ac) {
        this.ac = ac;
    }

    public Select getHeat() {
        return heat;
    }

    public Business heat(Select heat) {
        this.heat = heat;
        return this;
    }

    public void setHeat(Select heat) {
        this.heat = heat;
    }

    public Integer getSurfaceTerrace() {
        return surfaceTerrace;
    }

    public Business surfaceTerrace(Integer surfaceTerrace) {
        this.surfaceTerrace = surfaceTerrace;
        return this;
    }

    public void setSurfaceTerrace(Integer surfaceTerrace) {
        this.surfaceTerrace = surfaceTerrace;
    }

    public Integer getSurfaceGarden() {
        return surfaceGarden;
    }

    public Business surfaceGarden(Integer surfaceGarden) {
        this.surfaceGarden = surfaceGarden;
        return this;
    }

    public void setSurfaceGarden(Integer surfaceGarden) {
        this.surfaceGarden = surfaceGarden;
    }

    public Integer getNumberOffice() {
        return numberOffice;
    }

    public Business numberOffice(Integer numberOffice) {
        this.numberOffice = numberOffice;
        return this;
    }

    public void setNumberOffice(Integer numberOffice) {
        this.numberOffice = numberOffice;
    }

    public Integer getSurfaceSaloon() {
        return surfaceSaloon;
    }

    public Business surfaceSaloon(Integer surfaceSaloon) {
        this.surfaceSaloon = surfaceSaloon;
        return this;
    }

    public void setSurfaceSaloon(Integer surfaceSaloon) {
        this.surfaceSaloon = surfaceSaloon;
    }

    public Integer getHeight() {
        return height;
    }

    public Business height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Select getPool() {
        return pool;
    }

    public Business pool(Select pool) {
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
        Business business = (Business) o;
        if (business.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), business.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Business{" +
            "id=" + getId() +
            ", businessType='" + getBusinessType() + "'" +
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
