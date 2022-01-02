jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICarRentalCompany, CarRentalCompany } from '../car-rental-company.model';
import { CarRentalCompanyService } from '../service/car-rental-company.service';

import { CarRentalCompanyRoutingResolveService } from './car-rental-company-routing-resolve.service';

describe('Service Tests', () => {
  describe('CarRentalCompany routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CarRentalCompanyRoutingResolveService;
    let service: CarRentalCompanyService;
    let resultCarRentalCompany: ICarRentalCompany | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CarRentalCompanyRoutingResolveService);
      service = TestBed.inject(CarRentalCompanyService);
      resultCarRentalCompany = undefined;
    });

    describe('resolve', () => {
      it('should return ICarRentalCompany returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCarRentalCompany = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCarRentalCompany).toEqual({ id: 123 });
      });

      it('should return new ICarRentalCompany if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCarRentalCompany = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCarRentalCompany).toEqual(new CarRentalCompany());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCarRentalCompany = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCarRentalCompany).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
