package com.arnaugarcia.uplace.repository;

import com.arnaugarcia.uplace.domain.Bussiness;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Bussiness entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BussinessRepository extends JpaRepository<Bussiness, Long> {

}
