package com.arnaugarcia.uplace.repository;

import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.domain.RealEstate;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import javax.persistence.NamedEntityGraph;
import java.util.List;


/**
 * Spring Data JPA repository for the RealEstate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RealEstateRepository<T extends Property> extends JpaRepository<RealEstate, Long> {

    @EntityGraph(value = "graph.PropertyAll", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select p from Property p where p.realEstate.reference = :reference")
    List<T> findPropertiesByReference(@Param("reference") String reference);

}
