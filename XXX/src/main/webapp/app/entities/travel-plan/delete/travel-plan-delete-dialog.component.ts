import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITravelPlan } from '../travel-plan.model';
import { TravelPlanService } from '../service/travel-plan.service';

@Component({
  templateUrl: './travel-plan-delete-dialog.component.html',
})
export class TravelPlanDeleteDialogComponent {
  travelPlan?: ITravelPlan;

  constructor(protected travelPlanService: TravelPlanService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.travelPlanService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
