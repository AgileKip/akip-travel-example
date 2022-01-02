import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHotelRoom } from '../hotel-room.model';
import { HotelRoomService } from '../service/hotel-room.service';

@Component({
  templateUrl: './hotel-room-delete-dialog.component.html',
})
export class HotelRoomDeleteDialogComponent {
  hotelRoom?: IHotelRoom;

  constructor(protected hotelRoomService: HotelRoomService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hotelRoomService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
