package com.arnaugarcia.uplace.service.mapper;

import com.arnaugarcia.uplace.domain.*;
import com.arnaugarcia.uplace.service.dto.PhotoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Photo and its DTO PhotoDTO.
 */
@Mapper(componentModel = "spring", uses = {PropertyMapper.class})
public interface PhotoMapper extends EntityMapper<PhotoDTO, Photo> {

    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "property.reference", target = "propertyReference")
    PhotoDTO toDto(Photo photo);

    @Mapping(source = "propertyId", target = "property")
    Photo toEntity(PhotoDTO photoDTO);

    default Photo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Photo photo = new Photo();
        photo.setId(id);
        return photo;
    }
}
