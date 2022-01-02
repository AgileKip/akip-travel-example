import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaskSelectHotel } from '../task-select-hotel.model';

@Component({
  selector: 'jhi-task-select-hotel-detail',
  templateUrl: './task-select-hotel-detail.component.html',
})
export class TaskSelectHotelDetailComponent implements OnInit {
  taskSelectHotel: ITaskSelectHotel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskSelectHotel }) => {
      this.taskSelectHotel = taskSelectHotel;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
