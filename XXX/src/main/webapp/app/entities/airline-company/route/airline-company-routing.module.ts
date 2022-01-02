import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AirlineCompanyComponent } from '../list/airline-company.component';
import { AirlineCompanyDetailComponent } from '../detail/airline-company-detail.component';
import { AirlineCompanyUpdateComponent } from '../update/airline-company-update.component';
import { AirlineCompanyRoutingResolveService } from './airline-company-routing-resolve.service';

const airlineCompanyRoute: Routes = [
  {
    path: '',
    component: AirlineCompanyComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AirlineCompanyDetailComponent,
    resolve: {
      airlineCompany: AirlineCompanyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AirlineCompanyUpdateComponent,
    resolve: {
      airlineCompany: AirlineCompanyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AirlineCompanyUpdateComponent,
    resolve: {
      airlineCompany: AirlineCompanyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(airlineCompanyRoute)],
  exports: [RouterModule],
})
export class AirlineCompanyRoutingModule {}
