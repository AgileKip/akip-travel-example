package org.agilekip.tutorials.travelcomplete.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
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

    @Column(name = "code")
    private String code;

    @Column(name = "passangers")
    private Integer passangers;

    @Column(name = "boodked")
    private LocalDate boodked;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "price")
    private Integer price;

    @ManyToOne
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

    public String getCode() {
        return this.code;
    }

    public Car code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPassangers() {
        return this.passangers;
    }

    public Car passangers(Integer passangers) {
        this.passangers = passangers;
        return this;
    }

    public void setPassangers(Integer passangers) {
        this.passangers = passangers;
    }

    public LocalDate getBoodked() {
        return this.boodked;
    }

    public Car boodked(LocalDate boodked) {
        this.boodked = boodked;
        return this;
    }

    public void setBoodked(LocalDate boodked) {
        this.boodked = boodked;
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
            ", code='" + getCode() + "'" +
            ", passangers=" + getPassangers() +
            ", boodked='" + getBoodked() + "'" +
            ", duration=" + getDuration() +
            ", price=" + getPrice() +
            "}";
    }
}
