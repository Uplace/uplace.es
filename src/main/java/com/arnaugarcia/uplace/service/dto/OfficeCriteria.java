package com.arnaugarcia.uplace.service.dto;

import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;

/**
 * Criteria class for the Office entity. This class is used in OfficeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /offices?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OfficeCriteria extends PropertyCriteria implements Serializable {
    /**
     * Class for filtering Select
     */
    public static class SelectFilter extends Filter<Select> {
    }

    /**
     * Class for filtering EnergyCertificate
     */
    public static class EnergyCertificateFilter extends Filter<EnergyCertificate> {
    }

    private static final long serialVersionUID = 1L;

    private StringFilter bathrooms;

    private IntegerFilter floors;

    private SelectFilter terrace;

    private SelectFilter office;

    private SelectFilter storage;

    private IntegerFilter storageSurface;

    private IntegerFilter officesSurface;

    private SelectFilter ac;

    private SelectFilter heat;

    private EnergyCertificateFilter energyCertificate;

    public OfficeCriteria() {
    }

    public StringFilter getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(StringFilter bathrooms) {
        this.bathrooms = bathrooms;
    }

    public IntegerFilter getFloors() {
        return floors;
    }

    public void setFloors(IntegerFilter floors) {
        this.floors = floors;
    }

    public SelectFilter getTerrace() {
        return terrace;
    }

    public void setTerrace(SelectFilter terrace) {
        this.terrace = terrace;
    }

    public SelectFilter getOffice() {
        return office;
    }

    public void setOffice(SelectFilter office) {
        this.office = office;
    }

    public SelectFilter getStorage() {
        return storage;
    }

    public void setStorage(SelectFilter storage) {
        this.storage = storage;
    }

    public IntegerFilter getStorageSurface() {
        return storageSurface;
    }

    public void setStorageSurface(IntegerFilter storageSurface) {
        this.storageSurface = storageSurface;
    }

    public IntegerFilter getOfficesSurface() {
        return officesSurface;
    }

    public void setOfficesSurface(IntegerFilter officesSurface) {
        this.officesSurface = officesSurface;
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

    public EnergyCertificateFilter getEnergyCertificate() {
        return energyCertificate;
    }

    public void setEnergyCertificate(EnergyCertificateFilter energyCertificate) {
        this.energyCertificate = energyCertificate;
    }

    @Override
    public String toString() {
        return "OfficeCriteria{" +
            (bathrooms != null ? "bathrooms=" + bathrooms + ", " : "") +
            (floors != null ? "floors=" + floors + ", " : "") +
            (terrace != null ? "terrace=" + terrace + ", " : "") +
            (office != null ? "office=" + office + ", " : "") +
            (storage != null ? "storage=" + storage + ", " : "") +
            (storageSurface != null ? "storageSurface=" + storageSurface + ", " : "") +
            (officesSurface != null ? "officesSurface=" + officesSurface + ", " : "") +
            (ac != null ? "ac=" + ac + ", " : "") +
            (heat != null ? "heat=" + heat + ", " : "") +
            (energyCertificate != null ? "energyCertificate=" + energyCertificate + ", " : "") +
            "}";
    }

}
