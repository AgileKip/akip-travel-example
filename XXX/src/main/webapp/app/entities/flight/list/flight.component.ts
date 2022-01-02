import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFlight } from '../flight.model';
import { FlightService } from '../service/flight.service';
import { FlightDeleteDialogComponent } from '../delete/flight-delete-dialog.component';

@Component({
  selector: 'jhi-flight',
  templateUrl: './flight.component.html',
})
export class FlightComponent implements OnInit {
  flights?: IFlight[];
  isLoading = false;

  constructor(protected flightService: FlightService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.flightService.query().subscribe(
      (res: HttpResponse<IFlight[]>) => {
        this.isLoading = false;
        this.flights = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IFlight): number {
    return item.id!;
  }

  delete(flight: IFlight): void {
    const modalRef = this.modalService.open(FlightDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.flight = flight;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
