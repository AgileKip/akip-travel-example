package org.agilekip.tutorials.traveland.service.mapper;

import org.agilekip.tutorials.traveland.domain.*;
import org.agilekip.tutorials.traveland.service.dto.TravelPlanProcessDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TravelPlanProcess} and its DTO {@link TravelPlanProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class, TravelPlanMapper.class })
public interface TravelPlanProcessMapper extends EntityMapper<TravelPlanProcessDTO, TravelPlanProcess> {
    @Mapping(target = "processInstance", source = "processInstance")
    @Mapping(target = "travelPlan", source = "travelPlan")
    TravelPlanProcessDTO toDto(TravelPlanProcess s);
}
