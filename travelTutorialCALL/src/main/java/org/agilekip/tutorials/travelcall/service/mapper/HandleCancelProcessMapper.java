package org.agilekip.tutorials.travelcall.service.mapper;

import org.agilekip.tutorials.travelcall.domain.*;
import org.agilekip.tutorials.travelcall.service.dto.HandleCancelProcessDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HandleCancelProcess} and its DTO {@link HandleCancelProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class, HandleCancelMapper.class })
public interface HandleCancelProcessMapper extends EntityMapper<HandleCancelProcessDTO, HandleCancelProcess> {
    @Mapping(target = "processInstance", source = "processInstance")
    @Mapping(target = "handleCancel", source = "handleCancel")
    HandleCancelProcessDTO toDto(HandleCancelProcess s);
}
