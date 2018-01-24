package com.arnaugarcia.uplace.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Gallery entity.
 */
public class GalleryDTO implements Serializable {

    private Long id;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GalleryDTO galleryDTO = (GalleryDTO) o;
        if(galleryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), galleryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GalleryDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
