import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TravelPlanDetailComponent } from './travel-plan-detail.component';

describe('Component Tests', () => {
  describe('TravelPlan Management Detail Component', () => {
    let comp: TravelPlanDetailComponent;
    let fixture: ComponentFixture<TravelPlanDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TravelPlanDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ travelPlan: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TravelPlanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TravelPlanDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load travelPlan on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.travelPlan).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
