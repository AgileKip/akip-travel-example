package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.GenericTimelineDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GenericTimeline} and its DTO {@link GenericTimelineDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GenericTimelineMapper extends EntityMapper<GenericTimelineDTO, GenericTimeline> {}
