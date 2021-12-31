package org.agilekip.tutorials.travelor.service.mapper;

import org.agilekip.tutorials.travelor.domain.*;
import org.agilekip.tutorials.travelor.service.dto.TravelPlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TravelPlan} and its DTO {@link TravelPlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TravelPlanMapper extends EntityMapper<TravelPlanDTO, TravelPlan> {}
