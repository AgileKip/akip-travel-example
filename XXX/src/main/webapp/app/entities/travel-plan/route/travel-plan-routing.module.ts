import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TravelPlanComponent } from '../list/travel-plan.component';
import { TravelPlanDetailComponent } from '../detail/travel-plan-detail.component';
import { TravelPlanUpdateComponent } from '../update/travel-plan-update.component';
import { TravelPlanRoutingResolveService } from './travel-plan-routing-resolve.service';

const travelPlanRoute: Routes = [
  {
    path: '',
    component: TravelPlanComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TravelPlanDetailComponent,
    resolve: {
      travelPlan: TravelPlanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TravelPlanUpdateComponent,
    resolve: {
      travelPlan: TravelPlanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TravelPlanUpdateComponent,
    resolve: {
      travelPlan: TravelPlanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(travelPlanRoute)],
  exports: [RouterModule],
})
export class TravelPlanRoutingModule {}
