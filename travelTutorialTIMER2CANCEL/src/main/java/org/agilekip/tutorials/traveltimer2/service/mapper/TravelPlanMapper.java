package org.agilekip.tutorials.traveltimer2.service.mapper;

import org.agilekip.tutorials.traveltimer2.domain.*;
import org.agilekip.tutorials.traveltimer2.service.dto.TravelPlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TravelPlan} and its DTO {@link TravelPlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TravelPlanMapper extends EntityMapper<TravelPlanDTO, TravelPlan> {}
