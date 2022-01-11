package org.agilekip.tutorials.travelcomplete.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.agilekip.tutorials.travelcomplete.domain.HotelRoom} entity.
 */
public class HotelRoomDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 20)
    private String roomID;

    @NotNull
    private Integer sleeps;

    private LocalDate boodked;

    private Integer duration;

    @NotNull
    private Integer price;

    private HotelCompanyDTO hotelCo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public Integer getSleeps() {
        return sleeps;
    }

    public void setSleeps(Integer sleeps) {
        this.sleeps = sleeps;
    }

    public LocalDate getBoodked() {
        return boodked;
    }

    public void setBoodked(LocalDate boodked) {
        this.boodked = boodked;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public HotelCompanyDTO getHotelCo() {
        return hotelCo;
    }

    public void setHotelCo(HotelCompanyDTO hotelCo) {
        this.hotelCo = hotelCo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HotelRoomDTO)) {
            return false;
        }

        HotelRoomDTO hotelRoomDTO = (HotelRoomDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hotelRoomDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HotelRoomDTO{" +
            "id=" + getId() +
            ", roomID='" + getRoomID() + "'" +
            ", sleeps=" + getSleeps() +
            ", boodked='" + getBoodked() + "'" +
            ", duration=" + getDuration() +
            ", price=" + getPrice() +
            ", hotelCo=" + getHotelCo() +
            "}";
    }
}
