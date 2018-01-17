package com.arnaugarcia.uplace.service.dto;


import java.io.Serializable;
import java.util.Objects;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.ApartmentType;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;

/**
 * A DTO for the Apartment entity.
 */
public class ApartmentDTO implements Serializable {

    private Long id;

    private Integer numberBedrooms;

    private Integer numberBathrooms;

    private Select elevator;

    private Select ac;

    private Select heat;

    private Integer surfaceTerrace;

    private Integer surfaceSaloon;

    private ApartmentType propertyType;

    private Select office;

    private Select kitchenOffice;

    private Select storage;

    private Select sharedPool;

    private Select nearTransport;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberBedrooms() {
        return numberBedrooms;
    }

    public void setNumberBedrooms(Integer numberBedrooms) {
        this.numberBedrooms = numberBedrooms;
    }

    public Integer getNumberBathrooms() {
        return numberBathrooms;
    }

    public void setNumberBathrooms(Integer numberBathrooms) {
        this.numberBathrooms = numberBathrooms;
    }

    public Select getElevator() {
        return elevator;
    }

    public void setElevator(Select elevator) {
        this.elevator = elevator;
    }

    public Select getAc() {
        return ac;
    }

    public void setAc(Select ac) {
        this.ac = ac;
    }

    public Select getHeat() {
        return heat;
    }

    public void setHeat(Select heat) {
        this.heat = heat;
    }

    public Integer getSurfaceTerrace() {
        return surfaceTerrace;
    }

    public void setSurfaceTerrace(Integer surfaceTerrace) {
        this.surfaceTerrace = surfaceTerrace;
    }

    public Integer getSurfaceSaloon() {
        return surfaceSaloon;
    }

    public void setSurfaceSaloon(Integer surfaceSaloon) {
        this.surfaceSaloon = surfaceSaloon;
    }

    public ApartmentType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(ApartmentType propertyType) {
        this.propertyType = propertyType;
    }

    public Select getOffice() {
        return office;
    }

    public void setOffice(Select office) {
        this.office = office;
    }

    public Select getKitchenOffice() {
        return kitchenOffice;
    }

    public void setKitchenOffice(Select kitchenOffice) {
        this.kitchenOffice = kitchenOffice;
    }

    public Select getStorage() {
        return storage;
    }

    public void setStorage(Select storage) {
        this.storage = storage;
    }

    public Select getSharedPool() {
        return sharedPool;
    }

    public void setSharedPool(Select sharedPool) {
        this.sharedPool = sharedPool;
    }

    public Select getNearTransport() {
        return nearTransport;
    }

    public void setNearTransport(Select nearTransport) {
        this.nearTransport = nearTransport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApartmentDTO apartmentDTO = (ApartmentDTO) o;
        if(apartmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), apartmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApartmentDTO{" +
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
