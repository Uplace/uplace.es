package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Apartment;
import com.arnaugarcia.uplace.domain.Photo;
import com.arnaugarcia.uplace.domain.enumeration.ApartmentType;
import com.arnaugarcia.uplace.repository.ApartmentRepository;
import com.arnaugarcia.uplace.repository.PropertyRepository;
import com.arnaugarcia.uplace.service.util.RandomUtil;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

/**
 * Service Implementation for managing Apartment.
 */
@Service
@Transactional
public class ApartmentService {

    private final Logger log = LoggerFactory.getLogger(ApartmentService.class);

    private static final String ENTITY_FLAT = "FLAT";

    private final ApartmentRepository apartmentRepository;
    private final PropertyRepository propertyRepository;

    private final PropertyService propertyService;

    public ApartmentService(ApartmentRepository apartmentRepository, PropertyRepository propertyRepository, PropertyService propertyService) {
        this.apartmentRepository = apartmentRepository;
        this.propertyRepository = propertyRepository;
        this.propertyService = propertyService;
    }

    /**
     * Save a apartment.
     *
     * @param apartment the entity to save
     * @return the persisted entity
     */
    public Apartment save(Apartment apartment) {
        log.debug("Request to save Apartment : {}", apartment);

        apartment.setReference(propertyService.createReference());

        // Si tiene id o no haces el created o no
        if (apartment.getId() != null) { //Tiene id
            apartment.setUpdated(ZonedDateTime.now());
        } else { //No tiene id
            apartment.setCreated(ZonedDateTime.now());
        }

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
     * Get all the flats
     *
     * @return the list of flats
     */
    @Transactional(readOnly = true)
    public Page<Apartment> findAllFlats(Pageable pageable) {
        log.debug("Request to get all flats");
        return apartmentRepository.findAllByType(ApartmentType.FLAT, pageable);
    }

    /**
     * Get all the flats
     *
     * @return the list of flats
     */
    public Photo findThumbnailByReference(String reference) {
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
    }


    /**
     * Get a flat
     *
     * @return the flat of with the requested reference
     */
    @Transactional(readOnly = true)
    public Apartment findFlatByReference(String reference) {
        log.debug("Request to get all flats");
        Apartment apartment = apartmentRepository.findByReference(reference);
        ;
        if (apartment == null) {
            throw new BadRequestAlertException("Flat not found", ENTITY_FLAT, "badreference");
        } else if (!apartment.getType().equals(ApartmentType.FLAT)) {
            throw new BadRequestAlertException("The type must be 'FLAT' in order to retrieve a FLAT", ENTITY_FLAT, "badtype");
        }
        return apartment;
    }

    /**
     * Get one apartment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Apartment findOne(Long id) {
        log.debug("Request to get Apartment : {}", id);
        return apartmentRepository.findOne(id);
    }

    /**
     * Delete the apartment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Apartment : {}", id);
        apartmentRepository.delete(id);
    }

    /**
     * Delete the apartment by id.
     *
     * @param reference the reference of the apartment
     */
    public void deleteByReference(String reference) {
        log.debug("Request to delete Apartment : {}", reference);
        apartmentRepository.deleteByReference(reference);
    }


    /**
     * Create unique reference randomly.
     *
     * @return reference created
     */
    public String createReference() {
        String reference;
        do {
            reference = RandomUtil.generateReference().toUpperCase();
            log.debug("Generating reference: " + reference);
        } while (propertyRepository.findByReference(reference) != null);
        return reference;
    }

}
