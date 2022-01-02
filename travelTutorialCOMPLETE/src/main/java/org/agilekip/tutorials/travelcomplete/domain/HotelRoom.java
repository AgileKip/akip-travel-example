package org.agilekip.tutorials.travelcomplete.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HotelRoom.
 */
@Entity
@Table(name = "hotel_room")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HotelRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "sleeps")
    private Integer sleeps;

    @Column(name = "boodked")
    private LocalDate boodked;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "price")
    private Integer price;

    @ManyToOne
    private HotelCompany hotelCo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HotelRoom id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public HotelRoom code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSleeps() {
        return this.sleeps;
    }

    public HotelRoom sleeps(Integer sleeps) {
        this.sleeps = sleeps;
        return this;
    }

    public void setSleeps(Integer sleeps) {
        this.sleeps = sleeps;
    }

    public LocalDate getBoodked() {
        return this.boodked;
    }

    public HotelRoom boodked(LocalDate boodked) {
        this.boodked = boodked;
        return this;
    }

    public void setBoodked(LocalDate boodked) {
        this.boodked = boodked;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public HotelRoom duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPrice() {
        return this.price;
    }

    public HotelRoom price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public HotelCompany getHotelCo() {
        return this.hotelCo;
    }

    public HotelRoom hotelCo(HotelCompany hotelCompany) {
        this.setHotelCo(hotelCompany);
        return this;
    }

    public void setHotelCo(HotelCompany hotelCompany) {
        this.hotelCo = hotelCompany;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HotelRoom)) {
            return false;
        }
        return id != null && id.equals(((HotelRoom) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HotelRoom{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", sleeps=" + getSleeps() +
            ", boodked='" + getBoodked() + "'" +
            ", duration=" + getDuration() +
            ", price=" + getPrice() +
            "}";
    }
}
