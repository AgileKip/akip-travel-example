package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Tenant;
import com.mycompany.myapp.service.dto.TenantDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface TenantMapper extends EntityMapper<TenantDTO, Tenant> {}
