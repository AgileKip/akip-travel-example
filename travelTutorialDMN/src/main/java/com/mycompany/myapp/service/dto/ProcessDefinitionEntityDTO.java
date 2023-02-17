package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.loginlogistica.domain.ProcessDefinitionEntity} entity.
 */
public class ProcessDefinitionEntityDTO implements Serializable {

    private static final long serialVersionUID = -6501597509357971807L;

    private Long id;

    private Long entityId;

    private String entityName;

    private ProcessDefinitionDTO processDefinition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public ProcessDefinitionDTO getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinitionDTO processDefinition) {
        this.processDefinition = processDefinition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProcessDefinitionEntityDTO anexoEntityDTO = (ProcessDefinitionEntityDTO) o;
        if (anexoEntityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anexoEntityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "ProcessDefinitionEntityDTO{" + "id=" + getId() + ", entityId=" + getEntityId() + ", entityName='" + getEntityName() + "'" + "}"
        );
    }
}
