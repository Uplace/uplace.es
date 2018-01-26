package com.arnaugarcia.uplace.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;



import io.github.jhipster.service.filter.ZonedDateTimeFilter;


/**
 * Criteria class for the Property entity. This class is used in PropertyResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /properties?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PropertyCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter title;

    private DoubleFilter price;

    private ZonedDateTimeFilter created;

    private ZonedDateTimeFilter updated;

    private StringFilter reference;

    private DoubleFilter priceSell;

    private DoubleFilter priceRent;

    private IntegerFilter yearConstruction;

    private BooleanFilter newCreation;

    private BooleanFilter visible;

    private IntegerFilter surface;

    private LongFilter photoId;

    private LongFilter managerId;

    public PropertyCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public DoubleFilter getPrice() {
        return price;
    }

    public void setPrice(DoubleFilter price) {
        this.price = price;
    }

    public ZonedDateTimeFilter getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTimeFilter created) {
        this.created = created;
    }

    public ZonedDateTimeFilter getUpdated() {
        return updated;
    }

    public void setUpdated(ZonedDateTimeFilter updated) {
        this.updated = updated;
    }

    public StringFilter getReference() {
        return reference;
    }

    public void setReference(StringFilter reference) {
        this.reference = reference;
    }

    public DoubleFilter getPriceSell() {
        return priceSell;
    }

    public void setPriceSell(DoubleFilter priceSell) {
        this.priceSell = priceSell;
    }

    public DoubleFilter getPriceRent() {
        return priceRent;
    }

    public void setPriceRent(DoubleFilter priceRent) {
        this.priceRent = priceRent;
    }

    public IntegerFilter getYearConstruction() {
        return yearConstruction;
    }

    public void setYearConstruction(IntegerFilter yearConstruction) {
        this.yearConstruction = yearConstruction;
    }

    public BooleanFilter getNewCreation() {
        return newCreation;
    }

    public void setNewCreation(BooleanFilter newCreation) {
        this.newCreation = newCreation;
    }

    public BooleanFilter getVisible() {
        return visible;
    }

    public void setVisible(BooleanFilter visible) {
        this.visible = visible;
    }

    public IntegerFilter getSurface() {
        return surface;
    }

    public void setSurface(IntegerFilter surface) {
        this.surface = surface;
    }

    public LongFilter getPhotoId() {
        return photoId;
    }

    public void setPhotoId(LongFilter photoId) {
        this.photoId = photoId;
    }

    public LongFilter getManagerId() {
        return managerId;
    }

    public void setManagerId(LongFilter managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "PropertyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (reference != null ? "reference=" + reference + ", " : "") +
                (priceSell != null ? "priceSell=" + priceSell + ", " : "") +
                (priceRent != null ? "priceRent=" + priceRent + ", " : "") +
                (yearConstruction != null ? "yearConstruction=" + yearConstruction + ", " : "") +
                (newCreation != null ? "newCreation=" + newCreation + ", " : "") +
                (visible != null ? "visible=" + visible + ", " : "") +
                (surface != null ? "surface=" + surface + ", " : "") +
                (photoId != null ? "photoId=" + photoId + ", " : "") +
                (managerId != null ? "managerId=" + managerId + ", " : "") +
            "}";
    }

}