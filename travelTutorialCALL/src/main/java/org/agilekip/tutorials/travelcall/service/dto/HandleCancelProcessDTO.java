package org.agilekip.tutorials.travelcall.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.akip.service.dto.ProcessInstanceDTO;

/**
 * A DTO for the {@link org.agilekip.tutorials.travelcall.domain.HandleCancelProcess} entity.
 */
public class HandleCancelProcessDTO implements Serializable {

    private Long id;

    private ProcessInstanceDTO processInstance;

    private HandleCancelDTO handleCancel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProcessInstanceDTO getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstanceDTO processInstance) {
        this.processInstance = processInstance;
    }

    public HandleCancelDTO getHandleCancel() {
        return handleCancel;
    }

    public void setHandleCancel(HandleCancelDTO handleCancel) {
        this.handleCancel = handleCancel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HandleCancelProcessDTO)) {
            return false;
        }

        HandleCancelProcessDTO handleCancelProcessDTO = (HandleCancelProcessDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, handleCancelProcessDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HandleCancelProcessDTO{" +
            "id=" + getId() +
            ", processInstance=" + getProcessInstance() +
            ", handleCancel=" + getHandleCancel() +
            "}";
    }
}
