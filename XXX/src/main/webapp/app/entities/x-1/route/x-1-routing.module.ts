import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { X1Component } from '../list/x-1.component';
import { X1DetailComponent } from '../detail/x-1-detail.component';
import { X1UpdateComponent } from '../update/x-1-update.component';
import { X1RoutingResolveService } from './x-1-routing-resolve.service';

const x1Route: Routes = [
  {
    path: '',
    component: X1Component,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: X1DetailComponent,
    resolve: {
      x1: X1RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: X1UpdateComponent,
    resolve: {
      x1: X1RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: X1UpdateComponent,
    resolve: {
      x1: X1RoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(x1Route)],
  exports: [RouterModule],
})
export class X1RoutingModule {}
