import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HotelRoomDetailComponent } from './hotel-room-detail.component';

describe('Component Tests', () => {
  describe('HotelRoom Management Detail Component', () => {
    let comp: HotelRoomDetailComponent;
    let fixture: ComponentFixture<HotelRoomDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [HotelRoomDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ hotelRoom: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(HotelRoomDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HotelRoomDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load hotelRoom on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.hotelRoom).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
