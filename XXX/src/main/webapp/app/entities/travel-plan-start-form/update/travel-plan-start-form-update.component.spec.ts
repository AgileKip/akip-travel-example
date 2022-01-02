jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TravelPlanStartFormService } from '../service/travel-plan-start-form.service';
import { ITravelPlanStartForm, TravelPlanStartForm } from '../travel-plan-start-form.model';

import { TravelPlanStartFormUpdateComponent } from './travel-plan-start-form-update.component';

describe('Component Tests', () => {
  describe('TravelPlanStartForm Management Update Component', () => {
    let comp: TravelPlanStartFormUpdateComponent;
    let fixture: ComponentFixture<TravelPlanStartFormUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let travelPlanStartFormService: TravelPlanStartFormService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TravelPlanStartFormUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TravelPlanStartFormUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TravelPlanStartFormUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      travelPlanStartFormService = TestBed.inject(TravelPlanStartFormService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const travelPlanStartForm: ITravelPlanStartForm = { id: 456 };

        activatedRoute.data = of({ travelPlanStartForm });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(travelPlanStartForm));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const travelPlanStartForm = { id: 123 };
        spyOn(travelPlanStartFormService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ travelPlanStartForm });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: travelPlanStartForm }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(travelPlanStartFormService.update).toHaveBeenCalledWith(travelPlanStartForm);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const travelPlanStartForm = new TravelPlanStartForm();
        spyOn(travelPlanStartFormService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ travelPlanStartForm });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: travelPlanStartForm }));
        saveSubject.complete();

        // THEN
        expect(travelPlanStartFormService.create).toHaveBeenCalledWith(travelPlanStartForm);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const travelPlanStartForm = { id: 123 };
        spyOn(travelPlanStartFormService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ travelPlanStartForm });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(travelPlanStartFormService.update).toHaveBeenCalledWith(travelPlanStartForm);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
