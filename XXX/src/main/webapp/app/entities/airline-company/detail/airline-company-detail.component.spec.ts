import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AirlineCompanyDetailComponent } from './airline-company-detail.component';

describe('Component Tests', () => {
  describe('AirlineCompany Management Detail Component', () => {
    let comp: AirlineCompanyDetailComponent;
    let fixture: ComponentFixture<AirlineCompanyDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AirlineCompanyDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ airlineCompany: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AirlineCompanyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AirlineCompanyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load airlineCompany on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.airlineCompany).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
