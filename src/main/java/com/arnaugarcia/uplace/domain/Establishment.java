package com.arnaugarcia.uplace.domain;

import com.arnaugarcia.uplace.repository.PhotoRepository;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.arnaugarcia.uplace.domain.enumeration.Select;

import com.arnaugarcia.uplace.domain.enumeration.UseEstablishment;

import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;

/**
 * A Establishment.
 */
@Entity
@DiscriminatorValue("Establishment")
public class Establishment extends Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "m_2_facade")
    private Integer m2Facade;

    @Enumerated(EnumType.STRING)
    @Column(name = "bathroom")
    private Select bathroom;

    @Enumerated(EnumType.STRING)
    @Column(name = "up_use")
    private UseEstablishment use;

    @Enumerated(EnumType.STRING)
    @Column(name = "energy_certificate")
    private EnergyCertificate energyCertificate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getm2Facade() {
        return m2Facade;
    }

    public Establishment m2Facade(Integer m2Facade) {
        this.m2Facade = m2Facade;
        return this;
    }

    public void setm2Facade(Integer m2Facade) {
        this.m2Facade = m2Facade;
    }

    public Select getBathroom() {
        return bathroom;
    }

    public Establishment bathroom(Select bathroom) {
        this.bathroom = bathroom;
        return this;
    }

    public void setBathroom(Select bathroom) {
        this.bathroom = bathroom;
    }

    public UseEstablishment getUse() {
        return use;
    }

    public Establishment use(UseEstablishment use) {
        this.use = use;
        return this;
    }

    public void setUse(UseEstablishment use) {
        this.use = use;
    }

    public EnergyCertificate getEnergyCertificate() {
        return energyCertificate;
    }

    public Establishment energyCertificate(EnergyCertificate energyCertificate) {
        this.energyCertificate = energyCertificate;
        return this;
    }

    public void setEnergyCertificate(EnergyCertificate energyCertificate) {
        this.energyCertificate = energyCertificate;
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
        Establishment establishment = (Establishment) o;
        if (establishment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), establishment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Establishment{" +
            "id=" + getId() +
            ", m2Facade=" + getm2Facade() +
            ", bathroom='" + getBathroom() + "'" +
            ", use='" + getUse() + "'" +
            ", energyCertificate='" + getEnergyCertificate() + "'" +
            "}";
    }
}
