package com.arnaugarcia.uplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Property.
 */
@Table(name = "property")
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn()
public abstract class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @Column(name = "created", nullable = false)
    private ZonedDateTime created;

    @Column(name = "updated")
    private ZonedDateTime updated;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "reference")
    private String reference;

    @Column(name = "price_sell")
    private Double priceSell;

    @Column(name = "price_rent")
    private Double priceRent;

    @Min(value = 500)
    @Column(name = "year_construction")
    private Integer yearConstruction;

    @Column(name = "new_creation")
    private Boolean newCreation;

    @Column(name = "visible")
    private Boolean visible;

    @Column(name = "surface")
    private Integer surface;

    @OneToOne
    @JoinColumn(unique = true)
    private Gallery gallery;

    @OneToMany(mappedBy = "property")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Agent> manageds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Property title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public Property price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Property created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public Property updated(ZonedDateTime updated) {
        this.updated = updated;
        return this;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public Property description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReference() {
        return reference;
    }

    public Property reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Double getPriceSell() {
        return priceSell;
    }

    public Property priceSell(Double priceSell) {
        this.priceSell = priceSell;
        return this;
    }

    public void setPriceSell(Double priceSell) {
        this.priceSell = priceSell;
    }

    public Double getPriceRent() {
        return priceRent;
    }

    public Property priceRent(Double priceRent) {
        this.priceRent = priceRent;
        return this;
    }

    public void setPriceRent(Double priceRent) {
        this.priceRent = priceRent;
    }

    public Integer getYearConstruction() {
        return yearConstruction;
    }

    public Property yearConstruction(Integer yearConstruction) {
        this.yearConstruction = yearConstruction;
        return this;
    }

    public void setYearConstruction(Integer yearConstruction) {
        this.yearConstruction = yearConstruction;
    }

    public Boolean isNewCreation() {
        return newCreation;
    }

    public Property newCreation(Boolean newCreation) {
        this.newCreation = newCreation;
        return this;
    }

    public void setNewCreation(Boolean newCreation) {
        this.newCreation = newCreation;
    }

    public Boolean isVisible() {
        return visible;
    }

    public Property visible(Boolean visible) {
        this.visible = visible;
        return this;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Integer getSurface() {
        return surface;
    }

    public Property surface(Integer surface) {
        this.surface = surface;
        return this;
    }

    public void setSurface(Integer surface) {
        this.surface = surface;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public Property gallery(Gallery gallery) {
        this.gallery = gallery;
        return this;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public Set<Agent> getManageds() {
        return manageds;
    }

    public Property manageds(Set<Agent> agents) {
        this.manageds = agents;
        return this;
    }

    public Property addManaged(Agent agent) {
        this.manageds.add(agent);
        agent.setProperty(this);
        return this;
    }

    public Property removeManaged(Agent agent) {
        this.manageds.remove(agent);
        agent.setProperty(null);
        return this;
    }

    public void setManageds(Set<Agent> agents) {
        this.manageds = agents;
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
