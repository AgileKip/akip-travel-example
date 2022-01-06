package org.agilekip.tutorials.travelcomplete.service.mapper;

import org.agilekip.tutorials.travelcomplete.domain.*;
import org.agilekip.tutorials.travelcomplete.service.dto.CarDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Car} and its DTO {@link CarDTO}.
 */
@Mapper(componentModel = "spring", uses = { CarRentalCompanyMapper.class })
public interface CarMapper extends EntityMapper<CarDTO, Car> {
    @Mapping(target = "carCo", source = "carCo", qualifiedByName = "name")
    CarDTO toDto(Car s);

    @Named("license")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "license", source = "license")
    CarDTO toDtoLicense(Car car);
}
