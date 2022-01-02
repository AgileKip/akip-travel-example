import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITravelPlanStartForm } from '../travel-plan-start-form.model';
import { TravelPlanStartFormService } from '../service/travel-plan-start-form.service';

@Component({
  templateUrl: './travel-plan-start-form-delete-dialog.component.html',
})
export class TravelPlanStartFormDeleteDialogComponent {
  travelPlanStartForm?: ITravelPlanStartForm;

  constructor(protected travelPlanStartFormService: TravelPlanStartFormService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.travelPlanStartFormService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
