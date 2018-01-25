package com.arnaugarcia.uplace.repository;

import com.arnaugarcia.uplace.domain.Terrain;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Terrain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TerrainRepository extends JpaRepository<Terrain, Long>, JpaSpecificationExecutor<Terrain> {

}
