package org.agilekip.tutorials.travelcomplete.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.agilekip.tutorials.travelcomplete.domain.HotelRoom;
import org.agilekip.tutorials.travelcomplete.repository.HotelRoomRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.HotelRoomDTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.HotelRoomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HotelRoom}.
 */
@Service
@Transactional
public class HotelRoomService {

    private final Logger log = LoggerFactory.getLogger(HotelRoomService.class);

    private final HotelRoomRepository hotelRoomRepository;

    private final HotelRoomMapper hotelRoomMapper;

    public HotelRoomService(HotelRoomRepository hotelRoomRepository, HotelRoomMapper hotelRoomMapper) {
        this.hotelRoomRepository = hotelRoomRepository;
        this.hotelRoomMapper = hotelRoomMapper;
    }

    /**
     * Save a hotelRoom.
     *
     * @param hotelRoomDTO the entity to save.
     * @return the persisted entity.
     */
    public HotelRoomDTO save(HotelRoomDTO hotelRoomDTO) {
        log.debug("Request to save HotelRoom : {}", hotelRoomDTO);
        HotelRoom hotelRoom = hotelRoomMapper.toEntity(hotelRoomDTO);
        hotelRoom = hotelRoomRepository.save(hotelRoom);
        return hotelRoomMapper.toDto(hotelRoom);
    }

    /**
     * Partially update a hotelRoom.
     *
     * @param hotelRoomDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HotelRoomDTO> partialUpdate(HotelRoomDTO hotelRoomDTO) {
        log.debug("Request to partially update HotelRoom : {}", hotelRoomDTO);

        return hotelRoomRepository
            .findById(hotelRoomDTO.getId())
            .map(
                existingHotelRoom -> {
                    hotelRoomMapper.partialUpdate(existingHotelRoom, hotelRoomDTO);
                    return existingHotelRoom;
                }
            )
            .map(hotelRoomRepository::save)
            .map(hotelRoomMapper::toDto);
    }

    /**
     * Get all the hotelRooms.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<HotelRoomDTO> findAll() {
        log.debug("Request to get all HotelRooms");
        return hotelRoomRepository.findAll().stream().map(hotelRoomMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one hotelRoom by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HotelRoomDTO> findOne(Long id) {
        log.debug("Request to get HotelRoom : {}", id);
        return hotelRoomRepository.findById(id).map(hotelRoomMapper::toDto);
    }

    /**
     * Delete the hotelRoom by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HotelRoom : {}", id);
        hotelRoomRepository.deleteById(id);
    }
}
