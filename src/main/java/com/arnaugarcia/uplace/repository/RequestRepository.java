package com.arnaugarcia.uplace.repository;

import com.arnaugarcia.uplace.domain.Request;
import com.arnaugarcia.uplace.domain.enumeration.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Request entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @EntityGraph(value = "graph.requestPropertyLocation", type = EntityGraph.EntityGraphType.FETCH)
    @Override
    Page<Request> findAll(Pageable pageable);

    @Query("select request from Request request where request.reference = :reference")
    Request findOne(@Param("reference") String reference);

    @Modifying
    @Query("delete from Request request where request.reference = :reference")
    void delete(@Param("reference") String reference);

    @Modifying
    @Query("update Request request set request.requestStatus = :status where request.reference = :reference ")
    void updateStatus(@Param("reference") String reference, @Param("status") RequestStatus requestStatus);
}
