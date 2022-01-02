package org.agilekip.tutorials.travelcomplete.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.agilekip.tutorials.travelcomplete.domain.AirlineCompany} entity.
 */
public class AirlineCompanyDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Size(min = 3, max = 3)
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AirlineCompanyDTO)) {
            return false;
        }

        AirlineCompanyDTO airlineCompanyDTO = (AirlineCompanyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, airlineCompanyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AirlineCompanyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
