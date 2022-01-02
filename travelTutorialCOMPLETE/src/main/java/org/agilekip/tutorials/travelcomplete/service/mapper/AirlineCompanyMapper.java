package org.agilekip.tutorials.travelcomplete.service.mapper;

import org.agilekip.tutorials.travelcomplete.domain.*;
import org.agilekip.tutorials.travelcomplete.service.dto.AirlineCompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AirlineCompany} and its DTO {@link AirlineCompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AirlineCompanyMapper extends EntityMapper<AirlineCompanyDTO, AirlineCompany> {
    @Named("code")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    AirlineCompanyDTO toDtoCode(AirlineCompany airlineCompany);
}
