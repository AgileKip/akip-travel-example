package org.agilekip.tutorials.travelcomplete.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.agilekip.tutorials.travelcomplete.domain.CarRentalCompany;
import org.agilekip.tutorials.travelcomplete.repository.CarRentalCompanyRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.CarRentalCompanyDTO;
import org.agilekip.tutorials.travelcomplete.service.mapper.CarRentalCompanyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CarRentalCompany}.
 */
@Service
@Transactional
public class CarRentalCompanyService {

    private final Logger log = LoggerFactory.getLogger(CarRentalCompanyService.class);

    private final CarRentalCompanyRepository carRentalCompanyRepository;

    private final CarRentalCompanyMapper carRentalCompanyMapper;

    public CarRentalCompanyService(CarRentalCompanyRepository carRentalCompanyRepository, CarRentalCompanyMapper carRentalCompanyMapper) {
        this.carRentalCompanyRepository = carRentalCompanyRepository;
        this.carRentalCompanyMapper = carRentalCompanyMapper;
    }

    /**
     * Save a carRentalCompany.
     *
     * @param carRentalCompanyDTO the entity to save.
     * @return the persisted entity.
     */
    public CarRentalCompanyDTO save(CarRentalCompanyDTO carRentalCompanyDTO) {
        log.debug("Request to save CarRentalCompany : {}", carRentalCompanyDTO);
        CarRentalCompany carRentalCompany = carRentalCompanyMapper.toEntity(carRentalCompanyDTO);
        carRentalCompany = carRentalCompanyRepository.save(carRentalCompany);
        return carRentalCompanyMapper.toDto(carRentalCompany);
    }

    /**
     * Partially update a carRentalCompany.
     *
     * @param carRentalCompanyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CarRentalCompanyDTO> partialUpdate(CarRentalCompanyDTO carRentalCompanyDTO) {
        log.debug("Request to partially update CarRentalCompany : {}", carRentalCompanyDTO);

        return carRentalCompanyRepository
            .findById(carRentalCompanyDTO.getId())
            .map(
                existingCarRentalCompany -> {
                    carRentalCompanyMapper.partialUpdate(existingCarRentalCompany, carRentalCompanyDTO);
                    return existingCarRentalCompany;
                }
            )
            .map(carRentalCompanyRepository::save)
            .map(carRentalCompanyMapper::toDto);
    }

    /**
     * Get all the carRentalCompanies.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CarRentalCompanyDTO> findAll() {
        log.debug("Request to get all CarRentalCompanies");
        return carRentalCompanyRepository
            .findAll()
            .stream()
            .map(carRentalCompanyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one carRentalCompany by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CarRentalCompanyDTO> findOne(Long id) {
        log.debug("Request to get CarRentalCompany : {}", id);
        return carRentalCompanyRepository.findById(id).map(carRentalCompanyMapper::toDto);
    }

    /**
     * Delete the carRentalCompany by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CarRentalCompany : {}", id);
        carRentalCompanyRepository.deleteById(id);
    }
}
