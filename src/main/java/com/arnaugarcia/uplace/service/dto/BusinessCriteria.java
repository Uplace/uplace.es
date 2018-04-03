package com.arnaugarcia.uplace.service.dto;

import java.io.Serializable;
import com.arnaugarcia.uplace.domain.enumeration.BusinessType;
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
 * Criteria class for the Business entity. This class is used in BusinessResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /businesses?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BusinessCriteria extends PropertyCriteria implements Serializable {
    /**
     * Class for filtering BusinessType
     */
    public static class BusinessTypeFilter extends Filter<BusinessType> {
    }

    /**
     * Class for filtering Select
     */
    public static class SelectFilter extends Filter<Select> {
    }

    private static final long serialVersionUID = 1L;

    private BusinessTypeFilter type;

    private IntegerFilter numberBathrooms;

    private SelectFilter elevator;

    private SelectFilter ac;

    private SelectFilter heat;

    private IntegerFilter surfaceTerrace;

    private IntegerFilter surfaceGarden;

    private IntegerFilter numberOffice;

    private IntegerFilter surfaceSaloon;

    private IntegerFilter height;

    private SelectFilter pool;

    public BusinessCriteria() {
    }


    public BusinessTypeFilter getType() {
        return type;
    }

    public void setType(BusinessTypeFilter type) {
        this.type = type;
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

    public IntegerFilter getSurfaceGarden() {
        return surfaceGarden;
    }

    public void setSurfaceGarden(IntegerFilter surfaceGarden) {
        this.surfaceGarden = surfaceGarden;
    }

    public IntegerFilter getNumberOffice() {
        return numberOffice;
    }

    public void setNumberOffice(IntegerFilter numberOffice) {
        this.numberOffice = numberOffice;
    }

    public IntegerFilter getSurfaceSaloon() {
        return surfaceSaloon;
    }

    public void setSurfaceSaloon(IntegerFilter surfaceSaloon) {
        this.surfaceSaloon = surfaceSaloon;
    }

    public IntegerFilter getHeight() {
        return height;
    }

    public void setHeight(IntegerFilter height) {
        this.height = height;
    }

    public SelectFilter getPool() {
        return pool;
    }

    public void setPool(SelectFilter pool) {
        this.pool = pool;
    }

    @Override
    public String toString() {
        return "BusinessCriteria{" +
            (type != null ? "type=" + type + ", " : "") +
            (numberBathrooms != null ? "numberBathrooms=" + numberBathrooms + ", " : "") +
            (elevator != null ? "elevator=" + elevator + ", " : "") +
            (ac != null ? "ac=" + ac + ", " : "") +
            (heat != null ? "heat=" + heat + ", " : "") +
            (surfaceTerrace != null ? "surfaceTerrace=" + surfaceTerrace + ", " : "") +
            (surfaceGarden != null ? "surfaceGarden=" + surfaceGarden + ", " : "") +
            (numberOffice != null ? "numberOffice=" + numberOffice + ", " : "") +
            (surfaceSaloon != null ? "surfaceSaloon=" + surfaceSaloon + ", " : "") +
            (height != null ? "height=" + height + ", " : "") +
            (pool != null ? "pool=" + pool + ", " : "") +
            "}";
    }

}
