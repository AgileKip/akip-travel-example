package org.agilekip.tutorials.travelentities2.service.mapper;

import org.agilekip.tutorials.travelentities2.domain.*;
import org.agilekip.tutorials.travelentities2.service.dto.HotelCompanyDTO;
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
