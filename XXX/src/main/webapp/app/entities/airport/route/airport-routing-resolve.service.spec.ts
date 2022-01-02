jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAirport, Airport } from '../airport.model';
import { AirportService } from '../service/airport.service';

import { AirportRoutingResolveService } from './airport-routing-resolve.service';

describe('Service Tests', () => {
  describe('Airport routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AirportRoutingResolveService;
    let service: AirportService;
    let resultAirport: IAirport | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AirportRoutingResolveService);
      service = TestBed.inject(AirportService);
      resultAirport = undefined;
    });

    describe('resolve', () => {
      it('should return IAirport returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAirport = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAirport).toEqual({ id: 123 });
      });

      it('should return new IAirport if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAirport = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAirport).toEqual(new Airport());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAirport = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAirport).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
