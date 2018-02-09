package com.arnaugarcia.uplace.service.dto;

import java.io.Serializable;

public class MarkerDTO implements Serializable {

    private String propertyReference;
    private String latitude;
    private String longitude;

    public MarkerDTO(String propertyReference, String latitude, String longitude) {
        this.propertyReference = propertyReference;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPropertyReference() {
        return propertyReference;
    }

    public void setPropertyReference(String propertyReference) {
        this.propertyReference = propertyReference;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "MarkerDTO{" +
            "propertyReference='" + propertyReference + '\'' +
            ", latitude='" + latitude + '\'' +
            ", longitude='" + longitude + '\'' +
            '}';
    }
}
