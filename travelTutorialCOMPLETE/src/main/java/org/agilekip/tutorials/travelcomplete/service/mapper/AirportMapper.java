package org.agilekip.tutorials.travelcomplete.service.mapper;

import org.agilekip.tutorials.travelcomplete.domain.*;
import org.agilekip.tutorials.travelcomplete.service.dto.AirportDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Airport} and its DTO {@link AirportDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AirportMapper extends EntityMapper<AirportDTO, Airport> {
    @Named("code")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    AirportDTO toDtoCode(Airport airport);
}
