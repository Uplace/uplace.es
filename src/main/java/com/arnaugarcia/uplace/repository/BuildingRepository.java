package com.arnaugarcia.uplace.repository;

import com.arnaugarcia.uplace.domain.Building;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Building entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuildingRepository extends JpaRepository<Building, Long>, JpaSpecificationExecutor<Building> {

    /**
     * Query to get a building by ID
     *
     * @param reference of the building to search
     * @return building if found or null if not
     */
    @Query("SELECT p FROM Building p where p.reference = :reference")
    Building findByReference(@Param("reference") String reference);
}
