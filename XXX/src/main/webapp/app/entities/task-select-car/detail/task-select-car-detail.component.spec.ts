import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaskSelectCarDetailComponent } from './task-select-car-detail.component';

describe('Component Tests', () => {
  describe('TaskSelectCar Management Detail Component', () => {
    let comp: TaskSelectCarDetailComponent;
    let fixture: ComponentFixture<TaskSelectCarDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TaskSelectCarDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ taskSelectCar: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TaskSelectCarDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskSelectCarDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taskSelectCar on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taskSelectCar).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
