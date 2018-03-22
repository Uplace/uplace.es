package com.arnaugarcia.uplace.service.dto;

import java.io.Serializable;
import com.arnaugarcia.uplace.domain.enumeration.TransactionType;
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
    /**
     * Class for filtering TransactionType
     */
    public static class TransactionTypeFilter extends Filter<TransactionType> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter title;

    private ZonedDateTimeFilter created;

    private ZonedDateTimeFilter updated;

    private TransactionTypeFilter transaction;

    private StringFilter reference;

    private DoubleFilter priceSell;

    private DoubleFilter priceRent;

    private DoubleFilter priceTransfer;

    private IntegerFilter yearConstruction;

    private BooleanFilter newCreation;

    private BooleanFilter visible;

    private IntegerFilter surface;

    private LongFilter locationId;

    private LongFilter photoId;

    private LongFilter managerId;

    private LongFilter realEstateId;

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

    public TransactionTypeFilter getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionTypeFilter transaction) {
        this.transaction = transaction;
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

    public DoubleFilter getPriceTransfer() {
        return priceTransfer;
    }

    public void setPriceTransfer(DoubleFilter priceTransfer) {
        this.priceTransfer = priceTransfer;
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

    public LongFilter getLocationId() {
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
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

    public LongFilter getRealEstateId() {
        return realEstateId;
    }

    public void setRealEstateId(LongFilter realEstateId) {
        this.realEstateId = realEstateId;
    }

    @Override
    public String toString() {
        return "PropertyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (created != null ? "created=" + created + ", " : "") +
                (updated != null ? "updated=" + updated + ", " : "") +
                (transaction != null ? "transaction=" + transaction + ", " : "") +
                (reference != null ? "reference=" + reference + ", " : "") +
                (priceSell != null ? "priceSell=" + priceSell + ", " : "") +
                (priceRent != null ? "priceRent=" + priceRent + ", " : "") +
                (priceTransfer != null ? "priceTransfer=" + priceTransfer + ", " : "") +
                (yearConstruction != null ? "yearConstruction=" + yearConstruction + ", " : "") +
                (newCreation != null ? "newCreation=" + newCreation + ", " : "") +
                (visible != null ? "visible=" + visible + ", " : "") +
                (surface != null ? "surface=" + surface + ", " : "") +
                (locationId != null ? "locationId=" + locationId + ", " : "") +
                (photoId != null ? "photoId=" + photoId + ", " : "") +
                (managerId != null ? "managerId=" + managerId + ", " : "") +
                (realEstateId != null ? "realEstateId=" + realEstateId + ", " : "") +
            "}";
    }

}
