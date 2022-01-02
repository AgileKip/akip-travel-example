package org.agilekip.tutorials.travelcomplete.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.agilekip.tutorials.travelcomplete.domain.TaskSelectFlight} entity.
 */
public class TaskSelectFlightDTO implements Serializable {

    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private String airlineCompanyName;

    private String airlineTicketNumber;

    private FlightDTO flight;

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

    public String getAirlineCompanyName() {
        return airlineCompanyName;
    }

    public void setAirlineCompanyName(String airlineCompanyName) {
        this.airlineCompanyName = airlineCompanyName;
    }

    public String getAirlineTicketNumber() {
        return airlineTicketNumber;
    }

    public void setAirlineTicketNumber(String airlineTicketNumber) {
        this.airlineTicketNumber = airlineTicketNumber;
    }

    public FlightDTO getFlight() {
        return flight;
    }

    public void setFlight(FlightDTO flight) {
        this.flight = flight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskSelectFlightDTO)) {
            return false;
        }

        TaskSelectFlightDTO taskSelectFlightDTO = (TaskSelectFlightDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taskSelectFlightDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskSelectFlightDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", airlineCompanyName='" + getAirlineCompanyName() + "'" +
            ", airlineTicketNumber='" + getAirlineTicketNumber() + "'" +
            ", flight=" + getFlight() +
            "}";
    }
}
