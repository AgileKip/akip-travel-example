package org.agilekip.tutorials.travelcomplete.service.mapper;

import org.agilekip.tutorials.travelcomplete.domain.*;
import org.agilekip.tutorials.travelcomplete.service.dto.TaskSelectFlightDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskSelectFlight} and its DTO {@link TaskSelectFlightDTO}.
 */
@Mapper(componentModel = "spring", uses = { FlightMapper.class })
public interface TaskSelectFlightMapper extends EntityMapper<TaskSelectFlightDTO, TaskSelectFlight> {
    @Mapping(target = "flight", source = "flight", qualifiedByName = "code")
    TaskSelectFlightDTO toDto(TaskSelectFlight s);
}
