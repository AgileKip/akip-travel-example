jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITravelPlanStartForm, TravelPlanStartForm } from '../travel-plan-start-form.model';
import { TravelPlanStartFormService } from '../service/travel-plan-start-form.service';

import { TravelPlanStartFormRoutingResolveService } from './travel-plan-start-form-routing-resolve.service';

describe('Service Tests', () => {
  describe('TravelPlanStartForm routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TravelPlanStartFormRoutingResolveService;
    let service: TravelPlanStartFormService;
    let resultTravelPlanStartForm: ITravelPlanStartForm | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TravelPlanStartFormRoutingResolveService);
      service = TestBed.inject(TravelPlanStartFormService);
      resultTravelPlanStartForm = undefined;
    });

    describe('resolve', () => {
      it('should return ITravelPlanStartForm returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTravelPlanStartForm = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTravelPlanStartForm).toEqual({ id: 123 });
      });

      it('should return new ITravelPlanStartForm if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTravelPlanStartForm = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTravelPlanStartForm).toEqual(new TravelPlanStartForm());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTravelPlanStartForm = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTravelPlanStartForm).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
