package org.agilekip.tutorials.travelcomplete.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.agilekip.tutorials.travelcomplete.domain.Flight;
import org.agilekip.tutorials.travelcomplete.repository.FlightRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.FlightDTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.FlightMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Flight}.
 */
@Service
@Transactional
public class FlightService {

    private final Logger log = LoggerFactory.getLogger(FlightService.class);

    private final FlightRepository flightRepository;

    private final FlightMapper flightMapper;

    public FlightService(FlightRepository flightRepository, FlightMapper flightMapper) {
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
    }

    /**
     * Save a flight.
     *
     * @param flightDTO the entity to save.
     * @return the persisted entity.
     */
    public FlightDTO save(FlightDTO flightDTO) {
        log.debug("Request to save Flight : {}", flightDTO);
        Flight flight = flightMapper.toEntity(flightDTO);
        flight = flightRepository.save(flight);
        return flightMapper.toDto(flight);
    }

    /**
     * Partially update a flight.
     *
     * @param flightDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FlightDTO> partialUpdate(FlightDTO flightDTO) {
        log.debug("Request to partially update Flight : {}", flightDTO);

        return flightRepository
            .findById(flightDTO.getId())
            .map(
                existingFlight -> {
                    flightMapper.partialUpdate(existingFlight, flightDTO);
                    return existingFlight;
                }
            )
            .map(flightRepository::save)
            .map(flightMapper::toDto);
    }

    /**
     * Get all the flights.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FlightDTO> findAll() {
        log.debug("Request to get all Flights");
        return flightRepository.findAll().stream().map(flightMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one flight by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FlightDTO> findOne(Long id) {
        log.debug("Request to get Flight : {}", id);
        return flightRepository.findById(id).map(flightMapper::toDto);
    }

    /**
     * Delete the flight by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Flight : {}", id);
        flightRepository.deleteById(id);
    }
}
