package com.arnaugarcia.uplace.repository;

import com.arnaugarcia.uplace.domain.Location;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Location entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("select location.city from Location location")
    List<String> findAllCities();
}
