package com.arnaugarcia.uplace.service.dto;

import java.io.Serializable;
import com.arnaugarcia.uplace.domain.enumeration.TerrainType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Terrain entity. This class is used in TerrainResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /terrains?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TerrainCriteria implements Serializable {
    /**
     * Class for filtering TerrainType
     */
    public static class TerrainTypeFilter extends Filter<TerrainType> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter m2Buildable;

    private BooleanFilter buildable;

    private IntegerFilter buildableDepth;

    private IntegerFilter floorsSR;

    private IntegerFilter floorsBR;

    private DoubleFilter constructionCoefficient;

    private TerrainTypeFilter type;

    public TerrainCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getm2Buildable() {
        return m2Buildable;
    }

    public void setm2Buildable(IntegerFilter m2Buildable) {
        this.m2Buildable = m2Buildable;
    }

    public BooleanFilter getBuildable() {
        return buildable;
    }

    public void setBuildable(BooleanFilter buildable) {
        this.buildable = buildable;
    }

    public IntegerFilter getBuildableDepth() {
        return buildableDepth;
    }

    public void setBuildableDepth(IntegerFilter buildableDepth) {
        this.buildableDepth = buildableDepth;
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

    public DoubleFilter getConstructionCoefficient() {
        return constructionCoefficient;
    }

    public void setConstructionCoefficient(DoubleFilter constructionCoefficient) {
        this.constructionCoefficient = constructionCoefficient;
    }

    public TerrainTypeFilter getType() {
        return type;
    }

    public void setType(TerrainTypeFilter type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TerrainCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (m2Buildable != null ? "m2Buildable=" + m2Buildable + ", " : "") +
                (buildable != null ? "buildable=" + buildable + ", " : "") +
                (buildableDepth != null ? "buildableDepth=" + buildableDepth + ", " : "") +
                (floorsSR != null ? "floorsSR=" + floorsSR + ", " : "") +
                (floorsBR != null ? "floorsBR=" + floorsBR + ", " : "") +
                (constructionCoefficient != null ? "constructionCoefficient=" + constructionCoefficient + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
            "}";
    }

}
