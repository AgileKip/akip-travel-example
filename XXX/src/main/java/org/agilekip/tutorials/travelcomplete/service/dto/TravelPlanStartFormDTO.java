package org.agilekip.tutorials.travelcomplete.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.agilekip.tutorials.travelcomplete.domain.TravelPlanStartForm} entity.
 */
public class TravelPlanStartFormDTO implements Serializable {

    private Long id;

    private String name;

    private Integer numPax;

    private LocalDate startDate;

    private LocalDate endDate;

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

    public Integer getNumPax() {
        return numPax;
    }

    public void setNumPax(Integer numPax) {
        this.numPax = numPax;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TravelPlanStartFormDTO)) {
            return false;
        }

        TravelPlanStartFormDTO travelPlanStartFormDTO = (TravelPlanStartFormDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, travelPlanStartFormDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TravelPlanStartFormDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", numPax=" + getNumPax() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
