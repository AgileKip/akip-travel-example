import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITaskSelectCar, TaskSelectCar } from '../task-select-car.model';
import { TaskSelectCarService } from '../service/task-select-car.service';

@Injectable({ providedIn: 'root' })
export class TaskSelectCarRoutingResolveService implements Resolve<ITaskSelectCar> {
  constructor(protected service: TaskSelectCarService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaskSelectCar> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((taskSelectCar: HttpResponse<TaskSelectCar>) => {
          if (taskSelectCar.body) {
            return of(taskSelectCar.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaskSelectCar());
  }
}
