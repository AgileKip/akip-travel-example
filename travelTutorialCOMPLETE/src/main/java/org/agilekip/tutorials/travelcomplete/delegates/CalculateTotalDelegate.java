package org.agilekip.tutorials.travelcomplete.delegates;

import java.math.BigDecimal;
import org.agilekip.tutorials.travelcomplete.domain.Car;
import org.agilekip.tutorials.travelcomplete.domain.Flight;
import org.agilekip.tutorials.travelcomplete.domain.HotelRoom;
import org.agilekip.tutorials.travelcomplete.domain.TravelPlan;
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
public class CalculateTotalDelegate implements JavaDelegate {

    private final TravelPlanRepository travelPlanRepo;
    private final HotelRoomRepository hotelRoomRepo;
    private final FlightRepository flightRepo;
    private final CarRepository carRepo;

    public CalculateTotalDelegate(
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
        System.out.println("+++++++++++++++++++++++");
        System.out.println(delegateExecution.getVariable("processInstance").getClass().getName());
        System.out.println(delegateExecution.getVariable("processInstance"));
        System.out.println("+++++++++++++++++++++++");

        TravelPlanProcessDTO pi = (TravelPlanProcessDTO) delegateExecution.getVariable("processInstance");
        TravelPlanDTO travelPlanDTO = pi.getTravelPlan();

        HotelRoom hotelRoom = this.hotelRoomRepo.findById(travelPlanDTO.getHotelRoom().getId()).get();
        Flight flight = this.flightRepo.findById(travelPlanDTO.getFlight().getId()).get();
        Car car = this.carRepo.findById(travelPlanDTO.getCar().getId()).get();

        String name = travelPlanDTO.getName();

        Integer daysCar = travelPlanDTO.getCarDuration();
        Integer daysHotel = travelPlanDTO.getHotelDuration();

        Integer flightPrice = flight.getPrice();
        Integer hotelPrice = hotelRoom.getPrice() * daysHotel;
        Integer carPrice = car.getPrice() * daysCar;
        Integer total = flightPrice + hotelPrice + carPrice;

        TravelPlan travelPlanObj = travelPlanRepo.findById(travelPlanDTO.getId()).get();
        // Set the new values to the DTO and the Object so they are in sync
        travelPlanObj.setPrice(total);
        travelPlanDTO.setPrice(total);

        // save the modified obj to the repo
        travelPlanRepo.save(travelPlanObj);

        System.out.println("=================================================");
        System.out.println("=============== TOTAL FOR !!! " + name + "======================");
        System.out.println("=============== Flight = " + flightPrice + "======================");
        System.out.println("=============== Hotel = " + hotelPrice + "======================");
        System.out.println("=============== Car = " + carPrice + "======================");
        System.out.println("=================================================");
        System.out.println("=============== Total = " + total + "======================");
        System.out.println("=================================================");
    }
}
