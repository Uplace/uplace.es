package com.arnaugarcia.uplace.web.rest;


import com.arnaugarcia.uplace.domain.Mail;
import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ContactResource {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private final MailService mailService;

    public ContactResource(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("contact-property")
    public ResponseEntity sendContactProperty(@Valid @RequestBody Mail mail, @RequestBody Property property) {

        try {
            mailService.sendPropertyInfo(property, mail, "infoProperty", "infoProperty");
        }catch( Exception e ){
            log.info("Error Sending Email: " + e.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
