package org.agilekip.tutorials.travelcomplete.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.agilekip.tutorials.travelcomplete.domain.X1;
import org.agilekip.tutorials.travelcomplete.repository.X1Repository;
import org.agilekip.tutorials.travelcomplete.service.dto.X1DTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.X1Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link X1}.
 */
@Service
@Transactional
public class X1Service {

    private final Logger log = LoggerFactory.getLogger(X1Service.class);

    private final X1Repository x1Repository;

    private final X1Mapper x1Mapper;

    public X1Service(X1Repository x1Repository, X1Mapper x1Mapper) {
        this.x1Repository = x1Repository;
        this.x1Mapper = x1Mapper;
    }

    /**
     * Save a x1.
     *
     * @param x1DTO the entity to save.
     * @return the persisted entity.
     */
    public X1DTO save(X1DTO x1DTO) {
        log.debug("Request to save X1 : {}", x1DTO);
        X1 x1 = x1Mapper.toEntity(x1DTO);
        x1 = x1Repository.save(x1);
        return x1Mapper.toDto(x1);
    }

    /**
     * Partially update a x1.
     *
     * @param x1DTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<X1DTO> partialUpdate(X1DTO x1DTO) {
        log.debug("Request to partially update X1 : {}", x1DTO);

        return x1Repository
            .findById(x1DTO.getId())
            .map(
                existingX1 -> {
                    x1Mapper.partialUpdate(existingX1, x1DTO);
                    return existingX1;
                }
            )
            .map(x1Repository::save)
            .map(x1Mapper::toDto);
    }

    /**
     * Get all the x1s.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<X1DTO> findAll() {
        log.debug("Request to get all X1s");
        return x1Repository.findAllWithEagerRelationships().stream().map(x1Mapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the x1s with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<X1DTO> findAllWithEagerRelationships(Pageable pageable) {
        return x1Repository.findAllWithEagerRelationships(pageable).map(x1Mapper::toDto);
    }

    /**
     * Get one x1 by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<X1DTO> findOne(Long id) {
        log.debug("Request to get X1 : {}", id);
        return x1Repository.findOneWithEagerRelationships(id).map(x1Mapper::toDto);
    }

    /**
     * Delete the x1 by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete X1 : {}", id);
        x1Repository.deleteById(id);
    }
}
