package org.agilekip.tutorials.travelentities3.process.travelPlanProcess;

import org.agilekip.tutorials.travelentities3.domain.AirlineCompany;
import org.agilekip.tutorials.travelentities3.domain.TravelPlan;
import org.agilekip.tutorials.travelentities3.domain.TravelPlanProcess;
import org.agilekip.tutorials.travelentities3.service.dto.AirlineCompanyDTO;
import org.agilekip.tutorials.travelentities3.service.dto.TravelPlanDTO;
import org.agilekip.tutorials.travelentities3.service.dto.TravelPlanProcessDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class })
public interface TaskBuyFlightTicketsMapper {
    @Mapping(target = "processInstance", source = "processInstance", qualifiedByName = "loadTaskContext")
    TravelPlanProcessDTO toTravelPlanProcessDTO(TravelPlanProcess travelPlanProcess);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "airlineTicketNumber", source = "airlineTicketNumber")
    @Mapping(target = "airlineCompany", source = "airlineCompany")
    TravelPlanDTO toTravelPlanDTO(TravelPlan travelPlan);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    AirlineCompanyDTO toAirlineCompanyDTO(AirlineCompany name);
}
