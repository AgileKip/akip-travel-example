import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHotelRoom } from '../hotel-room.model';
import { HotelRoomService } from '../service/hotel-room.service';
import { HotelRoomDeleteDialogComponent } from '../delete/hotel-room-delete-dialog.component';

@Component({
  selector: 'jhi-hotel-room',
  templateUrl: './hotel-room.component.html',
})
export class HotelRoomComponent implements OnInit {
  hotelRooms?: IHotelRoom[];
  isLoading = false;

  constructor(protected hotelRoomService: HotelRoomService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.hotelRoomService.query().subscribe(
      (res: HttpResponse<IHotelRoom[]>) => {
        this.isLoading = false;
        this.hotelRooms = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IHotelRoom): number {
    return item.id!;
  }

  delete(hotelRoom: IHotelRoom): void {
    const modalRef = this.modalService.open(HotelRoomDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.hotelRoom = hotelRoom;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
