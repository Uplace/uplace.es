package com.arnaugarcia.uplace.repository;

import com.arnaugarcia.uplace.domain.Photo;
import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.service.dto.MarkerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Spring Data JPA repository for the Property entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropertyRepository<T extends Property> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    @Query("select distinct property from Property property left join fetch property.managers")
    // @Query(value = "SELECT * FROM property LEFT JOIN photo ON property.id = photo.property_id LEFT JOIN location ON property.location_id = location.id", nativeQuery = true)
    List<T> findAllWithEagerRelationships();

    @Query("select property from Property property left join fetch property.managers where property.id =:id")
    T findOneWithEagerRelationships(@Param("id") Long id);

    @EntityGraph(value = "graph.PropertyLocation", type = EntityGraph.EntityGraphType.LOAD)
    @Override
    Page<T> findAll(Pageable pageable);

    /**
     * Query to get a property by reference
     *
     * @param reference of the property to search
     * @return property if found or null if not
     */
    @Query("SELECT p FROM Property p left join fetch p.managers where p.reference = :reference")
    T findByReference(@Param("reference") String reference);

    List<T> findByReferenceIn(Collection<String> references);

    @Query("SELECT p FROM Property p ORDER BY p.created DESC")
    Page<T> findLastProperties(Pageable pageable);

    /**
     * Query to get the markers of all Properties
     *
     * @return a List of markers
     */
    // TODO : AND p.location.hide != null
    @Query("SELECT new com.arnaugarcia.uplace.service.dto.MarkerDTO(p.reference, p.price, p.updated, p.location.latitude, p.location.longitude, p.propertyType) FROM Property p WHERE p.location is not null and p.visible = true and p.location.hide = false")
    List<MarkerDTO> findAllMarkers();


    /**
     * Query to get the markers of all Properties
     *
     * @return a List of markers
     */
    // TODO : AND p.location.hide != null
    @Query("SELECT photo from Photo photo where photo.property.reference = :reference and photo.thumbnail = true")
    List<Photo> findThumbnailByReference(@Param("reference") String reference);

    /**
     * Query to get the prices of all Properties
     *
     * @return a List of integer
     */
    @Query("select distinct property.price from Property property")
    List<Double> findAllPrices();

    /**
     * Query to get the types of all Properties
     *
     * @return a List of strings
     */
    @Query("select distinct property.propertyType from Property property")
    List<String> findAllTypes();

}
