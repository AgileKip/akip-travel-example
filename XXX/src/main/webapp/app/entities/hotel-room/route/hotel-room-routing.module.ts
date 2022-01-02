import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HotelRoomComponent } from '../list/hotel-room.component';
import { HotelRoomDetailComponent } from '../detail/hotel-room-detail.component';
import { HotelRoomUpdateComponent } from '../update/hotel-room-update.component';
import { HotelRoomRoutingResolveService } from './hotel-room-routing-resolve.service';

const hotelRoomRoute: Routes = [
  {
    path: '',
    component: HotelRoomComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HotelRoomDetailComponent,
    resolve: {
      hotelRoom: HotelRoomRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HotelRoomUpdateComponent,
    resolve: {
      hotelRoom: HotelRoomRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HotelRoomUpdateComponent,
    resolve: {
      hotelRoom: HotelRoomRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(hotelRoomRoute)],
  exports: [RouterModule],
})
export class HotelRoomRoutingModule {}
