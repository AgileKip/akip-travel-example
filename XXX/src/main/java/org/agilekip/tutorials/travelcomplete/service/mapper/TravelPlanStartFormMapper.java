package org.agilekip.tutorials.travelcomplete.service.mapper;

import org.agilekip.tutorials.travelcomplete.domain.*;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelPlanStartFormDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TravelPlanStartForm} and its DTO {@link TravelPlanStartFormDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TravelPlanStartFormMapper extends EntityMapper<TravelPlanStartFormDTO, TravelPlanStartForm> {}
