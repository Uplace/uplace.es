package com.arnaugarcia.uplace.domain;

public class Mail {

    private String to;
    private String name;
    private String subject;
    private String content;

    public Mail() { }

    public Mail(String to, String name, String subject, String content) {
        this.to = to;
        this.name = name;
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
            ", subject='" + subject + '\'' +
            ", content='" + content + '\'' +
            '}';
    }
}
