package com.arnaugarcia.uplace.service.dto;

import java.util.List;

public class FilterDTO {
    private List<String> cities;
    private List<Integer> pricesRange;
    private Integer maxPrice;
    private Integer minPrice;
    private List<String> typeProperties;

    public FilterDTO(List<String> cities, List<Integer> pricesRange, Integer maxPrice, Integer minPrice, List<String> typeProperties) {
        this.cities = cities;
        this.pricesRange = pricesRange;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.typeProperties = typeProperties;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public List<Integer> getPricesRange() {
        return pricesRange;
    }

    public void setPricesRange(List<Integer> pricesRange) {
        this.pricesRange = pricesRange;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public List<String> getTypeProperties() {
        return typeProperties;
    }

    public void setTypeProperties(List<String> typeProperties) {
        this.typeProperties = typeProperties;
    }

    @Override
    public String toString() {
        return "FilterDTO{" +
            "cities=" + cities +
            ", pricesRange=" + pricesRange +
            ", maxPrice=" + maxPrice +
            ", minPrice=" + minPrice +
            ", typeProperties=" + typeProperties +
            '}';
    }
}
