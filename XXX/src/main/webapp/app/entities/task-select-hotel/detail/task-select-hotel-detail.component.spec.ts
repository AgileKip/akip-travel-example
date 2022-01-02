import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaskSelectHotelDetailComponent } from './task-select-hotel-detail.component';

describe('Component Tests', () => {
  describe('TaskSelectHotel Management Detail Component', () => {
    let comp: TaskSelectHotelDetailComponent;
    let fixture: ComponentFixture<TaskSelectHotelDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TaskSelectHotelDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ taskSelectHotel: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TaskSelectHotelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskSelectHotelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taskSelectHotel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taskSelectHotel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
