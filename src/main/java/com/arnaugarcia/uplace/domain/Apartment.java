package com.arnaugarcia.uplace.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.arnaugarcia.uplace.domain.enumeration.Select;

import com.arnaugarcia.uplace.domain.enumeration.ApartmentType;

/**
 * A Apartment.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DiscriminatorValue("Apartment")
public class Apartment extends Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number_bedrooms")
    private Integer numberBedrooms;

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

    @Column(name = "surface_saloon")
    private Integer surfaceSaloon;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type")
    private ApartmentType propertyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "office")
    private Select office;

    @Enumerated(EnumType.STRING)
    @Column(name = "kitchen_office")
    private Select kitchenOffice;

    @Enumerated(EnumType.STRING)
    @Column(name = "up_storage")
    private Select storage;

    @Enumerated(EnumType.STRING)
    @Column(name = "up_shared_pool")
    private Select sharedPool;

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

    public Integer getNumberBedrooms() {
        return numberBedrooms;
    }

    public Apartment numberBedrooms(Integer numberBedrooms) {
        this.numberBedrooms = numberBedrooms;
        return this;
    }

    public void setNumberBedrooms(Integer numberBedrooms) {
        this.numberBedrooms = numberBedrooms;
    }

    public Integer getNumberBathrooms() {
        return numberBathrooms;
    }

    public Apartment numberBathrooms(Integer numberBathrooms) {
        this.numberBathrooms = numberBathrooms;
        return this;
    }

    public void setNumberBathrooms(Integer numberBathrooms) {
        this.numberBathrooms = numberBathrooms;
    }

    public Select getElevator() {
        return elevator;
    }

    public Apartment elevator(Select elevator) {
        this.elevator = elevator;
        return this;
    }

    public void setElevator(Select elevator) {
        this.elevator = elevator;
    }

    public Select getAc() {
        return ac;
    }

    public Apartment ac(Select ac) {
        this.ac = ac;
        return this;
    }

    public void setAc(Select ac) {
        this.ac = ac;
    }

    public Select getHeat() {
        return heat;
    }

    public Apartment heat(Select heat) {
        this.heat = heat;
        return this;
    }

    public void setHeat(Select heat) {
        this.heat = heat;
    }

    public Integer getSurfaceTerrace() {
        return surfaceTerrace;
    }

    public Apartment surfaceTerrace(Integer surfaceTerrace) {
        this.surfaceTerrace = surfaceTerrace;
        return this;
    }

    public void setSurfaceTerrace(Integer surfaceTerrace) {
        this.surfaceTerrace = surfaceTerrace;
    }

    public Integer getSurfaceSaloon() {
        return surfaceSaloon;
    }

    public Apartment surfaceSaloon(Integer surfaceSaloon) {
        this.surfaceSaloon = surfaceSaloon;
        return this;
    }

    public void setSurfaceSaloon(Integer surfaceSaloon) {
        this.surfaceSaloon = surfaceSaloon;
    }

    public ApartmentType getPropertyType() {
        return propertyType;
    }

    public Apartment propertyType(ApartmentType propertyType) {
        this.propertyType = propertyType;
        return this;
    }

    public void setPropertyType(ApartmentType propertyType) {
        this.propertyType = propertyType;
    }

    public Select getOffice() {
        return office;
    }

    public Apartment office(Select office) {
        this.office = office;
        return this;
    }

    public void setOffice(Select office) {
        this.office = office;
    }

    public Select getKitchenOffice() {
        return kitchenOffice;
    }

    public Apartment kitchenOffice(Select kitchenOffice) {
        this.kitchenOffice = kitchenOffice;
        return this;
    }

    public void setKitchenOffice(Select kitchenOffice) {
        this.kitchenOffice = kitchenOffice;
    }

    public Select getStorage() {
        return storage;
    }

    public Apartment storage(Select storage) {
        this.storage = storage;
        return this;
    }

    public void setStorage(Select storage) {
        this.storage = storage;
    }

    public Select getSharedPool() {
        return sharedPool;
    }

    public Apartment sharedPool(Select sharedPool) {
        this.sharedPool = sharedPool;
        return this;
    }

    public void setSharedPool(Select sharedPool) {
        this.sharedPool = sharedPool;
    }

    public Select getNearTransport() {
        return nearTransport;
    }

    public Apartment nearTransport(Select nearTransport) {
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
        Apartment apartment = (Apartment) o;
        if (apartment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apartment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Apartment{" +
            "id=" + getId() +
            ", numberBedrooms=" + getNumberBedrooms() +
            ", numberBathrooms=" + getNumberBathrooms() +
            ", elevator='" + getElevator() + "'" +
            ", ac='" + getAc() + "'" +
            ", heat='" + getHeat() + "'" +
            ", surfaceTerrace=" + getSurfaceTerrace() +
            ", surfaceSaloon=" + getSurfaceSaloon() +
            ", propertyType='" + getPropertyType() + "'" +
            ", office='" + getOffice() + "'" +
            ", kitchenOffice='" + getKitchenOffice() + "'" +
            ", storage='" + getStorage() + "'" +
            ", sharedPool='" + getSharedPool() + "'" +
            ", nearTransport='" + getNearTransport() + "'" +
            "}";
    }
}
