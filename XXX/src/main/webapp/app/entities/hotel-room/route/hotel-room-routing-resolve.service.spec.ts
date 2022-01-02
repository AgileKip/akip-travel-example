jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IHotelRoom, HotelRoom } from '../hotel-room.model';
import { HotelRoomService } from '../service/hotel-room.service';

import { HotelRoomRoutingResolveService } from './hotel-room-routing-resolve.service';

describe('Service Tests', () => {
  describe('HotelRoom routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: HotelRoomRoutingResolveService;
    let service: HotelRoomService;
    let resultHotelRoom: IHotelRoom | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(HotelRoomRoutingResolveService);
      service = TestBed.inject(HotelRoomService);
      resultHotelRoom = undefined;
    });

    describe('resolve', () => {
      it('should return IHotelRoom returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHotelRoom = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHotelRoom).toEqual({ id: 123 });
      });

      it('should return new IHotelRoom if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHotelRoom = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultHotelRoom).toEqual(new HotelRoom());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHotelRoom = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHotelRoom).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
