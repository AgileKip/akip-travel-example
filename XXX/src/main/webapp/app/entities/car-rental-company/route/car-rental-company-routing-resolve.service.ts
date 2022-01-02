import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICarRentalCompany, CarRentalCompany } from '../car-rental-company.model';
import { CarRentalCompanyService } from '../service/car-rental-company.service';

@Injectable({ providedIn: 'root' })
export class CarRentalCompanyRoutingResolveService implements Resolve<ICarRentalCompany> {
  constructor(protected service: CarRentalCompanyService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICarRentalCompany> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((carRentalCompany: HttpResponse<CarRentalCompany>) => {
          if (carRentalCompany.body) {
            return of(carRentalCompany.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CarRentalCompany());
  }
}
