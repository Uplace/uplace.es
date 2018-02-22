package com.arnaugarcia.uplace.service.dto;

import java.io.Serializable;
import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the IndustrialPlant entity. This class is used in IndustrialPlantResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /industrial-plants?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IndustrialPlantCriteria implements Serializable {
    /**
     * Class for filtering EnergyCertificate
     */
    public static class EnergyCertificateFilter extends Filter<EnergyCertificate> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter solarSurface;

    private IntegerFilter numberRooms;

    private IntegerFilter m2Offices;

    private IntegerFilter m2Storage;

    private IntegerFilter heightFree;

    private IntegerFilter numberDocks;

    private EnergyCertificateFilter energyCertificate;

    public IndustrialPlantCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getSolarSurface() {
        return solarSurface;
    }

    public void setSolarSurface(IntegerFilter solarSurface) {
        this.solarSurface = solarSurface;
    }

    public IntegerFilter getNumberRooms() {
        return numberRooms;
    }

    public void setNumberRooms(IntegerFilter numberRooms) {
        this.numberRooms = numberRooms;
    }

    public IntegerFilter getm2Offices() {
        return m2Offices;
    }

    public void setm2Offices(IntegerFilter m2Offices) {
        this.m2Offices = m2Offices;
    }

    public IntegerFilter getm2Storage() {
        return m2Storage;
    }

    public void setm2Storage(IntegerFilter m2Storage) {
        this.m2Storage = m2Storage;
    }

    public IntegerFilter getHeightFree() {
        return heightFree;
    }

    public void setHeightFree(IntegerFilter heightFree) {
        this.heightFree = heightFree;
    }

    public IntegerFilter getNumberDocks() {
        return numberDocks;
    }

    public void setNumberDocks(IntegerFilter numberDocks) {
        this.numberDocks = numberDocks;
    }

    public EnergyCertificateFilter getEnergyCertificate() {
        return energyCertificate;
    }

    public void setEnergyCertificate(EnergyCertificateFilter energyCertificate) {
        this.energyCertificate = energyCertificate;
    }

    @Override
    public String toString() {
        return "IndustrialPlantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (solarSurface != null ? "solarSurface=" + solarSurface + ", " : "") +
                (numberRooms != null ? "numberRooms=" + numberRooms + ", " : "") +
                (m2Offices != null ? "m2Offices=" + m2Offices + ", " : "") +
                (m2Storage != null ? "m2Storage=" + m2Storage + ", " : "") +
                (heightFree != null ? "heightFree=" + heightFree + ", " : "") +
                (numberDocks != null ? "numberDocks=" + numberDocks + ", " : "") +
                (energyCertificate != null ? "energyCertificate=" + energyCertificate + ", " : "") +
            "}";
    }

}
