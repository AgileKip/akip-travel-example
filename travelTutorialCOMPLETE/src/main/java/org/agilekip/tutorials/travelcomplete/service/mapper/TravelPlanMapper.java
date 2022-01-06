package org.agilekip.tutorials.travelcomplete.service.mapper;

import org.agilekip.tutorials.travelcomplete.domain.*;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelPlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TravelPlan} and its DTO {@link TravelPlanDTO}.
 */
@Mapper(componentModel = "spring", uses = { CarMapper.class, FlightMapper.class, HotelRoomMapper.class })
public interface TravelPlanMapper extends EntityMapper<TravelPlanDTO, TravelPlan> {
    @Mapping(target = "car", source = "car", qualifiedByName = "license")
    @Mapping(target = "flight", source = "flight", qualifiedByName = "code")
    @Mapping(target = "hotelRoom", source = "hotelRoom", qualifiedByName = "roomID")
    TravelPlanDTO toDto(TravelPlan s);
}
