package org.agilekip.tutorials.travelentities3.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.agilekip.tutorials.travelentities3.domain.HotelCompany;
import org.agilekip.tutorials.travelentities3.repository.HotelCompanyRepository;
import org.agilekip.tutorials.travelentities3.service.dto.HotelCompanyDTO;
import org.agilekip.tutorials.travelentities3.service.mapper.HotelCompanyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HotelCompany}.
 */
@Service
@Transactional
public class HotelCompanyService {

    private final Logger log = LoggerFactory.getLogger(HotelCompanyService.class);

    private final HotelCompanyRepository hotelCompanyRepository;

    private final HotelCompanyMapper hotelCompanyMapper;

    public HotelCompanyService(HotelCompanyRepository hotelCompanyRepository, HotelCompanyMapper hotelCompanyMapper) {
        this.hotelCompanyRepository = hotelCompanyRepository;
        this.hotelCompanyMapper = hotelCompanyMapper;
    }

    /**
     * Save a hotelCompany.
     *
     * @param hotelCompanyDTO the entity to save.
     * @return the persisted entity.
     */
    public HotelCompanyDTO save(HotelCompanyDTO hotelCompanyDTO) {
        log.debug("Request to save HotelCompany : {}", hotelCompanyDTO);
        HotelCompany hotelCompany = hotelCompanyMapper.toEntity(hotelCompanyDTO);
        hotelCompany = hotelCompanyRepository.save(hotelCompany);
        return hotelCompanyMapper.toDto(hotelCompany);
    }

    /**
     * Partially update a hotelCompany.
     *
     * @param hotelCompanyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HotelCompanyDTO> partialUpdate(HotelCompanyDTO hotelCompanyDTO) {
        log.debug("Request to partially update HotelCompany : {}", hotelCompanyDTO);

        return hotelCompanyRepository
            .findById(hotelCompanyDTO.getId())
            .map(
                existingHotelCompany -> {
                    hotelCompanyMapper.partialUpdate(existingHotelCompany, hotelCompanyDTO);
                    return existingHotelCompany;
                }
            )
            .map(hotelCompanyRepository::save)
            .map(hotelCompanyMapper::toDto);
    }

    /**
     * Get all the hotelCompanies.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<HotelCompanyDTO> findAll() {
        log.debug("Request to get all HotelCompanies");
        return hotelCompanyRepository.findAll().stream().map(hotelCompanyMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one hotelCompany by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HotelCompanyDTO> findOne(Long id) {
        log.debug("Request to get HotelCompany : {}", id);
        return hotelCompanyRepository.findById(id).map(hotelCompanyMapper::toDto);
    }

    /**
     * Delete the hotelCompany by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HotelCompany : {}", id);
        hotelCompanyRepository.deleteById(id);
    }
}
