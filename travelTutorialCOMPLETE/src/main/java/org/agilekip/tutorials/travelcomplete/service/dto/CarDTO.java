package org.agilekip.tutorials.travelcomplete.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.agilekip.tutorials.travelcomplete.domain.Car} entity.
 */
public class CarDTO implements Serializable {

    private Long id;

    private String code;

    private Integer passengers;

    private LocalDate booked;

    private Integer duration;

    private Integer price;

    private CarRentalCompanyDTO carCo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPassengers() {
        return passengers;
    }

    public void setPassengers(Integer passengers) {
        this.passengers = passengers;
    }

    public LocalDate getBooked() {
        return booked;
    }

    public void setBooked(LocalDate booked) {
        this.booked = booked;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public CarRentalCompanyDTO getCarCo() {
        return carCo;
    }

    public void setCarCo(CarRentalCompanyDTO carCo) {
        this.carCo = carCo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarDTO)) {
            return false;
        }

        CarDTO carDTO = (CarDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, carDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", passengers=" + getPassengers() +
            ", booked='" + getBooked() + "'" +
            ", duration=" + getDuration() +
            ", price=" + getPrice() +
            ", carCo=" + getCarCo() +
            "}";
    }
}
