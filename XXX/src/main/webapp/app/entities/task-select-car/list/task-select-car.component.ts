import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaskSelectCar } from '../task-select-car.model';
import { TaskSelectCarService } from '../service/task-select-car.service';
import { TaskSelectCarDeleteDialogComponent } from '../delete/task-select-car-delete-dialog.component';

@Component({
  selector: 'jhi-task-select-car',
  templateUrl: './task-select-car.component.html',
})
export class TaskSelectCarComponent implements OnInit {
  taskSelectCars?: ITaskSelectCar[];
  isLoading = false;

  constructor(protected taskSelectCarService: TaskSelectCarService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.taskSelectCarService.query().subscribe(
      (res: HttpResponse<ITaskSelectCar[]>) => {
        this.isLoading = false;
        this.taskSelectCars = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITaskSelectCar): number {
    return item.id!;
  }

  delete(taskSelectCar: ITaskSelectCar): void {
    const modalRef = this.modalService.open(TaskSelectCarDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taskSelectCar = taskSelectCar;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
