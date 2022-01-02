import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TaskSelectFlightService } from '../service/task-select-flight.service';

import { TaskSelectFlightComponent } from './task-select-flight.component';

describe('Component Tests', () => {
  describe('TaskSelectFlight Management Component', () => {
    let comp: TaskSelectFlightComponent;
    let fixture: ComponentFixture<TaskSelectFlightComponent>;
    let service: TaskSelectFlightService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaskSelectFlightComponent],
      })
        .overrideTemplate(TaskSelectFlightComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskSelectFlightComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TaskSelectFlightService);

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
      expect(comp.taskSelectFlights?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
