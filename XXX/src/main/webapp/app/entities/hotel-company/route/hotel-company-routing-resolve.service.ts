import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHotelCompany, HotelCompany } from '../hotel-company.model';
import { HotelCompanyService } from '../service/hotel-company.service';

@Injectable({ providedIn: 'root' })
export class HotelCompanyRoutingResolveService implements Resolve<IHotelCompany> {
  constructor(protected service: HotelCompanyService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHotelCompany> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((hotelCompany: HttpResponse<HotelCompany>) => {
          if (hotelCompany.body) {
            return of(hotelCompany.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HotelCompany());
  }
}
