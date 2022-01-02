import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { FlightService } from '../service/flight.service';

import { FlightComponent } from './flight.component';

describe('Component Tests', () => {
  describe('Flight Management Component', () => {
    let comp: FlightComponent;
    let fixture: ComponentFixture<FlightComponent>;
    let service: FlightService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FlightComponent],
      })
        .overrideTemplate(FlightComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FlightComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(FlightService);

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
      expect(comp.flights?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
