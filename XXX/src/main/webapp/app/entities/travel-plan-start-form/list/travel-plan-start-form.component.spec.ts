import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TravelPlanStartFormService } from '../service/travel-plan-start-form.service';

import { TravelPlanStartFormComponent } from './travel-plan-start-form.component';

describe('Component Tests', () => {
  describe('TravelPlanStartForm Management Component', () => {
    let comp: TravelPlanStartFormComponent;
    let fixture: ComponentFixture<TravelPlanStartFormComponent>;
    let service: TravelPlanStartFormService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TravelPlanStartFormComponent],
      })
        .overrideTemplate(TravelPlanStartFormComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TravelPlanStartFormComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TravelPlanStartFormService);

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
      expect(comp.travelPlanStartForms?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
