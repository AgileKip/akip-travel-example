jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TaskSelectHotelService } from '../service/task-select-hotel.service';
import { ITaskSelectHotel, TaskSelectHotel } from '../task-select-hotel.model';
import { IHotelRoom } from 'app/entities/hotel-room/hotel-room.model';
import { HotelRoomService } from 'app/entities/hotel-room/service/hotel-room.service';

import { TaskSelectHotelUpdateComponent } from './task-select-hotel-update.component';

describe('Component Tests', () => {
  describe('TaskSelectHotel Management Update Component', () => {
    let comp: TaskSelectHotelUpdateComponent;
    let fixture: ComponentFixture<TaskSelectHotelUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let taskSelectHotelService: TaskSelectHotelService;
    let hotelRoomService: HotelRoomService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaskSelectHotelUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TaskSelectHotelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskSelectHotelUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      taskSelectHotelService = TestBed.inject(TaskSelectHotelService);
      hotelRoomService = TestBed.inject(HotelRoomService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call HotelRoom query and add missing value', () => {
        const taskSelectHotel: ITaskSelectHotel = { id: 456 };
        const hotel: IHotelRoom = { id: 5751 };
        taskSelectHotel.hotel = hotel;

        const hotelRoomCollection: IHotelRoom[] = [{ id: 77214 }];
        spyOn(hotelRoomService, 'query').and.returnValue(of(new HttpResponse({ body: hotelRoomCollection })));
        const additionalHotelRooms = [hotel];
        const expectedCollection: IHotelRoom[] = [...additionalHotelRooms, ...hotelRoomCollection];
        spyOn(hotelRoomService, 'addHotelRoomToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ taskSelectHotel });
        comp.ngOnInit();

        expect(hotelRoomService.query).toHaveBeenCalled();
        expect(hotelRoomService.addHotelRoomToCollectionIfMissing).toHaveBeenCalledWith(hotelRoomCollection, ...additionalHotelRooms);
        expect(comp.hotelRoomsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const taskSelectHotel: ITaskSelectHotel = { id: 456 };
        const hotel: IHotelRoom = { id: 80972 };
        taskSelectHotel.hotel = hotel;

        activatedRoute.data = of({ taskSelectHotel });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(taskSelectHotel));
        expect(comp.hotelRoomsSharedCollection).toContain(hotel);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taskSelectHotel = { id: 123 };
        spyOn(taskSelectHotelService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taskSelectHotel });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taskSelectHotel }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(taskSelectHotelService.update).toHaveBeenCalledWith(taskSelectHotel);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taskSelectHotel = new TaskSelectHotel();
        spyOn(taskSelectHotelService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taskSelectHotel });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taskSelectHotel }));
        saveSubject.complete();

        // THEN
        expect(taskSelectHotelService.create).toHaveBeenCalledWith(taskSelectHotel);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taskSelectHotel = { id: 123 };
        spyOn(taskSelectHotelService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taskSelectHotel });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(taskSelectHotelService.update).toHaveBeenCalledWith(taskSelectHotel);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackHotelRoomById', () => {
        it('Should return tracked HotelRoom primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackHotelRoomById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
