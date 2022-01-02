import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TaskSelectCarComponent } from '../list/task-select-car.component';
import { TaskSelectCarDetailComponent } from '../detail/task-select-car-detail.component';
import { TaskSelectCarUpdateComponent } from '../update/task-select-car-update.component';
import { TaskSelectCarRoutingResolveService } from './task-select-car-routing-resolve.service';

const taskSelectCarRoute: Routes = [
  {
    path: '',
    component: TaskSelectCarComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaskSelectCarDetailComponent,
    resolve: {
      taskSelectCar: TaskSelectCarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaskSelectCarUpdateComponent,
    resolve: {
      taskSelectCar: TaskSelectCarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaskSelectCarUpdateComponent,
    resolve: {
      taskSelectCar: TaskSelectCarRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(taskSelectCarRoute)],
  exports: [RouterModule],
})
export class TaskSelectCarRoutingModule {}
