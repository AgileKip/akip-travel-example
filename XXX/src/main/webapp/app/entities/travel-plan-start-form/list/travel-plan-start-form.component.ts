import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITravelPlanStartForm } from '../travel-plan-start-form.model';
import { TravelPlanStartFormService } from '../service/travel-plan-start-form.service';
import { TravelPlanStartFormDeleteDialogComponent } from '../delete/travel-plan-start-form-delete-dialog.component';

@Component({
  selector: 'jhi-travel-plan-start-form',
  templateUrl: './travel-plan-start-form.component.html',
})
export class TravelPlanStartFormComponent implements OnInit {
  travelPlanStartForms?: ITravelPlanStartForm[];
  isLoading = false;

  constructor(protected travelPlanStartFormService: TravelPlanStartFormService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.travelPlanStartFormService.query().subscribe(
      (res: HttpResponse<ITravelPlanStartForm[]>) => {
        this.isLoading = false;
        this.travelPlanStartForms = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITravelPlanStartForm): number {
    return item.id!;
  }

  delete(travelPlanStartForm: ITravelPlanStartForm): void {
    const modalRef = this.modalService.open(TravelPlanStartFormDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.travelPlanStartForm = travelPlanStartForm;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
