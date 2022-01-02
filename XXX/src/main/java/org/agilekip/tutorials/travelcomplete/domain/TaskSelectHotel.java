package org.agilekip.tutorials.travelcomplete.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TaskSelectHotel.
 */
@Entity
@Table(name = "task_select_hotel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskSelectHotel implements Serializable {

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

    @Column(name = "hotel_name")
    private String hotelName;

    @Column(name = "hotel_booking_number")
    private String hotelBookingNumber;

    @ManyToOne
    @JsonIgnoreProperties(value = { "hotelCo" }, allowSetters = true)
    private HotelRoom hotel;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskSelectHotel id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public TaskSelectHotel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public TaskSelectHotel startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public TaskSelectHotel endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getHotelName() {
        return this.hotelName;
    }

    public TaskSelectHotel hotelName(String hotelName) {
        this.hotelName = hotelName;
        return this;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelBookingNumber() {
        return this.hotelBookingNumber;
    }

    public TaskSelectHotel hotelBookingNumber(String hotelBookingNumber) {
        this.hotelBookingNumber = hotelBookingNumber;
        return this;
    }

    public void setHotelBookingNumber(String hotelBookingNumber) {
        this.hotelBookingNumber = hotelBookingNumber;
    }

    public HotelRoom getHotel() {
        return this.hotel;
    }

    public TaskSelectHotel hotel(HotelRoom hotelRoom) {
        this.setHotel(hotelRoom);
        return this;
    }

    public void setHotel(HotelRoom hotelRoom) {
        this.hotel = hotelRoom;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskSelectHotel)) {
            return false;
        }
        return id != null && id.equals(((TaskSelectHotel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskSelectHotel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", hotelName='" + getHotelName() + "'" +
            ", hotelBookingNumber='" + getHotelBookingNumber() + "'" +
            "}";
    }
}
