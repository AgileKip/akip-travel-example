package org.agilekip.tutorials.travelcomplete.service.mapper;

import org.agilekip.tutorials.travelcomplete.domain.*;
import org.agilekip.tutorials.travelcomplete.service.dto.CarRentalCompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CarRentalCompany} and its DTO {@link CarRentalCompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CarRentalCompanyMapper extends EntityMapper<CarRentalCompanyDTO, CarRentalCompany> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CarRentalCompanyDTO toDtoId(CarRentalCompany carRentalCompany);
}
