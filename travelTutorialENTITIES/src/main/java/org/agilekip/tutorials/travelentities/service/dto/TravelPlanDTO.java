package org.agilekip.tutorials.travelentities.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.agilekip.tutorials.travelentities.domain.TravelPlan} entity.
 */
public class TravelPlanDTO implements Serializable {

    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private String hotelBookingNumber;

    private String airlineTicketNumber;

    private String carBookingNumber;

    private AirlineCompanyDTO airlineCompany;

    private HotelCompanyDTO hotelCompany;

    private CarRentalCompanyDTO carRentalCompany;

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

    public String getHotelBookingNumber() {
        return hotelBookingNumber;
    }

    public void setHotelBookingNumber(String hotelBookingNumber) {
        this.hotelBookingNumber = hotelBookingNumber;
    }

    public String getAirlineTicketNumber() {
        return airlineTicketNumber;
    }

    public void setAirlineTicketNumber(String airlineTicketNumber) {
        this.airlineTicketNumber = airlineTicketNumber;
    }

    public String getCarBookingNumber() {
        return carBookingNumber;
    }

    public void setCarBookingNumber(String carBookingNumber) {
        this.carBookingNumber = carBookingNumber;
    }

    public AirlineCompanyDTO getAirlineCompany() {
        return airlineCompany;
    }

    public void setAirlineCompany(AirlineCompanyDTO airlineCompany) {
        this.airlineCompany = airlineCompany;
    }

    public HotelCompanyDTO getHotelCompany() {
        return hotelCompany;
    }

    public void setHotelCompany(HotelCompanyDTO hotelCompany) {
        this.hotelCompany = hotelCompany;
    }

    public CarRentalCompanyDTO getCarRentalCompany() {
        return carRentalCompany;
    }

    public void setCarRentalCompany(CarRentalCompanyDTO carRentalCompany) {
        this.carRentalCompany = carRentalCompany;
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
            ", hotelBookingNumber='" + getHotelBookingNumber() + "'" +
            ", airlineTicketNumber='" + getAirlineTicketNumber() + "'" +
            ", carBookingNumber='" + getCarBookingNumber() + "'" +
            ", airlineCompany=" + getAirlineCompany() +
            ", hotelCompany=" + getHotelCompany() +
            ", carRentalCompany=" + getCarRentalCompany() +
            "}";
    }
}
