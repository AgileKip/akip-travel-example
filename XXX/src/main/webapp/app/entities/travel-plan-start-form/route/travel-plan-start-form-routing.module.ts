import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TravelPlanStartFormComponent } from '../list/travel-plan-start-form.component';
import { TravelPlanStartFormDetailComponent } from '../detail/travel-plan-start-form-detail.component';
import { TravelPlanStartFormUpdateComponent } from '../update/travel-plan-start-form-update.component';
import { TravelPlanStartFormRoutingResolveService } from './travel-plan-start-form-routing-resolve.service';

const travelPlanStartFormRoute: Routes = [
  {
    path: '',
    component: TravelPlanStartFormComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TravelPlanStartFormDetailComponent,
    resolve: {
      travelPlanStartForm: TravelPlanStartFormRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TravelPlanStartFormUpdateComponent,
    resolve: {
      travelPlanStartForm: TravelPlanStartFormRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TravelPlanStartFormUpdateComponent,
    resolve: {
      travelPlanStartForm: TravelPlanStartFormRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(travelPlanStartFormRoute)],
  exports: [RouterModule],
})
export class TravelPlanStartFormRoutingModule {}
