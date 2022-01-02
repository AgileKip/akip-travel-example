import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { HotelCompanyService } from '../service/hotel-company.service';

import { HotelCompanyComponent } from './hotel-company.component';

describe('Component Tests', () => {
  describe('HotelCompany Management Component', () => {
    let comp: HotelCompanyComponent;
    let fixture: ComponentFixture<HotelCompanyComponent>;
    let service: HotelCompanyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [HotelCompanyComponent],
      })
        .overrideTemplate(HotelCompanyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HotelCompanyComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(HotelCompanyService);

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
      expect(comp.hotelCompanies?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
