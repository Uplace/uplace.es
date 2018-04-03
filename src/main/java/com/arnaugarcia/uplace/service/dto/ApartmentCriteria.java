package com.arnaugarcia.uplace.service.dto;

import java.io.Serializable;

import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.ApartmentType;
import io.github.jhipster.service.filter.*;


/**
 * Criteria class for the Apartment entity. This class is used in ApartmentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /apartments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApartmentCriteria extends PropertyCriteria implements Serializable {
    /**
     * Class for filtering Select
     */
    public static class SelectFilter extends Filter<Select> {
    }

    /**
     * Class for filtering ApartmentType
     */
    public static class ApartmentTypeFilter extends Filter<ApartmentType> {
    }

    /**
     * Class for filtering EnergyCertificate
     */
    public static class EnergyCertificateFilter extends Filter<EnergyCertificate> { }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private ZonedDateTimeFilter created;

    private ZonedDateTimeFilter updated;

    private StringFilter reference;

    private DoubleFilter priceSell;

    private DoubleFilter priceRent;

    private IntegerFilter yearConstruction;

    private BooleanFilter newCreation;

    private BooleanFilter visible;

    private IntegerFilter surface;

    private IntegerFilter numberBedrooms;

    private IntegerFilter numberBathrooms;

    private IntegerFilter m2Edified;

    private IntegerFilter m2Usable;

    private IntegerFilter height;

    private SelectFilter elevator;

    private SelectFilter ac;

    private SelectFilter heat;

    private SelectFilter parking;

    private SelectFilter terrace;

    private SelectFilter balcony;

    private IntegerFilter surfaceTerrace;

    private IntegerFilter surfaceSaloon;

    private ApartmentTypeFilter type;

    private SelectFilter office;

    private SelectFilter kitchenOffice;

    private SelectFilter storage;

    private SelectFilter sharedPool;

    private SelectFilter nearTransport;

    private SelectFilter reformed;

    private EnergyCertificateFilter energyCertificate;

    private SelectFilter certificateHabitability;

    public ApartmentCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    @Override
    public StringFilter getTitle() {
        return title;
    }

    @Override
    public void setTitle(StringFilter title) {
        this.title = title;
    }

    @Override
    public ZonedDateTimeFilter getCreated() {
        return created;
    }

    @Override
    public void setCreated(ZonedDateTimeFilter created) {
        this.created = created;
    }

    @Override
    public ZonedDateTimeFilter getUpdated() {
        return updated;
    }

    @Override
    public void setUpdated(ZonedDateTimeFilter updated) {
        this.updated = updated;
    }

    @Override
    public StringFilter getReference() {
        return reference;
    }

    @Override
    public void setReference(StringFilter reference) {
        this.reference = reference;
    }

    @Override
    public DoubleFilter getPriceSell() {
        return priceSell;
    }

    @Override
    public void setPriceSell(DoubleFilter priceSell) {
        this.priceSell = priceSell;
    }

    @Override
    public DoubleFilter getPriceRent() {
        return priceRent;
    }

    @Override
    public void setPriceRent(DoubleFilter priceRent) {
        this.priceRent = priceRent;
    }

    @Override
    public IntegerFilter getYearConstruction() {
        return yearConstruction;
    }

    @Override
    public void setYearConstruction(IntegerFilter yearConstruction) {
        this.yearConstruction = yearConstruction;
    }

    @Override
    public BooleanFilter getNewCreation() {
        return newCreation;
    }

    @Override
    public void setNewCreation(BooleanFilter newCreation) {
        this.newCreation = newCreation;
    }

    @Override
    public BooleanFilter getVisible() {
        return visible;
    }

    @Override
    public void setVisible(BooleanFilter visible) {
        this.visible = visible;
    }

    @Override
    public IntegerFilter getSurface() {
        return surface;
    }

    @Override
    public void setSurface(IntegerFilter surface) {
        this.surface = surface;
    }

    public IntegerFilter getM2Edified() {
        return m2Edified;
    }

    public void setM2Edified(IntegerFilter m2Edified) {
        this.m2Edified = m2Edified;
    }

    public IntegerFilter getM2Usable() {
        return m2Usable;
    }

    public void setM2Usable(IntegerFilter m2Usable) {
        this.m2Usable = m2Usable;
    }

    public IntegerFilter getNumberBedrooms() {
        return numberBedrooms;
    }

    public void setNumberBedrooms(IntegerFilter numberBedrooms) {
        this.numberBedrooms = numberBedrooms;
    }

    public IntegerFilter getNumberBathrooms() {
        return numberBathrooms;
    }

    public void setNumberBathrooms(IntegerFilter numberBathrooms) {
        this.numberBathrooms = numberBathrooms;
    }

    public IntegerFilter getm2Edified() {
        return m2Edified;
    }

    public void setm2Edified(IntegerFilter m2Edified) {
        this.m2Edified = m2Edified;
    }

    public IntegerFilter getm2Usable() {
        return m2Usable;
    }

    public void setm2Usable(IntegerFilter m2Usable) {
        this.m2Usable = m2Usable;
    }

    public IntegerFilter getHeight() {
        return height;
    }

    public void setHeight(IntegerFilter height) {
        this.height = height;
    }

    public SelectFilter getElevator() {
        return elevator;
    }

    public void setElevator(SelectFilter elevator) {
        this.elevator = elevator;
    }

    public SelectFilter getAc() {
        return ac;
    }

    public void setAc(SelectFilter ac) {
        this.ac = ac;
    }

    public SelectFilter getHeat() {
        return heat;
    }

    public void setHeat(SelectFilter heat) {
        this.heat = heat;
    }

    public SelectFilter getParking() {
        return parking;
    }

    public void setParking(SelectFilter parking) {
        this.parking = parking;
    }

    public SelectFilter getTerrace() {
        return terrace;
    }

    public void setTerrace(SelectFilter terrace) {
        this.terrace = terrace;
    }

    public SelectFilter getBalcony() {
        return balcony;
    }

    public void setBalcony(SelectFilter balcony) {
        this.balcony = balcony;
    }

    public IntegerFilter getSurfaceTerrace() {
        return surfaceTerrace;
    }

    public void setSurfaceTerrace(IntegerFilter surfaceTerrace) {
        this.surfaceTerrace = surfaceTerrace;
    }

    public IntegerFilter getSurfaceSaloon() {
        return surfaceSaloon;
    }

    public void setSurfaceSaloon(IntegerFilter surfaceSaloon) {
        this.surfaceSaloon = surfaceSaloon;
    }

    public ApartmentTypeFilter getType() {
        return type;
    }

    public void setType(ApartmentTypeFilter type) {
        this.type = type;
    }

    public SelectFilter getOffice() {
        return office;
    }

    public void setOffice(SelectFilter office) {
        this.office = office;
    }

    public SelectFilter getKitchenOffice() {
        return kitchenOffice;
    }

    public void setKitchenOffice(SelectFilter kitchenOffice) {
        this.kitchenOffice = kitchenOffice;
    }

    public SelectFilter getStorage() {
        return storage;
    }

    public void setStorage(SelectFilter storage) {
        this.storage = storage;
    }

    public SelectFilter getSharedPool() {
        return sharedPool;
    }

    public void setSharedPool(SelectFilter sharedPool) {
        this.sharedPool = sharedPool;
    }

    public SelectFilter getNearTransport() {
        return nearTransport;
    }

    public void setNearTransport(SelectFilter nearTransport) {
        this.nearTransport = nearTransport;
    }

    public SelectFilter getReformed() {
        return reformed;
    }

    public void setReformed(SelectFilter reformed) {
        this.reformed = reformed;
    }

    public EnergyCertificateFilter getEnergyCertificate() {
        return energyCertificate;
    }

    public void setEnergyCertificate(EnergyCertificateFilter energyCertificate) {
        this.energyCertificate = energyCertificate;
    }

    public SelectFilter getCertificateHabitability() {
        return certificateHabitability;
    }

    public void setCertificateHabitability(SelectFilter certificateHabitability) {
        this.certificateHabitability = certificateHabitability;
    }

    @Override
    public String toString() {
        return "ApartmentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (numberBedrooms != null ? "numberBedrooms=" + numberBedrooms + ", " : "") +
            (numberBathrooms != null ? "numberBathrooms=" + numberBathrooms + ", " : "") +
            (m2Edified != null ? "m2Edified=" + m2Edified + ", " : "") +
            (m2Usable != null ? "m2Usable=" + m2Usable + ", " : "") +
            (height != null ? "height=" + height + ", " : "") +
            (elevator != null ? "elevator=" + elevator + ", " : "") +
            (ac != null ? "ac=" + ac + ", " : "") +
            (heat != null ? "heat=" + heat + ", " : "") +
            (parking != null ? "parking=" + parking + ", " : "") +
            (terrace != null ? "terrace=" + terrace + ", " : "") +
            (balcony != null ? "balcony=" + balcony + ", " : "") +
            (surfaceTerrace != null ? "surfaceTerrace=" + surfaceTerrace + ", " : "") +
            (surfaceSaloon != null ? "surfaceSaloon=" + surfaceSaloon + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (office != null ? "office=" + office + ", " : "") +
            (kitchenOffice != null ? "kitchenOffice=" + kitchenOffice + ", " : "") +
            (storage != null ? "storage=" + storage + ", " : "") +
            (sharedPool != null ? "sharedPool=" + sharedPool + ", " : "") +
            (nearTransport != null ? "nearTransport=" + nearTransport + ", " : "") +
            (reformed != null ? "reformed=" + reformed + ", " : "") +
            (energyCertificate != null ? "energyCertificate=" + energyCertificate + ", " : "") +
            (certificateHabitability != null ? "certificateHabitability=" + certificateHabitability + ", " : "") +
            "}";
    }

}
