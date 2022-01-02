package org.agilekip.tutorials.travelcomplete.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.agilekip.tutorials.travelcomplete.domain.Traveler} entity.
 */
public class TravelerDTO implements Serializable {

    private Long id;

    private String name;

    @NotNull
    @Min(value = 0)
    @Max(value = 200)
    private Integer age;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TravelerDTO)) {
            return false;
        }

        TravelerDTO travelerDTO = (TravelerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, travelerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TravelerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", age=" + getAge() +
            "}";
    }
}
