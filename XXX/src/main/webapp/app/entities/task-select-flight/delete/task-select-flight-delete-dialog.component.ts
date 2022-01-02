import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaskSelectFlight } from '../task-select-flight.model';
import { TaskSelectFlightService } from '../service/task-select-flight.service';

@Component({
  templateUrl: './task-select-flight-delete-dialog.component.html',
})
export class TaskSelectFlightDeleteDialogComponent {
  taskSelectFlight?: ITaskSelectFlight;

  constructor(protected taskSelectFlightService: TaskSelectFlightService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taskSelectFlightService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
