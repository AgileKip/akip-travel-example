import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { AirportService } from '../service/airport.service';

import { AirportComponent } from './airport.component';

describe('Component Tests', () => {
  describe('Airport Management Component', () => {
    let comp: AirportComponent;
    let fixture: ComponentFixture<AirportComponent>;
    let service: AirportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AirportComponent],
      })
        .overrideTemplate(AirportComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AirportComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(AirportService);

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
      expect(comp.airports?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
