package com.arnaugarcia.uplace.domain;


import java.time.ZonedDateTime;

public class Contact {

    private Mail mail;
    private ZonedDateTime dateSend;
    private Property property;

    public Contact() { }

    public Contact(Mail mail, ZonedDateTime dateSend, Property property) {
        this.mail = mail;
        this.dateSend = dateSend;
        this.property = property;
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public ZonedDateTime getDateSend() {
        return dateSend;
    }

    public void setDateSend(ZonedDateTime dateSend) {
        this.dateSend = dateSend;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "Contact{" +
            "mail=" + mail +
            ", dateSend=" + dateSend +
            ", property=" + property +
            '}';
    }
}

