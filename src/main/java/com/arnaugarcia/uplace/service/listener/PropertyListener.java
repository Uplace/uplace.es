package com.arnaugarcia.uplace.service.listener;

import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.service.AutowireHelper;
import com.arnaugarcia.uplace.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;

@Component
public class PropertyListener<T extends Property> {

    @Autowired
    private PropertyService<T> propertyService;

    @PrePersist
    public void propertyPrePersist(T property) {
            AutowireHelper.autowire(this, this.propertyService);
            property.setReference(propertyService.createReference());
    }

}
