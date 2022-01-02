import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAirlineCompany, AirlineCompany } from '../airline-company.model';
import { AirlineCompanyService } from '../service/airline-company.service';

@Injectable({ providedIn: 'root' })
export class AirlineCompanyRoutingResolveService implements Resolve<IAirlineCompany> {
  constructor(protected service: AirlineCompanyService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAirlineCompany> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((airlineCompany: HttpResponse<AirlineCompany>) => {
          if (airlineCompany.body) {
            return of(airlineCompany.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AirlineCompany());
  }
}
