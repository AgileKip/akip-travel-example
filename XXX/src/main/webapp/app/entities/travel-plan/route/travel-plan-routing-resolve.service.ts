import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITravelPlan, TravelPlan } from '../travel-plan.model';
import { TravelPlanService } from '../service/travel-plan.service';

@Injectable({ providedIn: 'root' })
export class TravelPlanRoutingResolveService implements Resolve<ITravelPlan> {
  constructor(protected service: TravelPlanService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITravelPlan> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((travelPlan: HttpResponse<TravelPlan>) => {
          if (travelPlan.body) {
            return of(travelPlan.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TravelPlan());
  }
}
