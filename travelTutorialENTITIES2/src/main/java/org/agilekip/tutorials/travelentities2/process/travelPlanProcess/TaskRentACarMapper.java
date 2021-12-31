package org.agilekip.tutorials.travelentities2.process.travelPlanProcess;

import org.agilekip.tutorials.travelentities2.domain.CarRentalCompany;
import org.agilekip.tutorials.travelentities2.domain.TravelPlan;
import org.agilekip.tutorials.travelentities2.domain.TravelPlanProcess;
import org.agilekip.tutorials.travelentities2.service.dto.CarRentalCompanyDTO;
import org.agilekip.tutorials.travelentities2.service.dto.TravelPlanDTO;
import org.agilekip.tutorials.travelentities2.service.dto.TravelPlanProcessDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class })
public interface TaskRentACarMapper {
    @Mapping(target = "processInstance", source = "processInstance", qualifiedByName = "loadTaskContext")
    TravelPlanProcessDTO toTravelPlanProcessDTO(TravelPlanProcess travelPlanProcess);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "carBookingNumber", source = "carBookingNumber")
    @Mapping(target = "carRentalCompany", source = "carRentalCompany")
    TravelPlanDTO toTravelPlanDTO(TravelPlan travelPlan);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CarRentalCompanyDTO toCarRentalCompanyDTO(CarRentalCompany name);
}
