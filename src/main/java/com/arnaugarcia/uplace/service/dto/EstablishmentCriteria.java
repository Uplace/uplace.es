package com.arnaugarcia.uplace.service.dto;

import java.io.Serializable;
import com.arnaugarcia.uplace.domain.enumeration.Select;
import com.arnaugarcia.uplace.domain.enumeration.UseEstablishment;
import com.arnaugarcia.uplace.domain.enumeration.EnergyCertificate;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Establishment entity. This class is used in EstablishmentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /establishments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EstablishmentCriteria implements Serializable {
    /**
     * Class for filtering Select
     */
    public static class SelectFilter extends Filter<Select> {
    }

    /**
     * Class for filtering UseEstablishment
     */
    public static class UseEstablishmentFilter extends Filter<UseEstablishment> {
    }

    /**
     * Class for filtering EnergyCertificate
     */
    public static class EnergyCertificateFilter extends Filter<EnergyCertificate> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter m2Facade;

    private SelectFilter bathroom;

    private UseEstablishmentFilter use;

    private EnergyCertificateFilter energyCertificate;

    public EstablishmentCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getm2Facade() {
        return m2Facade;
    }

    public void setm2Facade(IntegerFilter m2Facade) {
        this.m2Facade = m2Facade;
    }

    public SelectFilter getBathroom() {
        return bathroom;
    }

    public void setBathroom(SelectFilter bathroom) {
        this.bathroom = bathroom;
    }

    public UseEstablishmentFilter getUse() {
        return use;
    }

    public void setUse(UseEstablishmentFilter use) {
        this.use = use;
    }

    public EnergyCertificateFilter getEnergyCertificate() {
        return energyCertificate;
    }

    public void setEnergyCertificate(EnergyCertificateFilter energyCertificate) {
        this.energyCertificate = energyCertificate;
    }

    @Override
    public String toString() {
        return "EstablishmentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (m2Facade != null ? "m2Facade=" + m2Facade + ", " : "") +
                (bathroom != null ? "bathroom=" + bathroom + ", " : "") +
                (use != null ? "use=" + use + ", " : "") +
                (energyCertificate != null ? "energyCertificate=" + energyCertificate + ", " : "") +
            "}";
    }

}
