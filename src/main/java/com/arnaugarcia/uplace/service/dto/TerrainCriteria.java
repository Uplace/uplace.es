package com.arnaugarcia.uplace.service.dto;

import java.io.Serializable;
import com.arnaugarcia.uplace.domain.enumeration.TerrainType;
import com.arnaugarcia.uplace.domain.enumeration.Select;
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

    /**
     * Class for filtering Select
     */
    public static class SelectFilter extends Filter<Select> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private TerrainTypeFilter terrainType;

    private SelectFilter nearTransport;

    public TerrainCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TerrainTypeFilter getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(TerrainTypeFilter terrainType) {
        this.terrainType = terrainType;
    }

    public SelectFilter getNearTransport() {
        return nearTransport;
    }

    public void setNearTransport(SelectFilter nearTransport) {
        this.nearTransport = nearTransport;
    }

    @Override
    public String toString() {
        return "TerrainCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (terrainType != null ? "terrainType=" + terrainType + ", " : "") +
                (nearTransport != null ? "nearTransport=" + nearTransport + ", " : "") +
            "}";
    }

}
