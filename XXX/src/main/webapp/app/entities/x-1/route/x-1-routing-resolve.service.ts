import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IX1, X1 } from '../x-1.model';
import { X1Service } from '../service/x-1.service';

@Injectable({ providedIn: 'root' })
export class X1RoutingResolveService implements Resolve<IX1> {
  constructor(protected service: X1Service, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IX1> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((x1: HttpResponse<X1>) => {
          if (x1.body) {
            return of(x1.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new X1());
  }
}
