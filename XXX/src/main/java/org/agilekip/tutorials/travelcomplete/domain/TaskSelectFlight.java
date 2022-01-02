package org.agilekip.tutorials.travelcomplete.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TaskSelectFlight.
 */
@Entity
@Table(name = "task_select_flight")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskSelectFlight implements Serializable {

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

    @Column(name = "airline_company_name")
    private String airlineCompanyName;

    @Column(name = "airline_ticket_number")
    private String airlineTicketNumber;

    @ManyToOne
    @JsonIgnoreProperties(value = { "from", "to", "airline" }, allowSetters = true)
    private Flight flight;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskSelectFlight id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public TaskSelectFlight name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public TaskSelectFlight startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public TaskSelectFlight endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getAirlineCompanyName() {
        return this.airlineCompanyName;
    }

    public TaskSelectFlight airlineCompanyName(String airlineCompanyName) {
        this.airlineCompanyName = airlineCompanyName;
        return this;
    }

    public void setAirlineCompanyName(String airlineCompanyName) {
        this.airlineCompanyName = airlineCompanyName;
    }

    public String getAirlineTicketNumber() {
        return this.airlineTicketNumber;
    }

    public TaskSelectFlight airlineTicketNumber(String airlineTicketNumber) {
        this.airlineTicketNumber = airlineTicketNumber;
        return this;
    }

    public void setAirlineTicketNumber(String airlineTicketNumber) {
        this.airlineTicketNumber = airlineTicketNumber;
    }

    public Flight getFlight() {
        return this.flight;
    }

    public TaskSelectFlight flight(Flight flight) {
        this.setFlight(flight);
        return this;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskSelectFlight)) {
            return false;
        }
        return id != null && id.equals(((TaskSelectFlight) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskSelectFlight{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", airlineCompanyName='" + getAirlineCompanyName() + "'" +
            ", airlineTicketNumber='" + getAirlineTicketNumber() + "'" +
            "}";
    }
}
