package com.arnaugarcia.uplace.service;

import com.arnaugarcia.uplace.domain.Request;
import com.arnaugarcia.uplace.domain.enumeration.RequestStatus;
import com.arnaugarcia.uplace.repository.RequestRepository;
import com.arnaugarcia.uplace.service.util.RandomUtil;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.errors.ErrorConstants;
import org.hibernate.loader.plan.exec.process.internal.EntityReturnReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Request.
 */
@Service
@Transactional
public class RequestService {

    private final Logger log = LoggerFactory.getLogger(RequestService.class);

    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    /**
     * Save a request.
     *
     * @param request the entity to save
     * @return the persisted entity
     */
    public Request save(Request request) {
        log.debug("Request to save Request : {}", request);
        return requestRepository.save(request);
    }

    /**
     * Get all the requests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Request> findAll(Pageable pageable) {
        log.debug("Request to get all Requests");
        return requestRepository.findAll(pageable);
    }

    /**
     * Get one request by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Request findOne(Long id) {
        log.debug("Request to get Request : {}", id);
        return requestRepository.findOne(id);
    }

    /**
     * Get one request by reference.
     *
     * @param reference the reference of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Request findOne(String reference) {
        log.debug("Request to get Request : {}", reference);
        return requestRepository.findOne(reference);
    }

    /**
     * Delete the request by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Request : {}", id);
        requestRepository.delete(id);
    }

    public void delete(String reference) {
        Request request = requestRepository.findOne(reference);
        if (request != null) {
            requestRepository.delete(reference);
        } else {
            throw new BadRequestAlertException("Reference not found", "REQUEST", ErrorConstants.ERR_BAD_REFERENCE);
        }
    }

    @Transactional()
    public Request updateStatus(String reference, RequestStatus status) {
        Request request = requestRepository.findOne(reference);
        if (request != null) {
            request.setRequestStatus(status);
            return requestRepository.save(request);
        } else {
            throw new BadRequestAlertException("Reference not found", "REQUEST", ErrorConstants.ERR_BAD_REFERENCE);
        }
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
        } while (requestRepository.findOne(reference) != null);
        return reference;
    }
}
