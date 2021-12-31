package org.agilekip.tutorials.travelcall.service.mapper;

import org.agilekip.tutorials.travelcall.domain.*;
import org.agilekip.tutorials.travelcall.service.dto.HandleCancelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HandleCancel} and its DTO {@link HandleCancelDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HandleCancelMapper extends EntityMapper<HandleCancelDTO, HandleCancel> {}
