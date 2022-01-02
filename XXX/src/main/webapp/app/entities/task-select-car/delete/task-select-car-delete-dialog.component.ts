import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaskSelectCar } from '../task-select-car.model';
import { TaskSelectCarService } from '../service/task-select-car.service';

@Component({
  templateUrl: './task-select-car-delete-dialog.component.html',
})
export class TaskSelectCarDeleteDialogComponent {
  taskSelectCar?: ITaskSelectCar;

  constructor(protected taskSelectCarService: TaskSelectCarService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taskSelectCarService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
