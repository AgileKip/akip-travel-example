jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { HotelCompanyService } from '../service/hotel-company.service';
import { IHotelCompany, HotelCompany } from '../hotel-company.model';

import { HotelCompanyUpdateComponent } from './hotel-company-update.component';

describe('Component Tests', () => {
  describe('HotelCompany Management Update Component', () => {
    let comp: HotelCompanyUpdateComponent;
    let fixture: ComponentFixture<HotelCompanyUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let hotelCompanyService: HotelCompanyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [HotelCompanyUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(HotelCompanyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HotelCompanyUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      hotelCompanyService = TestBed.inject(HotelCompanyService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const hotelCompany: IHotelCompany = { id: 456 };

        activatedRoute.data = of({ hotelCompany });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(hotelCompany));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const hotelCompany = { id: 123 };
        spyOn(hotelCompanyService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ hotelCompany });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: hotelCompany }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(hotelCompanyService.update).toHaveBeenCalledWith(hotelCompany);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const hotelCompany = new HotelCompany();
        spyOn(hotelCompanyService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ hotelCompany });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: hotelCompany }));
        saveSubject.complete();

        // THEN
        expect(hotelCompanyService.create).toHaveBeenCalledWith(hotelCompany);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const hotelCompany = { id: 123 };
        spyOn(hotelCompanyService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ hotelCompany });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(hotelCompanyService.update).toHaveBeenCalledWith(hotelCompany);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
