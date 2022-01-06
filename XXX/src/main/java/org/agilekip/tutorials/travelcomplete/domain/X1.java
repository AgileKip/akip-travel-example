package org.agilekip.tutorials.travelcomplete.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A X1.
 */
@Entity
@Table(name = "x1")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class X1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @JoinTable(name = "rel_x1__car", joinColumns = @JoinColumn(name = "x1_id"), inverseJoinColumns = @JoinColumn(name = "car_id"))
    @JsonIgnoreProperties(value = { "carCo" }, allowSetters = true)
    private Set<Car> cars = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public X1 id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public X1 name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Car> getCars() {
        return this.cars;
    }

    public X1 cars(Set<Car> cars) {
        this.setCars(cars);
        return this;
    }

    public X1 addCar(Car car) {
        this.cars.add(car);
        return this;
    }

    public X1 removeCar(Car car) {
        this.cars.remove(car);
        return this;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof X1)) {
            return false;
        }
        return id != null && id.equals(((X1) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "X1{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
