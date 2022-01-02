import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaskSelectCar } from '../task-select-car.model';

@Component({
  selector: 'jhi-task-select-car-detail',
  templateUrl: './task-select-car-detail.component.html',
})
export class TaskSelectCarDetailComponent implements OnInit {
  taskSelectCar: ITaskSelectCar | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskSelectCar }) => {
      this.taskSelectCar = taskSelectCar;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
