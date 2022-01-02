package org.agilekip.tutorials.travelcomplete.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TaskSelectCar.
 */
@Entity
@Table(name = "task_select_car")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskSelectCar implements Serializable {

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

    @Column(name = "car_booking_number")
    private String carBookingNumber;

    @ManyToOne
    @JsonIgnoreProperties(value = { "carCo" }, allowSetters = true)
    private Car car;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskSelectCar id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public TaskSelectCar name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public TaskSelectCar startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public TaskSelectCar endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCarBookingNumber() {
        return this.carBookingNumber;
    }

    public TaskSelectCar carBookingNumber(String carBookingNumber) {
        this.carBookingNumber = carBookingNumber;
        return this;
    }

    public void setCarBookingNumber(String carBookingNumber) {
        this.carBookingNumber = carBookingNumber;
    }

    public Car getCar() {
        return this.car;
    }

    public TaskSelectCar car(Car car) {
        this.setCar(car);
        return this;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskSelectCar)) {
            return false;
        }
        return id != null && id.equals(((TaskSelectCar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskSelectCar{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", carBookingNumber='" + getCarBookingNumber() + "'" +
            "}";
    }
}
