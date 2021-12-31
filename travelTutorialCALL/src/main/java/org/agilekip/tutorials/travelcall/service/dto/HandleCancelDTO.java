package org.agilekip.tutorials.travelcall.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.agilekip.tutorials.travelcall.domain.HandleCancel} entity.
 */
public class HandleCancelDTO implements Serializable {

    private Long id;

    private String reason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HandleCancelDTO)) {
            return false;
        }

        HandleCancelDTO handleCancelDTO = (HandleCancelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, handleCancelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HandleCancelDTO{" +
            "id=" + getId() +
            ", reason='" + getReason() + "'" +
            "}";
    }
}
