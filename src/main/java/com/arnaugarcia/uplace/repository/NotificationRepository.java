package com.arnaugarcia.uplace.repository;

import com.arnaugarcia.uplace.domain.Notification;
import com.arnaugarcia.uplace.domain.enumeration.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
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
    List<Notification> findByUserIsCurrentUser();

    @Query("select notification from Notification notification where notification.user.login = ?#{principal.username}")
    Page<Notification> findByUserIsCurrentUserAndReadFalse(Pageable pageable);

    Page<Notification> findAllByType(NotificationType type, Pageable pageable);

    @Query("select notification from Notification notification where notification.user.login = ?#{principal.username} and notification.read = :status and notification.type = 'NOTIFICATION'")
    Page<Notification> findAllNotificationsByCurrentUserAndStatus(@Param("status") Boolean status, Pageable pageable);

    @Query("select notification from Notification notification where notification.user.login = ?#{principal.username} and notification.type = 'NOTIFICATION'")
    Page<Notification> findAllNotificationsByCurrentUser(Pageable pageable);

    @Query("select notification from Notification notification where notification.type = 'NOTIFICATION'")
    Page<Notification> findAllNotifications(Pageable pageable);

    @Query("select notification from Notification notification where notification.type = 'REQUEST'")
    Page<Notification> findAllRequests(Pageable pageable);

}
