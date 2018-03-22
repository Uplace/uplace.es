package com.arnaugarcia.uplace.service.dto;

import com.arnaugarcia.uplace.domain.Marker;
import com.arnaugarcia.uplace.domain.Photo;

import java.time.ZonedDateTime;

public class MarkerDTO {

    private String propertyReference;
    private Double price;
    private Photo photo;
    private Boolean isNew;
    private ZonedDateTime date;
    private Double latitude;
    private Double longitude;
    private String propertyType;

    public MarkerDTO() {
    }

    public MarkerDTO(String propertyReference, Double price, Photo photo, Boolean isNew, ZonedDateTime date, Double latitude, Double longitude, String propertyType) {
        this.propertyReference = propertyReference;
        this.price = price;
        this.photo = photo;
        this.isNew = isNew;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.propertyType = propertyType;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
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
