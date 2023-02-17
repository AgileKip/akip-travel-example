package com.mycompany.myapp.service.dto;

import br.com.loginlogistica.domain.enumeration.StatusProcessDefinition;
import org.akip.domain.enumeration.StatusProcessDefinition;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ProcessDefinition} entity.
 */
public class ProcessDefinitionDTO implements Serializable {

    private static final long serialVersionUID = -1213556574365030378L;

    private Long id;

    private String name;

    private String description;

    private StatusProcessDefinition status;

    private String bpmnProcessDefinitionId;

    private Boolean canBeManuallyStarted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusProcessDefinition getStatus() {
        return status;
    }

    public void setStatus(StatusProcessDefinition status) {
        this.status = status;
    }

    public String getBpmnProcessDefinitionId() {
        return bpmnProcessDefinitionId;
    }

    public void setBpmnProcessDefinitionId(String bpmnProcessDefinitionId) {
        this.bpmnProcessDefinitionId = bpmnProcessDefinitionId;
    }

    public Boolean getCanBeManuallyStarted() {
        return canBeManuallyStarted;
    }

    public void setCanBeManuallyStarted(Boolean canBeManuallyStarted) {
        this.canBeManuallyStarted = canBeManuallyStarted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessDefinitionDTO)) {
            return false;
        }

        ProcessDefinitionDTO processDefinitionDTO = (ProcessDefinitionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, processDefinitionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessDefinitionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", bpmnProcessDefinitionId='" + getBpmnProcessDefinitionId() + "'" +
            "}";
    }
}
