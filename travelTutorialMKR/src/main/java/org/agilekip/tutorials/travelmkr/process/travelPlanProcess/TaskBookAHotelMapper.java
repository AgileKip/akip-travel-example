package org.agilekip.tutorials.travelmkr.process.travelPlanProcess;

import org.agilekip.tutorials.travelmkr.domain.TravelPlan;
import org.agilekip.tutorials.travelmkr.domain.TravelPlanProcess;
import org.agilekip.tutorials.travelmkr.service.dto.TravelPlanDTO;
import org.agilekip.tutorials.travelmkr.service.dto.TravelPlanProcessDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class })
public interface TaskBookAHotelMapper {
    @Mapping(target = "processInstance", source = "processInstance", qualifiedByName = "loadTaskContext")
    TravelPlanProcessDTO toTravelPlanProcessDTO(TravelPlanProcess travelPlanProcess);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "hotelName", source = "hotelName")
    @Mapping(target = "hotelBookingNumber", source = "hotelBookingNumber")
    TravelPlanDTO toTravelPlanDTO(TravelPlan travelPlan);
}
