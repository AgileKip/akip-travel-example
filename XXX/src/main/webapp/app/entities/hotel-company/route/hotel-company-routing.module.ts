import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HotelCompanyComponent } from '../list/hotel-company.component';
import { HotelCompanyDetailComponent } from '../detail/hotel-company-detail.component';
import { HotelCompanyUpdateComponent } from '../update/hotel-company-update.component';
import { HotelCompanyRoutingResolveService } from './hotel-company-routing-resolve.service';

const hotelCompanyRoute: Routes = [
  {
    path: '',
    component: HotelCompanyComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HotelCompanyDetailComponent,
    resolve: {
      hotelCompany: HotelCompanyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HotelCompanyUpdateComponent,
    resolve: {
      hotelCompany: HotelCompanyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HotelCompanyUpdateComponent,
    resolve: {
      hotelCompany: HotelCompanyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(hotelCompanyRoute)],
  exports: [RouterModule],
})
export class HotelCompanyRoutingModule {}
