package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Photo;
import com.arnaugarcia.uplace.security.exceptions.CDNException;

public interface CDNService {

    String uploadImage(String imageId, byte[] imageData, String folder) throws CDNException;
    String uploadImage(Photo photo) throws CDNException;
    void deleteImage(String imageId, String folder) throws CDNException;
    void deleteImage(Photo photo) throws CDNException;
}
