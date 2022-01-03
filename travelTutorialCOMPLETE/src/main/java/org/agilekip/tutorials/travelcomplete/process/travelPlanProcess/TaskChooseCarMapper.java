package org.agilekip.tutorials.travelcomplete.process.travelPlanProcess;

import org.agilekip.tutorials.travelcomplete.domain.Car;
import org.agilekip.tutorials.travelcomplete.domain.TravelPlan;
import org.agilekip.tutorials.travelcomplete.domain.TravelPlanProcess;
import org.agilekip.tutorials.travelcomplete.service.dto.CarDTO;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelPlanDTO;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelPlanProcessDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class })
public interface TaskChooseCarMapper {
    @Mapping(target = "processInstance", source = "processInstance", qualifiedByName = "loadTaskContext")
    TravelPlanProcessDTO toTravelPlanProcessDTO(TravelPlanProcess travelPlanProcess);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "car", source = "car")
    TravelPlanDTO toTravelPlanDTO(TravelPlan travelPlan);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    CarDTO toCarDTO(Car code);
}
