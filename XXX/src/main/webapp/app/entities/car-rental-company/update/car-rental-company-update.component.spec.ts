jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CarRentalCompanyService } from '../service/car-rental-company.service';
import { ICarRentalCompany, CarRentalCompany } from '../car-rental-company.model';

import { CarRentalCompanyUpdateComponent } from './car-rental-company-update.component';

describe('Component Tests', () => {
  describe('CarRentalCompany Management Update Component', () => {
    let comp: CarRentalCompanyUpdateComponent;
    let fixture: ComponentFixture<CarRentalCompanyUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let carRentalCompanyService: CarRentalCompanyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CarRentalCompanyUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CarRentalCompanyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CarRentalCompanyUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      carRentalCompanyService = TestBed.inject(CarRentalCompanyService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const carRentalCompany: ICarRentalCompany = { id: 456 };

        activatedRoute.data = of({ carRentalCompany });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(carRentalCompany));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const carRentalCompany = { id: 123 };
        spyOn(carRentalCompanyService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ carRentalCompany });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: carRentalCompany }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(carRentalCompanyService.update).toHaveBeenCalledWith(carRentalCompany);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const carRentalCompany = new CarRentalCompany();
        spyOn(carRentalCompanyService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ carRentalCompany });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: carRentalCompany }));
        saveSubject.complete();

        // THEN
        expect(carRentalCompanyService.create).toHaveBeenCalledWith(carRentalCompany);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const carRentalCompany = { id: 123 };
        spyOn(carRentalCompanyService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ carRentalCompany });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(carRentalCompanyService.update).toHaveBeenCalledWith(carRentalCompany);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
