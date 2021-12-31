package org.agilekip.traveltutorials.travelcancel.service.mapper;

import org.agilekip.traveltutorials.travelcancel.domain.*;
import org.agilekip.traveltutorials.travelcancel.service.dto.TravelPlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TravelPlan} and its DTO {@link TravelPlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TravelPlanMapper extends EntityMapper<TravelPlanDTO, TravelPlan> {}
