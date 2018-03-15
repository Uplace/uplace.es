package com.arnaugarcia.uplace.domain;

import org.hibernate.validator.constraints.NotEmpty;

public class Mail {

    @NotEmpty
    private String to;
    private String subject;
    private String content;
    private boolean isMultipart;
    private boolean isHtml;

    public Mail() {
    }

    public Mail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.isMultipart = isMultipart;
        this.isHtml = isHtml;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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

    public boolean isMultipart() {
        return isMultipart;
    }

    public void setMultipart(boolean multipart) {
        isMultipart = multipart;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public void setHtml(boolean html) {
        isHtml = html;
    }

    @Override
    public String toString() {
        return "Mail{" +
            "to='" + to + '\'' +
            ", subject='" + subject + '\'' +
            ", content='" + content + '\'' +
            ", isMultipart=" + isMultipart +
            ", isHtml=" + isHtml +
            '}';
    }
}

