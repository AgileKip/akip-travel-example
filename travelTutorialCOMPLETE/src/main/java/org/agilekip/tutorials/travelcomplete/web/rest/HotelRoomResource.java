package org.agilekip.tutorials.travelcomplete.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.agilekip.tutorials.travelcomplete.repository.HotelRoomRepository;
import org.agilekip.tutorials.travelcomplete.service.HotelRoomService;
import org.agilekip.tutorials.travelcomplete.service.dto.HotelRoomDTO;
import org.agilekip.tutorials.travelcomplete.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.agilekip.tutorials.travelcomplete.domain.HotelRoom}.
 */
@RestController
@RequestMapping("/api")
public class HotelRoomResource {

    private final Logger log = LoggerFactory.getLogger(HotelRoomResource.class);

    private static final String ENTITY_NAME = "hotelRoom";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HotelRoomService hotelRoomService;

    private final HotelRoomRepository hotelRoomRepository;

    public HotelRoomResource(HotelRoomService hotelRoomService, HotelRoomRepository hotelRoomRepository) {
        this.hotelRoomService = hotelRoomService;
        this.hotelRoomRepository = hotelRoomRepository;
    }

    /**
     * {@code POST  /hotel-rooms} : Create a new hotelRoom.
     *
     * @param hotelRoomDTO the hotelRoomDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hotelRoomDTO, or with status {@code 400 (Bad Request)} if the hotelRoom has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hotel-rooms")
    public ResponseEntity<HotelRoomDTO> createHotelRoom(@Valid @RequestBody HotelRoomDTO hotelRoomDTO) throws URISyntaxException {
        log.debug("REST request to save HotelRoom : {}", hotelRoomDTO);
        if (hotelRoomDTO.getId() != null) {
            throw new BadRequestAlertException("A new hotelRoom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HotelRoomDTO result = hotelRoomService.save(hotelRoomDTO);
        return ResponseEntity
            .created(new URI("/api/hotel-rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hotel-rooms/:id} : Updates an existing hotelRoom.
     *
     * @param id the id of the hotelRoomDTO to save.
     * @param hotelRoomDTO the hotelRoomDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotelRoomDTO,
     * or with status {@code 400 (Bad Request)} if the hotelRoomDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hotelRoomDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hotel-rooms/{id}")
    public ResponseEntity<HotelRoomDTO> updateHotelRoom(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HotelRoomDTO hotelRoomDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HotelRoom : {}, {}", id, hotelRoomDTO);
        if (hotelRoomDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotelRoomDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelRoomRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HotelRoomDTO result = hotelRoomService.save(hotelRoomDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hotelRoomDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hotel-rooms/:id} : Partial updates given fields of an existing hotelRoom, field will ignore if it is null
     *
     * @param id the id of the hotelRoomDTO to save.
     * @param hotelRoomDTO the hotelRoomDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotelRoomDTO,
     * or with status {@code 400 (Bad Request)} if the hotelRoomDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hotelRoomDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hotelRoomDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hotel-rooms/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<HotelRoomDTO> partialUpdateHotelRoom(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HotelRoomDTO hotelRoomDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HotelRoom partially : {}, {}", id, hotelRoomDTO);
        if (hotelRoomDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotelRoomDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelRoomRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HotelRoomDTO> result = hotelRoomService.partialUpdate(hotelRoomDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hotelRoomDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hotel-rooms} : get all the hotelRooms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hotelRooms in body.
     */
    @GetMapping("/hotel-rooms")
    public List<HotelRoomDTO> getAllHotelRooms() {
        log.debug("REST request to get all HotelRooms");
        return hotelRoomService.findAll();
    }

    /**
     * {@code GET  /hotel-rooms/:id} : get the "id" hotelRoom.
     *
     * @param id the id of the hotelRoomDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hotelRoomDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hotel-rooms/{id}")
    public ResponseEntity<HotelRoomDTO> getHotelRoom(@PathVariable Long id) {
        log.debug("REST request to get HotelRoom : {}", id);
        Optional<HotelRoomDTO> hotelRoomDTO = hotelRoomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hotelRoomDTO);
    }

    /**
     * {@code DELETE  /hotel-rooms/:id} : delete the "id" hotelRoom.
     *
     * @param id the id of the hotelRoomDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hotel-rooms/{id}")
    public ResponseEntity<Void> deleteHotelRoom(@PathVariable Long id) {
        log.debug("REST request to delete HotelRoom : {}", id);
        hotelRoomService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
