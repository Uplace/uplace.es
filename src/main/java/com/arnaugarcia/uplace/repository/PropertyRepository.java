package com.arnaugarcia.uplace.repository;

import com.arnaugarcia.uplace.domain.Property;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
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

}
