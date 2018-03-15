package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Mail;
import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.errors.ErrorConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ContactService<T extends Property> {

    private final MailService mailService;

    private final NotificationService notificationService;

    private final PropertyService<T> propertyService;

    public ContactService(MailService mailService, NotificationService notificationService, PropertyService<T> propertyService) {
        this.mailService = mailService;
        this.notificationService = notificationService;
        this.propertyService = propertyService;
    }

    public ResponseEntity<Mail> sendInquire(String propertyReference, Mail mail) {
        T property = this.propertyService.findOne(propertyReference);
        if (property == null) {
            throw new BadRequestAlertException("Property not found", "PROPERTY", ErrorConstants.ERR_BAD_REFERENCE);
        }
        mailService.sendPropertyInfo(mail, property,"infoProperty","email.inquire.title");
        return new ResponseEntity<>(mail, HttpStatus.OK);
    }
}
