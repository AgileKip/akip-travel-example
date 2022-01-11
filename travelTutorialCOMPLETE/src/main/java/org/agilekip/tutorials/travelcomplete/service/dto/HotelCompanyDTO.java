package org.agilekip.tutorials.travelcomplete.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.agilekip.tutorials.travelcomplete.domain.HotelCompany} entity.
 */
public class HotelCompanyDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 20)
    private String name;

    @NotNull
    private String place;

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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HotelCompanyDTO)) {
            return false;
        }

        HotelCompanyDTO hotelCompanyDTO = (HotelCompanyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hotelCompanyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HotelCompanyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", place='" + getPlace() + "'" +
            "}";
    }
}
