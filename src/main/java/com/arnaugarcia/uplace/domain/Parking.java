package com.arnaugarcia.uplace.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.arnaugarcia.uplace.domain.enumeration.ParkingType;

import com.arnaugarcia.uplace.domain.enumeration.Select;

/**
 * A Parking.
 */
@Entity
@Table(name = "parking")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Parking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "parking_type")
    private ParkingType parkingType;

    @Enumerated(EnumType.STRING)
    @Column(name = "near_transport")
    private Select nearTransport;

    @Enumerated(EnumType.STRING)
    @Column(name = "surveillance")
    private Select surveillance;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParkingType getParkingType() {
        return parkingType;
    }

    public Parking parkingType(ParkingType parkingType) {
        this.parkingType = parkingType;
        return this;
    }

    public void setParkingType(ParkingType parkingType) {
        this.parkingType = parkingType;
    }

    public Select getNearTransport() {
        return nearTransport;
    }

    public Parking nearTransport(Select nearTransport) {
        this.nearTransport = nearTransport;
        return this;
    }

    public void setNearTransport(Select nearTransport) {
        this.nearTransport = nearTransport;
    }

    public Select getSurveillance() {
        return surveillance;
    }

    public Parking surveillance(Select surveillance) {
        this.surveillance = surveillance;
        return this;
    }

    public void setSurveillance(Select surveillance) {
        this.surveillance = surveillance;
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
        Parking parking = (Parking) o;
        if (parking.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), parking.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Parking{" +
            "id=" + getId() +
            ", parkingType='" + getParkingType() + "'" +
            ", nearTransport='" + getNearTransport() + "'" +
            ", surveillance='" + getSurveillance() + "'" +
            "}";
    }
}
