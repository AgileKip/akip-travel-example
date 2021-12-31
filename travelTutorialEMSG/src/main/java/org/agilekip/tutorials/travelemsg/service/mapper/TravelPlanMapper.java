package org.agilekip.tutorials.travelemsg.service.mapper;

import org.agilekip.tutorials.travelemsg.domain.*;
import org.agilekip.tutorials.travelemsg.service.dto.TravelPlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TravelPlan} and its DTO {@link TravelPlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TravelPlanMapper extends EntityMapper<TravelPlanDTO, TravelPlan> {}
