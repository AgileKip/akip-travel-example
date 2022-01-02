import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { HotelRoomComponent } from './list/hotel-room.component';
import { HotelRoomDetailComponent } from './detail/hotel-room-detail.component';
import { HotelRoomUpdateComponent } from './update/hotel-room-update.component';
import { HotelRoomDeleteDialogComponent } from './delete/hotel-room-delete-dialog.component';
import { HotelRoomRoutingModule } from './route/hotel-room-routing.module';

@NgModule({
  imports: [SharedModule, HotelRoomRoutingModule],
  declarations: [HotelRoomComponent, HotelRoomDetailComponent, HotelRoomUpdateComponent, HotelRoomDeleteDialogComponent],
  entryComponents: [HotelRoomDeleteDialogComponent],
})
export class HotelRoomModule {}
