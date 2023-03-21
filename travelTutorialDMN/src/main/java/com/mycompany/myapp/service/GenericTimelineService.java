package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.GenericTimeline;
import com.mycompany.myapp.repository.GenericTimelineRepository;
import com.mycompany.myapp.service.dto.GenericTimelineDTO;
import com.mycompany.myapp.service.mapper.GenericTimelineMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GenericTimeline}.
 */
@Service
@Transactional
public class GenericTimelineService {

    private final Logger log = LoggerFactory.getLogger(GenericTimelineService.class);

    private final GenericTimelineRepository genericTimelineRepository;

    private final GenericTimelineMapper genericTimelineMapper;

    public GenericTimelineService(GenericTimelineRepository genericTimelineRepository, GenericTimelineMapper genericTimelineMapper) {
        this.genericTimelineRepository = genericTimelineRepository;
        this.genericTimelineMapper = genericTimelineMapper;
    }

    /**
     * Save a genericTimeline.
     *
     * @param genericTimelineDTO the entity to save.
     * @return the persisted entity.
     */
    public GenericTimelineDTO save(GenericTimelineDTO genericTimelineDTO) {
        log.debug("Request to save GenericTimeline : {}", genericTimelineDTO);
        GenericTimeline genericTimeline = genericTimelineMapper.toEntity(genericTimelineDTO);
        genericTimeline = genericTimelineRepository.save(genericTimeline);
        return genericTimelineMapper.toDto(genericTimeline);
    }

    /**
     * Partially update a genericTimeline.
     *
     * @param genericTimelineDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GenericTimelineDTO> partialUpdate(GenericTimelineDTO genericTimelineDTO) {
        log.debug("Request to partially update GenericTimeline : {}", genericTimelineDTO);

        return genericTimelineRepository
            .findById(genericTimelineDTO.getId())
            .map(
                existingGenericTimeline -> {
                    genericTimelineMapper.partialUpdate(existingGenericTimeline, genericTimelineDTO);
                    return existingGenericTimeline;
                }
            )
            .map(genericTimelineRepository::save)
            .map(genericTimelineMapper::toDto);
    }

    /**
     * Get all the genericTimelines.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GenericTimelineDTO> findAll() {
        log.debug("Request to get all GenericTimelines");
        return genericTimelineRepository
            .findAll()
            .stream()
            .map(genericTimelineMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one genericTimeline by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GenericTimelineDTO> findOne(Long id) {
        log.debug("Request to get GenericTimeline : {}", id);
        return genericTimelineRepository.findById(id).map(genericTimelineMapper::toDto);
    }

    /**
     * Delete the genericTimeline by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GenericTimeline : {}", id);
        genericTimelineRepository.deleteById(id);
    }
}
