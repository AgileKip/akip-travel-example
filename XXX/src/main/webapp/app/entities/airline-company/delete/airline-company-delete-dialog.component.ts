import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAirlineCompany } from '../airline-company.model';
import { AirlineCompanyService } from '../service/airline-company.service';

@Component({
  templateUrl: './airline-company-delete-dialog.component.html',
})
export class AirlineCompanyDeleteDialogComponent {
  airlineCompany?: IAirlineCompany;

  constructor(protected airlineCompanyService: AirlineCompanyService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.airlineCompanyService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
