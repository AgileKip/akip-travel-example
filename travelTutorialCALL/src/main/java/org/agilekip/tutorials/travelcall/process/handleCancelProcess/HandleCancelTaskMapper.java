package org.agilekip.tutorials.travelcall.process.handleCancelProcess;

import org.agilekip.tutorials.travelcall.domain.HandleCancel;
import org.agilekip.tutorials.travelcall.domain.HandleCancelProcess;
import org.agilekip.tutorials.travelcall.service.dto.HandleCancelDTO;
import org.agilekip.tutorials.travelcall.service.dto.HandleCancelProcessDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class })
public interface HandleCancelTaskMapper {
    @Mapping(target = "processInstance", source = "processInstance", qualifiedByName = "loadTaskContext")
    HandleCancelProcessDTO toHandleCancelProcessDTO(HandleCancelProcess handleCancelProcess);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "reason", source = "reason")
    HandleCancelDTO toHandleCancelDTO(HandleCancel handleCancel);
}
