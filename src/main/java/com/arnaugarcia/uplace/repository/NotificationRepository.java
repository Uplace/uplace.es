package com.arnaugarcia.uplace.repository;

import com.arnaugarcia.uplace.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Notification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select notification from Notification notification where notification.user.login = ?#{principal.username}")
    Page<Notification> findByUserIsCurrentUser(Pageable pageable);

    @Query("select notification from Notification notification where notification.user.login = ?#{principal.username}")
    Page<Notification> findByUserIsCurrentUserAndReadFalse(Pageable pageable);

}
