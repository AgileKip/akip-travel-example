import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHotelRoom } from '../hotel-room.model';

@Component({
  selector: 'jhi-hotel-room-detail',
  templateUrl: './hotel-room-detail.component.html',
})
export class HotelRoomDetailComponent implements OnInit {
  hotelRoom: IHotelRoom | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hotelRoom }) => {
      this.hotelRoom = hotelRoom;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
