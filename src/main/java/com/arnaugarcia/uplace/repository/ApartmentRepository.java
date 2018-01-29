package com.arnaugarcia.uplace.repository;

import com.arnaugarcia.uplace.domain.Apartment;
import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.domain.enumeration.ApartmentType;
import org.hibernate.validator.internal.xml.PropertyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Apartment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long>, JpaSpecificationExecutor<Apartment>{

    @Query("SELECT p FROM Apartment p where p.reference = :reference")
    Apartment findByReference(@Param("reference") String reference);

    Page<Apartment> findAllByType(ApartmentType apartmentType, Pageable pageable);

    void deleteByReference(String reference);
}
