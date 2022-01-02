package org.agilekip.tutorials.travelcomplete.service.mapper;

import org.agilekip.tutorials.travelcomplete.domain.*;
import org.agilekip.tutorials.travelcomplete.service.dto.TaskSelectCarDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskSelectCar} and its DTO {@link TaskSelectCarDTO}.
 */
@Mapper(componentModel = "spring", uses = { CarMapper.class })
public interface TaskSelectCarMapper extends EntityMapper<TaskSelectCarDTO, TaskSelectCar> {
    @Mapping(target = "car", source = "car", qualifiedByName = "code")
    TaskSelectCarDTO toDto(TaskSelectCar s);
}
