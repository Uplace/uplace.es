package com.arnaugarcia.uplace.service.dto;
import com.arnaugarcia.uplace.domain.Agent;
import com.arnaugarcia.uplace.domain.Location;
import com.arnaugarcia.uplace.domain.Photo;
import com.arnaugarcia.uplace.domain.Property;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.arnaugarcia.uplace.domain.enumeration.TransactionType;

/**
 * A Property.
 */
public class PropertyDTO implements Serializable {

    private Long id;

    private String title;

    private Double price;

    private ZonedDateTime created;

    private ZonedDateTime updated;

    private String description;

    private TransactionType transaction;

    private String propertyType;

    private String reference;

    private Double priceSell;

    private Double priceRent;

    private Integer yearConstruction;

    private Boolean newCreation;

    private Boolean visible;

    private Integer surface;

    private Location location;

    private Set<Photo> photos = new HashSet<>();

    private Set<Agent> managers = new HashSet<>();

    public PropertyDTO(Long id, String title, Double price, ZonedDateTime created, ZonedDateTime updated, String description, TransactionType transaction, String propertyType, String reference, Double priceSell, Double priceRent, Integer yearConstruction, Boolean newCreation, Boolean visible, Integer surface, Location location, Set<Photo> photos, Set<Agent> managers) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.transaction = transaction;
        this.propertyType = propertyType;
        this.reference = reference;
        this.priceSell = priceSell;
        this.priceRent = priceRent;
        this.yearConstruction = yearConstruction;
        this.newCreation = newCreation;
        this.visible = visible;
        this.surface = surface;
        this.location = location;
        this.photos = photos;
        this.managers = managers;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public Boolean getNewCreation() {
        return newCreation;
    }

    public Boolean getVisible() {
        return visible;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public PropertyDTO title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public PropertyDTO price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public PropertyDTO created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public PropertyDTO updated(ZonedDateTime updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public PropertyDTO description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getTransaction() {
        return transaction;
    }

    public PropertyDTO transaction(TransactionType transaction) {
        this.transaction = transaction;
        return this;
    }

    public void setTransaction(TransactionType transaction) {
        this.transaction = transaction;
    }

    public String getReference() {
        return reference;
    }

    public PropertyDTO reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Double getPriceSell() {
        return priceSell;
    }

    public PropertyDTO priceSell(Double priceSell) {
        this.priceSell = priceSell;
        return this;
    }

    public void setPriceSell(Double priceSell) {
        this.priceSell = priceSell;
    }

    public Double getPriceRent() {
        return priceRent;
    }

    public PropertyDTO priceRent(Double priceRent) {
        this.priceRent = priceRent;
        return this;
    }

    public void setPriceRent(Double priceRent) {
        this.priceRent = priceRent;
    }

    public Integer getYearConstruction() {
        return yearConstruction;
    }

    public PropertyDTO yearConstruction(Integer yearConstruction) {
        this.yearConstruction = yearConstruction;
        return this;
    }

    public void setYearConstruction(Integer yearConstruction) {
        this.yearConstruction = yearConstruction;
    }

    public Boolean isNewCreation() {
        return newCreation;
    }

    public PropertyDTO newCreation(Boolean newCreation) {
        this.newCreation = newCreation;
        return this;
    }

    public void setNewCreation(Boolean newCreation) {
        this.newCreation = newCreation;
    }

    public Boolean isVisible() {
        return visible;
    }

    public PropertyDTO visible(Boolean visible) {
        this.visible = visible;
        return this;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Integer getSurface() {
        return surface;
    }

    public PropertyDTO surface(Integer surface) {
        this.surface = surface;
        return this;
    }

    public void setSurface(Integer surface) {
        this.surface = surface;
    }

    public Location getLocation() {
        return location;
    }

    public PropertyDTO location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public PropertyDTO photos(Set<Photo> photos) {
        this.photos = photos;
        return this;
    }

    public Set<Agent> getManagers() {
        return managers;
    }

    public PropertyDTO managers(Set<Agent> agents) {
        this.managers = agents;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Property property = (Property) o;
        if (property.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), property.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Property{" +
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
            ", newCreation='" + isNewCreation() + "'" +
            ", visible='" + isVisible() + "'" +
            ", surface=" + getSurface() +
            "}";
    }
}
