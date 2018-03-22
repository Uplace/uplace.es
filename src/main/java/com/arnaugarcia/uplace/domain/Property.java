package com.arnaugarcia.uplace.domain;

import com.arnaugarcia.uplace.domain.enumeration.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Property.
 */
@Entity
@Table(name = "property")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "propertyType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Apartment.class, name = "Apartment"),
    @JsonSubTypes.Type(value = Building.class, name = "Building"),
    @JsonSubTypes.Type(value = Business.class, name = "Business"),
    @JsonSubTypes.Type(value = Establishment.class, name = "Establishment"),
    @JsonSubTypes.Type(value = Hotel.class, name = "Hotel"),
    @JsonSubTypes.Type(value = IndustrialPlant.class, name = "IndustrialPlant"),
    @JsonSubTypes.Type(value = Office.class, name = "Office"),
    @JsonSubTypes.Type(value = Parking.class, name = "Parking"),
    @JsonSubTypes.Type(value = Terrain.class, name = "Terrain")
})
@NamedEntityGraphs({
    @NamedEntityGraph(name = "graph.PropertyAll", attributeNodes = {
        @NamedAttributeNode("location"),
        @NamedAttributeNode(value = "managers", subgraph = "graph.AgentUser"),
        @NamedAttributeNode("photos"),
        @NamedAttributeNode("realEstate")

    },
    subgraphs = {
        @NamedSubgraph(name = "graph.AgentUser", attributeNodes = {
            @NamedAttributeNode("user")
        })
    }),
    @NamedEntityGraph(name = "graph.PropertyLocationPhotos", attributeNodes = {
        @NamedAttributeNode("location"),
        @NamedAttributeNode("photos")
    })
})
// TODO: Make Property abstract
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "created", nullable = false)
    private ZonedDateTime created;

    @Column(name = "updated")
    private ZonedDateTime updated;

    @Column(name = "dtype", insertable = false, updatable = false, nullable = false)
    private String propertyType;

    @Lob
    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "up_transaction", nullable = false)
    private TransactionType transaction;

    @Column(name = "reference", nullable = false)
    private String reference;

    @Column(name = "price_transfer")
    private Double priceTransfer;

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

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true, nullable = false)
    private Location location;

    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Photo> photos = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "property_manager",
               joinColumns = @JoinColumn(name="properties_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="managers_id", referencedColumnName="id"))
    private Set<Agent> managers = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private RealEstate realEstate;

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

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public Double getPriceTransfer() {
        return priceTransfer;
    }

    public void setPriceTransfer(Double priceTransfer) {
        this.priceTransfer = priceTransfer;
    }

    public Boolean getNewCreation() {
        return newCreation;
    }

    public Boolean getVisible() {
        return visible;
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

    public String getPropertyType() {
        return propertyType;
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

    public TransactionType getTransaction() {
        return transaction;
    }

    public Property transaction(TransactionType transaction) {
        this.transaction = transaction;
        return this;
    }

    public void setTransaction(TransactionType transaction) {
        this.transaction = transaction;
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

    public Location getLocation() {
        return location;
    }

    public Property location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public Property photos(Set<Photo> photos) {
        this.photos = photos;
        return this;
    }

    public Property addPhoto(Photo photo) {
        this.photos.add(photo);
        photo.setProperty(this);
        return this;
    }

    public Property removePhoto(Photo photo) {
        this.photos.remove(photo);
        photo.setProperty(null);
        return this;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public Set<Agent> getManagers() {
        return managers;
    }

    public Property managers(Set<Agent> agents) {
        this.managers = agents;
        return this;
    }

    public Property addManager(Agent agent) {
        this.managers.add(agent);
        agent.getProperties().add(this);
        return this;
    }

    public Property removeManager(Agent agent) {
        this.managers.remove(agent);
        agent.getProperties().remove(this);
        return this;
    }

    public void setManagers(Set<Agent> agents) {
        this.managers = agents;
    }

    public RealEstate getRealEstate() {
        return realEstate;
    }

    public Property realEstate(RealEstate realEstate) {
        this.realEstate = realEstate;
        return this;
    }

    public void setRealEstate(RealEstate realEstate) {
        this.realEstate = realEstate;
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
            "id=" + id +
            ", title='" + title + '\'' +
            ", created=" + created +
            ", updated=" + updated +
            ", propertyType='" + propertyType + '\'' +
            ", description='" + description + '\'' +
            ", transaction=" + transaction +
            ", reference='" + reference + '\'' +
            ", priceTransfer=" + priceTransfer +
            ", priceSell=" + priceSell +
            ", priceRent=" + priceRent +
            ", yearConstruction=" + yearConstruction +
            ", newCreation=" + newCreation +
            ", visible=" + visible +
            ", surface=" + surface +
            ", location=" + location +
            ", photos=" + photos +
            ", managers=" + managers +
            ", realEstate=" + realEstate +
            '}';
    }
}
