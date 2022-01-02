import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TaskSelectHotelService } from '../service/task-select-hotel.service';

import { TaskSelectHotelComponent } from './task-select-hotel.component';

describe('Component Tests', () => {
  describe('TaskSelectHotel Management Component', () => {
    let comp: TaskSelectHotelComponent;
    let fixture: ComponentFixture<TaskSelectHotelComponent>;
    let service: TaskSelectHotelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaskSelectHotelComponent],
      })
        .overrideTemplate(TaskSelectHotelComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskSelectHotelComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TaskSelectHotelService);

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
      expect(comp.taskSelectHotels?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
