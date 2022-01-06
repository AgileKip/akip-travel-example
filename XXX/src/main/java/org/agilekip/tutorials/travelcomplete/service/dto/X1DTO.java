package org.agilekip.tutorials.travelcomplete.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.agilekip.tutorials.travelcomplete.domain.X1} entity.
 */
public class X1DTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Set<CarDTO> cars = new HashSet<>();

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

    public Set<CarDTO> getCars() {
        return cars;
    }

    public void setCars(Set<CarDTO> cars) {
        this.cars = cars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof X1DTO)) {
            return false;
        }

        X1DTO x1DTO = (X1DTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, x1DTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "X1DTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cars=" + getCars() +
            "}";
    }
}
