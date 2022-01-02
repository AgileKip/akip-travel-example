package org.agilekip.tutorials.travelcomplete.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.agilekip.tutorials.travelcomplete.domain.Airport;
import org.agilekip.tutorials.travelcomplete.repository.AirportRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.AirportDTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.AirportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Airport}.
 */
@Service
@Transactional
public class AirportService {

    private final Logger log = LoggerFactory.getLogger(AirportService.class);

    private final AirportRepository airportRepository;

    private final AirportMapper airportMapper;

    public AirportService(AirportRepository airportRepository, AirportMapper airportMapper) {
        this.airportRepository = airportRepository;
        this.airportMapper = airportMapper;
    }

    /**
     * Save a airport.
     *
     * @param airportDTO the entity to save.
     * @return the persisted entity.
     */
    public AirportDTO save(AirportDTO airportDTO) {
        log.debug("Request to save Airport : {}", airportDTO);
        Airport airport = airportMapper.toEntity(airportDTO);
        airport = airportRepository.save(airport);
        return airportMapper.toDto(airport);
    }

    /**
     * Partially update a airport.
     *
     * @param airportDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AirportDTO> partialUpdate(AirportDTO airportDTO) {
        log.debug("Request to partially update Airport : {}", airportDTO);

        return airportRepository
            .findById(airportDTO.getId())
            .map(
                existingAirport -> {
                    airportMapper.partialUpdate(existingAirport, airportDTO);
                    return existingAirport;
                }
            )
            .map(airportRepository::save)
            .map(airportMapper::toDto);
    }

    /**
     * Get all the airports.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AirportDTO> findAll() {
        log.debug("Request to get all Airports");
        return airportRepository.findAll().stream().map(airportMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one airport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AirportDTO> findOne(Long id) {
        log.debug("Request to get Airport : {}", id);
        return airportRepository.findById(id).map(airportMapper::toDto);
    }

    /**
     * Delete the airport by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Airport : {}", id);
        airportRepository.deleteById(id);
    }
}
