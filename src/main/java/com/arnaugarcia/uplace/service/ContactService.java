package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Contact;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final MailService mailService;

    private final NotificationService notificationService;

    public ContactService(MailService mailService, NotificationService notificationService) {
        this.mailService = mailService;
        this.notificationService = notificationService;
    }

    public ResponseEntity<Contact> sendInquire(Contact contact) {
        mailService.sendPropertyInfo(contact, "infoProperty","email.inquire.title");
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }
}
