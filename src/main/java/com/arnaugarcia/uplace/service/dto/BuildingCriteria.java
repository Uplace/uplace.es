package com.arnaugarcia.uplace.service.dto;

import java.io.Serializable;
import com.arnaugarcia.uplace.domain.enumeration.BuildingType;
import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Building entity. This class is used in BuildingResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /buildings?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BuildingCriteria extends PropertyCriteria implements Serializable {
    /**
     * Class for filtering BuildingType
     */
    public static class BuildingTypeFilter extends Filter<BuildingType> {
    }

    /**
     * Class for filtering EnergyCertificate
     */
    public static class EnergyCertificateFilter extends Filter<EnergyCertificate> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private BuildingTypeFilter type;

    private IntegerFilter solarSurface;

    private IntegerFilter m2Edified;

    private IntegerFilter floorsSR;

    private IntegerFilter floorsBR;

    private EnergyCertificateFilter energyCertificate;

    public BuildingCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BuildingTypeFilter getType() {
        return type;
    }

    public void setType(BuildingTypeFilter type) {
        this.type = type;
    }

    public IntegerFilter getSolarSurface() {
        return solarSurface;
    }

    public void setSolarSurface(IntegerFilter solarSurface) {
        this.solarSurface = solarSurface;
    }

    public IntegerFilter getm2Edified() {
        return m2Edified;
    }

    public void setm2Edified(IntegerFilter m2Edified) {
        this.m2Edified = m2Edified;
    }

    public IntegerFilter getFloorsSR() {
        return floorsSR;
    }

    public void setFloorsSR(IntegerFilter floorsSR) {
        this.floorsSR = floorsSR;
    }

    public IntegerFilter getFloorsBR() {
        return floorsBR;
    }

    public void setFloorsBR(IntegerFilter floorsBR) {
        this.floorsBR = floorsBR;
    }

    public EnergyCertificateFilter getEnergyCertificate() {
        return energyCertificate;
    }

    public void setEnergyCertificate(EnergyCertificateFilter energyCertificate) {
        this.energyCertificate = energyCertificate;
    }

    @Override
    public String toString() {
        return "BuildingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (solarSurface != null ? "solarSurface=" + solarSurface + ", " : "") +
                (m2Edified != null ? "m2Edified=" + m2Edified + ", " : "") +
                (floorsSR != null ? "floorsSR=" + floorsSR + ", " : "") +
                (floorsBR != null ? "floorsBR=" + floorsBR + ", " : "") +
                (energyCertificate != null ? "energyCertificate=" + energyCertificate + ", " : "") +
            "}";
    }

}
