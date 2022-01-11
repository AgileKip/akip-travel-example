package org.agilekip.tutorials.travelcomplete.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Car.
 */
@Entity
@Table(name = "car")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 8)
    @Column(name = "license", length = 8, nullable = false)
    private String license;

    @NotNull
    @Column(name = "passengers", nullable = false)
    private Integer passengers;

    @Column(name = "booked")
    private LocalDate booked;

    @Column(name = "duration")
    private Integer duration;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @ManyToOne(optional = false)
    @NotNull
    private CarRentalCompany carCo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Car id(Long id) {
        this.id = id;
        return this;
    }

    public String getLicense() {
        return this.license;
    }

    public Car license(String license) {
        this.license = license;
        return this;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Integer getPassengers() {
        return this.passengers;
    }

    public Car passengers(Integer passengers) {
        this.passengers = passengers;
        return this;
    }

    public void setPassengers(Integer passengers) {
        this.passengers = passengers;
    }

    public LocalDate getBooked() {
        return this.booked;
    }

    public Car booked(LocalDate booked) {
        this.booked = booked;
        return this;
    }

    public void setBooked(LocalDate booked) {
        this.booked = booked;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Car duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPrice() {
        return this.price;
    }

    public Car price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public CarRentalCompany getCarCo() {
        return this.carCo;
    }

    public Car carCo(CarRentalCompany carRentalCompany) {
        this.setCarCo(carRentalCompany);
        return this;
    }

    public void setCarCo(CarRentalCompany carRentalCompany) {
        this.carCo = carRentalCompany;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Car)) {
            return false;
        }
        return id != null && id.equals(((Car) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Car{" +
            "id=" + getId() +
            ", license='" + getLicense() + "'" +
            ", passengers=" + getPassengers() +
            ", booked='" + getBooked() + "'" +
            ", duration=" + getDuration() +
            ", price=" + getPrice() +
            "}";
    }
}
