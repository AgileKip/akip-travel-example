import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITravelPlanStartForm, TravelPlanStartForm } from '../travel-plan-start-form.model';
import { TravelPlanStartFormService } from '../service/travel-plan-start-form.service';

@Injectable({ providedIn: 'root' })
export class TravelPlanStartFormRoutingResolveService implements Resolve<ITravelPlanStartForm> {
  constructor(protected service: TravelPlanStartFormService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITravelPlanStartForm> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((travelPlanStartForm: HttpResponse<TravelPlanStartForm>) => {
          if (travelPlanStartForm.body) {
            return of(travelPlanStartForm.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TravelPlanStartForm());
  }
}
