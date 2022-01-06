import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IX1 } from '../x-1.model';
import { X1Service } from '../service/x-1.service';

@Component({
  templateUrl: './x-1-delete-dialog.component.html',
})
export class X1DeleteDialogComponent {
  x1?: IX1;

  constructor(protected x1Service: X1Service, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.x1Service.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
