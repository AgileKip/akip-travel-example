import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICarRentalCompany } from '../car-rental-company.model';
import { CarRentalCompanyService } from '../service/car-rental-company.service';
import { CarRentalCompanyDeleteDialogComponent } from '../delete/car-rental-company-delete-dialog.component';

@Component({
  selector: 'jhi-car-rental-company',
  templateUrl: './car-rental-company.component.html',
})
export class CarRentalCompanyComponent implements OnInit {
  carRentalCompanies?: ICarRentalCompany[];
  isLoading = false;

  constructor(protected carRentalCompanyService: CarRentalCompanyService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.carRentalCompanyService.query().subscribe(
      (res: HttpResponse<ICarRentalCompany[]>) => {
        this.isLoading = false;
        this.carRentalCompanies = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ICarRentalCompany): number {
    return item.id!;
  }

  delete(carRentalCompany: ICarRentalCompany): void {
    const modalRef = this.modalService.open(CarRentalCompanyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.carRentalCompany = carRentalCompany;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
