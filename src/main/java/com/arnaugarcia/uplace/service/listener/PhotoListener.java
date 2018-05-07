package com.arnaugarcia.uplace.service.listener;

import com.arnaugarcia.uplace.domain.Photo;
import com.arnaugarcia.uplace.service.AutowireHelper;
import com.arnaugarcia.uplace.service.CDNService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostRemove;
import javax.persistence.PrePersist;

@Component
public class PhotoListener {

    private final Logger log = LoggerFactory.getLogger(PhotoListener.class);

    @Autowired
    private CDNService cdnService;

    @PrePersist
    public void onPrePersist(Photo photo) {
        // Implement and check if the property has already a thumbnail
    }

    @PostRemove
    public void onOrphanRemoval(Photo photo) {
        AutowireHelper.autowire(this, this.cdnService);
        cdnService.deleteImage(photo);
        log.debug("Removed a photo with public_id {}", photo.getPublicId());
    }

}
