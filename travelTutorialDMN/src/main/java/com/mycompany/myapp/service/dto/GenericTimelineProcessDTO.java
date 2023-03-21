package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.akip.service.dto.ProcessInstanceDTO;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.GenericTimelineProcess} entity.
 */
public class GenericTimelineProcessDTO implements Serializable {

    private Long id;

    private ProcessInstanceDTO processInstance;

    private GenericTimelineDTO genericTimeline;

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

    public GenericTimelineDTO getGenericTimeline() {
        return genericTimeline;
    }

    public void setGenericTimeline(GenericTimelineDTO genericTimeline) {
        this.genericTimeline = genericTimeline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GenericTimelineProcessDTO)) {
            return false;
        }

        GenericTimelineProcessDTO genericTimelineProcessDTO = (GenericTimelineProcessDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, genericTimelineProcessDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GenericTimelineProcessDTO{" +
            "id=" + getId() +
            ", processInstance=" + getProcessInstance() +
            ", genericTimeline=" + getGenericTimeline() +
            "}";
    }
}
