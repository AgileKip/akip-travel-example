package org.agilekip.tutorial.travel.service.mapper;

import org.agilekip.tutorial.travel.domain.*;
import org.agilekip.tutorial.travel.service.dto.TravelPlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TravelPlan} and its DTO {@link TravelPlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TravelPlanMapper extends EntityMapper<TravelPlanDTO, TravelPlan> {}
