package com.arnaugarcia.uplace.domain;


import java.time.ZonedDateTime;

public class Contact {

    private String to;
    private String name;
    private String phone;
    private ZonedDateTime dateSend;
    private Property property;

    public Contact() { }

    public Contact(String to, String name, String phone, ZonedDateTime dateSend, Property property) {
        this.to = to;
        this.name = name;
        this.phone = phone;
        this.dateSend = dateSend;
        this.property = property;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
            "to='" + to + '\'' +
            ", name='" + name + '\'' +
            ", phone='" + phone + '\'' +
            ", dateSend=" + dateSend +
            ", property=" + property +
            '}';
    }
}

