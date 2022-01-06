package org.agilekip.tutorials.travelcomplete.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.agilekip.tutorials.travelcomplete.domain.enumeration.PlanStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TravelPlan.
 */
@Entity
@Table(name = "travel_plan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TravelPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "num_pax")
    private Integer numPax;

    @Column(name = "price")
    private Integer price;

    @Column(name = "payment")
    private String payment;

    @Column(name = "hotel_start_date")
    private LocalDate hotelStartDate;

    @Column(name = "hotel_duration")
    private Integer hotelDuration;

    @Column(name = "car_start_date")
    private LocalDate carStartDate;

    @Column(name = "car_duration")
    private Integer carDuration;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PlanStatus status;

    @Column(name = "proceed_to_check_out")
    private Boolean proceedToCheckOut;

    @ManyToOne
    @JsonIgnoreProperties(value = { "carCo" }, allowSetters = true)
    private Car car;

    @ManyToOne
    @JsonIgnoreProperties(value = { "from", "to", "airline" }, allowSetters = true)
    private Flight flight;

    @ManyToOne
    @JsonIgnoreProperties(value = { "hotelCo" }, allowSetters = true)
    private HotelRoom hotelRoom;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TravelPlan id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public TravelPlan name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public TravelPlan startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public TravelPlan endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getNumPax() {
        return this.numPax;
    }

    public TravelPlan numPax(Integer numPax) {
        this.numPax = numPax;
        return this;
    }

    public void setNumPax(Integer numPax) {
        this.numPax = numPax;
    }

    public Integer getPrice() {
        return this.price;
    }

    public TravelPlan price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPayment() {
        return this.payment;
    }

    public TravelPlan payment(String payment) {
        this.payment = payment;
        return this;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public LocalDate getHotelStartDate() {
        return this.hotelStartDate;
    }

    public TravelPlan hotelStartDate(LocalDate hotelStartDate) {
        this.hotelStartDate = hotelStartDate;
        return this;
    }

    public void setHotelStartDate(LocalDate hotelStartDate) {
        this.hotelStartDate = hotelStartDate;
    }

    public Integer getHotelDuration() {
        return this.hotelDuration;
    }

    public TravelPlan hotelDuration(Integer hotelDuration) {
        this.hotelDuration = hotelDuration;
        return this;
    }

    public void setHotelDuration(Integer hotelDuration) {
        this.hotelDuration = hotelDuration;
    }

    public LocalDate getCarStartDate() {
        return this.carStartDate;
    }

    public TravelPlan carStartDate(LocalDate carStartDate) {
        this.carStartDate = carStartDate;
        return this;
    }

    public void setCarStartDate(LocalDate carStartDate) {
        this.carStartDate = carStartDate;
    }

    public Integer getCarDuration() {
        return this.carDuration;
    }

    public TravelPlan carDuration(Integer carDuration) {
        this.carDuration = carDuration;
        return this;
    }

    public void setCarDuration(Integer carDuration) {
        this.carDuration = carDuration;
    }

    public PlanStatus getStatus() {
        return this.status;
    }

    public TravelPlan status(PlanStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(PlanStatus status) {
        this.status = status;
    }

    public Boolean getProceedToCheckOut() {
        return this.proceedToCheckOut;
    }

    public TravelPlan proceedToCheckOut(Boolean proceedToCheckOut) {
        this.proceedToCheckOut = proceedToCheckOut;
        return this;
    }

    public void setProceedToCheckOut(Boolean proceedToCheckOut) {
        this.proceedToCheckOut = proceedToCheckOut;
    }

    public Car getCar() {
        return this.car;
    }

    public TravelPlan car(Car car) {
        this.setCar(car);
        return this;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Flight getFlight() {
        return this.flight;
    }

    public TravelPlan flight(Flight flight) {
        this.setFlight(flight);
        return this;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public HotelRoom getHotelRoom() {
        return this.hotelRoom;
    }

    public TravelPlan hotelRoom(HotelRoom hotelRoom) {
        this.setHotelRoom(hotelRoom);
        return this;
    }

    public void setHotelRoom(HotelRoom hotelRoom) {
        this.hotelRoom = hotelRoom;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TravelPlan)) {
            return false;
        }
        return id != null && id.equals(((TravelPlan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TravelPlan{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", numPax=" + getNumPax() +
            ", price=" + getPrice() +
            ", payment='" + getPayment() + "'" +
            ", hotelStartDate='" + getHotelStartDate() + "'" +
            ", hotelDuration=" + getHotelDuration() +
            ", carStartDate='" + getCarStartDate() + "'" +
            ", carDuration=" + getCarDuration() +
            ", status='" + getStatus() + "'" +
            ", proceedToCheckOut='" + getProceedToCheckOut() + "'" +
            "}";
    }
}
