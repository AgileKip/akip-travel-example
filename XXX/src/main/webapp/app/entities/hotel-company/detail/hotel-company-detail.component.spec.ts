import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HotelCompanyDetailComponent } from './hotel-company-detail.component';

describe('Component Tests', () => {
  describe('HotelCompany Management Detail Component', () => {
    let comp: HotelCompanyDetailComponent;
    let fixture: ComponentFixture<HotelCompanyDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [HotelCompanyDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ hotelCompany: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(HotelCompanyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HotelCompanyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load hotelCompany on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.hotelCompany).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
