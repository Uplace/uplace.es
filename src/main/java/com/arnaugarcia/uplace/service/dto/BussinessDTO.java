package com.arnaugarcia.uplace.service.dto;


import java.io.Serializable;
import java.util.Objects;
import com.arnaugarcia.uplace.domain.enumeration.BussinessType;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;

/**
 * A DTO for the Bussiness entity.
 */
public class BussinessDTO implements Serializable {

    private Long id;

    private BussinessType bussinessType;

    private Integer numberBathrooms;

    private Select elevator;

    private Select ac;

    private Select heat;

    private Integer surfaceTerrace;

    private Integer surfaceGarden;

    private Integer numberOffice;

    private Integer surfaceSaloon;

    private Integer height;

    private Select pool;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BussinessType getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(BussinessType bussinessType) {
        this.bussinessType = bussinessType;
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

    public Integer getSurfaceGarden() {
        return surfaceGarden;
    }

    public void setSurfaceGarden(Integer surfaceGarden) {
        this.surfaceGarden = surfaceGarden;
    }

    public Integer getNumberOffice() {
        return numberOffice;
    }

    public void setNumberOffice(Integer numberOffice) {
        this.numberOffice = numberOffice;
    }

    public Integer getSurfaceSaloon() {
        return surfaceSaloon;
    }

    public void setSurfaceSaloon(Integer surfaceSaloon) {
        this.surfaceSaloon = surfaceSaloon;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Select getPool() {
        return pool;
    }

    public void setPool(Select pool) {
        this.pool = pool;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BussinessDTO bussinessDTO = (BussinessDTO) o;
        if(bussinessDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bussinessDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BussinessDTO{" +
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
