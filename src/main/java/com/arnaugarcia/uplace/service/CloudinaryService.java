package com.arnaugarcia.uplace.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.arnaugarcia.uplace.domain.Photo;
import com.arnaugarcia.uplace.security.exceptions.CDNException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import javax.validation.Valid;

@Service
public class CloudinaryService implements CDNService {

    private final Logger log = LoggerFactory.getLogger(CloudinaryService.class);

    @Autowired Cloudinary cloudinary;

    @Override
    public String uploadImage(String imageId, byte[] imageData, String folder) throws CDNException {
        Map params = ObjectUtils.asMap("public_id", folder + "/" + imageId);
        File file = new File("uplace_" + imageId);
        try {
            FileUtils.writeByteArrayToFile(file, imageData);
            Map imgUpload = cloudinary.uploader().upload(file, params);
            log.debug("Photo uploaded to cloudinary, folder {}, url {}", folder,  imgUpload.get("secure_url"));
            return imgUpload.get("secure_url").toString();
        } catch (IOException e) {
            log.error("Image could not be uploaded to CDN", e);
            throw new CDNException(e);
        } finally {
            if (file.exists() || file.exists()) file.delete();
        }
    }

    @Override
    public Map uploadImage(Photo photo) {
        String photoId = cloudinary.randomPublicId();
        if (photo.getProperty() == null) {
            throw new CDNException("The image doesn't have a property");
        }
        String folder = photo.getProperty().getReference();
        Map params = ObjectUtils.asMap("public_id", photo.getProperty().getReference() + "/" + photoId);
        File file = new File("uplace_" + photoId);
        try {
            FileUtils.writeByteArrayToFile(file, photo.getPhoto());
            Map imgUpload = cloudinary.uploader().upload(file, params);
            log.debug("Photo uploaded to cloudinary, folder {}, url {}", folder,  imgUpload.get("secure_url"));
            return imgUpload;
        } catch (IOException e) {
            log.error("Image could not be uploaded to CDN", e);
            throw new CDNException(e);
        } finally {
            if (file.exists() || file.exists()) file.delete();
        }
    }

    @Override
    public void deleteImage(String imageId, String folder) throws CDNException {
        try {
            cloudinary.uploader().destroy(folder + "/" + imageId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            log.error("Image could not be deleted from CDN", e);
            throw new CDNException(e);
        }

    }

    @Override
    public void deleteImage(Photo photo) throws CDNException {
        try {
            cloudinary.uploader().destroy(photo.getPublicId(), ObjectUtils.emptyMap());
        } catch (IOException e) {
            log.error("Image could not be deleted from CDN", e);
            throw new CDNException(e);
        }

    }

}
