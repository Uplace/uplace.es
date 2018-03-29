package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.*;
import com.arnaugarcia.uplace.domain.enumeration.NotificationType;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.errors.ErrorConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InquireService<T extends Property> {

    private final MailService mailService;

    private final NotificationService notificationService;

    private final RequestService requestService;

    private final PropertyService<T> propertyService;

    public InquireService(MailService mailService, NotificationService notificationService, PropertyService<T> propertyService, RequestService requestService) {
        this.mailService = mailService;
        this.notificationService = notificationService;
        this.propertyService = propertyService;
        this.requestService = requestService;
    }

    public ResponseEntity<Mail> sendInquire(String propertyReference, Request request) {
        T property = this.propertyService.findOne(propertyReference);
        if (property == null) {
            throw new BadRequestAlertException("Property not found", "PROPERTY", ErrorConstants.ERR_BAD_REFERENCE);
        }

        request = requestService.save(request);


        // TODO : DON'T hardcode this
        /*notificationService.saveNotification(new Notification(
            "New inquire #[" + requestSaved.getReference() + "]",
            "A new inquired has been registered click here to know the details",
            ZonedDateTime.now(),
            NotificationType.REQUEST,
            "N/A",
            "N/A",
            false,
            null
            ));*/

        // TODO : Send a notification to the agent
        // TODO : Send an email to the agent
        // TODO : Make the template of contact
        Mail mail = new Mail(
            request.getEmail(),
            request.getFirstName() + ", " + request.getLastName(),
            ZonedDateTime.now(),
            "Uplace - Muchas gracias por su interés",
            "Estimad@ " + request.getFirstName() + " hemos recibido su solicitud con éxito en breves nos pondremos en contacto con usted"
        );
        mailService.sendPropertyInfo(mail, property,"infoProperty","email.inquire.title");
        return new ResponseEntity<>(mail, HttpStatus.OK);
    }
}
