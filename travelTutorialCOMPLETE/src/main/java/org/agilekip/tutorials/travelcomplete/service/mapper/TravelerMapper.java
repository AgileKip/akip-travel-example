package org.agilekip.tutorials.travelcomplete.service.mapper;

import org.agilekip.tutorials.travelcomplete.domain.*;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Traveler} and its DTO {@link TravelerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TravelerMapper extends EntityMapper<TravelerDTO, Traveler> {}
