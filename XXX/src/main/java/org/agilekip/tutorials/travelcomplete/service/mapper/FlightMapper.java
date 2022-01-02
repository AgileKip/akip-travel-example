package org.agilekip.tutorials.travelcomplete.service.mapper;

import org.agilekip.tutorials.travelcomplete.domain.*;
import org.agilekip.tutorials.travelcomplete.service.dto.FlightDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Flight} and its DTO {@link FlightDTO}.
 */
@Mapper(componentModel = "spring", uses = { AirportMapper.class, AirlineCompanyMapper.class })
public interface FlightMapper extends EntityMapper<FlightDTO, Flight> {
    @Mapping(target = "from", source = "from", qualifiedByName = "code")
    @Mapping(target = "to", source = "to", qualifiedByName = "code")
    @Mapping(target = "airline", source = "airline", qualifiedByName = "code")
    FlightDTO toDto(Flight s);

    @Named("code")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    FlightDTO toDtoCode(Flight flight);
}
