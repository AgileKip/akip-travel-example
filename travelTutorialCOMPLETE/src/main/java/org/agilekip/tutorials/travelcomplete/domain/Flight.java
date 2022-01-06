package org.agilekip.tutorials.travelcomplete.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Flight.
 */
@Entity
@Table(name = "flight")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Flight implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 5, max = 5)
    @Column(name = "code", length = 5, nullable = false)
    private String code;

    @NotNull
    @Column(name = "departure", nullable = false)
    private ZonedDateTime departure;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Float duration;

    @NotNull
    @Column(name = "empty_seats", nullable = false)
    private Integer emptySeats;

    @Column(name = "price")
    private Integer price;

    @ManyToOne
    private Airport from;

    @ManyToOne
    private Airport to;

    @ManyToOne
    private AirlineCompany airline;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Flight id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public Flight code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ZonedDateTime getDeparture() {
        return this.departure;
    }

    public Flight departure(ZonedDateTime departure) {
        this.departure = departure;
        return this;
    }

    public void setDeparture(ZonedDateTime departure) {
        this.departure = departure;
    }

    public Float getDuration() {
        return this.duration;
    }

    public Flight duration(Float duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public Integer getEmptySeats() {
        return this.emptySeats;
    }

    public Flight emptySeats(Integer emptySeats) {
        this.emptySeats = emptySeats;
        return this;
    }

    public void setEmptySeats(Integer emptySeats) {
        this.emptySeats = emptySeats;
    }

    public Integer getPrice() {
        return this.price;
    }

    public Flight price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Airport getFrom() {
        return this.from;
    }

    public Flight from(Airport airport) {
        this.setFrom(airport);
        return this;
    }

    public void setFrom(Airport airport) {
        this.from = airport;
    }

    public Airport getTo() {
        return this.to;
    }

    public Flight to(Airport airport) {
        this.setTo(airport);
        return this;
    }

    public void setTo(Airport airport) {
        this.to = airport;
    }

    public AirlineCompany getAirline() {
        return this.airline;
    }

    public Flight airline(AirlineCompany airlineCompany) {
        this.setAirline(airlineCompany);
        return this;
    }

    public void setAirline(AirlineCompany airlineCompany) {
        this.airline = airlineCompany;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Flight)) {
            return false;
        }
        return id != null && id.equals(((Flight) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Flight{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", departure='" + getDeparture() + "'" +
            ", duration=" + getDuration() +
            ", emptySeats=" + getEmptySeats() +
            ", price=" + getPrice() +
            "}";
    }
}
