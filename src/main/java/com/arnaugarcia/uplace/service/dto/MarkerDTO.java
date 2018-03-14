package com.arnaugarcia.uplace.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class MarkerDTO implements Serializable {

    private String propertyReference;
    private Double price;
    private Boolean isNew;
    private ZonedDateTime date;
    private Double latitude;
    private Double longitude;
    private String propertyType;

    public MarkerDTO(String propertyReference, Double price, ZonedDateTime date, Double latitude, Double longitude, String propertyType) {
        this.propertyReference = propertyReference;
        this.price = price;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.propertyType = propertyType;
    }

    public MarkerDTO(String propertyReference, Double price, Boolean isNew, ZonedDateTime date, Double latitude, Double longitude, String propertyType) {
        this.propertyReference = propertyReference;
        this.price = price;
        this.isNew = isNew;
        this.date = date;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
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
            ", price=" + price +
            ", isNew=" + isNew +
            ", date=" + date +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", propertyType='" + propertyType + '\'' +
            '}';
    }
}
