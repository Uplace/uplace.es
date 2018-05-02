package com.arnaugarcia.uplace.service.dto;

public class SearchDTO {

    private String city;

    private String category;

    private String keywords;

    public SearchDTO() { }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "SearchDTO{" +
            "city='" + city + '\'' +
            ", category='" + category + '\'' +
            '}';
    }
}
