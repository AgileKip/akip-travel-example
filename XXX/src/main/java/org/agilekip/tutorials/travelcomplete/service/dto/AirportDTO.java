package org.agilekip.tutorials.travelcomplete.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.agilekip.tutorials.travelcomplete.domain.Airport} entity.
 */
public class AirportDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String city;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
        if (!(o instanceof AirportDTO)) {
            return false;
        }

        AirportDTO airportDTO = (AirportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, airportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AirportDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", city='" + getCity() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
