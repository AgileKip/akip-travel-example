package org.agilekip.tutorials.travelcomplete.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.agilekip.tutorials.travelcomplete.domain.TravelPlanStartForm;
import org.agilekip.tutorials.travelcomplete.repository.TravelPlanStartFormRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelPlanStartFormDTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.TravelPlanStartFormMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TravelPlanStartForm}.
 */
@Service
@Transactional
public class TravelPlanStartFormService {

    private final Logger log = LoggerFactory.getLogger(TravelPlanStartFormService.class);

    private final TravelPlanStartFormRepository travelPlanStartFormRepository;

    private final TravelPlanStartFormMapper travelPlanStartFormMapper;

    public TravelPlanStartFormService(
        TravelPlanStartFormRepository travelPlanStartFormRepository,
        TravelPlanStartFormMapper travelPlanStartFormMapper
    ) {
        this.travelPlanStartFormRepository = travelPlanStartFormRepository;
        this.travelPlanStartFormMapper = travelPlanStartFormMapper;
    }

    /**
     * Save a travelPlanStartForm.
     *
     * @param travelPlanStartFormDTO the entity to save.
     * @return the persisted entity.
     */
    public TravelPlanStartFormDTO save(TravelPlanStartFormDTO travelPlanStartFormDTO) {
        log.debug("Request to save TravelPlanStartForm : {}", travelPlanStartFormDTO);
        TravelPlanStartForm travelPlanStartForm = travelPlanStartFormMapper.toEntity(travelPlanStartFormDTO);
        travelPlanStartForm = travelPlanStartFormRepository.save(travelPlanStartForm);
        return travelPlanStartFormMapper.toDto(travelPlanStartForm);
    }

    /**
     * Partially update a travelPlanStartForm.
     *
     * @param travelPlanStartFormDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TravelPlanStartFormDTO> partialUpdate(TravelPlanStartFormDTO travelPlanStartFormDTO) {
        log.debug("Request to partially update TravelPlanStartForm : {}", travelPlanStartFormDTO);

        return travelPlanStartFormRepository
            .findById(travelPlanStartFormDTO.getId())
            .map(
                existingTravelPlanStartForm -> {
                    travelPlanStartFormMapper.partialUpdate(existingTravelPlanStartForm, travelPlanStartFormDTO);
                    return existingTravelPlanStartForm;
                }
            )
            .map(travelPlanStartFormRepository::save)
            .map(travelPlanStartFormMapper::toDto);
    }

    /**
     * Get all the travelPlanStartForms.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TravelPlanStartFormDTO> findAll() {
        log.debug("Request to get all TravelPlanStartForms");
        return travelPlanStartFormRepository
            .findAll()
            .stream()
            .map(travelPlanStartFormMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one travelPlanStartForm by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TravelPlanStartFormDTO> findOne(Long id) {
        log.debug("Request to get TravelPlanStartForm : {}", id);
        return travelPlanStartFormRepository.findById(id).map(travelPlanStartFormMapper::toDto);
    }

    /**
     * Delete the travelPlanStartForm by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TravelPlanStartForm : {}", id);
        travelPlanStartFormRepository.deleteById(id);
    }
}
