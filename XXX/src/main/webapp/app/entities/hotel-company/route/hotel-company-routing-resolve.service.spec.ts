jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IHotelCompany, HotelCompany } from '../hotel-company.model';
import { HotelCompanyService } from '../service/hotel-company.service';

import { HotelCompanyRoutingResolveService } from './hotel-company-routing-resolve.service';

describe('Service Tests', () => {
  describe('HotelCompany routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: HotelCompanyRoutingResolveService;
    let service: HotelCompanyService;
    let resultHotelCompany: IHotelCompany | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(HotelCompanyRoutingResolveService);
      service = TestBed.inject(HotelCompanyService);
      resultHotelCompany = undefined;
    });

    describe('resolve', () => {
      it('should return IHotelCompany returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHotelCompany = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHotelCompany).toEqual({ id: 123 });
      });

      it('should return new IHotelCompany if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHotelCompany = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultHotelCompany).toEqual(new HotelCompany());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHotelCompany = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHotelCompany).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
