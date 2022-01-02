jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAirlineCompany, AirlineCompany } from '../airline-company.model';
import { AirlineCompanyService } from '../service/airline-company.service';

import { AirlineCompanyRoutingResolveService } from './airline-company-routing-resolve.service';

describe('Service Tests', () => {
  describe('AirlineCompany routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AirlineCompanyRoutingResolveService;
    let service: AirlineCompanyService;
    let resultAirlineCompany: IAirlineCompany | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AirlineCompanyRoutingResolveService);
      service = TestBed.inject(AirlineCompanyService);
      resultAirlineCompany = undefined;
    });

    describe('resolve', () => {
      it('should return IAirlineCompany returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAirlineCompany = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAirlineCompany).toEqual({ id: 123 });
      });

      it('should return new IAirlineCompany if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAirlineCompany = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAirlineCompany).toEqual(new AirlineCompany());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAirlineCompany = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAirlineCompany).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
