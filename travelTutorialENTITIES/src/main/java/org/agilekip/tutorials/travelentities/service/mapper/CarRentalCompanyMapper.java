package org.agilekip.tutorials.travelentities.service.mapper;

import org.agilekip.tutorials.travelentities.domain.*;
import org.agilekip.tutorials.travelentities.service.dto.CarRentalCompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CarRentalCompany} and its DTO {@link CarRentalCompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CarRentalCompanyMapper extends EntityMapper<CarRentalCompanyDTO, CarRentalCompany> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CarRentalCompanyDTO toDtoName(CarRentalCompany carRentalCompany);
}
