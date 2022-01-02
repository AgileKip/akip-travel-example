package org.agilekip.tutorials.travelcomplete.service.mapper;

import org.agilekip.tutorials.travelcomplete.domain.*;
import org.agilekip.tutorials.travelcomplete.service.dto.HotelCompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HotelCompany} and its DTO {@link HotelCompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HotelCompanyMapper extends EntityMapper<HotelCompanyDTO, HotelCompany> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    HotelCompanyDTO toDtoName(HotelCompany hotelCompany);
}
