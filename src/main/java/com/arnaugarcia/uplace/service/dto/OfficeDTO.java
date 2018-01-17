package com.arnaugarcia.uplace.service.dto;


import java.io.Serializable;
import java.util.Objects;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;

/**
 * A DTO for the Office entity.
 */
public class OfficeDTO implements Serializable {

    private Long id;

    private String bathrooms;

    private Integer floors;

    private Integer terrace;

    private Select office;

    private Select storage;

    private Integer storageSurface;

    private Integer officesSurface;

    private Select ac;

    private Select heat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    public Integer getTerrace() {
        return terrace;
    }

    public void setTerrace(Integer terrace) {
        this.terrace = terrace;
    }

    public Select getOffice() {
        return office;
    }

    public void setOffice(Select office) {
        this.office = office;
    }

    public Select getStorage() {
        return storage;
    }

    public void setStorage(Select storage) {
        this.storage = storage;
    }

    public Integer getStorageSurface() {
        return storageSurface;
    }

    public void setStorageSurface(Integer storageSurface) {
        this.storageSurface = storageSurface;
    }

    public Integer getOfficesSurface() {
        return officesSurface;
    }

    public void setOfficesSurface(Integer officesSurface) {
        this.officesSurface = officesSurface;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OfficeDTO officeDTO = (OfficeDTO) o;
        if(officeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), officeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OfficeDTO{" +
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
