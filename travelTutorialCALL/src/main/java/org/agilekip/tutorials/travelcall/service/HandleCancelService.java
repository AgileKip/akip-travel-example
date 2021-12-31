package org.agilekip.tutorials.travelcall.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.agilekip.tutorials.travelcall.domain.HandleCancel;
import org.agilekip.tutorials.travelcall.repository.HandleCancelRepository;
import org.agilekip.tutorials.travelcall.service.dto.HandleCancelDTO;
import org.agilekip.tutorials.travelcall.service.mapper.HandleCancelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HandleCancel}.
 */
@Service
@Transactional
public class HandleCancelService {

    private final Logger log = LoggerFactory.getLogger(HandleCancelService.class);

    private final HandleCancelRepository handleCancelRepository;

    private final HandleCancelMapper handleCancelMapper;

    public HandleCancelService(HandleCancelRepository handleCancelRepository, HandleCancelMapper handleCancelMapper) {
        this.handleCancelRepository = handleCancelRepository;
        this.handleCancelMapper = handleCancelMapper;
    }

    /**
     * Save a handleCancel.
     *
     * @param handleCancelDTO the entity to save.
     * @return the persisted entity.
     */
    public HandleCancelDTO save(HandleCancelDTO handleCancelDTO) {
        log.debug("Request to save HandleCancel : {}", handleCancelDTO);
        HandleCancel handleCancel = handleCancelMapper.toEntity(handleCancelDTO);
        handleCancel = handleCancelRepository.save(handleCancel);
        return handleCancelMapper.toDto(handleCancel);
    }

    /**
     * Partially update a handleCancel.
     *
     * @param handleCancelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HandleCancelDTO> partialUpdate(HandleCancelDTO handleCancelDTO) {
        log.debug("Request to partially update HandleCancel : {}", handleCancelDTO);

        return handleCancelRepository
            .findById(handleCancelDTO.getId())
            .map(
                existingHandleCancel -> {
                    handleCancelMapper.partialUpdate(existingHandleCancel, handleCancelDTO);
                    return existingHandleCancel;
                }
            )
            .map(handleCancelRepository::save)
            .map(handleCancelMapper::toDto);
    }

    /**
     * Get all the handleCancels.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<HandleCancelDTO> findAll() {
        log.debug("Request to get all HandleCancels");
        return handleCancelRepository.findAll().stream().map(handleCancelMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one handleCancel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HandleCancelDTO> findOne(Long id) {
        log.debug("Request to get HandleCancel : {}", id);
        return handleCancelRepository.findById(id).map(handleCancelMapper::toDto);
    }

    /**
     * Delete the handleCancel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HandleCancel : {}", id);
        handleCancelRepository.deleteById(id);
    }
}
