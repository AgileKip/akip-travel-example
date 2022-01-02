jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITraveler, Traveler } from '../traveler.model';
import { TravelerService } from '../service/traveler.service';

import { TravelerRoutingResolveService } from './traveler-routing-resolve.service';

describe('Service Tests', () => {
  describe('Traveler routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TravelerRoutingResolveService;
    let service: TravelerService;
    let resultTraveler: ITraveler | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TravelerRoutingResolveService);
      service = TestBed.inject(TravelerService);
      resultTraveler = undefined;
    });

    describe('resolve', () => {
      it('should return ITraveler returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTraveler = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTraveler).toEqual({ id: 123 });
      });

      it('should return new ITraveler if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTraveler = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTraveler).toEqual(new Traveler());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTraveler = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTraveler).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
