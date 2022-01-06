package org.agilekip.tutorials.travelcomplete.service.mapper;

import org.agilekip.tutorials.travelcomplete.domain.*;
import org.agilekip.tutorials.travelcomplete.service.dto.X1DTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link X1} and its DTO {@link X1DTO}.
 */
@Mapper(componentModel = "spring", uses = { CarMapper.class })
public interface X1Mapper extends EntityMapper<X1DTO, X1> {
    @Mapping(target = "cars", source = "cars", qualifiedByName = "codeSet")
    X1DTO toDto(X1 s);

    @Mapping(target = "removeCar", ignore = true)
    X1 toEntity(X1DTO x1DTO);
}
