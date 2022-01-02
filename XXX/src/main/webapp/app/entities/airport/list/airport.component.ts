import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAirport } from '../airport.model';
import { AirportService } from '../service/airport.service';
import { AirportDeleteDialogComponent } from '../delete/airport-delete-dialog.component';

@Component({
  selector: 'jhi-airport',
  templateUrl: './airport.component.html',
})
export class AirportComponent implements OnInit {
  airports?: IAirport[];
  isLoading = false;

  constructor(protected airportService: AirportService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.airportService.query().subscribe(
      (res: HttpResponse<IAirport[]>) => {
        this.isLoading = false;
        this.airports = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IAirport): number {
    return item.id!;
  }

  delete(airport: IAirport): void {
    const modalRef = this.modalService.open(AirportDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.airport = airport;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
