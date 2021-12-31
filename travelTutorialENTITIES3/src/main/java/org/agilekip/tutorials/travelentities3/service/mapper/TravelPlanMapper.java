package org.agilekip.tutorials.travelentities3.service.mapper;

import org.agilekip.tutorials.travelentities3.domain.*;
import org.agilekip.tutorials.travelentities3.service.dto.TravelPlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TravelPlan} and its DTO {@link TravelPlanDTO}.
 */
@Mapper(componentModel = "spring", uses = { AirlineCompanyMapper.class, HotelCompanyMapper.class, CarRentalCompanyMapper.class })
public interface TravelPlanMapper extends EntityMapper<TravelPlanDTO, TravelPlan> {
    @Mapping(target = "airlineCompany", source = "airlineCompany", qualifiedByName = "name")
    @Mapping(target = "hotelCompany", source = "hotelCompany", qualifiedByName = "name")
    @Mapping(target = "carRentalCompany", source = "carRentalCompany", qualifiedByName = "name")
    TravelPlanDTO toDto(TravelPlan s);
}
