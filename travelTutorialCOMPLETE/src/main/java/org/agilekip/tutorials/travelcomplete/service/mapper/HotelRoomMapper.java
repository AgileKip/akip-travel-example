package org.agilekip.tutorials.travelcomplete.service.mapper;

import org.agilekip.tutorials.travelcomplete.domain.*;
import org.agilekip.tutorials.travelcomplete.service.dto.HotelRoomDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HotelRoom} and its DTO {@link HotelRoomDTO}.
 */
@Mapper(componentModel = "spring", uses = { HotelCompanyMapper.class })
public interface HotelRoomMapper extends EntityMapper<HotelRoomDTO, HotelRoom> {
    @Mapping(target = "hotelCo", source = "hotelCo", qualifiedByName = "name")
    HotelRoomDTO toDto(HotelRoom s);

    @Named("roomID")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "roomID", source = "roomID")
    HotelRoomDTO toDtoRoomID(HotelRoom hotelRoom);
}
