package com.arnaugarcia.uplace.service.dto;

public class SearchDTO {

    private String city;

    private String category;

    private String keywords;

    private Double priceMin;

    private Double priceMax;

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

    public Double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Double priceMin) {
        this.priceMin = priceMin;
    }

    public Double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Double priceMax) {
        this.priceMax = priceMax;
    }

    @Override
    public String toString() {
        return "SearchDTO{" +
            "city='" + city + '\'' +
            ", category='" + category + '\'' +
            '}';
    }
}
