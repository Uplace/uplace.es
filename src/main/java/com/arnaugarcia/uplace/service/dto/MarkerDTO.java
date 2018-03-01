package com.arnaugarcia.uplace.service.dto;

import java.io.Serializable;

public class MarkerDTO implements Serializable {

    private String propertyReference;
    private Double latitude;
    private Double longitude;
    private String propertyType;

    public MarkerDTO(String propertyReference, Double latitude, Double longitude, String propertyType) {
        this.propertyReference = propertyReference;
        this.latitude = latitude;
        this.longitude = longitude;
        this.propertyType = propertyType;
    }

    public String getPropertyReference() {
        return propertyReference;
    }

    public void setPropertyReference(String propertyReference) {
        this.propertyReference = propertyReference;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    @Override
    public String toString() {
        return "MarkerDTO{" +
            "propertyReference='" + propertyReference + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", propertyType='" + propertyType + '\'' +
            '}';
    }
}
