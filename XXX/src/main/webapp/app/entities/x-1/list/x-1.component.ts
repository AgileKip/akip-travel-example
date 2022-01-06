import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IX1 } from '../x-1.model';
import { X1Service } from '../service/x-1.service';
import { X1DeleteDialogComponent } from '../delete/x-1-delete-dialog.component';

@Component({
  selector: 'jhi-x-1',
  templateUrl: './x-1.component.html',
})
export class X1Component implements OnInit {
  x1s?: IX1[];
  isLoading = false;

  constructor(protected x1Service: X1Service, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.x1Service.query().subscribe(
      (res: HttpResponse<IX1[]>) => {
        this.isLoading = false;
        this.x1s = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IX1): number {
    return item.id!;
  }

  delete(x1: IX1): void {
    const modalRef = this.modalService.open(X1DeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.x1 = x1;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
