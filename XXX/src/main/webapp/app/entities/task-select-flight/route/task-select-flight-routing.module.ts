import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TaskSelectFlightComponent } from '../list/task-select-flight.component';
import { TaskSelectFlightDetailComponent } from '../detail/task-select-flight-detail.component';
import { TaskSelectFlightUpdateComponent } from '../update/task-select-flight-update.component';
import { TaskSelectFlightRoutingResolveService } from './task-select-flight-routing-resolve.service';

const taskSelectFlightRoute: Routes = [
  {
    path: '',
    component: TaskSelectFlightComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaskSelectFlightDetailComponent,
    resolve: {
      taskSelectFlight: TaskSelectFlightRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaskSelectFlightUpdateComponent,
    resolve: {
      taskSelectFlight: TaskSelectFlightRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaskSelectFlightUpdateComponent,
    resolve: {
      taskSelectFlight: TaskSelectFlightRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(taskSelectFlightRoute)],
  exports: [RouterModule],
})
export class TaskSelectFlightRoutingModule {}
