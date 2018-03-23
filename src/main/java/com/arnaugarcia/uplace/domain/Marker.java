package com.arnaugarcia.uplace.domain;

import com.arnaugarcia.uplace.domain.enumeration.TransactionType;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Marker implements Serializable {

    private String propertyReference;
    private Double priceSell;
    private Double priceRent;
    private Double priceTransfer;
    private TransactionType transactionType;
    private ZonedDateTime date;
    private Double latitude;
    private Double longitude;
    private String propertyType;

    public Marker() {
    }

    public Marker(String propertyReference, Double priceSell, Double priceRent, Double priceTransfer, TransactionType transactionType, ZonedDateTime date, Double latitude, Double longitude, String propertyType) {
        this.propertyReference = propertyReference;
        this.priceSell = priceSell;
        this.priceRent = priceRent;
        this.priceTransfer = priceTransfer;
        this.transactionType = transactionType;
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

    public Double getPriceSell() {
        return priceSell;
    }

    public void setPriceSell(Double priceSell) {
        this.priceSell = priceSell;
    }

    public Double getPriceRent() {
        return priceRent;
    }

    public void setPriceRent(Double priceRent) {
        this.priceRent = priceRent;
    }

    public Double getPriceTransfer() {
        return priceTransfer;
    }

    public void setPriceTransfer(Double priceTransfer) {
        this.priceTransfer = priceTransfer;
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "Marker{" +
            "propertyReference='" + propertyReference + '\'' +
            ", priceSell=" + priceSell +
            ", priceRent=" + priceRent +
            ", priceTransfer=" + priceTransfer +
            ", transactionType=" + transactionType +
            ", date=" + date +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", propertyType='" + propertyType + '\'' +
            '}';
    }
}
