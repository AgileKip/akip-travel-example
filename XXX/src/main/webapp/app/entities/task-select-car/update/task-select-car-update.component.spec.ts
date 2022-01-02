jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TaskSelectCarService } from '../service/task-select-car.service';
import { ITaskSelectCar, TaskSelectCar } from '../task-select-car.model';
import { ICar } from 'app/entities/car/car.model';
import { CarService } from 'app/entities/car/service/car.service';

import { TaskSelectCarUpdateComponent } from './task-select-car-update.component';

describe('Component Tests', () => {
  describe('TaskSelectCar Management Update Component', () => {
    let comp: TaskSelectCarUpdateComponent;
    let fixture: ComponentFixture<TaskSelectCarUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let taskSelectCarService: TaskSelectCarService;
    let carService: CarService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaskSelectCarUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TaskSelectCarUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskSelectCarUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      taskSelectCarService = TestBed.inject(TaskSelectCarService);
      carService = TestBed.inject(CarService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Car query and add missing value', () => {
        const taskSelectCar: ITaskSelectCar = { id: 456 };
        const car: ICar = { id: 95892 };
        taskSelectCar.car = car;

        const carCollection: ICar[] = [{ id: 57945 }];
        spyOn(carService, 'query').and.returnValue(of(new HttpResponse({ body: carCollection })));
        const additionalCars = [car];
        const expectedCollection: ICar[] = [...additionalCars, ...carCollection];
        spyOn(carService, 'addCarToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ taskSelectCar });
        comp.ngOnInit();

        expect(carService.query).toHaveBeenCalled();
        expect(carService.addCarToCollectionIfMissing).toHaveBeenCalledWith(carCollection, ...additionalCars);
        expect(comp.carsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const taskSelectCar: ITaskSelectCar = { id: 456 };
        const car: ICar = { id: 38104 };
        taskSelectCar.car = car;

        activatedRoute.data = of({ taskSelectCar });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(taskSelectCar));
        expect(comp.carsSharedCollection).toContain(car);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taskSelectCar = { id: 123 };
        spyOn(taskSelectCarService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taskSelectCar });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taskSelectCar }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(taskSelectCarService.update).toHaveBeenCalledWith(taskSelectCar);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taskSelectCar = new TaskSelectCar();
        spyOn(taskSelectCarService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taskSelectCar });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taskSelectCar }));
        saveSubject.complete();

        // THEN
        expect(taskSelectCarService.create).toHaveBeenCalledWith(taskSelectCar);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taskSelectCar = { id: 123 };
        spyOn(taskSelectCarService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taskSelectCar });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(taskSelectCarService.update).toHaveBeenCalledWith(taskSelectCar);
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
  });
});
