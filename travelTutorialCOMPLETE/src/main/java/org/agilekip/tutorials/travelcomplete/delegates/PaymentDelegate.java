package org.agilekip.tutorials.travelcomplete.delegates;

import java.math.BigDecimal;
import org.agilekip.tutorials.travelcomplete.domain.Car;
import org.agilekip.tutorials.travelcomplete.domain.Flight;
import org.agilekip.tutorials.travelcomplete.domain.HotelRoom;
import org.agilekip.tutorials.travelcomplete.domain.TravelPlan;
import org.agilekip.tutorials.travelcomplete.domain.enumeration.PlanStatus;
import org.agilekip.tutorials.travelcomplete.repository.CarRepository;
import org.agilekip.tutorials.travelcomplete.repository.FlightRepository;
import org.agilekip.tutorials.travelcomplete.repository.HotelRoomRepository;
import org.agilekip.tutorials.travelcomplete.repository.TravelPlanRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.CarDTO;
import org.agilekip.tutorials.travelcomplete.service.dto.FlightDTO;
import org.agilekip.tutorials.travelcomplete.service.dto.HotelRoomDTO;
import org.agilekip.tutorials.travelcomplete.service.dto.HotelRoomDTO;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelPlanDTO;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelPlanProcessDTO;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentDelegate implements JavaDelegate {

    private final TravelPlanRepository travelPlanRepo;
    private final HotelRoomRepository hotelRoomRepo;
    private final FlightRepository flightRepo;
    private final CarRepository carRepo;

    public PaymentDelegate(
        TravelPlanRepository travelPlanRepository,
        HotelRoomRepository hotelRoomRepo,
        FlightRepository flightRepo,
        CarRepository carRepo
    ) {
        this.travelPlanRepo = travelPlanRepository;
        this.hotelRoomRepo = hotelRoomRepo;
        this.carRepo = carRepo;
        this.flightRepo = flightRepo;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        TravelPlanProcessDTO pi = (TravelPlanProcessDTO) delegateExecution.getVariable("processInstance");
        TravelPlanDTO travelPlanDTO = pi.getTravelPlan();
        TravelPlan travelPlanObj = travelPlanRepo.findById(travelPlanDTO.getId()).get();
        // Set the new values to the DTO and the Object so they are in sync
        travelPlanObj.setStatus(PlanStatus.PAID);
        travelPlanDTO.setStatus(PlanStatus.PAID);

        // save the modified obj to the repo
        travelPlanRepo.save(travelPlanObj);

        if (travelPlanDTO.getFlight() != null) {
            Flight flight = this.flightRepo.findById(travelPlanDTO.getFlight().getId()).get();
            flight.setEmptySeats(flight.getEmptySeats() - travelPlanDTO.getNumPax());
            flightRepo.save(flight);
        }
        if (travelPlanDTO.getHotelRoom() != null) {
            HotelRoom hotelRoom = this.hotelRoomRepo.findById(travelPlanDTO.getHotelRoom().getId()).get();
            hotelRoom.setBoodked(travelPlanDTO.getHotelStartDate());
            hotelRoom.setDuration(travelPlanDTO.getHotelDuration());
            hotelRoomRepo.save(hotelRoom);
        }

        if (travelPlanDTO.getCar() != null) {
            Car car = this.carRepo.findById(travelPlanDTO.getCar().getId()).get();
            car.setBooked(travelPlanDTO.getCarStartDate());
            car.setDuration(travelPlanDTO.getCarDuration());
            carRepo.save(car);
        }
    }
}
