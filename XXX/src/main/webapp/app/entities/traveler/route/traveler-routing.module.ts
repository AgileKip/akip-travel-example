import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TravelerComponent } from '../list/traveler.component';
import { TravelerDetailComponent } from '../detail/traveler-detail.component';
import { TravelerUpdateComponent } from '../update/traveler-update.component';
import { TravelerRoutingResolveService } from './traveler-routing-resolve.service';

const travelerRoute: Routes = [
  {
    path: '',
    component: TravelerComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TravelerDetailComponent,
    resolve: {
      traveler: TravelerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TravelerUpdateComponent,
    resolve: {
      traveler: TravelerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TravelerUpdateComponent,
    resolve: {
      traveler: TravelerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(travelerRoute)],
  exports: [RouterModule],
})
export class TravelerRoutingModule {}
