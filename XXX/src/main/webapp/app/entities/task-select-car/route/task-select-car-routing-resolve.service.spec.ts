jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITaskSelectCar, TaskSelectCar } from '../task-select-car.model';
import { TaskSelectCarService } from '../service/task-select-car.service';

import { TaskSelectCarRoutingResolveService } from './task-select-car-routing-resolve.service';

describe('Service Tests', () => {
  describe('TaskSelectCar routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TaskSelectCarRoutingResolveService;
    let service: TaskSelectCarService;
    let resultTaskSelectCar: ITaskSelectCar | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TaskSelectCarRoutingResolveService);
      service = TestBed.inject(TaskSelectCarService);
      resultTaskSelectCar = undefined;
    });

    describe('resolve', () => {
      it('should return ITaskSelectCar returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaskSelectCar = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaskSelectCar).toEqual({ id: 123 });
      });

      it('should return new ITaskSelectCar if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaskSelectCar = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTaskSelectCar).toEqual(new TaskSelectCar());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaskSelectCar = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaskSelectCar).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
