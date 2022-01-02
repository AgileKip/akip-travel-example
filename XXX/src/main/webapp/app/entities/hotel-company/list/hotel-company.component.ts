import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHotelCompany } from '../hotel-company.model';
import { HotelCompanyService } from '../service/hotel-company.service';
import { HotelCompanyDeleteDialogComponent } from '../delete/hotel-company-delete-dialog.component';

@Component({
  selector: 'jhi-hotel-company',
  templateUrl: './hotel-company.component.html',
})
export class HotelCompanyComponent implements OnInit {
  hotelCompanies?: IHotelCompany[];
  isLoading = false;

  constructor(protected hotelCompanyService: HotelCompanyService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.hotelCompanyService.query().subscribe(
      (res: HttpResponse<IHotelCompany[]>) => {
        this.isLoading = false;
        this.hotelCompanies = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IHotelCompany): number {
    return item.id!;
  }

  delete(hotelCompany: IHotelCompany): void {
    const modalRef = this.modalService.open(HotelCompanyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.hotelCompany = hotelCompany;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
