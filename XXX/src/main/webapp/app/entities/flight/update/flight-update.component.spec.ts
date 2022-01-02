jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FlightService } from '../service/flight.service';
import { IFlight, Flight } from '../flight.model';
import { IAirport } from 'app/entities/airport/airport.model';
import { AirportService } from 'app/entities/airport/service/airport.service';
import { IAirlineCompany } from 'app/entities/airline-company/airline-company.model';
import { AirlineCompanyService } from 'app/entities/airline-company/service/airline-company.service';

import { FlightUpdateComponent } from './flight-update.component';

describe('Component Tests', () => {
  describe('Flight Management Update Component', () => {
    let comp: FlightUpdateComponent;
    let fixture: ComponentFixture<FlightUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let flightService: FlightService;
    let airportService: AirportService;
    let airlineCompanyService: AirlineCompanyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FlightUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FlightUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FlightUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      flightService = TestBed.inject(FlightService);
      airportService = TestBed.inject(AirportService);
      airlineCompanyService = TestBed.inject(AirlineCompanyService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Airport query and add missing value', () => {
        const flight: IFlight = { id: 456 };
        const from: IAirport = { id: 90790 };
        flight.from = from;
        const to: IAirport = { id: 33356 };
        flight.to = to;

        const airportCollection: IAirport[] = [{ id: 90880 }];
        spyOn(airportService, 'query').and.returnValue(of(new HttpResponse({ body: airportCollection })));
        const additionalAirports = [from, to];
        const expectedCollection: IAirport[] = [...additionalAirports, ...airportCollection];
        spyOn(airportService, 'addAirportToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ flight });
        comp.ngOnInit();

        expect(airportService.query).toHaveBeenCalled();
        expect(airportService.addAirportToCollectionIfMissing).toHaveBeenCalledWith(airportCollection, ...additionalAirports);
        expect(comp.airportsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call AirlineCompany query and add missing value', () => {
        const flight: IFlight = { id: 456 };
        const airline: IAirlineCompany = { id: 4996 };
        flight.airline = airline;

        const airlineCompanyCollection: IAirlineCompany[] = [{ id: 67645 }];
        spyOn(airlineCompanyService, 'query').and.returnValue(of(new HttpResponse({ body: airlineCompanyCollection })));
        const additionalAirlineCompanies = [airline];
        const expectedCollection: IAirlineCompany[] = [...additionalAirlineCompanies, ...airlineCompanyCollection];
        spyOn(airlineCompanyService, 'addAirlineCompanyToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ flight });
        comp.ngOnInit();

        expect(airlineCompanyService.query).toHaveBeenCalled();
        expect(airlineCompanyService.addAirlineCompanyToCollectionIfMissing).toHaveBeenCalledWith(
          airlineCompanyCollection,
          ...additionalAirlineCompanies
        );
        expect(comp.airlineCompaniesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const flight: IFlight = { id: 456 };
        const from: IAirport = { id: 67777 };
        flight.from = from;
        const to: IAirport = { id: 30575 };
        flight.to = to;
        const airline: IAirlineCompany = { id: 1032 };
        flight.airline = airline;

        activatedRoute.data = of({ flight });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(flight));
        expect(comp.airportsSharedCollection).toContain(from);
        expect(comp.airportsSharedCollection).toContain(to);
        expect(comp.airlineCompaniesSharedCollection).toContain(airline);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const flight = { id: 123 };
        spyOn(flightService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ flight });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: flight }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(flightService.update).toHaveBeenCalledWith(flight);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const flight = new Flight();
        spyOn(flightService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ flight });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: flight }));
        saveSubject.complete();

        // THEN
        expect(flightService.create).toHaveBeenCalledWith(flight);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const flight = { id: 123 };
        spyOn(flightService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ flight });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(flightService.update).toHaveBeenCalledWith(flight);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackAirportById', () => {
        it('Should return tracked Airport primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackAirportById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackAirlineCompanyById', () => {
        it('Should return tracked AirlineCompany primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackAirlineCompanyById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
