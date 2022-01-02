import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TaskSelectCarService } from '../service/task-select-car.service';

import { TaskSelectCarComponent } from './task-select-car.component';

describe('Component Tests', () => {
  describe('TaskSelectCar Management Component', () => {
    let comp: TaskSelectCarComponent;
    let fixture: ComponentFixture<TaskSelectCarComponent>;
    let service: TaskSelectCarService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaskSelectCarComponent],
      })
        .overrideTemplate(TaskSelectCarComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskSelectCarComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TaskSelectCarService);

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
      expect(comp.taskSelectCars?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
