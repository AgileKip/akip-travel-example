import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaskSelectFlight } from '../task-select-flight.model';

@Component({
  selector: 'jhi-task-select-flight-detail',
  templateUrl: './task-select-flight-detail.component.html',
})
export class TaskSelectFlightDetailComponent implements OnInit {
  taskSelectFlight: ITaskSelectFlight | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskSelectFlight }) => {
      this.taskSelectFlight = taskSelectFlight;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
