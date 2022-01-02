jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TaskSelectFlightService } from '../service/task-select-flight.service';
import { ITaskSelectFlight, TaskSelectFlight } from '../task-select-flight.model';
import { IFlight } from 'app/entities/flight/flight.model';
import { FlightService } from 'app/entities/flight/service/flight.service';

import { TaskSelectFlightUpdateComponent } from './task-select-flight-update.component';

describe('Component Tests', () => {
  describe('TaskSelectFlight Management Update Component', () => {
    let comp: TaskSelectFlightUpdateComponent;
    let fixture: ComponentFixture<TaskSelectFlightUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let taskSelectFlightService: TaskSelectFlightService;
    let flightService: FlightService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaskSelectFlightUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TaskSelectFlightUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskSelectFlightUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      taskSelectFlightService = TestBed.inject(TaskSelectFlightService);
      flightService = TestBed.inject(FlightService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Flight query and add missing value', () => {
        const taskSelectFlight: ITaskSelectFlight = { id: 456 };
        const flight: IFlight = { id: 89492 };
        taskSelectFlight.flight = flight;

        const flightCollection: IFlight[] = [{ id: 36089 }];
        spyOn(flightService, 'query').and.returnValue(of(new HttpResponse({ body: flightCollection })));
        const additionalFlights = [flight];
        const expectedCollection: IFlight[] = [...additionalFlights, ...flightCollection];
        spyOn(flightService, 'addFlightToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ taskSelectFlight });
        comp.ngOnInit();

        expect(flightService.query).toHaveBeenCalled();
        expect(flightService.addFlightToCollectionIfMissing).toHaveBeenCalledWith(flightCollection, ...additionalFlights);
        expect(comp.flightsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const taskSelectFlight: ITaskSelectFlight = { id: 456 };
        const flight: IFlight = { id: 85083 };
        taskSelectFlight.flight = flight;

        activatedRoute.data = of({ taskSelectFlight });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(taskSelectFlight));
        expect(comp.flightsSharedCollection).toContain(flight);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taskSelectFlight = { id: 123 };
        spyOn(taskSelectFlightService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taskSelectFlight });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taskSelectFlight }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(taskSelectFlightService.update).toHaveBeenCalledWith(taskSelectFlight);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taskSelectFlight = new TaskSelectFlight();
        spyOn(taskSelectFlightService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taskSelectFlight });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taskSelectFlight }));
        saveSubject.complete();

        // THEN
        expect(taskSelectFlightService.create).toHaveBeenCalledWith(taskSelectFlight);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taskSelectFlight = { id: 123 };
        spyOn(taskSelectFlightService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taskSelectFlight });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(taskSelectFlightService.update).toHaveBeenCalledWith(taskSelectFlight);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackFlightById', () => {
        it('Should return tracked Flight primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackFlightById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
