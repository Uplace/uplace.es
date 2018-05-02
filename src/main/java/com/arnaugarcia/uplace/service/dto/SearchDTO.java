package com.arnaugarcia.uplace.service.dto;

public class SearchDTO {

    private String city;

    private String category;

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

    @Override
    public String toString() {
        return "SearchDTO{" +
            "city='" + city + '\'' +
            ", category='" + category + '\'' +
            '}';
    }
}
