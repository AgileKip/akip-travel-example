import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CarRentalCompanyComponent } from '../list/car-rental-company.component';
import { CarRentalCompanyDetailComponent } from '../detail/car-rental-company-detail.component';
import { CarRentalCompanyUpdateComponent } from '../update/car-rental-company-update.component';
import { CarRentalCompanyRoutingResolveService } from './car-rental-company-routing-resolve.service';

const carRentalCompanyRoute: Routes = [
  {
    path: '',
    component: CarRentalCompanyComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CarRentalCompanyDetailComponent,
    resolve: {
      carRentalCompany: CarRentalCompanyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CarRentalCompanyUpdateComponent,
    resolve: {
      carRentalCompany: CarRentalCompanyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CarRentalCompanyUpdateComponent,
    resolve: {
      carRentalCompany: CarRentalCompanyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(carRentalCompanyRoute)],
  exports: [RouterModule],
})
export class CarRentalCompanyRoutingModule {}
