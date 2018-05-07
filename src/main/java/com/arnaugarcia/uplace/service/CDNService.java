package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Photo;
import com.arnaugarcia.uplace.security.exceptions.CDNException;

import java.util.Map;

public interface CDNService {

    String uploadImage(String imageId, byte[] imageData, String folder) throws CDNException;
    Map uploadImage(Photo photo) throws CDNException;
    void deleteImage(String imageId, String folder) throws CDNException;
    void deleteImage(Photo photo) throws CDNException;
}
