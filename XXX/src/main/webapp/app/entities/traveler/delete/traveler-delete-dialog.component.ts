import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITraveler } from '../traveler.model';
import { TravelerService } from '../service/traveler.service';

@Component({
  templateUrl: './traveler-delete-dialog.component.html',
})
export class TravelerDeleteDialogComponent {
  traveler?: ITraveler;

  constructor(protected travelerService: TravelerService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.travelerService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
