package com.arnaugarcia.uplace.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Location entity.
 */
public class LocationDTO implements Serializable {

    private Long id;

    private Double latitude;

    private String postalCode;

    private Double longitude;

    private String city;

    private String fullAddress;

    private Boolean hide;

    private String urlGMaps;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public Boolean isHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public String getUrlGMaps() {
        return urlGMaps;
    }

    public void setUrlGMaps(String urlGMaps) {
        this.urlGMaps = urlGMaps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocationDTO locationDTO = (LocationDTO) o;
        if(locationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), locationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LocationDTO{" +
            "id=" + getId() +
            ", latitude=" + getLatitude() +
            ", postalCode='" + getPostalCode() + "'" +
            ", longitude=" + getLongitude() +
            ", city='" + getCity() + "'" +
            ", fullAddress='" + getFullAddress() + "'" +
            ", hide='" + isHide() + "'" +
            ", urlGMaps='" + getUrlGMaps() + "'" +
            "}";
    }
}
