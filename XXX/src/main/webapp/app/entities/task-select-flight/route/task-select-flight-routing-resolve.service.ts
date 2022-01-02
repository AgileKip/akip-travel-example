import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITaskSelectFlight, TaskSelectFlight } from '../task-select-flight.model';
import { TaskSelectFlightService } from '../service/task-select-flight.service';

@Injectable({ providedIn: 'root' })
export class TaskSelectFlightRoutingResolveService implements Resolve<ITaskSelectFlight> {
  constructor(protected service: TaskSelectFlightService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaskSelectFlight> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((taskSelectFlight: HttpResponse<TaskSelectFlight>) => {
          if (taskSelectFlight.body) {
            return of(taskSelectFlight.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaskSelectFlight());
  }
}
