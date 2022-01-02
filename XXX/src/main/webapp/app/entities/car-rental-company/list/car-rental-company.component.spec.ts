import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { CarRentalCompanyService } from '../service/car-rental-company.service';

import { CarRentalCompanyComponent } from './car-rental-company.component';

describe('Component Tests', () => {
  describe('CarRentalCompany Management Component', () => {
    let comp: CarRentalCompanyComponent;
    let fixture: ComponentFixture<CarRentalCompanyComponent>;
    let service: CarRentalCompanyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CarRentalCompanyComponent],
      })
        .overrideTemplate(CarRentalCompanyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CarRentalCompanyComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(CarRentalCompanyService);

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
      expect(comp.carRentalCompanies?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
