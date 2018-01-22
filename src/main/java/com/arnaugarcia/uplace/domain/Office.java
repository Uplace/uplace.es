package com.arnaugarcia.uplace.domain;

import com.arnaugarcia.uplace.domain.enumeration.Select;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Office.
 */
@Entity
@DiscriminatorValue("Office")
public class Office extends Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bathrooms")
    private String bathrooms;

    @Column(name = "floors")
    private Integer floors;

    @Column(name = "terrace")
    private Integer terrace;

    @Enumerated(EnumType.STRING)
    @Column(name = "office")
    private Select office;

    @Enumerated(EnumType.STRING)
    @Column(name = "up_storage")
    private Select storage;

    @Column(name = "storage_surface")
    private Integer storageSurface;

    @Column(name = "offices_surface")
    private Integer officesSurface;

    @Enumerated(EnumType.STRING)
    @Column(name = "ac")
    private Select ac;

    @Enumerated(EnumType.STRING)
    @Column(name = "heat")
    private Select heat;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public Office bathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
        return this;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Integer getFloors() {
        return floors;
    }

    public Office floors(Integer floors) {
        this.floors = floors;
        return this;
    }

    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    public Integer getTerrace() {
        return terrace;
    }

    public Office terrace(Integer terrace) {
        this.terrace = terrace;
        return this;
    }

    public void setTerrace(Integer terrace) {
        this.terrace = terrace;
    }

    public Select getOffice() {
        return office;
    }

    public Office office(Select office) {
        this.office = office;
        return this;
    }

    public void setOffice(Select office) {
        this.office = office;
    }

    public Select getStorage() {
        return storage;
    }

    public Office storage(Select storage) {
        this.storage = storage;
        return this;
    }

    public void setStorage(Select storage) {
        this.storage = storage;
    }

    public Integer getStorageSurface() {
        return storageSurface;
    }

    public Office storageSurface(Integer storageSurface) {
        this.storageSurface = storageSurface;
        return this;
    }

    public void setStorageSurface(Integer storageSurface) {
        this.storageSurface = storageSurface;
    }

    public Integer getOfficesSurface() {
        return officesSurface;
    }

    public Office officesSurface(Integer officesSurface) {
        this.officesSurface = officesSurface;
        return this;
    }

    public void setOfficesSurface(Integer officesSurface) {
        this.officesSurface = officesSurface;
    }

    public Select getAc() {
        return ac;
    }

    public Office ac(Select ac) {
        this.ac = ac;
        return this;
    }

    public void setAc(Select ac) {
        this.ac = ac;
    }

    public Select getHeat() {
        return heat;
    }

    public Office heat(Select heat) {
        this.heat = heat;
        return this;
    }

    public void setHeat(Select heat) {
        this.heat = heat;
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
        Office office = (Office) o;
        if (office.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), office.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Office{" +
            "id=" + getId() +
            ", bathrooms='" + getBathrooms() + "'" +
            ", floors=" + getFloors() +
            ", terrace=" + getTerrace() +
            ", office='" + getOffice() + "'" +
            ", storage='" + getStorage() + "'" +
            ", storageSurface=" + getStorageSurface() +
            ", officesSurface=" + getOfficesSurface() +
            ", ac='" + getAc() + "'" +
            ", heat='" + getHeat() + "'" +
            "}";
    }
}
