package com.arnaugarcia.uplace.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.arnaugarcia.uplace.domain.enumeration.Select;

import com.arnaugarcia.uplace.domain.enumeration.ApartmentType;

import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;

/**
 * A Apartment.
 */
@Entity
@DiscriminatorValue("Apartment")
public class Apartment extends Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_bedrooms")
    private Integer numberBedrooms;

    @Column(name = "number_bathrooms")
    private Integer numberBathrooms;

    @Column(name = "m_2_edified")
    private Integer m2Edified;

    @Column(name = "m_2_usable")
    private Integer m2Usable;

    @Column(name = "height")
    private Integer height;

    @Enumerated(EnumType.STRING)
    @Column(name = "elevator")
    private Select elevator;

    @Enumerated(EnumType.STRING)
    @Column(name = "ac")
    private Select ac;

    @Enumerated(EnumType.STRING)
    @Column(name = "heat")
    private Select heat;

    @Enumerated(EnumType.STRING)
    @Column(name = "parking")
    private Select parking;

    @Enumerated(EnumType.STRING)
    @Column(name = "terrace")
    private Select terrace;

    @Enumerated(EnumType.STRING)
    @Column(name = "balcony")
    private Select balcony;

    @Column(name = "surface_terrace")
    private Integer surfaceTerrace;

    @Column(name = "surface_saloon")
    private Integer surfaceSaloon;

    @Enumerated(EnumType.STRING)
    @Column(name = "up_type")
    private ApartmentType type;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "reformed")
    private Select reformed;

    @Enumerated(EnumType.STRING)
    @Column(name = "energy_certificate")
    private EnergyCertificate energyCertificate;

    @Enumerated(EnumType.STRING)
    @Column(name = "certificate_habitability")
    private Select certificateHabitability;

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

    public Integer getm2Edified() {
        return m2Edified;
    }

    public Apartment m2Edified(Integer m2Edified) {
        this.m2Edified = m2Edified;
        return this;
    }

    public void setm2Edified(Integer m2Edified) {
        this.m2Edified = m2Edified;
    }

    public Integer getm2Usable() {
        return m2Usable;
    }

    public Apartment m2Usable(Integer m2Usable) {
        this.m2Usable = m2Usable;
        return this;
    }

    public void setm2Usable(Integer m2Usable) {
        this.m2Usable = m2Usable;
    }

    public Integer getHeight() {
        return height;
    }

    public Apartment height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
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

    public Select getParking() {
        return parking;
    }

    public Apartment parking(Select parking) {
        this.parking = parking;
        return this;
    }

    public void setParking(Select parking) {
        this.parking = parking;
    }

    public Select getTerrace() {
        return terrace;
    }

    public Apartment terrace(Select terrace) {
        this.terrace = terrace;
        return this;
    }

    public void setTerrace(Select terrace) {
        this.terrace = terrace;
    }

    public Select getBalcony() {
        return balcony;
    }

    public Apartment balcony(Select balcony) {
        this.balcony = balcony;
        return this;
    }

    public void setBalcony(Select balcony) {
        this.balcony = balcony;
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

    public ApartmentType getType() {
        return type;
    }

    public Apartment type(ApartmentType type) {
        this.type = type;
        return this;
    }

    public void setType(ApartmentType type) {
        this.type = type;
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

    public Select getReformed() {
        return reformed;
    }

    public Apartment reformed(Select reformed) {
        this.reformed = reformed;
        return this;
    }

    public void setReformed(Select reformed) {
        this.reformed = reformed;
    }

    public EnergyCertificate getEnergyCertificate() {
        return energyCertificate;
    }

    public Apartment energyCertificate(EnergyCertificate energyCertificate) {
        this.energyCertificate = energyCertificate;
        return this;
    }

    public void setEnergyCertificate(EnergyCertificate energyCertificate) {
        this.energyCertificate = energyCertificate;
    }

    public Select getCertificateHabitability() {
        return certificateHabitability;
    }

    public Apartment certificateHabitability(Select certificateHabitability) {
        this.certificateHabitability = certificateHabitability;
        return this;
    }

    public void setCertificateHabitability(Select certificateHabitability) {
        this.certificateHabitability = certificateHabitability;
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
            ", m2Edified=" + getm2Edified() +
            ", m2Usable=" + getm2Usable() +
            ", height=" + getHeight() +
            ", elevator='" + getElevator() + "'" +
            ", ac='" + getAc() + "'" +
            ", heat='" + getHeat() + "'" +
            ", parking='" + getParking() + "'" +
            ", terrace='" + getTerrace() + "'" +
            ", balcony='" + getBalcony() + "'" +
            ", surfaceTerrace=" + getSurfaceTerrace() +
            ", surfaceSaloon=" + getSurfaceSaloon() +
            ", type='" + getType() + "'" +
            ", office='" + getOffice() + "'" +
            ", kitchenOffice='" + getKitchenOffice() + "'" +
            ", storage='" + getStorage() + "'" +
            ", sharedPool='" + getSharedPool() + "'" +
            ", nearTransport='" + getNearTransport() + "'" +
            ", reformed='" + getReformed() + "'" +
            ", energyCertificate='" + getEnergyCertificate() + "'" +
            ", certificateHabitability='" + getCertificateHabitability() + "'" +
            "}";
    }
}
