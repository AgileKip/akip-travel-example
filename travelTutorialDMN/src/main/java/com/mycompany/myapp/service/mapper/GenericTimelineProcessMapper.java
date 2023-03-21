package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.GenericTimelineProcessDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GenericTimelineProcess} and its DTO {@link GenericTimelineProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class, GenericTimelineMapper.class })
public interface GenericTimelineProcessMapper extends EntityMapper<GenericTimelineProcessDTO, GenericTimelineProcess> {
    @Mapping(target = "processInstance", source = "processInstance")
    @Mapping(target = "genericTimeline", source = "genericTimeline")
    GenericTimelineProcessDTO toDto(GenericTimelineProcess s);
}
