package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Notification;
import com.arnaugarcia.uplace.domain.User;
import com.arnaugarcia.uplace.domain.enumeration.NotificationType;
import com.arnaugarcia.uplace.repository.NotificationRepository;
import com.arnaugarcia.uplace.repository.UserRepository;
import com.arnaugarcia.uplace.security.SecurityUtils;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
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

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a notification.
     *
     * @param notification the entity to save
     * @return the persisted entity
     */
    public Notification saveNotification(Notification notification) {
        log.debug("Request to save Notification : {}", notification);
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
            page = notificationRepository.findAll(pageable);
        } else {
            log.debug("Request to get a page of Notifications");
            page = notificationRepository.findByUserIsCurrentUserAndReadFalse(pageable);
        }

        return page;
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

        Optional<String> username = SecurityUtils.getCurrentUserLogin();

        Optional<User> user = Optional.empty();

        // If the user is logged
        if (username.isPresent()) {
             user = userRepository.findOneByLogin(username.get());
        }

        if (notification == null) {
            throw new BadRequestAlertException("No notification was found with this ID", ENTITY_NOTIFICATION, "badid");

        } else if (!user.isPresent()) {
            throw new BadRequestAlertException("No user was fou... WTF broh?", ENTITY_NOTIFICATION, "baduser");

        } else if (!notification.getType().equals(NotificationType.NOTIFICATION)) {
            throw new BadRequestAlertException("No notification was found with this ID", ENTITY_NOTIFICATION, "badid");
        }

        // If the notification belong to the logged user
        if (notification.getUser().equals(user.get())) {
            return notification;
        } else {
            throw new BadRequestAlertException("This notification doesn't belongs to you :)", ENTITY_NOTIFICATION, "badid");
        }

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
