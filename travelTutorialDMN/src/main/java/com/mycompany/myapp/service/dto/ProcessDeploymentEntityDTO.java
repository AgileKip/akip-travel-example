package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.loginlogistica.domain.ProcessDeploymentEntity} entity.
 */
public class ProcessDeploymentEntityDTO implements Serializable {

    private static final long serialVersionUID = 5901093590836897945L;

    private Long id;

    private Long entityId;

    private String entityName;

    private ProcessDeploymentDTO processDeployment;

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

    public ProcessDeploymentDTO getProcessDeployment() {
        return processDeployment;
    }

    public void setProcessDeployment(ProcessDeploymentDTO processDeployment) {
        this.processDeployment = processDeployment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProcessDeploymentEntityDTO anexoEntityDTO = (ProcessDeploymentEntityDTO) o;
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
            "ProcessDeploymentEntityDTO{" + "id=" + getId() + ", entityId=" + getEntityId() + ", entityName='" + getEntityName() + "'" + "}"
        );
    }
}
