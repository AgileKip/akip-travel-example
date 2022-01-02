jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CarService } from '../service/car.service';
import { ICar, Car } from '../car.model';
import { ICarRentalCompany } from 'app/entities/car-rental-company/car-rental-company.model';
import { CarRentalCompanyService } from 'app/entities/car-rental-company/service/car-rental-company.service';

import { CarUpdateComponent } from './car-update.component';

describe('Component Tests', () => {
  describe('Car Management Update Component', () => {
    let comp: CarUpdateComponent;
    let fixture: ComponentFixture<CarUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let carService: CarService;
    let carRentalCompanyService: CarRentalCompanyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CarUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CarUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CarUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      carService = TestBed.inject(CarService);
      carRentalCompanyService = TestBed.inject(CarRentalCompanyService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call CarRentalCompany query and add missing value', () => {
        const car: ICar = { id: 456 };
        const carCo: ICarRentalCompany = { id: 88439 };
        car.carCo = carCo;

        const carRentalCompanyCollection: ICarRentalCompany[] = [{ id: 59384 }];
        spyOn(carRentalCompanyService, 'query').and.returnValue(of(new HttpResponse({ body: carRentalCompanyCollection })));
        const additionalCarRentalCompanies = [carCo];
        const expectedCollection: ICarRentalCompany[] = [...additionalCarRentalCompanies, ...carRentalCompanyCollection];
        spyOn(carRentalCompanyService, 'addCarRentalCompanyToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ car });
        comp.ngOnInit();

        expect(carRentalCompanyService.query).toHaveBeenCalled();
        expect(carRentalCompanyService.addCarRentalCompanyToCollectionIfMissing).toHaveBeenCalledWith(
          carRentalCompanyCollection,
          ...additionalCarRentalCompanies
        );
        expect(comp.carRentalCompaniesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const car: ICar = { id: 456 };
        const carCo: ICarRentalCompany = { id: 47860 };
        car.carCo = carCo;

        activatedRoute.data = of({ car });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(car));
        expect(comp.carRentalCompaniesSharedCollection).toContain(carCo);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const car = { id: 123 };
        spyOn(carService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ car });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: car }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(carService.update).toHaveBeenCalledWith(car);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const car = new Car();
        spyOn(carService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ car });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: car }));
        saveSubject.complete();

        // THEN
        expect(carService.create).toHaveBeenCalledWith(car);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const car = { id: 123 };
        spyOn(carService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ car });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(carService.update).toHaveBeenCalledWith(car);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCarRentalCompanyById', () => {
        it('Should return tracked CarRentalCompany primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCarRentalCompanyById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
