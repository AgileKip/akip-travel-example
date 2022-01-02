package org.agilekip.tutorials.travelcomplete.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.agilekip.tutorials.travelcomplete.domain.TaskSelectCar} entity.
 */
public class TaskSelectCarDTO implements Serializable {

    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private String carBookingNumber;

    private CarDTO car;

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

    public String getCarBookingNumber() {
        return carBookingNumber;
    }

    public void setCarBookingNumber(String carBookingNumber) {
        this.carBookingNumber = carBookingNumber;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskSelectCarDTO)) {
            return false;
        }

        TaskSelectCarDTO taskSelectCarDTO = (TaskSelectCarDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taskSelectCarDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskSelectCarDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", carBookingNumber='" + getCarBookingNumber() + "'" +
            ", car=" + getCar() +
            "}";
    }
}
