jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { HotelRoomService } from '../service/hotel-room.service';
import { IHotelRoom, HotelRoom } from '../hotel-room.model';
import { IHotelCompany } from 'app/entities/hotel-company/hotel-company.model';
import { HotelCompanyService } from 'app/entities/hotel-company/service/hotel-company.service';

import { HotelRoomUpdateComponent } from './hotel-room-update.component';

describe('Component Tests', () => {
  describe('HotelRoom Management Update Component', () => {
    let comp: HotelRoomUpdateComponent;
    let fixture: ComponentFixture<HotelRoomUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let hotelRoomService: HotelRoomService;
    let hotelCompanyService: HotelCompanyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [HotelRoomUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(HotelRoomUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HotelRoomUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      hotelRoomService = TestBed.inject(HotelRoomService);
      hotelCompanyService = TestBed.inject(HotelCompanyService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call HotelCompany query and add missing value', () => {
        const hotelRoom: IHotelRoom = { id: 456 };
        const hotelCo: IHotelCompany = { id: 1448 };
        hotelRoom.hotelCo = hotelCo;

        const hotelCompanyCollection: IHotelCompany[] = [{ id: 54620 }];
        spyOn(hotelCompanyService, 'query').and.returnValue(of(new HttpResponse({ body: hotelCompanyCollection })));
        const additionalHotelCompanies = [hotelCo];
        const expectedCollection: IHotelCompany[] = [...additionalHotelCompanies, ...hotelCompanyCollection];
        spyOn(hotelCompanyService, 'addHotelCompanyToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ hotelRoom });
        comp.ngOnInit();

        expect(hotelCompanyService.query).toHaveBeenCalled();
        expect(hotelCompanyService.addHotelCompanyToCollectionIfMissing).toHaveBeenCalledWith(
          hotelCompanyCollection,
          ...additionalHotelCompanies
        );
        expect(comp.hotelCompaniesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const hotelRoom: IHotelRoom = { id: 456 };
        const hotelCo: IHotelCompany = { id: 88980 };
        hotelRoom.hotelCo = hotelCo;

        activatedRoute.data = of({ hotelRoom });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(hotelRoom));
        expect(comp.hotelCompaniesSharedCollection).toContain(hotelCo);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const hotelRoom = { id: 123 };
        spyOn(hotelRoomService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ hotelRoom });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: hotelRoom }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(hotelRoomService.update).toHaveBeenCalledWith(hotelRoom);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const hotelRoom = new HotelRoom();
        spyOn(hotelRoomService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ hotelRoom });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: hotelRoom }));
        saveSubject.complete();

        // THEN
        expect(hotelRoomService.create).toHaveBeenCalledWith(hotelRoom);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const hotelRoom = { id: 123 };
        spyOn(hotelRoomService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ hotelRoom });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(hotelRoomService.update).toHaveBeenCalledWith(hotelRoom);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackHotelCompanyById', () => {
        it('Should return tracked HotelCompany primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackHotelCompanyById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
