package org.agilekip.tutorials.travelpool.service.mapper;

import org.agilekip.tutorials.travelpool.domain.*;
import org.agilekip.tutorials.travelpool.service.dto.TravelPlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TravelPlan} and its DTO {@link TravelPlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TravelPlanMapper extends EntityMapper<TravelPlanDTO, TravelPlan> {}
