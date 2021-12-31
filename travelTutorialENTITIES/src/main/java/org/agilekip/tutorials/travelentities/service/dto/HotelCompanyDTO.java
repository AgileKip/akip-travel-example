package org.agilekip.tutorials.travelentities.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.agilekip.tutorials.travelentities.domain.HotelCompany} entity.
 */
public class HotelCompanyDTO implements Serializable {

    private Long id;

    private String name;

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
            ", code='" + getCode() + "'" +
            "}";
    }
}
