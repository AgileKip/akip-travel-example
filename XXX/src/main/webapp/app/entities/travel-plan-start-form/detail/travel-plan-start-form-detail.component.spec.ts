import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TravelPlanStartFormDetailComponent } from './travel-plan-start-form-detail.component';

describe('Component Tests', () => {
  describe('TravelPlanStartForm Management Detail Component', () => {
    let comp: TravelPlanStartFormDetailComponent;
    let fixture: ComponentFixture<TravelPlanStartFormDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TravelPlanStartFormDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ travelPlanStartForm: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TravelPlanStartFormDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TravelPlanStartFormDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load travelPlanStartForm on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.travelPlanStartForm).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
