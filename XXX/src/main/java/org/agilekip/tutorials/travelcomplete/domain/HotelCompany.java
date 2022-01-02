package org.agilekip.tutorials.travelcomplete.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HotelCompany.
 */
@Entity
@Table(name = "hotel_company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HotelCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "place")
    private String place;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HotelCompany id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public HotelCompany name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return this.place;
    }

    public HotelCompany place(String place) {
        this.place = place;
        return this;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HotelCompany)) {
            return false;
        }
        return id != null && id.equals(((HotelCompany) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HotelCompany{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", place='" + getPlace() + "'" +
            "}";
    }
}
