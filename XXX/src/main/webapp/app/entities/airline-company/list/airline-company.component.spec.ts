import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { AirlineCompanyService } from '../service/airline-company.service';

import { AirlineCompanyComponent } from './airline-company.component';

describe('Component Tests', () => {
  describe('AirlineCompany Management Component', () => {
    let comp: AirlineCompanyComponent;
    let fixture: ComponentFixture<AirlineCompanyComponent>;
    let service: AirlineCompanyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AirlineCompanyComponent],
      })
        .overrideTemplate(AirlineCompanyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AirlineCompanyComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(AirlineCompanyService);

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
      expect(comp.airlineCompanies?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
