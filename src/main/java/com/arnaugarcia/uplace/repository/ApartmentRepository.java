package com.arnaugarcia.uplace.repository;

import com.arnaugarcia.uplace.domain.Apartment;
import com.arnaugarcia.uplace.domain.Property;
import com.arnaugarcia.uplace.domain.enumeration.ApartmentType;
import org.hibernate.validator.internal.xml.PropertyType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Apartment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    Apartment findFirstByReference(String reference);

    List<Apartment> findAllByPropertyType(ApartmentType apartmentType);

    void deleteByReference(String reference);
}
