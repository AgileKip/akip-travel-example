import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaskSelectHotel } from '../task-select-hotel.model';
import { TaskSelectHotelService } from '../service/task-select-hotel.service';

@Component({
  templateUrl: './task-select-hotel-delete-dialog.component.html',
})
export class TaskSelectHotelDeleteDialogComponent {
  taskSelectHotel?: ITaskSelectHotel;

  constructor(protected taskSelectHotelService: TaskSelectHotelService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taskSelectHotelService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
