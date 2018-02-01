package com.arnaugarcia.uplace.service.dto;

import java.io.Serializable;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Hotel entity. This class is used in HotelResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /hotels?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HotelCriteria extends PropertyCriteria implements Serializable {
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


    private LongFilter id;

    private IntegerFilter solarSurface;

    private IntegerFilter m2Edified;

    private IntegerFilter numberRooms;

    private SelectFilter operator;

    private SelectFilter pool;

    private SelectFilter spa;

    private SelectFilter conferenceRoom;

    private IntegerFilter floorsSR;

    private IntegerFilter floorsBR;

    private EnergyCertificateFilter energyCertificate;

    public HotelCriteria() {
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

    public IntegerFilter getm2Edified() {
        return m2Edified;
    }

    public void setm2Edified(IntegerFilter m2Edified) {
        this.m2Edified = m2Edified;
    }

    public IntegerFilter getNumberRooms() {
        return numberRooms;
    }

    public void setNumberRooms(IntegerFilter numberRooms) {
        this.numberRooms = numberRooms;
    }

    public SelectFilter getOperator() {
        return operator;
    }

    public void setOperator(SelectFilter operator) {
        this.operator = operator;
    }

    public SelectFilter getPool() {
        return pool;
    }

    public void setPool(SelectFilter pool) {
        this.pool = pool;
    }

    public SelectFilter getSpa() {
        return spa;
    }

    public void setSpa(SelectFilter spa) {
        this.spa = spa;
    }

    public SelectFilter getConferenceRoom() {
        return conferenceRoom;
    }

    public void setConferenceRoom(SelectFilter conferenceRoom) {
        this.conferenceRoom = conferenceRoom;
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
        return "HotelCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (solarSurface != null ? "solarSurface=" + solarSurface + ", " : "") +
                (m2Edified != null ? "m2Edified=" + m2Edified + ", " : "") +
                (numberRooms != null ? "numberRooms=" + numberRooms + ", " : "") +
                (operator != null ? "operator=" + operator + ", " : "") +
                (pool != null ? "pool=" + pool + ", " : "") +
                (spa != null ? "spa=" + spa + ", " : "") +
                (conferenceRoom != null ? "conferenceRoom=" + conferenceRoom + ", " : "") +
                (floorsSR != null ? "floorsSR=" + floorsSR + ", " : "") +
                (floorsBR != null ? "floorsBR=" + floorsBR + ", " : "") +
                (energyCertificate != null ? "energyCertificate=" + energyCertificate + ", " : "") +
            "}";
    }

}
