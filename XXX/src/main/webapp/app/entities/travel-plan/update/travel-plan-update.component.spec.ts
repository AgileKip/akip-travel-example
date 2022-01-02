jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TravelPlanService } from '../service/travel-plan.service';
import { ITravelPlan, TravelPlan } from '../travel-plan.model';
import { ICar } from 'app/entities/car/car.model';
import { CarService } from 'app/entities/car/service/car.service';
import { IFlight } from 'app/entities/flight/flight.model';
import { FlightService } from 'app/entities/flight/service/flight.service';
import { IHotelRoom } from 'app/entities/hotel-room/hotel-room.model';
import { HotelRoomService } from 'app/entities/hotel-room/service/hotel-room.service';

import { TravelPlanUpdateComponent } from './travel-plan-update.component';

describe('Component Tests', () => {
  describe('TravelPlan Management Update Component', () => {
    let comp: TravelPlanUpdateComponent;
    let fixture: ComponentFixture<TravelPlanUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let travelPlanService: TravelPlanService;
    let carService: CarService;
    let flightService: FlightService;
    let hotelRoomService: HotelRoomService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TravelPlanUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TravelPlanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TravelPlanUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      travelPlanService = TestBed.inject(TravelPlanService);
      carService = TestBed.inject(CarService);
      flightService = TestBed.inject(FlightService);
      hotelRoomService = TestBed.inject(HotelRoomService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Car query and add missing value', () => {
        const travelPlan: ITravelPlan = { id: 456 };
        const car: ICar = { id: 17126 };
        travelPlan.car = car;

        const carCollection: ICar[] = [{ id: 50633 }];
        spyOn(carService, 'query').and.returnValue(of(new HttpResponse({ body: carCollection })));
        const additionalCars = [car];
        const expectedCollection: ICar[] = [...additionalCars, ...carCollection];
        spyOn(carService, 'addCarToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ travelPlan });
        comp.ngOnInit();

        expect(carService.query).toHaveBeenCalled();
        expect(carService.addCarToCollectionIfMissing).toHaveBeenCalledWith(carCollection, ...additionalCars);
        expect(comp.carsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Flight query and add missing value', () => {
        const travelPlan: ITravelPlan = { id: 456 };
        const flight: IFlight = { id: 42300 };
        travelPlan.flight = flight;

        const flightCollection: IFlight[] = [{ id: 55598 }];
        spyOn(flightService, 'query').and.returnValue(of(new HttpResponse({ body: flightCollection })));
        const additionalFlights = [flight];
        const expectedCollection: IFlight[] = [...additionalFlights, ...flightCollection];
        spyOn(flightService, 'addFlightToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ travelPlan });
        comp.ngOnInit();

        expect(flightService.query).toHaveBeenCalled();
        expect(flightService.addFlightToCollectionIfMissing).toHaveBeenCalledWith(flightCollection, ...additionalFlights);
        expect(comp.flightsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call HotelRoom query and add missing value', () => {
        const travelPlan: ITravelPlan = { id: 456 };
        const hotelRoom: IHotelRoom = { id: 59278 };
        travelPlan.hotelRoom = hotelRoom;

        const hotelRoomCollection: IHotelRoom[] = [{ id: 84539 }];
        spyOn(hotelRoomService, 'query').and.returnValue(of(new HttpResponse({ body: hotelRoomCollection })));
        const additionalHotelRooms = [hotelRoom];
        const expectedCollection: IHotelRoom[] = [...additionalHotelRooms, ...hotelRoomCollection];
        spyOn(hotelRoomService, 'addHotelRoomToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ travelPlan });
        comp.ngOnInit();

        expect(hotelRoomService.query).toHaveBeenCalled();
        expect(hotelRoomService.addHotelRoomToCollectionIfMissing).toHaveBeenCalledWith(hotelRoomCollection, ...additionalHotelRooms);
        expect(comp.hotelRoomsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const travelPlan: ITravelPlan = { id: 456 };
        const car: ICar = { id: 34373 };
        travelPlan.car = car;
        const flight: IFlight = { id: 75603 };
        travelPlan.flight = flight;
        const hotelRoom: IHotelRoom = { id: 77044 };
        travelPlan.hotelRoom = hotelRoom;

        activatedRoute.data = of({ travelPlan });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(travelPlan));
        expect(comp.carsSharedCollection).toContain(car);
        expect(comp.flightsSharedCollection).toContain(flight);
        expect(comp.hotelRoomsSharedCollection).toContain(hotelRoom);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const travelPlan = { id: 123 };
        spyOn(travelPlanService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ travelPlan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: travelPlan }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(travelPlanService.update).toHaveBeenCalledWith(travelPlan);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const travelPlan = new TravelPlan();
        spyOn(travelPlanService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ travelPlan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: travelPlan }));
        saveSubject.complete();

        // THEN
        expect(travelPlanService.create).toHaveBeenCalledWith(travelPlan);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const travelPlan = { id: 123 };
        spyOn(travelPlanService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ travelPlan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(travelPlanService.update).toHaveBeenCalledWith(travelPlan);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCarById', () => {
        it('Should return tracked Car primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCarById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackFlightById', () => {
        it('Should return tracked Flight primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackFlightById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackHotelRoomById', () => {
        it('Should return tracked HotelRoom primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackHotelRoomById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
