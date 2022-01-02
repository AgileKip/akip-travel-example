import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { HotelRoomService } from '../service/hotel-room.service';

import { HotelRoomComponent } from './hotel-room.component';

describe('Component Tests', () => {
  describe('HotelRoom Management Component', () => {
    let comp: HotelRoomComponent;
    let fixture: ComponentFixture<HotelRoomComponent>;
    let service: HotelRoomService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [HotelRoomComponent],
      })
        .overrideTemplate(HotelRoomComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HotelRoomComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(HotelRoomService);

      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.hotelRooms?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
