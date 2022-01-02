import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITraveler } from '../traveler.model';
import { TravelerService } from '../service/traveler.service';
import { TravelerDeleteDialogComponent } from '../delete/traveler-delete-dialog.component';

@Component({
  selector: 'jhi-traveler',
  templateUrl: './traveler.component.html',
})
export class TravelerComponent implements OnInit {
  travelers?: ITraveler[];
  isLoading = false;

  constructor(protected travelerService: TravelerService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.travelerService.query().subscribe(
      (res: HttpResponse<ITraveler[]>) => {
        this.isLoading = false;
        this.travelers = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITraveler): number {
    return item.id!;
  }

  delete(traveler: ITraveler): void {
    const modalRef = this.modalService.open(TravelerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.traveler = traveler;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
