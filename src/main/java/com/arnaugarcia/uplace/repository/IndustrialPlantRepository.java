package com.arnaugarcia.uplace.repository;

import com.arnaugarcia.uplace.domain.IndustrialPlant;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IndustrialPlant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndustrialPlantRepository extends JpaRepository<IndustrialPlant, Long> {

}
