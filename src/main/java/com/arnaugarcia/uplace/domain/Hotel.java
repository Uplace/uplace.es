package com.arnaugarcia.uplace.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.arnaugarcia.uplace.domain.enumeration.Select;

import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;

/**
 * A Hotel.
 */
@Entity
@DiscriminatorValue("Hotel")
@ApiModel(value = "Hotel")
public class Hotel extends Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "solar_surface")
    private Integer solarSurface;

    @Column(name = "m_2_edified")
    private Integer m2Edified;

    @Column(name = "number_rooms")
    private Integer numberRooms;

    @Enumerated(EnumType.STRING)
    @Column(name = "operator")
    private Select operator;

    @Enumerated(EnumType.STRING)
    @Column(name = "pool")
    private Select pool;

    @Enumerated(EnumType.STRING)
    @Column(name = "spa")
    private Select spa;

    @Enumerated(EnumType.STRING)
    @Column(name = "conference_room")
    private Select conferenceRoom;

    @Column(name = "floors_sr")
    private Integer floorsSR;

    @Column(name = "floors_br")
    private Integer floorsBR;

    @Enumerated(EnumType.STRING)
    @Column(name = "energy_certificate")
    private EnergyCertificate energyCertificate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSolarSurface() {
        return solarSurface;
    }

    public Hotel solarSurface(Integer solarSurface) {
        this.solarSurface = solarSurface;
        return this;
    }

    public void setSolarSurface(Integer solarSurface) {
        this.solarSurface = solarSurface;
    }

    public Integer getm2Edified() {
        return m2Edified;
    }

    public Hotel m2Edified(Integer m2Edified) {
        this.m2Edified = m2Edified;
        return this;
    }

    public void setm2Edified(Integer m2Edified) {
        this.m2Edified = m2Edified;
    }

    public Integer getNumberRooms() {
        return numberRooms;
    }

    public Hotel numberRooms(Integer numberRooms) {
        this.numberRooms = numberRooms;
        return this;
    }

    public void setNumberRooms(Integer numberRooms) {
        this.numberRooms = numberRooms;
    }

    public Select getOperator() {
        return operator;
    }

    public Hotel operator(Select operator) {
        this.operator = operator;
        return this;
    }

    public void setOperator(Select operator) {
        this.operator = operator;
    }

    public Select getPool() {
        return pool;
    }

    public Hotel pool(Select pool) {
        this.pool = pool;
        return this;
    }

    public void setPool(Select pool) {
        this.pool = pool;
    }

    public Select getSpa() {
        return spa;
    }

    public Hotel spa(Select spa) {
        this.spa = spa;
        return this;
    }

    public void setSpa(Select spa) {
        this.spa = spa;
    }

    public Select getConferenceRoom() {
        return conferenceRoom;
    }

    public Hotel conferenceRoom(Select conferenceRoom) {
        this.conferenceRoom = conferenceRoom;
        return this;
    }

    public void setConferenceRoom(Select conferenceRoom) {
        this.conferenceRoom = conferenceRoom;
    }

    public Integer getFloorsSR() {
        return floorsSR;
    }

    public Hotel floorsSR(Integer floorsSR) {
        this.floorsSR = floorsSR;
        return this;
    }

    public void setFloorsSR(Integer floorsSR) {
        this.floorsSR = floorsSR;
    }

    public Integer getFloorsBR() {
        return floorsBR;
    }

    public Hotel floorsBR(Integer floorsBR) {
        this.floorsBR = floorsBR;
        return this;
    }

    public void setFloorsBR(Integer floorsBR) {
        this.floorsBR = floorsBR;
    }

    public EnergyCertificate getEnergyCertificate() {
        return energyCertificate;
    }

    public Hotel energyCertificate(EnergyCertificate energyCertificate) {
        this.energyCertificate = energyCertificate;
        return this;
    }

    public void setEnergyCertificate(EnergyCertificate energyCertificate) {
        this.energyCertificate = energyCertificate;
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
        Hotel hotel = (Hotel) o;
        if (hotel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hotel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Hotel{" +
            "id=" + getId() +
            ", solarSurface=" + getSolarSurface() +
            ", m2Edified=" + getm2Edified() +
            ", numberRooms=" + getNumberRooms() +
            ", operator='" + getOperator() + "'" +
            ", pool='" + getPool() + "'" +
            ", spa='" + getSpa() + "'" +
            ", conferenceRoom='" + getConferenceRoom() + "'" +
            ", floorsSR=" + getFloorsSR() +
            ", floorsBR=" + getFloorsBR() +
            ", energyCertificate='" + getEnergyCertificate() + "'" +
            "}";
    }
}
