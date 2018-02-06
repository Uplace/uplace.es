package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Apartment;
import com.arnaugarcia.uplace.domain.Notification;
import com.arnaugarcia.uplace.domain.User;
import com.arnaugarcia.uplace.domain.enumeration.ApartmentType;
import com.arnaugarcia.uplace.domain.enumeration.NotificationType;
import com.arnaugarcia.uplace.repository.ApartmentRepository;
import com.arnaugarcia.uplace.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Implementation for managing Apartment.
 */
@Service
@Transactional
public class ApartmentService {

    private final Logger log = LoggerFactory.getLogger(ApartmentService.class);

    private static final String ENTITY_FLAT = "FLAT";

    private final ApartmentRepository apartmentRepository;

    private final PropertyService propertyService;

    private final NotificationService notificationService;

    private final UserService userService;

    public ApartmentService(ApartmentRepository apartmentRepository, PropertyService propertyService, NotificationService notificationService, UserService userService) {
        this.apartmentRepository = apartmentRepository;
        this.propertyService = propertyService;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    /**
     * Save a apartment.
     *
     * @param apartmentType the type of the Apartment
     * @param apartment the entity to save
     * @return the persisted entity
     */
    public Apartment save(ApartmentType apartmentType, Apartment apartment) {
        log.debug("Request to save Apartment : {}", apartment);

        apartment.setReference(propertyService.createReference());
        apartment.setType(apartmentType);
        // Si tiene id o no haces el created o no

        if (apartment.getId() != null) { //Tiene id
            apartment.setUpdated(ZonedDateTime.now());
        } else { //No tiene id
            apartment.setCreated(ZonedDateTime.now());
        }


        // TODO: send a notification to the user
        return apartmentRepository.save(apartment);
    }

    /**
     * Get all the apartments.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Apartment> findAll() {
        log.debug("Request to get all Apartments");
        return apartmentRepository.findAll();
    }

    /**
     * Get all apartments by type.
     *
     * @param apartmentType the type of the apartment
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Apartment> findAllByApartmentType(ApartmentType apartmentType) {
        log.debug("Request to get all Apartments by type and criteria");
        return apartmentRepository.findByType(apartmentType);
    }

    /**
     * Get all apartments by type and reference.
     *
     * @param apartmentType the type of the Apartment
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Apartment findByReference(ApartmentType apartmentType, String reference) {
        log.debug("Request to get all Apartments by type and criteria");
        return apartmentRepository.findByReferenceAndType(reference, apartmentType);
    }

    /**
     * Get all the flats
     *
     * @return the list of flats
     */
    /*@Transactional(readOnly = true)
    public Page<Apartment> findAllFlats(Pageable pageable) {
        log.debug("Request to get all flats");
        return null;
    }*/

    /**
     * Get all the flats
     *
     * @return the list of flats
     */
    /*public Photo findThumbnailByReference(String reference) {
        log.debug("Request to get all flats");
        Apartment apartment = apartmentRepository.findByReference(reference);
        if (apartment == null) {
            throw new BadRequestAlertException("FLAT", "No Apartment was found with this reference", "badreference");
        }
        Photo thumbnail = null;
        Set<Photo> photos = apartment.getPhotos();
        if (!photos.isEmpty()) {
            for (Photo photo : photos) {
                if (photo.isThumbnail()) thumbnail = photo;
            }
        }
        return thumbnail;
    }*/


    /**
     * Get a flat
     *
     * @return the flat of with the requested reference
     */
   /* @Transactional(readOnly = true)
    public Apartment findFlatByReference(String reference) {
        log.debug("Request to get all flats");
        Apartment apartment = apartmentRepository.findByReference(reference);
        ;
        if (apartment == null) {
            throw new BadRequestAlertException("Flat not found", ENTITY_FLAT, "badreference");
        } else if (!apartment.getType().equals(ApartmentType.FLATS)) {
            throw new BadRequestAlertException("The type must be 'FLAT' in order to retrieve a FLAT", ENTITY_FLAT, "badtype");
        }
        return apartment;
    }*/

    /**
     * Get one apartment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    /*@Transactional(readOnly = true)
    public Apartment findOne(Long id) {
        log.debug("Request to get Apartment : {}", id);
        return apartmentRepository.findOne(id);
    }*/

    /**
     * Delete the apartment by id.
     *
     * @param id the id of the entity
     */
    /*public void delete(Long id) {
        log.debug("Request to delete Apartment : {}", id);
        apartmentRepository.delete(id);
    }*/

    /**
     * Delete the apartment by id.
     *
     * @param reference the reference of the apartment
     */
    /*public void deleteByReference(String reference) {
        log.debug("Request to delete Apartment : {}", reference);
        apartmentRepository.deleteByReference(reference);
    }*/

}
