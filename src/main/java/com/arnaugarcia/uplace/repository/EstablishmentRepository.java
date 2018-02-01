package com.arnaugarcia.uplace.repository;

import com.arnaugarcia.uplace.domain.Establishment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Establishment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstablishmentRepository extends JpaRepository<Establishment, Long>, JpaSpecificationExecutor<Establishment> {

}
