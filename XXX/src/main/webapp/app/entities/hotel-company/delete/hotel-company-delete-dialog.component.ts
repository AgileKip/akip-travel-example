import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHotelCompany } from '../hotel-company.model';
import { HotelCompanyService } from '../service/hotel-company.service';

@Component({
  templateUrl: './hotel-company-delete-dialog.component.html',
})
export class HotelCompanyDeleteDialogComponent {
  hotelCompany?: IHotelCompany;

  constructor(protected hotelCompanyService: HotelCompanyService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hotelCompanyService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
