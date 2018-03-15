package com.arnaugarcia.uplace.domain;

import java.time.ZonedDateTime;

public class Mail {

    private String to;
    private String name;
    private ZonedDateTime date;
    private String subject;
    private String content;

    public Mail() { }

    public Mail(String to, String name, ZonedDateTime date, String subject, String content) {
        this.to = to;
        this.name = name;
        this.date = date;
        this.subject = subject;
        this.content = content;
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

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Mail{" +
            "to='" + to + '\'' +
            ", name='" + name + '\'' +
            ", date=" + date +
            ", subject='" + subject + '\'' +
            ", content='" + content + '\'' +
            '}';
    }
}
