jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFlight, Flight } from '../flight.model';
import { FlightService } from '../service/flight.service';

import { FlightRoutingResolveService } from './flight-routing-resolve.service';

describe('Service Tests', () => {
  describe('Flight routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: FlightRoutingResolveService;
    let service: FlightService;
    let resultFlight: IFlight | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(FlightRoutingResolveService);
      service = TestBed.inject(FlightService);
      resultFlight = undefined;
    });

    describe('resolve', () => {
      it('should return IFlight returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFlight = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFlight).toEqual({ id: 123 });
      });

      it('should return new IFlight if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFlight = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultFlight).toEqual(new Flight());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFlight = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFlight).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
