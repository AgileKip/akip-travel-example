import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITaskSelectHotel, TaskSelectHotel } from '../task-select-hotel.model';
import { TaskSelectHotelService } from '../service/task-select-hotel.service';

@Injectable({ providedIn: 'root' })
export class TaskSelectHotelRoutingResolveService implements Resolve<ITaskSelectHotel> {
  constructor(protected service: TaskSelectHotelService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaskSelectHotel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((taskSelectHotel: HttpResponse<TaskSelectHotel>) => {
          if (taskSelectHotel.body) {
            return of(taskSelectHotel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaskSelectHotel());
  }
}
