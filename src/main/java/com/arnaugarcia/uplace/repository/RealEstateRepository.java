package com.arnaugarcia.uplace.repository;

import com.arnaugarcia.uplace.domain.RealEstate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RealEstate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate, Long> {

}
