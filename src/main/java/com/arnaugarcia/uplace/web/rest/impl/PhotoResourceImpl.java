package com.arnaugarcia.uplace.web.rest.impl;

import com.arnaugarcia.uplace.domain.Photo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

public interface PhotoResourceImpl {

    public Set<Photo> getPhotosOfFlat(@PathVariable String reference);

    public Set<Photo> deletePhotoFlat(@PathVariable String reference, @PathVariable Long id);

    public Set<Photo> updatePhotosFlat(@PathVariable String reference, @RequestBody Photo photo);
}
