import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITraveler, Traveler } from '../traveler.model';
import { TravelerService } from '../service/traveler.service';

@Injectable({ providedIn: 'root' })
export class TravelerRoutingResolveService implements Resolve<ITraveler> {
  constructor(protected service: TravelerService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITraveler> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((traveler: HttpResponse<Traveler>) => {
          if (traveler.body) {
            return of(traveler.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Traveler());
  }
}
