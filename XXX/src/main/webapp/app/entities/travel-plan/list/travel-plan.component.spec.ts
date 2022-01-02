import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TravelPlanService } from '../service/travel-plan.service';

import { TravelPlanComponent } from './travel-plan.component';

describe('Component Tests', () => {
  describe('TravelPlan Management Component', () => {
    let comp: TravelPlanComponent;
    let fixture: ComponentFixture<TravelPlanComponent>;
    let service: TravelPlanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TravelPlanComponent],
      })
        .overrideTemplate(TravelPlanComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TravelPlanComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TravelPlanService);

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
      expect(comp.travelPlans?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
