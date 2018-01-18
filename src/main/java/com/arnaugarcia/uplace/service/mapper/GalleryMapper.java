package com.arnaugarcia.uplace.service.mapper;

import com.arnaugarcia.uplace.domain.*;
import com.arnaugarcia.uplace.service.dto.GalleryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Gallery and its DTO GalleryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GalleryMapper extends EntityMapper<GalleryDTO, Gallery> {


    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "property", ignore = true)
    Gallery toEntity(GalleryDTO galleryDTO);

    default Gallery fromId(Long id) {
        if (id == null) {
            return null;
        }
        Gallery gallery = new Gallery();
        gallery.setId(id);
        return gallery;
    }
}
