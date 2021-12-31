package org.agilekip.tutorials.traveltimer.service.mapper;

import org.agilekip.tutorials.traveltimer.domain.*;
import org.agilekip.tutorials.traveltimer.service.dto.TravelPlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TravelPlan} and its DTO {@link TravelPlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TravelPlanMapper extends EntityMapper<TravelPlanDTO, TravelPlan> {}
