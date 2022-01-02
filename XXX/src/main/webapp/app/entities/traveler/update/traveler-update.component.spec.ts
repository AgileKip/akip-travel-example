jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TravelerService } from '../service/traveler.service';
import { ITraveler, Traveler } from '../traveler.model';

import { TravelerUpdateComponent } from './traveler-update.component';

describe('Component Tests', () => {
  describe('Traveler Management Update Component', () => {
    let comp: TravelerUpdateComponent;
    let fixture: ComponentFixture<TravelerUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let travelerService: TravelerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TravelerUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TravelerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TravelerUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      travelerService = TestBed.inject(TravelerService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const traveler: ITraveler = { id: 456 };

        activatedRoute.data = of({ traveler });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(traveler));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const traveler = { id: 123 };
        spyOn(travelerService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ traveler });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: traveler }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(travelerService.update).toHaveBeenCalledWith(traveler);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const traveler = new Traveler();
        spyOn(travelerService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ traveler });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: traveler }));
        saveSubject.complete();

        // THEN
        expect(travelerService.create).toHaveBeenCalledWith(traveler);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const traveler = { id: 123 };
        spyOn(travelerService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ traveler });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(travelerService.update).toHaveBeenCalledWith(traveler);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
