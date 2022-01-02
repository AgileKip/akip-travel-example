package org.agilekip.tutorials.travelcomplete.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.agilekip.tutorials.travelcomplete.domain.TaskSelectHotel;
import org.agilekip.tutorials.travelcomplete.repository.TaskSelectHotelRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.TaskSelectHotelDTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.TaskSelectHotelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaskSelectHotel}.
 */
@Service
@Transactional
public class TaskSelectHotelService {

    private final Logger log = LoggerFactory.getLogger(TaskSelectHotelService.class);

    private final TaskSelectHotelRepository taskSelectHotelRepository;

    private final TaskSelectHotelMapper taskSelectHotelMapper;

    public TaskSelectHotelService(TaskSelectHotelRepository taskSelectHotelRepository, TaskSelectHotelMapper taskSelectHotelMapper) {
        this.taskSelectHotelRepository = taskSelectHotelRepository;
        this.taskSelectHotelMapper = taskSelectHotelMapper;
    }

    /**
     * Save a taskSelectHotel.
     *
     * @param taskSelectHotelDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskSelectHotelDTO save(TaskSelectHotelDTO taskSelectHotelDTO) {
        log.debug("Request to save TaskSelectHotel : {}", taskSelectHotelDTO);
        TaskSelectHotel taskSelectHotel = taskSelectHotelMapper.toEntity(taskSelectHotelDTO);
        taskSelectHotel = taskSelectHotelRepository.save(taskSelectHotel);
        return taskSelectHotelMapper.toDto(taskSelectHotel);
    }

    /**
     * Partially update a taskSelectHotel.
     *
     * @param taskSelectHotelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TaskSelectHotelDTO> partialUpdate(TaskSelectHotelDTO taskSelectHotelDTO) {
        log.debug("Request to partially update TaskSelectHotel : {}", taskSelectHotelDTO);

        return taskSelectHotelRepository
            .findById(taskSelectHotelDTO.getId())
            .map(
                existingTaskSelectHotel -> {
                    taskSelectHotelMapper.partialUpdate(existingTaskSelectHotel, taskSelectHotelDTO);
                    return existingTaskSelectHotel;
                }
            )
            .map(taskSelectHotelRepository::save)
            .map(taskSelectHotelMapper::toDto);
    }

    /**
     * Get all the taskSelectHotels.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TaskSelectHotelDTO> findAll() {
        log.debug("Request to get all TaskSelectHotels");
        return taskSelectHotelRepository
            .findAll()
            .stream()
            .map(taskSelectHotelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one taskSelectHotel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskSelectHotelDTO> findOne(Long id) {
        log.debug("Request to get TaskSelectHotel : {}", id);
        return taskSelectHotelRepository.findById(id).map(taskSelectHotelMapper::toDto);
    }

    /**
     * Delete the taskSelectHotel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskSelectHotel : {}", id);
        taskSelectHotelRepository.deleteById(id);
    }
}
