jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITravelPlan, TravelPlan } from '../travel-plan.model';
import { TravelPlanService } from '../service/travel-plan.service';

import { TravelPlanRoutingResolveService } from './travel-plan-routing-resolve.service';

describe('Service Tests', () => {
  describe('TravelPlan routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TravelPlanRoutingResolveService;
    let service: TravelPlanService;
    let resultTravelPlan: ITravelPlan | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TravelPlanRoutingResolveService);
      service = TestBed.inject(TravelPlanService);
      resultTravelPlan = undefined;
    });

    describe('resolve', () => {
      it('should return ITravelPlan returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTravelPlan = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTravelPlan).toEqual({ id: 123 });
      });

      it('should return new ITravelPlan if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTravelPlan = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTravelPlan).toEqual(new TravelPlan());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTravelPlan = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTravelPlan).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
