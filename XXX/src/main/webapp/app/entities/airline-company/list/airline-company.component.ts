import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAirlineCompany } from '../airline-company.model';
import { AirlineCompanyService } from '../service/airline-company.service';
import { AirlineCompanyDeleteDialogComponent } from '../delete/airline-company-delete-dialog.component';

@Component({
  selector: 'jhi-airline-company',
  templateUrl: './airline-company.component.html',
})
export class AirlineCompanyComponent implements OnInit {
  airlineCompanies?: IAirlineCompany[];
  isLoading = false;

  constructor(protected airlineCompanyService: AirlineCompanyService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.airlineCompanyService.query().subscribe(
      (res: HttpResponse<IAirlineCompany[]>) => {
        this.isLoading = false;
        this.airlineCompanies = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IAirlineCompany): number {
    return item.id!;
  }

  delete(airlineCompany: IAirlineCompany): void {
    const modalRef = this.modalService.open(AirlineCompanyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.airlineCompany = airlineCompany;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
