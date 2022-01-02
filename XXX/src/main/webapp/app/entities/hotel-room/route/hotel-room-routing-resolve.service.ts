import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHotelRoom, HotelRoom } from '../hotel-room.model';
import { HotelRoomService } from '../service/hotel-room.service';

@Injectable({ providedIn: 'root' })
export class HotelRoomRoutingResolveService implements Resolve<IHotelRoom> {
  constructor(protected service: HotelRoomService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHotelRoom> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((hotelRoom: HttpResponse<HotelRoom>) => {
          if (hotelRoom.body) {
            return of(hotelRoom.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HotelRoom());
  }
}
