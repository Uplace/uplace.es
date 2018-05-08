package com.arnaugarcia.uplace.domain;

import com.arnaugarcia.uplace.service.listener.PhotoListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * PHOTO
 */
@ApiModel(description = "PHOTO")
@Entity
@Table(name = "photo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@EntityListeners(PhotoListener.class)
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "photo_url")
    @URL(protocol = "https")
    private String photoUrl;

    @Column(name = "photo_public_id")
    private String publicId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private byte[] photo;

    @NotNull
    @Column(name = "thumbnail", nullable = false)
    private Boolean thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Property property;

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

    public Photo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Photo description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Boolean getThumbnail() {
        return thumbnail;
    }

    public Boolean isThumbnail() {
        return thumbnail;
    }

    public Photo thumbnail(Boolean thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public void setThumbnail(Boolean thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Property getProperty() {
        return property;
    }

    public Photo property(Property property) {
        this.property = property;
        return this;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Photo)) return false;
        Photo photo1 = (Photo) o;
        return Objects.equals(id, photo1.id) &&
            Objects.equals(name, photo1.name) &&
            Objects.equals(description, photo1.description) &&
            Objects.equals(photoUrl, photo1.photoUrl) &&
            Objects.equals(publicId, photo1.publicId) &&
            Arrays.equals(photo, photo1.photo) &&
            Objects.equals(thumbnail, photo1.thumbnail) &&
            Objects.equals(property, photo1.property);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Photo{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", photoUrl='" + photoUrl + '\'' +
            ", publicId='" + publicId + '\'' +
            ", photo=" + Arrays.toString(photo) +
            ", thumbnail=" + thumbnail +
            ", property=" + property +
            '}';
    }
}
