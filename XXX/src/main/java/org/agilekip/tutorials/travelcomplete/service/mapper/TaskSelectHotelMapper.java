package org.agilekip.tutorials.travelcomplete.service.mapper;

import org.agilekip.tutorials.travelcomplete.domain.*;
import org.agilekip.tutorials.travelcomplete.service.dto.TaskSelectHotelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskSelectHotel} and its DTO {@link TaskSelectHotelDTO}.
 */
@Mapper(componentModel = "spring", uses = { HotelRoomMapper.class })
public interface TaskSelectHotelMapper extends EntityMapper<TaskSelectHotelDTO, TaskSelectHotel> {
    @Mapping(target = "hotel", source = "hotel", qualifiedByName = "code")
    TaskSelectHotelDTO toDto(TaskSelectHotel s);
}
