package org.agilekip.tutorials.travelcall.service.mapper;

import org.agilekip.tutorials.travelcall.domain.*;
import org.agilekip.tutorials.travelcall.service.dto.TravelPlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TravelPlan} and its DTO {@link TravelPlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TravelPlanMapper extends EntityMapper<TravelPlanDTO, TravelPlan> {}
