package org.agilekip.tutorials.travelcomplete.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.agilekip.tutorials.travelcomplete.domain.TaskSelectCar;
import org.agilekip.tutorials.travelcomplete.repository.TaskSelectCarRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.TaskSelectCarDTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.TaskSelectCarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaskSelectCar}.
 */
@Service
@Transactional
public class TaskSelectCarService {

    private final Logger log = LoggerFactory.getLogger(TaskSelectCarService.class);

    private final TaskSelectCarRepository taskSelectCarRepository;

    private final TaskSelectCarMapper taskSelectCarMapper;

    public TaskSelectCarService(TaskSelectCarRepository taskSelectCarRepository, TaskSelectCarMapper taskSelectCarMapper) {
        this.taskSelectCarRepository = taskSelectCarRepository;
        this.taskSelectCarMapper = taskSelectCarMapper;
    }

    /**
     * Save a taskSelectCar.
     *
     * @param taskSelectCarDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskSelectCarDTO save(TaskSelectCarDTO taskSelectCarDTO) {
        log.debug("Request to save TaskSelectCar : {}", taskSelectCarDTO);
        TaskSelectCar taskSelectCar = taskSelectCarMapper.toEntity(taskSelectCarDTO);
        taskSelectCar = taskSelectCarRepository.save(taskSelectCar);
        return taskSelectCarMapper.toDto(taskSelectCar);
    }

    /**
     * Partially update a taskSelectCar.
     *
     * @param taskSelectCarDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TaskSelectCarDTO> partialUpdate(TaskSelectCarDTO taskSelectCarDTO) {
        log.debug("Request to partially update TaskSelectCar : {}", taskSelectCarDTO);

        return taskSelectCarRepository
            .findById(taskSelectCarDTO.getId())
            .map(
                existingTaskSelectCar -> {
                    taskSelectCarMapper.partialUpdate(existingTaskSelectCar, taskSelectCarDTO);
                    return existingTaskSelectCar;
                }
            )
            .map(taskSelectCarRepository::save)
            .map(taskSelectCarMapper::toDto);
    }

    /**
     * Get all the taskSelectCars.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TaskSelectCarDTO> findAll() {
        log.debug("Request to get all TaskSelectCars");
        return taskSelectCarRepository.findAll().stream().map(taskSelectCarMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one taskSelectCar by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskSelectCarDTO> findOne(Long id) {
        log.debug("Request to get TaskSelectCar : {}", id);
        return taskSelectCarRepository.findById(id).map(taskSelectCarMapper::toDto);
    }

    /**
     * Delete the taskSelectCar by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskSelectCar : {}", id);
        taskSelectCarRepository.deleteById(id);
    }
}
