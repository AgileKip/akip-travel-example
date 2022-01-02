import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TaskSelectHotelComponent } from '../list/task-select-hotel.component';
import { TaskSelectHotelDetailComponent } from '../detail/task-select-hotel-detail.component';
import { TaskSelectHotelUpdateComponent } from '../update/task-select-hotel-update.component';
import { TaskSelectHotelRoutingResolveService } from './task-select-hotel-routing-resolve.service';

const taskSelectHotelRoute: Routes = [
  {
    path: '',
    component: TaskSelectHotelComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaskSelectHotelDetailComponent,
    resolve: {
      taskSelectHotel: TaskSelectHotelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaskSelectHotelUpdateComponent,
    resolve: {
      taskSelectHotel: TaskSelectHotelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaskSelectHotelUpdateComponent,
    resolve: {
      taskSelectHotel: TaskSelectHotelRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(taskSelectHotelRoute)],
  exports: [RouterModule],
})
export class TaskSelectHotelRoutingModule {}
