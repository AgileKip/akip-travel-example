import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICarRentalCompany } from '../car-rental-company.model';
import { CarRentalCompanyService } from '../service/car-rental-company.service';

@Component({
  templateUrl: './car-rental-company-delete-dialog.component.html',
})
export class CarRentalCompanyDeleteDialogComponent {
  carRentalCompany?: ICarRentalCompany;

  constructor(protected carRentalCompanyService: CarRentalCompanyService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.carRentalCompanyService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
