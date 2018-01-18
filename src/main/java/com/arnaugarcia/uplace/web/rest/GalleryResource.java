package com.arnaugarcia.uplace.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.arnaugarcia.uplace.domain.Gallery;

import com.arnaugarcia.uplace.repository.GalleryRepository;
import com.arnaugarcia.uplace.web.rest.errors.BadRequestAlertException;
import com.arnaugarcia.uplace.web.rest.util.HeaderUtil;
import com.arnaugarcia.uplace.service.dto.GalleryDTO;
import com.arnaugarcia.uplace.service.mapper.GalleryMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Gallery.
 */
@RestController
@RequestMapping("/api")
public class GalleryResource {

    private final Logger log = LoggerFactory.getLogger(GalleryResource.class);

    private static final String ENTITY_NAME = "gallery";

    private final GalleryRepository galleryRepository;

    private final GalleryMapper galleryMapper;

    public GalleryResource(GalleryRepository galleryRepository, GalleryMapper galleryMapper) {
        this.galleryRepository = galleryRepository;
        this.galleryMapper = galleryMapper;
    }

    /**
     * POST  /galleries : Create a new gallery.
     *
     * @param galleryDTO the galleryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new galleryDTO, or with status 400 (Bad Request) if the gallery has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/galleries")
    @Timed
    public ResponseEntity<GalleryDTO> createGallery(@RequestBody GalleryDTO galleryDTO) throws URISyntaxException {
        log.debug("REST request to save Gallery : {}", galleryDTO);
        if (galleryDTO.getId() != null) {
            throw new BadRequestAlertException("A new gallery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gallery gallery = galleryMapper.toEntity(galleryDTO);
        gallery = galleryRepository.save(gallery);
        GalleryDTO result = galleryMapper.toDto(gallery);
        return ResponseEntity.created(new URI("/api/galleries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /galleries : Updates an existing gallery.
     *
     * @param galleryDTO the galleryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated galleryDTO,
     * or with status 400 (Bad Request) if the galleryDTO is not valid,
     * or with status 500 (Internal Server Error) if the galleryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/galleries")
    @Timed
    public ResponseEntity<GalleryDTO> updateGallery(@RequestBody GalleryDTO galleryDTO) throws URISyntaxException {
        log.debug("REST request to update Gallery : {}", galleryDTO);
        if (galleryDTO.getId() == null) {
            return createGallery(galleryDTO);
        }
        Gallery gallery = galleryMapper.toEntity(galleryDTO);
        gallery = galleryRepository.save(gallery);
        GalleryDTO result = galleryMapper.toDto(gallery);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, galleryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /galleries : get all the galleries.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of galleries in body
     */
    @GetMapping("/galleries")
    @Timed
    public List<GalleryDTO> getAllGalleries(@RequestParam(required = false) String filter) {
        if ("property-is-null".equals(filter)) {
            log.debug("REST request to get all Gallerys where property is null");
            return StreamSupport
                .stream(galleryRepository.findAll().spliterator(), false)
                .filter(gallery -> gallery.getProperty() == null)
                .map(galleryMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        }
        log.debug("REST request to get all Galleries");
        List<Gallery> galleries = galleryRepository.findAll();
        return galleryMapper.toDto(galleries);
        }

    /**
     * GET  /galleries/:id : get the "id" gallery.
     *
     * @param id the id of the galleryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the galleryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/galleries/{id}")
    @Timed
    public ResponseEntity<GalleryDTO> getGallery(@PathVariable Long id) {
        log.debug("REST request to get Gallery : {}", id);
        Gallery gallery = galleryRepository.findOne(id);
        GalleryDTO galleryDTO = galleryMapper.toDto(gallery);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(galleryDTO));
    }

    /**
     * DELETE  /galleries/:id : delete the "id" gallery.
     *
     * @param id the id of the galleryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/galleries/{id}")
    @Timed
    public ResponseEntity<Void> deleteGallery(@PathVariable Long id) {
        log.debug("REST request to delete Gallery : {}", id);
        galleryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
