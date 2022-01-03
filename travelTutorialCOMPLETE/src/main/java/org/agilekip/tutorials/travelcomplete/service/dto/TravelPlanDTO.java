package org.agilekip.tutorials.travelcomplete.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import org.agilekip.tutorials.travelcomplete.domain.enumeration.PlanStatus;

/**
 * A DTO for the {@link org.agilekip.tutorials.travelcomplete.domain.TravelPlan} entity.
 */
public class TravelPlanDTO implements Serializable {

    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer numPax;

    private Integer price;

    private String payment;

    private Integer hotelDuration;

    private Integer carDuration;

    private PlanStatus status;

    private Boolean proceedToCheckOut;

    private CarDTO car;

    private FlightDTO flight;

    private HotelRoomDTO hotelRoom;

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

    public Integer getNumPax() {
        return numPax;
    }

    public void setNumPax(Integer numPax) {
        this.numPax = numPax;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Integer getHotelDuration() {
        return hotelDuration;
    }

    public void setHotelDuration(Integer hotelDuration) {
        this.hotelDuration = hotelDuration;
    }

    public Integer getCarDuration() {
        return carDuration;
    }

    public void setCarDuration(Integer carDuration) {
        this.carDuration = carDuration;
    }

    public PlanStatus getStatus() {
        return status;
    }

    public void setStatus(PlanStatus status) {
        this.status = status;
    }

    public Boolean getProceedToCheckOut() {
        return proceedToCheckOut;
    }

    public void setProceedToCheckOut(Boolean proceedToCheckOut) {
        this.proceedToCheckOut = proceedToCheckOut;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    public FlightDTO getFlight() {
        return flight;
    }

    public void setFlight(FlightDTO flight) {
        this.flight = flight;
    }

    public HotelRoomDTO getHotelRoom() {
        return hotelRoom;
    }

    public void setHotelRoom(HotelRoomDTO hotelRoom) {
        this.hotelRoom = hotelRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TravelPlanDTO)) {
            return false;
        }

        TravelPlanDTO travelPlanDTO = (TravelPlanDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, travelPlanDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TravelPlanDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", numPax=" + getNumPax() +
            ", price=" + getPrice() +
            ", payment='" + getPayment() + "'" +
            ", hotelDuration=" + getHotelDuration() +
            ", carDuration=" + getCarDuration() +
            ", status='" + getStatus() + "'" +
            ", proceedToCheckOut='" + getProceedToCheckOut() + "'" +
            ", car=" + getCar() +
            ", flight=" + getFlight() +
            ", hotelRoom=" + getHotelRoom() +
            "}";
    }
}
