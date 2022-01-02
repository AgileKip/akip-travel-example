package org.agilekip.tutorials.travelcomplete.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.agilekip.tutorials.travelcomplete.domain.TaskSelectHotel} entity.
 */
public class TaskSelectHotelDTO implements Serializable {

    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private String hotelName;

    private String hotelBookingNumber;

    private HotelRoomDTO hotel;

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

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelBookingNumber() {
        return hotelBookingNumber;
    }

    public void setHotelBookingNumber(String hotelBookingNumber) {
        this.hotelBookingNumber = hotelBookingNumber;
    }

    public HotelRoomDTO getHotel() {
        return hotel;
    }

    public void setHotel(HotelRoomDTO hotel) {
        this.hotel = hotel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskSelectHotelDTO)) {
            return false;
        }

        TaskSelectHotelDTO taskSelectHotelDTO = (TaskSelectHotelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taskSelectHotelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskSelectHotelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", hotelName='" + getHotelName() + "'" +
            ", hotelBookingNumber='" + getHotelBookingNumber() + "'" +
            ", hotel=" + getHotel() +
            "}";
    }
}
