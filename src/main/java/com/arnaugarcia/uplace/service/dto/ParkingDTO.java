package com.arnaugarcia.uplace.service.dto;


import java.io.Serializable;
import java.util.Objects;
import com.arnaugarcia.uplace.domain.enumeration.ParkingType;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;

/**
 * A DTO for the Parking entity.
 */
public class ParkingDTO implements Serializable {

    private Long id;

    private ParkingType parkingType;

    private Select nearTransport;

    private Select surveillance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParkingType getParkingType() {
        return parkingType;
    }

    public void setParkingType(ParkingType parkingType) {
        this.parkingType = parkingType;
    }

    public Select getNearTransport() {
        return nearTransport;
    }

    public void setNearTransport(Select nearTransport) {
        this.nearTransport = nearTransport;
    }

    public Select getSurveillance() {
        return surveillance;
    }

    public void setSurveillance(Select surveillance) {
        this.surveillance = surveillance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParkingDTO parkingDTO = (ParkingDTO) o;
        if(parkingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), parkingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParkingDTO{" +
            "id=" + getId() +
            ", parkingType='" + getParkingType() + "'" +
            ", nearTransport='" + getNearTransport() + "'" +
            ", surveillance='" + getSurveillance() + "'" +
            "}";
    }
}
