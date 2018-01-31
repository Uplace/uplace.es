package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Notification;
import com.arnaugarcia.uplace.domain.User;
import com.arnaugarcia.uplace.domain.enumeration.NotificationType;
import com.arnaugarcia.uplace.repository.NotificationRepository;
import com.arnaugarcia.uplace.repository.UserRepository;
import com.arnaugarcia.uplace.security.AuthoritiesConstants;
import com.arnaugarcia.uplace.security.SecurityUtils;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service Implementation for managing Notification.
 */
@Service
@Transactional
public class NotificationService {

    private final static String ENTITY_NOTIFICATION = "NOTIFICATION";

    private final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository, UserService userService) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * Save a notification.
     *
     * @param notification the entity to save
     * @return the persisted entity
     *
     */
    public Notification saveNotification(Notification notification) {
        log.debug("Request to save Notification : {}", notification);

        //Getting the current user
        User user = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get();

        //If the current user hasn't the ROLE_ADMIN sets by default the user for security reasons
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            notification.setUser(user);
        }

        //Setting the type to notification
        notification.setType(NotificationType.NOTIFICATION);
        return notificationRepository.save(notification);
    }

    /**
     * Save a request.
     *
     * @param request the entity to save
     * @return the persisted entity
     */
    public Notification saveRequest(Notification request) {
        log.debug("Request to save Notification : {}", request);
        request.setType(NotificationType.REQUEST);
        return notificationRepository.save(request);
    }

    /**
     * Get all the notifications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Notification> findAllNotifications(Pageable pageable) {
        Page<Notification> page;

        if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            log.debug("Request to get a page of all notifications");
            page = notificationRepository.findAllNotifications(pageable);
        } else {
            log.debug("Request to get a page of Notifications by current User");
            page = notificationRepository.findAllNotificationsByCurrentUser(pageable);
        }

        return page;
    }

    /**
     * Get all the notifications.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Notification> findAllNotifications() {

        return notificationRepository.findAll();
    }

    /**
     * Get one notification by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Notification findOneNotification(Long id) {
        log.debug("Request to get Notification : {}", id);

        Notification notification = notificationRepository.findOne(id);

        User user = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get();

        if (notification == null || !notification.getType().equals(NotificationType.NOTIFICATION)) {
            throw new BadRequestAlertException("No notification was found with this ID", ENTITY_NOTIFICATION, "badid");
        }

        // If the notification.user does not match and isn't admin... error
        if (!notification.getUser().equals(user) && !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            throw new BadRequestAlertException("This notification doesn't belongs to you :)", ENTITY_NOTIFICATION, "baduser");
        }

        return notification;

    }

    /*
     * Delete the notification by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Notification : {}", id);
        notificationRepository.delete(id);
    }
}
