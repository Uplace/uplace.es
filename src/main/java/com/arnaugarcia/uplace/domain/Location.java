package com.arnaugarcia.uplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "full_address")
    private String fullAddress;

    @Column(name = "hide")
    private Boolean hide;

    @Column(name = "url_g_maps")
    private String urlGMaps;

    @OneToOne(mappedBy = "location")
    @JsonIgnore
    private Property property;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public Location latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Location postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLongitude() {
        return longitude;
    }

    public Location longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public Location fullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
        return this;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public Boolean isHide() {
        return hide;
    }

    public Location hide(Boolean hide) {
        this.hide = hide;
        return this;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public String getUrlGMaps() {
        return urlGMaps;
    }

    public Location urlGMaps(String urlGMaps) {
        this.urlGMaps = urlGMaps;
        return this;
    }

    public void setUrlGMaps(String urlGMaps) {
        this.urlGMaps = urlGMaps;
    }

    public Property getProperty() {
        return property;
    }

    public Location property(Property property) {
        this.property = property;
        return this;
    }

    public void setProperty(Property property) {
        this.property = property;
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
        Location location = (Location) o;
        if (location.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), location.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", latitude='" + getLatitude() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", fullAddress='" + getFullAddress() + "'" +
            ", hide='" + isHide() + "'" +
            ", urlGMaps='" + getUrlGMaps() + "'" +
            "}";
    }
}
