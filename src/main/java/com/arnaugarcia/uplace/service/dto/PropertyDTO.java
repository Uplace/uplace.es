package com.arnaugarcia.uplace.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

import com.arnaugarcia.uplace.domain.*;
import com.arnaugarcia.uplace.domain.enumeration.TransactionType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * A DTO for the Property entity.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "propertyType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Apartment.class, name = "Apartment"),
    @JsonSubTypes.Type(value = BuildingDTO.class, name = "Building"),
    @JsonSubTypes.Type(value = Business.class, name = "Business"),
    @JsonSubTypes.Type(value = Establishment.class, name = "Establishment"),
    @JsonSubTypes.Type(value = Hotel.class, name = "Hotel"),
    @JsonSubTypes.Type(value = IndustrialPlant.class, name = "IndustrialPlant"),
    @JsonSubTypes.Type(value = Office.class, name = "Office"),
    @JsonSubTypes.Type(value = Parking.class, name = "Parking"),
    @JsonSubTypes.Type(value = Terrain.class, name = "Terrain")
})
public class PropertyDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private Double price;

    @NotNull
    private ZonedDateTime created;

    private ZonedDateTime updated;

    @Lob
    private String description;

    @NotNull
    private TransactionType transaction;

    @NotNull
    private String propertyType;

    private String reference;

    private Double priceSell;

    private Double priceRent;

    @Min(value = 500)
    private Integer yearConstruction;

    private Boolean newCreation;

    private Boolean visible;

    private Integer surface;

    private Long locationId;

    private Set<AgentDTO> managers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionType transaction) {
        this.transaction = transaction;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    public Integer getYearConstruction() {
        return yearConstruction;
    }

    public void setYearConstruction(Integer yearConstruction) {
        this.yearConstruction = yearConstruction;
    }

    public Boolean getNewCreation() {
        return newCreation;
    }

    public void setNewCreation(Boolean newCreation) {
        this.newCreation = newCreation;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Integer getSurface() {
        return surface;
    }

    public void setSurface(Integer surface) {
        this.surface = surface;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Set<AgentDTO> getManagers() {
        return managers;
    }

    public void setManagers(Set<AgentDTO> managers) {
        this.managers = managers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PropertyDTO propertyDTO = (PropertyDTO) o;
        if(propertyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), propertyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PropertyDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", price=" + getPrice() +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", description='" + getDescription() + "'" +
            ", transaction='" + getTransaction() + "'" +
            ", reference='" + getReference() + "'" +
            ", priceSell=" + getPriceSell() +
            ", priceRent=" + getPriceRent() +
            ", yearConstruction=" + getYearConstruction() +
            ", newCreation='" + getNewCreation() + "'" +
            ", visible='" + getVisible() + "'" +
            ", surface=" + getSurface() +
            "}";
    }
}
