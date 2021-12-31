package org.agilekip.tutorials.travelentities2.process.travelPlanProcess;

import org.agilekip.tutorials.travelentities2.domain.HotelCompany;
import org.agilekip.tutorials.travelentities2.domain.TravelPlan;
import org.agilekip.tutorials.travelentities2.domain.TravelPlanProcess;
import org.agilekip.tutorials.travelentities2.service.dto.HotelCompanyDTO;
import org.agilekip.tutorials.travelentities2.service.dto.TravelPlanDTO;
import org.agilekip.tutorials.travelentities2.service.dto.TravelPlanProcessDTO;
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
    @Mapping(target = "hotelBookingNumber", source = "hotelBookingNumber")
    @Mapping(target = "hotelCompany", source = "hotelCompany")
    TravelPlanDTO toTravelPlanDTO(TravelPlan travelPlan);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    HotelCompanyDTO toHotelCompanyDTO(HotelCompany name);
}
