package org.agilekip.tutorials.travelcomplete.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.agilekip.tutorials.travelcomplete.domain.Flight} entity.
 */
public class FlightDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 5, max = 5)
    private String code;

    @NotNull
    private ZonedDateTime departure;

    @NotNull
    private Float duration;

    @NotNull
    private Integer emptySeats;

    private Integer price;

    private AirportDTO from;

    private AirportDTO to;

    private AirlineCompanyDTO airline;

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

    public ZonedDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(ZonedDateTime departure) {
        this.departure = departure;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public Integer getEmptySeats() {
        return emptySeats;
    }

    public void setEmptySeats(Integer emptySeats) {
        this.emptySeats = emptySeats;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public AirportDTO getFrom() {
        return from;
    }

    public void setFrom(AirportDTO from) {
        this.from = from;
    }

    public AirportDTO getTo() {
        return to;
    }

    public void setTo(AirportDTO to) {
        this.to = to;
    }

    public AirlineCompanyDTO getAirline() {
        return airline;
    }

    public void setAirline(AirlineCompanyDTO airline) {
        this.airline = airline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FlightDTO)) {
            return false;
        }

        FlightDTO flightDTO = (FlightDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, flightDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FlightDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", departure='" + getDeparture() + "'" +
            ", duration=" + getDuration() +
            ", emptySeats=" + getEmptySeats() +
            ", price=" + getPrice() +
            ", from=" + getFrom() +
            ", to=" + getTo() +
            ", airline=" + getAirline() +
            "}";
    }
}
