package com.arnaugarcia.uplace.service.dto;

import java.io.Serializable;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.ApartmentType;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Apartment entity. This class is used in ApartmentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /apartments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApartmentCriteria implements Serializable {
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

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter numberBedrooms;

    private IntegerFilter numberBathrooms;

    private SelectFilter elevator;

    private SelectFilter ac;

    private SelectFilter heat;

    private IntegerFilter surfaceTerrace;

    private IntegerFilter surfaceSaloon;

    private ApartmentTypeFilter propertyType;

    private SelectFilter office;

    private SelectFilter kitchenOffice;

    private SelectFilter storage;

    private SelectFilter sharedPool;

    private SelectFilter nearTransport;

    public ApartmentCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public ApartmentTypeFilter getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(ApartmentTypeFilter propertyType) {
        this.propertyType = propertyType;
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

    @Override
    public String toString() {
        return "ApartmentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (numberBedrooms != null ? "numberBedrooms=" + numberBedrooms + ", " : "") +
                (numberBathrooms != null ? "numberBathrooms=" + numberBathrooms + ", " : "") +
                (elevator != null ? "elevator=" + elevator + ", " : "") +
                (ac != null ? "ac=" + ac + ", " : "") +
                (heat != null ? "heat=" + heat + ", " : "") +
                (surfaceTerrace != null ? "surfaceTerrace=" + surfaceTerrace + ", " : "") +
                (surfaceSaloon != null ? "surfaceSaloon=" + surfaceSaloon + ", " : "") +
                (propertyType != null ? "propertyType=" + propertyType + ", " : "") +
                (office != null ? "office=" + office + ", " : "") +
                (kitchenOffice != null ? "kitchenOffice=" + kitchenOffice + ", " : "") +
                (storage != null ? "storage=" + storage + ", " : "") +
                (sharedPool != null ? "sharedPool=" + sharedPool + ", " : "") +
                (nearTransport != null ? "nearTransport=" + nearTransport + ", " : "") +
            "}";
    }

}
