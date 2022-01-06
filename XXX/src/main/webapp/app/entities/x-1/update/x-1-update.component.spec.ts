jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { X1Service } from '../service/x-1.service';
import { IX1, X1 } from '../x-1.model';
import { ICar } from 'app/entities/car/car.model';
import { CarService } from 'app/entities/car/service/car.service';

import { X1UpdateComponent } from './x-1-update.component';

describe('Component Tests', () => {
  describe('X1 Management Update Component', () => {
    let comp: X1UpdateComponent;
    let fixture: ComponentFixture<X1UpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let x1Service: X1Service;
    let carService: CarService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [X1UpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(X1UpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(X1UpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      x1Service = TestBed.inject(X1Service);
      carService = TestBed.inject(CarService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Car query and add missing value', () => {
        const x1: IX1 = { id: 456 };
        const cars: ICar[] = [{ id: 93487 }];
        x1.cars = cars;

        const carCollection: ICar[] = [{ id: 30772 }];
        spyOn(carService, 'query').and.returnValue(of(new HttpResponse({ body: carCollection })));
        const additionalCars = [...cars];
        const expectedCollection: ICar[] = [...additionalCars, ...carCollection];
        spyOn(carService, 'addCarToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ x1 });
        comp.ngOnInit();

        expect(carService.query).toHaveBeenCalled();
        expect(carService.addCarToCollectionIfMissing).toHaveBeenCalledWith(carCollection, ...additionalCars);
        expect(comp.carsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const x1: IX1 = { id: 456 };
        const cars: ICar = { id: 19288 };
        x1.cars = [cars];

        activatedRoute.data = of({ x1 });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(x1));
        expect(comp.carsSharedCollection).toContain(cars);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const x1 = { id: 123 };
        spyOn(x1Service, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ x1 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: x1 }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(x1Service.update).toHaveBeenCalledWith(x1);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const x1 = new X1();
        spyOn(x1Service, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ x1 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: x1 }));
        saveSubject.complete();

        // THEN
        expect(x1Service.create).toHaveBeenCalledWith(x1);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const x1 = { id: 123 };
        spyOn(x1Service, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ x1 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(x1Service.update).toHaveBeenCalledWith(x1);
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
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedCar', () => {
        it('Should return option if no Car is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedCar(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Car for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedCar(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Car is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedCar(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
