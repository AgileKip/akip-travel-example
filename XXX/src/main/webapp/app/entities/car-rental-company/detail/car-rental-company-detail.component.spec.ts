import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CarRentalCompanyDetailComponent } from './car-rental-company-detail.component';

describe('Component Tests', () => {
  describe('CarRentalCompany Management Detail Component', () => {
    let comp: CarRentalCompanyDetailComponent;
    let fixture: ComponentFixture<CarRentalCompanyDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CarRentalCompanyDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ carRentalCompany: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CarRentalCompanyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CarRentalCompanyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load carRentalCompany on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.carRentalCompany).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
