package com.arnaugarcia.uplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RealEstate.
 */
@Entity
@Table(name = "real_estate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RealEstate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nif")
    private String nif;

    @Column(name = "reference")
    private String reference;

    @OneToMany(mappedBy = "realEstate")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Property> properties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public RealEstate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNif() {
        return nif;
    }

    public RealEstate nif(String nif) {
        this.nif = nif;
        return this;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getReference() {
        return reference;
    }

    public RealEstate reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public RealEstate properties(Set<Property> properties) {
        this.properties = properties;
        return this;
    }

    public RealEstate addProperty(Property property) {
        this.properties.add(property);
        property.setRealEstate(this);
        return this;
    }

    public RealEstate removeProperty(Property property) {
        this.properties.remove(property);
        property.setRealEstate(null);
        return this;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
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
        RealEstate realEstate = (RealEstate) o;
        if (realEstate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), realEstate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RealEstate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nif='" + getNif() + "'" +
            ", reference='" + getReference() + "'" +
            "}";
    }
}
