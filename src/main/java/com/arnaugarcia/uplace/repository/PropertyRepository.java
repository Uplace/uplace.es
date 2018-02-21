package com.arnaugarcia.uplace.repository;

import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.service.dto.MarkerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.net.PortUnreachableException;
import java.util.List;

/**
 * Spring Data JPA repository for the Property entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {
    @Query("select distinct property from Property property left join fetch property.managers")
    List<Property> findAllWithEagerRelationships();

    @Query("select property from Property property left join fetch property.managers where property.id =:id")
    Property findOneWithEagerRelationships(@Param("id") Long id);

    /**
     * Query to get a property by reference
     *
     * @param reference of the property to search
     * @return property if found or null if not
     */
    @Query("SELECT p FROM Property p where p.reference = :reference")
    Property findByReference(@Param("reference") String reference);

    @Query("SELECT p FROM Property p ORDER BY p.created DESC")
    Page<Property> findLastProperties(Pageable pageable);

    /**
     * Query to get the markers of all Properties
     *
     * @return a List of markers
     */
    // TODO : AND p.location.hide != null
    @Query("SELECT new com.arnaugarcia.uplace.service.dto.MarkerDTO(p.reference, p.location.latitude, p.location.longitude) FROM Property p WHERE p.location is not null and p.visible = true")
    List<MarkerDTO> findAllMarkers();

}
