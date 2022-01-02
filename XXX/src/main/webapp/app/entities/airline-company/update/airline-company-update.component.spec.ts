jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AirlineCompanyService } from '../service/airline-company.service';
import { IAirlineCompany, AirlineCompany } from '../airline-company.model';

import { AirlineCompanyUpdateComponent } from './airline-company-update.component';

describe('Component Tests', () => {
  describe('AirlineCompany Management Update Component', () => {
    let comp: AirlineCompanyUpdateComponent;
    let fixture: ComponentFixture<AirlineCompanyUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let airlineCompanyService: AirlineCompanyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AirlineCompanyUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AirlineCompanyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AirlineCompanyUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      airlineCompanyService = TestBed.inject(AirlineCompanyService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const airlineCompany: IAirlineCompany = { id: 456 };

        activatedRoute.data = of({ airlineCompany });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(airlineCompany));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const airlineCompany = { id: 123 };
        spyOn(airlineCompanyService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ airlineCompany });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: airlineCompany }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(airlineCompanyService.update).toHaveBeenCalledWith(airlineCompany);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const airlineCompany = new AirlineCompany();
        spyOn(airlineCompanyService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ airlineCompany });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: airlineCompany }));
        saveSubject.complete();

        // THEN
        expect(airlineCompanyService.create).toHaveBeenCalledWith(airlineCompany);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const airlineCompany = { id: 123 };
        spyOn(airlineCompanyService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ airlineCompany });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(airlineCompanyService.update).toHaveBeenCalledWith(airlineCompany);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
