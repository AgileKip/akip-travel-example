package org.agilekip.tutorials.travelcomplete.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.agilekip.tutorials.travelcomplete.domain.TaskSelectFlight;
import org.agilekip.tutorials.travelcomplete.repository.TaskSelectFlightRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.TaskSelectFlightDTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.TaskSelectFlightMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaskSelectFlight}.
 */
@Service
@Transactional
public class TaskSelectFlightService {

    private final Logger log = LoggerFactory.getLogger(TaskSelectFlightService.class);

    private final TaskSelectFlightRepository taskSelectFlightRepository;

    private final TaskSelectFlightMapper taskSelectFlightMapper;

    public TaskSelectFlightService(TaskSelectFlightRepository taskSelectFlightRepository, TaskSelectFlightMapper taskSelectFlightMapper) {
        this.taskSelectFlightRepository = taskSelectFlightRepository;
        this.taskSelectFlightMapper = taskSelectFlightMapper;
    }

    /**
     * Save a taskSelectFlight.
     *
     * @param taskSelectFlightDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskSelectFlightDTO save(TaskSelectFlightDTO taskSelectFlightDTO) {
        log.debug("Request to save TaskSelectFlight : {}", taskSelectFlightDTO);
        TaskSelectFlight taskSelectFlight = taskSelectFlightMapper.toEntity(taskSelectFlightDTO);
        taskSelectFlight = taskSelectFlightRepository.save(taskSelectFlight);
        return taskSelectFlightMapper.toDto(taskSelectFlight);
    }

    /**
     * Partially update a taskSelectFlight.
     *
     * @param taskSelectFlightDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TaskSelectFlightDTO> partialUpdate(TaskSelectFlightDTO taskSelectFlightDTO) {
        log.debug("Request to partially update TaskSelectFlight : {}", taskSelectFlightDTO);

        return taskSelectFlightRepository
            .findById(taskSelectFlightDTO.getId())
            .map(
                existingTaskSelectFlight -> {
                    taskSelectFlightMapper.partialUpdate(existingTaskSelectFlight, taskSelectFlightDTO);
                    return existingTaskSelectFlight;
                }
            )
            .map(taskSelectFlightRepository::save)
            .map(taskSelectFlightMapper::toDto);
    }

    /**
     * Get all the taskSelectFlights.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TaskSelectFlightDTO> findAll() {
        log.debug("Request to get all TaskSelectFlights");
        return taskSelectFlightRepository
            .findAll()
            .stream()
            .map(taskSelectFlightMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one taskSelectFlight by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskSelectFlightDTO> findOne(Long id) {
        log.debug("Request to get TaskSelectFlight : {}", id);
        return taskSelectFlightRepository.findById(id).map(taskSelectFlightMapper::toDto);
    }

    /**
     * Delete the taskSelectFlight by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskSelectFlight : {}", id);
        taskSelectFlightRepository.deleteById(id);
    }
}
