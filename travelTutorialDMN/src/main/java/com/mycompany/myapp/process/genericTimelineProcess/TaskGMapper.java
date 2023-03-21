package com.mycompany.myapp.process.genericTimelineProcess;

import com.mycompany.myapp.domain.GenericTimeline;
import com.mycompany.myapp.domain.GenericTimelineProcess;
import com.mycompany.myapp.service.dto.GenericTimelineDTO;
import com.mycompany.myapp.service.dto.GenericTimelineProcessDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class })
public interface TaskGMapper {
    @Mapping(target = "processInstance", source = "processInstance", qualifiedByName = "loadTaskContext")
    GenericTimelineProcessDTO toGenericTimelineProcessDTO(GenericTimelineProcess genericTimelineProcess);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "needTaskH", source = "needTaskH")
    GenericTimelineDTO toGenericTimelineDTO(GenericTimeline genericTimeline);
}
