package com.arnaugarcia.uplace.service.dto;

import com.arnaugarcia.uplace.service.filter.CityFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.StringFilter;

public class SearchDTO {

    private CityFilter city;

    private StringFilter category;

    private StringFilter keywords;

    private IntegerFilter bathrooms;

    private IntegerFilter bedrooms;

    private DoubleFilter price;

    public SearchDTO() { }

    public CityFilter getCity() {
        return city;
    }

    public void setCity(CityFilter city) {
        this.city = city;
    }

    public StringFilter getCategory() {
        return category;
    }

    public void setCategory(StringFilter category) {
        this.category = category;
    }

    public StringFilter getKeywords() {
        return keywords;
    }

    public void setKeywords(StringFilter keywords) {
        this.keywords = keywords;
    }

    public IntegerFilter getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(IntegerFilter bathrooms) {
        this.bathrooms = bathrooms;
    }

    public IntegerFilter getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(IntegerFilter bedrooms) {
        this.bedrooms = bedrooms;
    }

    public DoubleFilter getPrice() {
        return price;
    }

    public void setPrice(DoubleFilter price) {
        this.price = price;
    }
}
