import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaskSelectFlightDetailComponent } from './task-select-flight-detail.component';

describe('Component Tests', () => {
  describe('TaskSelectFlight Management Detail Component', () => {
    let comp: TaskSelectFlightDetailComponent;
    let fixture: ComponentFixture<TaskSelectFlightDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TaskSelectFlightDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ taskSelectFlight: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TaskSelectFlightDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskSelectFlightDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taskSelectFlight on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taskSelectFlight).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
