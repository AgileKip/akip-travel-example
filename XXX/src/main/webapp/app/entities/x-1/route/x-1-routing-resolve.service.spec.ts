jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IX1, X1 } from '../x-1.model';
import { X1Service } from '../service/x-1.service';

import { X1RoutingResolveService } from './x-1-routing-resolve.service';

describe('Service Tests', () => {
  describe('X1 routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: X1RoutingResolveService;
    let service: X1Service;
    let resultX1: IX1 | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(X1RoutingResolveService);
      service = TestBed.inject(X1Service);
      resultX1 = undefined;
    });

    describe('resolve', () => {
      it('should return IX1 returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultX1 = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultX1).toEqual({ id: 123 });
      });

      it('should return new IX1 if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultX1 = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultX1).toEqual(new X1());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultX1 = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultX1).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
