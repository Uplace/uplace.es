package com.arnaugarcia.uplace.service.dto;

import java.io.Serializable;
import com.arnaugarcia.uplace.domain.enumeration.ParkingType;
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
 * Criteria class for the Parking entity. This class is used in ParkingResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /parkings?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ParkingCriteria implements Serializable {
    /**
     * Class for filtering ParkingType
     */
    public static class ParkingTypeFilter extends Filter<ParkingType> {
    }

    /**
     * Class for filtering Select
     */
    public static class SelectFilter extends Filter<Select> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private ParkingTypeFilter parkingType;

    private SelectFilter nearTransport;

    private SelectFilter surveillance;

    public ParkingCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ParkingTypeFilter getParkingType() {
        return parkingType;
    }

    public void setParkingType(ParkingTypeFilter parkingType) {
        this.parkingType = parkingType;
    }

    public SelectFilter getNearTransport() {
        return nearTransport;
    }

    public void setNearTransport(SelectFilter nearTransport) {
        this.nearTransport = nearTransport;
    }

    public SelectFilter getSurveillance() {
        return surveillance;
    }

    public void setSurveillance(SelectFilter surveillance) {
        this.surveillance = surveillance;
    }

    @Override
    public String toString() {
        return "ParkingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (parkingType != null ? "parkingType=" + parkingType + ", " : "") +
                (nearTransport != null ? "nearTransport=" + nearTransport + ", " : "") +
                (surveillance != null ? "surveillance=" + surveillance + ", " : "") +
            "}";
    }

}
