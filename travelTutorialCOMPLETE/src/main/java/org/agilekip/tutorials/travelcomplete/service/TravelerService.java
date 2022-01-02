package org.agilekip.tutorials.travelcomplete.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.agilekip.tutorials.travelcomplete.domain.Traveler;
import org.agilekip.tutorials.travelcomplete.repository.TravelerRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelerDTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.TravelerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Traveler}.
 */
@Service
@Transactional
public class TravelerService {

    private final Logger log = LoggerFactory.getLogger(TravelerService.class);

    private final TravelerRepository travelerRepository;

    private final TravelerMapper travelerMapper;

    public TravelerService(TravelerRepository travelerRepository, TravelerMapper travelerMapper) {
        this.travelerRepository = travelerRepository;
        this.travelerMapper = travelerMapper;
    }

    /**
     * Save a traveler.
     *
     * @param travelerDTO the entity to save.
     * @return the persisted entity.
     */
    public TravelerDTO save(TravelerDTO travelerDTO) {
        log.debug("Request to save Traveler : {}", travelerDTO);
        Traveler traveler = travelerMapper.toEntity(travelerDTO);
        traveler = travelerRepository.save(traveler);
        return travelerMapper.toDto(traveler);
    }

    /**
     * Partially update a traveler.
     *
     * @param travelerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TravelerDTO> partialUpdate(TravelerDTO travelerDTO) {
        log.debug("Request to partially update Traveler : {}", travelerDTO);

        return travelerRepository
            .findById(travelerDTO.getId())
            .map(
                existingTraveler -> {
                    travelerMapper.partialUpdate(existingTraveler, travelerDTO);
                    return existingTraveler;
                }
            )
            .map(travelerRepository::save)
            .map(travelerMapper::toDto);
    }

    /**
     * Get all the travelers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TravelerDTO> findAll() {
        log.debug("Request to get all Travelers");
        return travelerRepository.findAll().stream().map(travelerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one traveler by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TravelerDTO> findOne(Long id) {
        log.debug("Request to get Traveler : {}", id);
        return travelerRepository.findById(id).map(travelerMapper::toDto);
    }

    /**
     * Delete the traveler by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Traveler : {}", id);
        travelerRepository.deleteById(id);
    }
}
